package tanzeer.parkmycar;

/**
 * Created by tanze on 4/16/2018.
 */

public class Parking {
    String name, capacity;
    String free;

    public Parking() {
    }

    public Parking(String name, String capacity, String free) {
        this.name = name;
        this.capacity = capacity;

        this.free = free.toString();
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

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }
}
