/*

Definition:
    Decorator is a structural design pattern that lets you attach new behaviors to objects by placing these objects inside special wrapper objects that contain the behaviors.

Problem:
    Let's say we have Coffee ordering system, which has add-ons like milk, extra coffee, whipped cream, sugar etc.
    To build such system we would create classes like CoffeeWithMilk, ExtraCoffee, CoffeeWithCream, CoffeeWitSugar... and as the add-ons on the menu increases the combination of classes
    would increase leading to burst of classes. This would also make the system harder to maintain. Adding new add-on like Caramel, we have to create classes like CoffeeWithCaramel, 
    CoffeeWithMilkWithCaramel etc..

Solution:
    The Decorator pattern provides a more flexible and scalable solution. Instead of creating subclasses for each combination, you create decorator classes that add behavior dynamically.
    Component Interface: Defines the basic type for objects (e.g., Coffee).
    Concrete Component: The base class that implements the interface (e.g., BaseCoffee).
    Decorator Classes: Implement the same interface and add behavior to the component (e.g., MilkDecorator, SugarDecorator).

Use cases:
    1. Use the Decorator pattern when you need to be able to assign extra behaviors to objects at runtime without breaking the code that uses these objects.
    2. Use the pattern when it’s awkward or not possible to extend an object’s behavior using inheritance.

Pros:
    1. Dynamic Composition: You can combine and extend behavior dynamically without modifying existing code.
    2. Single Responsibility: Each decorator has a focused task (e.g., adding milk, adding sugar).
    3. Open/Closed Principle: The system is open for extension (new add-ons) but closed for modification (existing classes remain unchanged).

Cons:
    1. Increased Complexity: Can lead to many small, hard-to-manage classes.
    2. Difficult to Understand: Stacking multiple decorators can make code harder to read and debug.
    3. Order Dependency: The behavior depends on the order of applied decorators, which can lead to unexpected outcomes.
    4. Performance Overhead: Multiple layers can impact performance and increase memory usage.
    5. Transparency Issues: Understanding the final behavior requires checking all applied decorators, reducing code clarity.
*/

// Component Interface
interface Coffee {
    String getDescription();
    double getCost();
}

// // Concrete Component, base of any item
class BaseCoffee implements Coffee {

    public double getCost() {
        return 1.0;
    }

    public String getDescription() {
        return "Base coffee ";
    }
}

// Decorator Class
class CoffeeDecorator implements Coffee {
    Coffee coffee;

    CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    public double getCost() {
        return coffee.getCost();
    }

    public String getDescription() {
        return coffee.getDescription();
    }
}

// Concrete Decorators
class SugarDecorator extends CoffeeDecorator {

    SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    public double getCost() {
        return super.getCost()+1;
    }

    public String getDescription() {
        return super.getDescription() + ", sugar";
    }
}

// Concrete Decorators
class MilkDecorator extends CoffeeDecorator {

    MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    public double getCost() {
        return super.getCost()+1;
    }

    public String getDescription() {
        return super.getDescription() + ", milk";
    }
}

// Client Code
class Decorator {
    public static void main (String[] args) {
        Coffee cd = new BaseCoffee();
        System.out.println(cd.getDescription()+": $ "+cd.getCost()); // cost of just base coffee 

        SugarDecorator sd = new SugarDecorator(cd);
        System.out.println(sd.getDescription()+": $ "+sd.getCost()); // cost of coffee with sugar

        MilkDecorator md = new MilkDecorator(sd);
        System.out.println(md.getDescription()+": $ "+md.getCost()); // cost of coffee with sugar and milk
    }
}