package chalo.com.system.Booking;

import chalo.com.system.baseObjects.Traveler;
import chalo.com.system.baseObjects.station;
import chalo.com.system.trip.Trip;

import java.util.Random;
import java.util.Set;

import static chalo.com.system.Booking.TicketStatus.NoConfirmed;

public class Ticket {
    Traveler traveler;
    Trip trip;
    TicketStatus status;
    station source;
    station destination;
    int noOfSeats;
    int ticketid;


    public Ticket(Traveler traveler, Trip trip, station start, station end, int noOfSeats){
        this.traveler = traveler;
        this.trip = trip;
        this.source = start;
        this.destination = end;
        this.noOfSeats = noOfSeats;
        this.status = NoConfirmed;
        this.ticketid = generateUniqeId();

        System.out.println("current status of ticket "+ticketid+" for "+traveler.getName()+" for "+getNoOfSeats()+" seats is "+status);
        bookTicket();
    }

    private int generateUniqeId(){
        Set<Integer> AllTicketIds = trip.getAllTicketIds();
        Random random = new Random();
        while(true){
            int id = random.nextInt(1000000);
            if(!AllTicketIds.contains(id)){
                return id;
            }
        }
    }

    public station getSource(){
        return this.source;
    }

    public station getDestination(){
        return this.destination;
    }

    public int getNoOfSeats(){
        return this.noOfSeats;
    }

    public Traveler getTraveler(){
        return this.traveler;
    }

    public TicketStatus getStatus(){
        return this.status;
    }

    public void UpdateTicketStatus(TicketStatus status){
        System.out.println("Status of ticket "+getTicketid()+" for "+traveler.getName()+", from "+getSource().getName() + " to "+getDestination().getName()+" for "+getNoOfSeats()+" seats is changes from "+this.status+" -> "+status);
        this.status = status;
    }

    public void bookTicket(){
        getTrip().bookTicket(this);
    }

    public Trip getTrip(){
        return this.trip;
    }

    public int getTicketid(){
        return this.ticketid;
    }
}
