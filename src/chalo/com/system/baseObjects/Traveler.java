package chalo.com.system.baseObjects;

import chalo.com.system.Booking.Ticket;
import chalo.com.system.Booking.TicketStatus;

import java.util.ArrayList;

public class Traveler {
    String name;
    ArrayList<Ticket> tickets;

    public Traveler(String name){
        this.name = name;
        this.tickets = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public void DisplayTickets(){
        Ticket curr_ticket;
        int conifmed=0;
        int notconifmed=0;
        int pending=0;
        int invalid=0;

        for (Ticket ticket : tickets) {
            curr_ticket = ticket;
            if (curr_ticket.getStatus() == TicketStatus.Confirmed) {
                conifmed++;
            }
            if (curr_ticket.getStatus() == TicketStatus.NoConfirmed) {
                notconifmed++;
            }
            if (curr_ticket.getStatus() == TicketStatus.Pending) {
                pending++;
            }
            if (curr_ticket.getStatus() == TicketStatus.InvalidTicket) {
                invalid++;
            }
        }
        System.out.println("ALL "+tickets.size()+" Tickets (confirmed: "+conifmed+", not confirmed: "+notconifmed+", pending: "+pending+", invalid: "+invalid+") Status for "+name+" ->");

        for (Ticket ticket : tickets) {
            curr_ticket = ticket;
            if (curr_ticket.getStatus() == TicketStatus.Confirmed) {
                System.out.println("Trip " + curr_ticket.getTrip().getTripName() + " from " + curr_ticket.getSource().getName() + " to "
                        + curr_ticket.getDestination().getName() + " for " + curr_ticket.getNoOfSeats() + " seats, Ticket " + curr_ticket.getTicketid() + " status:" + curr_ticket.getStatus());
            }
        }
    }
}
