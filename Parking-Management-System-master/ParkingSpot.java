public class ParkingSpot {
    private int spotNumber;
    private boolean available;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.available = true;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
