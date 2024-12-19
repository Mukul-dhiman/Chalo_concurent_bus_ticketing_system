package chalo.com.system.trip;

import chalo.com.system.Booking.Ticket;
import chalo.com.system.baseObjects.Vehicle;
import chalo.com.system.baseObjects.station;
import chalo.com.system.route.Route;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

import static chalo.com.system.Booking.TicketStatus.*;

public class Trip {
    private String name;
    private Vehicle vehicle;
    private Route route;
    private List<Integer> stationCapacity;
    private static final ReentrantLock lock = new ReentrantLock();
    private int noOFStations;
    Queue<Ticket> TicketingQueue;
    boolean TicketAvailable = true;
    private Map<station, ArrayList<Ticket>> passengerMap;
    Set<Integer> AllTicketIds;

    public Trip(String name, Vehicle vehicle, Route route){
        this.name = name;
        this.vehicle = vehicle;
        this.route = route;
        int maxCap = vehicle.GetMaxCapcity();
        int noOfStations = route.getNumberOfStation();
        stationCapacity = new ArrayList<>(noOfStations);
        this.noOFStations = noOfStations;
        this.TicketingQueue = new ConcurrentLinkedQueue<>();
        passengerMap = new HashMap<>();
        AllTicketIds = new HashSet<>();

        for(int i=0;i<noOfStations;i++){
            stationCapacity.add(maxCap);
        }
    }

    public Route getTripRoute(){
        return this.route;
    }

    private boolean isTicketValid(Ticket ticket){
        station source = ticket.getSource();
        station destination = ticket.getDestination();

        if(!(getTripRoute().getRoute().contains(source)) && getTripRoute().getRoute().contains(destination)){
            return false;
        }

        int indexOfSource = getTripRoute().getRoute().indexOf(source);
        int indexofDestination = getTripRoute().getRoute().indexOf(destination);

        return indexOfSource < indexofDestination;
    }

    private void checkAvailablity(){
        if(!TicketAvailable){
            return;
        }
        for(int i=0;i<this.noOFStations;i++){
            if(stationCapacity.get(i)!=0){
                return;
            }
        }
        TicketAvailable = false;
    }

    private boolean isBookingPossible(Ticket ticket){
        station source = ticket.getSource();
        station destination = ticket.getDestination();
        int seats = ticket.getNoOfSeats();

        int indexOfSource = getTripRoute().getRoute().indexOf(source);
        int indexofDestination = getTripRoute().getRoute().indexOf(destination);

        for(int i=indexOfSource;i<=indexofDestination;i++){
            if(stationCapacity.get(i) < seats){
                ticket.UpdateTicketStatus(NoConfirmed);
                return false;
            }
        }
        return true;
    }

    private void finalizeTicket(Ticket ticket){
        station source = ticket.getSource();
        station destination = ticket.getDestination();
        int seats = ticket.getNoOfSeats();
        int currentCap;

        int indexOfSource = getTripRoute().getRoute().indexOf(source);
        int indexofDestination = getTripRoute().getRoute().indexOf(destination);

        for(int i=indexOfSource;i<indexofDestination;i++){
            currentCap = stationCapacity.get(i) - seats;

            passengerMap.computeIfAbsent(getTripRoute().getRoute().get(i), k -> new ArrayList<>()).addAll(Collections.nCopies(seats, ticket));

            stationCapacity.set(i,currentCap);
        }
        ticket.UpdateTicketStatus(Confirmed);
    }

    private void checkGrantTicket(Ticket ticket){
        if(isBookingPossible(ticket)){
            finalizeTicket(ticket);
        }
    }

    private void GrantTicket(){
        lock.lock();
        try{
            while (!TicketingQueue.isEmpty()){
                checkAvailablity();
                if(TicketAvailable){
                    checkGrantTicket(TicketingQueue.poll());
                }
                else{
                    break;
                }
            }
        }
        finally {
            lock.unlock();
        }
    }

    public void bookTicket(Ticket ticket){
        if(!isTicketValid(ticket)){
            ticket.UpdateTicketStatus(InvalidTicket);
            System.out.println("invalid ticket "+ticket.getTicketid()+" from "+ticket.getSource().getName()+" to "+ticket.getDestination().getName());
            return;
        }
        if(!TicketAvailable){
            System.out.println("No seat Available for ticket "+ticket.getTicketid());
            return;
        }

        ticket.UpdateTicketStatus(Pending);

        TicketingQueue.offer(ticket);
        GrantTicket();
    }

    public void DisplayPassengerMap(){
        station curr_station;
        ArrayList<Ticket> passengers;
        Ticket curr_ticket;
        for(int i=0;i< route.getNumberOfStation();i++){
            curr_station = route.getRoute().get(i);
            if(!passengerMap.containsKey(curr_station)){
                System.out.println(curr_station.getName() + " -> 0 []");
                continue;
            }
            passengers = passengerMap.get(curr_station);

            System.out.print(curr_station.getName() + " -> "+passengers.size()+" [");
            for (Ticket passenger : passengers) {
                curr_ticket = passenger;
                System.out.print(curr_ticket.getTicketid() + "-" + curr_ticket.getTraveler().getName() + ", ");
            }
            System.out.println("]");
        }
    }

    public Set<Integer> getAllTicketIds(){
        return this.AllTicketIds;
    }

    public String getTripName(){
        return this.name;
    }
}
