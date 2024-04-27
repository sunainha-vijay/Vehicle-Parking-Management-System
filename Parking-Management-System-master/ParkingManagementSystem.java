import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParkingManagementSystem {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/parking_system";
    private static final String USERNAME = "user1";
    private static final String PASSWORD = "User101";

    private Connection connection;
    private List<ParkingSpot> parkingSpots;
    private List<Vehicle> parkedVehicles;

    public ParkingManagementSystem(int numSpots) {
        parkingSpots = new ArrayList<>();
        parkedVehicles = new ArrayList<>();
        // Initialize parking spots
        for (int i = 1; i <= numSpots; i++) {
            parkingSpots.add(new ParkingSpot(i));
        }
        try {
            // Step 1: Load the MySQL JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish the database connection
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to database.");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }

    public void parkVehicle(Vehicle vehicle, Payment payment) {
        ParkingSpot availableSpot = findAvailableSpot();
        if (availableSpot != null) {
            availableSpot.setAvailable(false); // Mark the spot as unavailable
            vehicle.setSpotNumber(availableSpot.getSpotNumber());  // Set the spot number for the vehicle
            parkedVehicles.add(vehicle);
            payment.processPayment(calculateParkingFee(vehicle));
            insertParkingRecord(vehicle);
            System.out.println("Vehicle parked at spot number: " + availableSpot.getSpotNumber());
        } else {
            System.out.println("No available parking spots.");
        }
    }

    public void exitVehicle(String licensePlate) {
        boolean found = false; // Initialize found to false
        Vehicle foundVehicle = null; // Initialize foundVehicle to null to store the found vehicle
        for (Vehicle vehicle : parkedVehicles) {
            if (vehicle.getLicensePlate().equals(licensePlate)) {
                foundVehicle = vehicle; // Store the found vehicle
                found = true; // Set found to true
                break;
            }
        }
        if (found) {
            parkedVehicles.remove(foundVehicle); // Remove the found vehicle from the list
            for (ParkingSpot spot : parkingSpots) {
                if (spot.getSpotNumber() == foundVehicle.getSpotNumber()) {
                    spot.setAvailable(true); // Mark the parking spot as available
                    updateExitTime(licensePlate);
                    break;
                }
            }
            System.out.println("Vehicle with license plate " + licensePlate + " exited successfully.");
        } else {
            System.out.println("Vehicle with license plate " + licensePlate + " not found in the parking lot.");
        }
    }

    private ParkingSpot findAvailableSpot() {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.isAvailable()) {
                return spot;
            }
        }
        return null;
    }

    private double calculateParkingFee(Vehicle vehicle) {
        // Logic to calculate parking fee based on vehicle type
        // Example: $5 per hour for cars, $10 per hour for trucks
        if (vehicle.getVehicleType().equalsIgnoreCase("car")) {
            return 5.0;
        } else if (vehicle.getVehicleType().equalsIgnoreCase("truck")) {
            return 10.0;
        } else {
            return 0.0;
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public void insertParkingRecord(Vehicle vehicle) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO parking_records (license_plate, vehicle_type, spot_number) VALUES (?, ?, ?)")) {
            pstmt.setString(1, vehicle.getLicensePlate());
            pstmt.setString(2, vehicle.getVehicleType());
            pstmt.setInt(3, vehicle.getSpotNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExitTime(String licensePlate) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE parking_records SET exit_time = CURRENT_TIMESTAMP WHERE license_plate = ? AND exit_time IS NULL")) {
            pstmt.setString(1, licensePlate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("removal")
    @Override
    protected void finalize() throws Throwable {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed.");
        }
        super.finalize();
    }
}
