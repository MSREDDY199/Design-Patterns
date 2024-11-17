/*
Definition: Strategy is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable.
-> Strategy design patterns means there can be more than one algorithm that can used to solved a specific challenge, these algorithms are kept in separate classes called strategies.

Problem: 
    Navigation system, The first version of the app could only build the routes over roads. People who traveled by car were bursting with joy. But apparently, 
    not everybody likes to drive on their vacation. So with the next update, you added an option to build walking routes. Right after that, you added another 
    option to let people use public transport in their routes.
    Now if we want to add route for cyclists or any other route in future, each time you add a new algorithm, the main class of the navigator gets doubled. This becomes very
    hard for developers to maintain, and as the code is in same class doing some change would affect the already working code.
    And it would also be challenging for developers to resolve merge conflicts as they'll working on same class.

Solution:
    The Strategy pattern suggests that you take a class that does something specific in a lot of different ways and extract all of these algorithms into separate classes called strategies.
    The original class, called context, must have a field for storing a reference to one of the strategies. 
    The context delegates the work to a linked strategy object instead of executing it on its own.
    The context isn’t responsible for selecting an appropriate algorithm for the job. Instead, the client passes the desired strategy to the context. In fact, 
    the context doesn’t know much about strategies. It works with all strategies through the same generic interface, which only exposes a single method for triggering the algorithm 
    encapsulated within the selected strategy.
    This way the context becomes independent of concrete strategies, so you can add new algorithms or modify existing ones without changing the code of the context or other strategies.

Use cases:
    1. Use Strategy pattern if there are lot of conditional statements that switches between different variants of the same algorithm.
    2. Use Strategy pattern, when you want to use different variants of an algorithm within an object and be able to switch from one algorithm to another.
    3. Use Strategy pattern, when you massive similar classes that only differ in the way they execute.

Pros:
    1. You can swap algorithms used inside an object at runtime.
    2. You can isolate the implementation details of an algorithm from the code that uses it.
    3. You can replace inheritance with composition.
    4. Open/Closed Principle. You can introduce new strategies without having to change the context.

Cons:
    1. If you only have a couple of algorithms and they rarely change, there’s no real reason to overcomplicated the program with new classes and interfaces that come along with the pattern.
    2. Clients must be aware of the differences between strategies to be able to select a proper one.
    3. A lot of modern programming languages have functional type support that lets you implement different versions of an algorithm inside a set of anonymous functions.  
        Then you could use these functions exactly as you’d have used the strategy objects, but without bloating your code with extra classes and interfaces.

examples:
    1. Sorting algorithms like Quick, Merger and Bubble sort
    2. Payment Gateways using different modes of payment like credit card, debit card, upi, wallets etc.
    3. Input validations like validating email, phone number, password
*/

public interface PaymentStrategy {
    void pay(int amount);
}

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}

public class PayPalPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using PayPal.");
    }
}

public class BankTransferPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Bank Transfer.");
    }
}

public class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    // Method to set the payment strategy
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Method to checkout with the chosen payment strategy
    public void checkout(int amount) {
        if (paymentStrategy == null) {
            System.out.println("Please select a payment method before checking out.");
        } else {
            paymentStrategy.pay(amount);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // Example with Credit Card Payment
        cart.setPaymentStrategy(new CreditCardPayment());
        cart.checkout(100);  // Output: Paid 100 using Credit Card.

        // Example with PayPal Payment
        cart.setPaymentStrategy(new PayPalPayment());
        cart.checkout(200);  // Output: Paid 200 using PayPal.

        // Example with Bank Transfer Payment
        cart.setPaymentStrategy(new BankTransferPayment());
        cart.checkout(300);  // Output: Paid 300 using Bank Transfer.
    }
}
