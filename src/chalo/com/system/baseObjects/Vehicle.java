package chalo.com.system.baseObjects;


public class Vehicle {
    private final String license_no;
    private final vehicaleType vechile_Type;
    private Integer maxCapacity;

    public Vehicle(String license_no, vehicaleType type, int maxCapacity){
        this.license_no = license_no;
        this.vechile_Type = type;
        this.maxCapacity = maxCapacity;
    }

    public int GetMaxCapcity(){
        return this.maxCapacity;
    }

}
