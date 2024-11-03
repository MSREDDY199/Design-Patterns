/*
If we want to restrict the creation of objects of a class or wishes to maintain only single instance of a class then it's known as singleton pattern.

How to create singleton pattern or single object?
-> Create a static variable which can get assigned with a new instance, when it is created.
-> Make the constructor private, so that it cannot bw accessed from outside of the class.
-> Create a public static method which can be called globally and return this single object/instance of this class.

Why use singleton pattern?
-> Provides global access to the instance.
-> Only one instance is created, which avoid conflicting of multiple objects.
-> helpful in scenarios like databse connections.

Pros:
-> Lazy initialization: Instance is created when it is required.
-> Controlled access: since there is only one instance, you can control it's state when shared.
-> Reduces global state: you can avoid using global variables and manage shared resources in a controlled manner.

Cons:
-> Harder to test
-> Concurrency issue: In a multithreaded environment, there can be a possibility of creation of multiple instances by multiple threads, if not handled properly.
-> Singleton classes can't be sub classed, as it has private constructor, will limit inheritance.

*/

class Product {
    private static Product product; // create a static variable to store the single object
    String productName;

    // constructor should be private
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