package chalo.com.system.API;

import chalo.com.system.Booking.Ticket;
import chalo.com.system.baseObjects.Traveler;
import chalo.com.system.baseObjects.Vehicle;
import chalo.com.system.baseObjects.station;
import chalo.com.system.route.Route;
import chalo.com.system.trip.Trip;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static chalo.com.system.baseObjects.vehicaleType.Bus;

public class master {

    public static void main(String[] args) {
        int noOfThreads = 1000;

        Vehicle punjabroadways = new Vehicle("pb 48 AM 0001", Bus, 50);
        Route bangaloreToMysoreRoute = new Route("Bangalore to mysore");
        station a = new station("a", "A");
        station b = new station("b", "B");
        station c = new station("c", "C");
        station d = new station("d", "D");
        station e = new station("e", "E");
        station f = new station("f", "F");
        station g = new station("g", "G");
        station h = new station("h", "H");
        station i = new station("i", "I");

        bangaloreToMysoreRoute.addStationAtEnd(a);
        bangaloreToMysoreRoute.addStationAtEnd(b);
        bangaloreToMysoreRoute.addStationAtEnd(c);
        bangaloreToMysoreRoute.addStationAtEnd(d);
        bangaloreToMysoreRoute.addStationAtEnd(e);
        bangaloreToMysoreRoute.addStationAtEnd(f);
        bangaloreToMysoreRoute.addStationAtEnd(g);
        bangaloreToMysoreRoute.addStationAtEnd(h);
        bangaloreToMysoreRoute.addStationAtEnd(i);

        Trip saturdayTriptrip = new Trip("saturdayTrip", punjabroadways, bangaloreToMysoreRoute);

        Traveler bobby = new Traveler("bobby");
        Traveler sunny = new Traveler("sunny");
        Traveler hritik = new Traveler("hritik");
        Traveler dharam = new Traveler("dharam");
        Traveler salman = new Traveler("salman");
        Traveler srk = new Traveler("srk");
        Traveler hrk = new Traveler("hrk");
        Traveler modi = new Traveler("modi");

        ArrayList<Traveler> TravelersList = new ArrayList<>();
        TravelersList.add(bobby);
        TravelersList.add(sunny);
        TravelersList.add(hritik);
        TravelersList.add(dharam);
        TravelersList.add(salman);
        TravelersList.add(srk);
        TravelersList.add(hrk);
        TravelersList.add(modi);

        ExecutorService executors = Executors.newFixedThreadPool(noOfThreads);

        Random random = new Random();

        for(int thr=0;thr<noOfThreads;thr++){
            int finalThr = thr;
            executors.execute(() -> {
                System.out.println("Starting, thread no -> "+ finalThr);

                Traveler curr_traveler = TravelersList.get(random.nextInt(TravelersList.size()));
                station source_station = bangaloreToMysoreRoute.getRoute().get(random.nextInt(bangaloreToMysoreRoute.getNumberOfStation()));
                station destination_station = bangaloreToMysoreRoute.getRoute().get(random.nextInt(bangaloreToMysoreRoute.getNumberOfStation()));
                int nomber_of_seats = random.nextInt(5)+1;

                Ticket ticket1 = new Ticket(curr_traveler,
                        saturdayTriptrip,
                        source_station,
                        destination_station,
                        nomber_of_seats);

                System.out.println("thread no: "+finalThr+" request from "+curr_traveler.getName()+" for "+nomber_of_seats+" seats from "+source_station.getName()+" to "+destination_station.getName()+" with ticketid:"+ticket1.getTicketid());
                curr_traveler.addTicket(ticket1);
            });
        }

        executors.shutdown();

        try {
            if (!executors.awaitTermination(600, TimeUnit.SECONDS)) {
                System.out.println("Some tasks did not finish within the timeout");
                executors.shutdownNow();
            } else {
                System.out.println("All tasks finished successfully");
            }
            saturdayTriptrip.DisplayPassengerMap();
            for (Traveler traveler : TravelersList) {
                traveler.DisplayTickets();
            }
        } catch (InterruptedException err) {
            executors.shutdownNow();
        }

    }
}
