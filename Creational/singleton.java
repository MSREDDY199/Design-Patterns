/*
If we want to restrict the creation of objects of a class or wishes to maintain only single instance of a class then it's known as singleton pattern.

How to create singleton pattern or single object?
-> Create a static variable which can get assigned with a new instance, when it is created.
-> Make the constructor private, so that it cannot be accessed from outside of the class.
-> Create a public static method which can be called globally and return this single object/instance of this class.

Why use singleton pattern?
-> Provides global access to the instance and also restricts if from modification or over written.
-> Only one instance is created, which avoid conflicting of multiple objects.
-> Helpful in scenarios like database connections.

Use cases:
    1. Use the Singleton pattern when a class in your program should have just a single instance available to all clients; for example, 
        a single database object shared by different parts of the program.
    2. Use the Singleton pattern when you need stricter control over global variables.

Pros:
    1. Lazy initialization: Instance is created when it is required.
    2. Controlled access: Since there is only one instance, you can control it's state when shared.
    3. Reduces global state: You can avoid using global variables and manage shared resources in a controlled manner.

Cons:
    1. Harder to test
    2. Violates the Single Responsibility Principle. The pattern solves two problems at the time i.e only single instance and global access of the instance.
    3. Singleton classes can't be sub classed, as it has private constructor, will limit inheritance.
    4. The pattern requires special treatment in a multithreaded environment so that multiple threads won’t create a singleton object several times.

*/

class Product {
    private static Product product; // create a static variable to store the single object
    String productName;

    // constructor should be private as it prevents other classes from instantiating the Singleton class directly.
    private Product (String productName) {
        this.productName = productName;
    }

    // returns the instance globally
    public static Product getInstance (String productName) {
        if (product == null) product = new Product(productName);

        return product;
    }
}

class ProductCompany {
    public static void main(String[] args) {
        Product product1 = Product.getInstance("Mobile");
        Product product2 = Product.getInstance("TV");

        // if only single object is created above, It'll print Mobile twice
        System.out.println("product1: "+product1.productName);
        System.out.println("product2: "+product2.productName);
    }
}