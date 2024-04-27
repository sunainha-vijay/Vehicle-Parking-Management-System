import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ParkingGUI extends JFrame {
    private ParkingManagementSystem parkingSystem;
    private JTextField licensePlateField, exitLicensePlateField;
    private JComboBox<String> paymentMethodComboBox, choiceComboBox, vehicleTypeField;
    private JTextArea outputArea;
    private JPanel inputPanel;

    public ParkingGUI() {
        super("Parking Management System");
        parkingSystem = new ParkingManagementSystem(10); // Initialize with 10 parking spots
        setupUI();
    }

    private void setupUI() {
        // Create components
        choiceComboBox = new JComboBox<>(new String[]{"", "Park Vehicle", "Exit Vehicle", "Exit Program"});
        choiceComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String choice = (String) choiceComboBox.getSelectedItem();
                updateInputPanel(choice);
            }
        });

        inputPanel = new JPanel(new GridLayout(5, 2));

        // Add components to input panel
        add(choiceComboBox, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private synchronized void updateInputPanel(String choice) {
        inputPanel.removeAll();
        inputPanel.revalidate();
        inputPanel.repaint();

        if (choice.equals("Park Vehicle")) {
            setupParkVehicleInputPanel();
        } else if (choice.equals("Exit Vehicle")) {
            setupExitVehicleInputPanel();
        } else if (choice.equals("Exit Program")) {
            System.exit(0);
        }
    }

    private void setupParkVehicleInputPanel() {
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("License Plate:"));
        licensePlateField = new JTextField();
        inputPanel.add(licensePlateField);
        inputPanel.add(new JLabel("Vehicle Type:"));
        vehicleTypeField = new JComboBox<>(new String[]{"","Car", "Truck"});
        inputPanel.add(vehicleTypeField);
        inputPanel.add(new JLabel("Payment Method:"));
        paymentMethodComboBox = new JComboBox<>(new String[]{"Cash", "Credit Card"});
        inputPanel.add(paymentMethodComboBox);
        JButton parkButton = new JButton("Park Vehicle");
        parkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform parking operation in a separate thread
                Thread parkingThread = new Thread(new Runnable() {
                    public void run() {
                        synchronized(parkingSystem) {
                            String licensePlate = licensePlateField.getText();
                            String vehicleType = (String)vehicleTypeField.getSelectedItem();
                            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
                            Vehicle vehicle = new Vehicle(licensePlate, vehicleType, -1);
                            Payment payment;
                            if (paymentMethod.equalsIgnoreCase("cash")) {
                                payment = new CashPayment();
                            } else {
                                payment = new CreditCardPayment();
                            }
                            parkingSystem.parkVehicle(vehicle, payment);
                            updateOutput("Vehicle parked successfully.");
                            resetGUI();
                        }
                    }
                });
                parkingThread.start();
            }
        });
        inputPanel.add(parkButton);
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel());
    }

    private void setupExitVehicleInputPanel() {
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Exit License Plate:"));
        exitLicensePlateField = new JTextField();
        inputPanel.add(exitLicensePlateField);
        JButton exitButton = new JButton("Exit Vehicle");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                synchronized(parkingSystem) {
                    String exitLicensePlate = exitLicensePlateField.getText();
                    parkingSystem.exitVehicle(exitLicensePlate);
                    updateOutput("Vehicle exited successfully.");
                    resetGUI();
                }
            }
        });
        inputPanel.add(exitButton);
        inputPanel.add(new JLabel());
    }

    private void resetGUI() {
        choiceComboBox.setSelectedIndex(0);
        licensePlateField.setText("");
        vehicleTypeField.setSelectedIndex(0);
        exitLicensePlateField.setText("");
        paymentMethodComboBox.setSelectedIndex(0);
    }

    private void updateOutput(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ParkingGUI();
            }
        });
    }
}

