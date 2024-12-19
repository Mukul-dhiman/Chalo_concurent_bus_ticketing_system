package chalo.com.system.route;

import chalo.com.system.baseObjects.station;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private String name;
    private List<station> stationList;

    public Route(String name){
        this.name = name;
        stationList = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public int getNumberOfStation(){
        return stationList.size();
    }

    public void addStationAtEnd(station stop){
        System.out.println("staion added: "+stop.getName() + " in " + getName());
        stationList.add(stop);
    }

    public void addStationAfterStationX(station stop, station x){
        int indexOfX = this.stationList.indexOf(x);
        if(indexOfX==-1){
            System.out.println("station not found in route");
            return;
        }
        stationList.add(indexOfX, stop);
    }

    public List<station> getRoute() {
        return this.stationList;
    }

    public void DisplayRoute(){
        for(int i=0;i<this.getNumberOfStation();i++){
            System.out.print(stationList.get(i).getName()+ " ");
        }
        System.out.println("|");
    }
}
