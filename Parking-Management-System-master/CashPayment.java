public class CashPayment implements Payment {
    @Override
    public void processPayment(double amount) {
        // Logic to process cash payment
        System.out.println("Cash payment received for amount: " + amount);
    }
}
