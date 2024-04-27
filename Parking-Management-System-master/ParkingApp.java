import java.util.Scanner;

public class ParkingApp {
    public static void main(String[] args) {
        ParkingManagementSystem parkingSystem = new ParkingManagementSystem(10); // Initialize with 10 parking spots
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Park Vehicle\n2. Exit Vehicle\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter vehicle license plate: ");
                    String licensePlate = scanner.nextLine();
                    System.out.print("Enter vehicle type (car/truck): ");
                    String vehicleType = scanner.nextLine();
                    System.out.print("Enter payment method (cash/credit): ");
                    String paymentMethod = scanner.nextLine();
                    Vehicle vehicle = new Vehicle(licensePlate, vehicleType, -1); // Initialize spot number as -1
                    Payment payment;
                    if (paymentMethod.equalsIgnoreCase("cash")) {
                        payment = new CashPayment();
                    } else {
                        payment = new CreditCardPayment();
                    }
                    parkingSystem.parkVehicle(vehicle, payment);
                    break;
                case 2:
                    System.out.print("Enter vehicle license plate to exit: ");
                    String exitLicensePlate = scanner.nextLine();
                    parkingSystem.exitVehicle(exitLicensePlate);
                    break;
                case 3:
                    System.out.println("Exiting program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
