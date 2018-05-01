package tanzeer.parkmycar;

/**
 * Created by tanze on 4/14/2018.
 */

public class AddParking {
   private String name,capacity,lat,lon,free;

    public AddParking() {
    }

    public AddParking(String name, String capacity, String lat, String lon, String free) {
        this.name = name;
        this.capacity = capacity;
        this.lat = lat;
        this.lon = lon;
        this.free = free;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }
}
