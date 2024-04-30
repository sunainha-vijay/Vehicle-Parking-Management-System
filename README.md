## Description: ##
The Parking Management System is a Java-based application designed to manage parking operations such as parking vehicles, exiting vehicles, and processing payments. The system provides three interfaces: command-line interface (CLI), graphical user interface (GUI), and database interaction for storing parking records.

## Features: ##

Park Vehicle: Allows users to park vehicles by providing details such as license plate, vehicle type, and payment method (cash or credit card).

Exit Vehicle: Enables users to exit vehicles by entering the license plate number, updating the parking spot status, and recording exit time.

Payment Processing: Supports payment processing using either cash or credit card methods.

Command-Line Interface (CLI): Provides a simple text-based interface for interacting with the parking system.

Graphical User Interface (GUI): Offers a more user-friendly interface for interacting with the system, built using Java Swing.

Database Integration: Stores parking records in a MySQL database, allowing for data persistence and retrieval.

## Project Flow:
![image](https://github.com/sunainha-vijay/Vehicle-Parking-Management-System/assets/113001688/39df258a-dd3e-41dc-a81d-de96ed1551e9)


## Files: ##

ParkingApp.java: Contains the main class for the CLI interface.

ParkingGUI.java: Implements the GUI interface using Java Swing components.

ParkingManagementSystem.java: Manages parking operations such as parking, exiting vehicles, and database interactions.

ParkingSpot.java: Represents a parking spot with attributes such as spot number and availability.

Vehicle.java: Defines the vehicle class with attributes like license plate, vehicle type, and spot number.

CashPayment.java: Implements the Payment interface for processing cash payments.

CreditCardPayment.java: Implements the Payment interface for processing credit card payments.

Payment.java: Defines the Payment interface for processing payments.

README.md: Provides instructions and information about the project.

## Setup Instructions: ##

Clone the repository to your local machine.

Import the project into your preferred Java IDE.

Ensure that MySQL is installed and running on your system.

Create a MySQL database named "parking_system" with appropriate tables (details provided in the code).

Update the JDBC connection URL, username, and password in the ParkingManagementSystem.java file.

Compile and run the ParkingApp.java file for the CLI interface or ParkingGUI.java for the GUI interface.

## Usage: ##

For the CLI interface, follow the on-screen prompts to park vehicles, exit vehicles, or exit the program.

For the GUI interface, launch the ParkingGUI.java file, and use the provided buttons and fields to interact with the system.

## License:##
This project is licensed under the [License Name] License. See the LICENSE file for details.
