public class Vehicle {
    private String licensePlate;
    private String vehicleType;
    private int spotNumber;

    public Vehicle(String licensePlate, String vehicleType, int spotNumber) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.spotNumber = spotNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }
}
