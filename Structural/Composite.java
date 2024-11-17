/*

Definition:
    The Composite Design Pattern is a structural pattern used to treat individual objects and compositions of objects uniformly. 
    It is particularly useful when working with a tree-like structure where each node can be an individual object or a collection of objects.

Problem:
    Imagine you're building a graphics editor application where the user can draw and manipulate simple shapes like circles, rectangles, and lines. 
    Over time, the application needs to support complex structures, such as grouping multiple shapes together and treating them as a single unit. 
    This way, users can apply transformations or move an entire group of shapes at once.

Solution:
    The Composite Design Pattern allows you to create a tree structure of objects where both individual objects (e.g., Circle, Rectangle) and 
    composite objects (CompoundShape containing multiple shapes) can be treated uniformly.

Use cases:
    1. Use the Composite pattern when you have to implement a tree-like object structure.
    2. Use the pattern when you want the client code to treat both simple and complex elements uniformly.

Pros:
    1. You can work with complex tree structures more conveniently: use polymorphism and recursion to your advantage.
    2. Open/Closed Principle. You can introduce new element types into the app without breaking the existing code, which now works with the object tree.

Cons:
    1. It might be difficult to provide a common interface for classes whose functionality differs too much. In certain scenarios, you’d need to overgeneralize the component interface, 
        making it harder to comprehend.
*/

import java.util.ArrayList;
import java.util.List;

// Component interface
interface Graphic {
    void move(int x, int y);
    void draw();
}

// Leaf Classes (Simple shapes)
class Circle implements Graphic {
    @Override
    public void move(int x, int y) {
        System.out.println("Moving circle to (" + x + ", " + y + ")");
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

class Rectangle implements Graphic {
    @Override
    public void move(int x, int y) {
        System.out.println("Moving rectangle to (" + x + ", " + y + ")");
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle");
    }
}

// Composite Class (Compound shape):
class CompoundShape implements Graphic {
    private List<Graphic> children = new ArrayList<>();

    public void add(Graphic graphic) {
        children.add(graphic);
    }

    public void remove(Graphic graphic) {
        children.remove(graphic);
    }

    @Override
    public void move(int x, int y) {
        for (Graphic child : children) {
            child.move(x, y);
        }
        System.out.println("Moving compound shape to (" + x + ", " + y + ")");
    }

    @Override
    public void draw() {
        for (Graphic child : children) {
            child.draw();
        }
        System.out.println("Drawing a compound shape");
    }
}

// Client Code
public class Main {
    public static void main(String[] args) {
        // Create individual shapes
        Circle circle = new Circle();
        Rectangle rectangle = new Rectangle();

        // Create a compound shape
        CompoundShape compoundShape = new CompoundShape();
        compoundShape.add(circle);
        compoundShape.add(rectangle);

        // Treating both simple and composite objects uniformly
        compoundShape.move(10, 20);
        compoundShape.draw();
    }
}



