public class CreditCardPayment implements Payment {
    @Override
    public void processPayment(double amount) {
        // Logic to process credit card payment
        System.out.println("Credit card payment processed for amount: " + amount);
    }
}
