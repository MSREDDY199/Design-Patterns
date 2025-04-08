/*
Definition:
    Template Method is a behavioral design pattern that defines the skeleton of an algorithm in the superclass but lets subclasses override specific steps of the algorithm 
    without changing its structure.

Problem:
    Lets say you're building Document analyzing system which support documents like PDF, DOCX, CSV etc,. In processing these different files we have different classes and each one have
    to perform series of steps like open the file, extract the data, parse the data, analyze the data and close the file.
    Now these methods will have similar code for data processing and analyzing leading to code duplication.

Solution:
    Now, let’s see what we can do to get rid of the duplicate code. It looks like the code for opening/closing files and extracting/parsing data is different for various data formats, 
    so there’s no point in touching those methods. However, implementation of other steps, such as analyzing the raw data and composing reports, is very similar, 
    so it can be pulled up into the base class, where subclasses can share that code.
    As you can see, we’ve got two types of steps:
        -> abstract steps must be implemented by every subclass
        -> optional steps already have some default implementation, but still can be overridden if needed

Pros:
    1. You can let clients override only certain parts of a large algorithm, making them less affected by changes that happen to other parts of the algorithm.
    2. You can pull the duplicate code into a superclass.

Cons:
    1. Some clients may be limited by the provided skeleton of an algorithm.
    2. You might violate the Liskov's Substitution Principle by suppressing a default step implementation via a subclass.
    3. Template methods tend to be harder to maintain the more steps they have.

Use cases:
    1. Use the Template Method pattern when you want to let clients extend only particular steps of an algorithm, but not the whole algorithm or its structure.
    2. Use the pattern when you have several classes that contain almost identical algorithms with some minor differences. As a result, you might need to modify all classes 
        when the algorithm changes.
*/

abstract class GameAI {

    // Template method defining the skeleton of the algorithm
    public final void turn() {
        collectResources();
        buildStructures();
        buildUnits();
        attack();
    }

    // Concrete method implemented in the base class
    protected void collectResources() {
        System.out.println("Collecting resources from built structures...");
        // Logic to iterate over built structures and collect resources
    }

    // Abstract methods to be implemented by subclasses
    protected abstract void buildStructures();
    protected abstract void buildUnits();

    // Template method with conditional logic
    protected void attack() {
        String enemy = closestEnemy();
        if (enemy == null) {
            sendScouts("map center");
        } else {
            sendWarriors(enemy);
        }
    }

    // Abstract methods for attack strategy
    protected abstract void sendScouts(String position);
    protected abstract void sendWarriors(String position);

    // Helper method to find the closest enemy
    protected String closestEnemy() {
        // Return a dummy enemy for the sake of this example
        return null; // Could be replaced with actual logic
    }
}

class OrcsAI extends GameAI {

    @Override
    protected void buildStructures() {
        System.out.println("Orcs are building farms, barracks, and stronghold...");
        // Logic for building Orc-specific structures
    }

    @Override
    protected void buildUnits() {
        System.out.println("Orcs are building units...");
        // Logic for building Orc-specific units
        // e.g., build scouts if none exist, otherwise build grunts
    }

    @Override
    protected void sendScouts(String position) {
        System.out.println("Orc scouts are heading to position: " + position);
        // Logic to send scouts
    }

    @Override
    protected void sendWarriors(String position) {
        System.out.println("Orc warriors are heading to position: " + position);
        // Logic to send warriors
    }
}

class MonstersAI extends GameAI {

    @Override
    protected void collectResources() {
        System.out.println("Monsters don't collect resources.");
        // Monsters don't need to collect resources
    }

    @Override
    protected void buildStructures() {
        System.out.println("Monsters don't build structures.");
        // Monsters don't build structures
    }

    @Override
    protected void buildUnits() {
        System.out.println("Monsters don't build units.");
        // Monsters don't build units
    }

    @Override
    protected void sendScouts(String position) {
        System.out.println("Monster scouts are heading to position: " + position);
        // Logic to send monster scouts
    }

    @Override
    protected void sendWarriors(String position) {
        System.out.println("Monster warriors are heading to position: " + position);
        // Logic to send monster warriors
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Orcs AI Turn:");
        GameAI orcsAI = new OrcsAI();
        orcsAI.turn();

        System.out.println("\nMonsters AI Turn:");
        GameAI monstersAI = new MonstersAI();
        monstersAI.turn();
    }
}
