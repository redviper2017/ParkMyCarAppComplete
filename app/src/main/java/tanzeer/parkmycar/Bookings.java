package tanzeer.parkmycar;

/**
 * Created by tanze on 4/1/2018.
 */

public class Bookings {
    String name, mobileNumber, vehicleNumber, from, to, parking;

    public Bookings() {
    }

    public Bookings(String name, String mobileNumber, String vehicleNumber, String from, String to, String parking) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.vehicleNumber = vehicleNumber;
        this.from = from;
        this.to = to;
        this.parking=parking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }
}
