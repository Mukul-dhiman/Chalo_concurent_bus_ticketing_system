package chalo.com.system.baseObjects;

public class station {
    String name;
    String location;

    public station(String name, String location){
        this.name = name;
        this.location = location;
    }

    public String getName(){
        return name;
    }
}
