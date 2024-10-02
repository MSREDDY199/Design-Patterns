/*
 Prototype is a creational design pattern that lets you copy existing objects without making your code dependent on their classes.

 Why Cloning objects?
 1. If we have to copy each property from one to another, it may not be reliable if the object has lot of complex properties, which can lead to errors as we have to
    create an object from scratch.
 2. If the creation of an object is computationally expensive (e.g., involves costly computations, database queries, or complex initialization processes), 
    it's more efficient to clone an existing object.
 3. Objects can be cloned independently, let's say if there is a Person object and which has Address object inside it, now cloning Person will also clone Address. 
    Now if there is any change in Person, will not effect the original object.

 Problem:
 If you wanted to create a copy of a object, you would assign each property of it to the new one, but the problem here is, what if we don't know the class of the object?
 And let's say if you know the class before creating a clone, there can be another challenge, what if the object follows an interface? then we may not know about it's 
 concrete classes.
 In this case, it would be challenging to create a clone of the object.

 Solution:
 The Prototype pattern delegates the cloning process to the actual objects that are being cloned. The pattern declares a common interface for all objects that support 
 cloning. This interface lets you clone an object without coupling your code to the class of that object. Usually, such an interface contains just a single clone method.
 
 */

import java.util.*;

// Abstract Shape class acting as a Prototype
abstract class Shape {
    int x;
    int y;
    String color;

    // Copy constructor
    public Shape(Shape target) {
        if (target != null) {
            this.x = target.x;
            this.y = target.y;
            this.color = target.color;
        }
    }

    public Shape() {
        // Default constructor
    }

    // Abstract clone method
    public abstract Shape clone();

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Shape)) {
            return false;
        }
        Shape shape = (Shape) object;
        return shape.x == x && shape.y == y && Objects.equals(shape.color, color);
    }
}

// Concrete class Rectangle
class Rectangle extends Shape {
    int length;
    int breadth;

    // Copy constructor
    public Rectangle(Rectangle rectangle) {
        super(rectangle);
        if (rectangle != null) {
            this.length = rectangle.length;
            this.breadth = rectangle.breadth;
        }
    }

    public Rectangle() {
        // Default constructor
    }

    @Override
    public Shape clone() {
        return new Rectangle(this);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Rectangle) || !super.equals(object))
            return false;
        Rectangle rectangle = (Rectangle) object;
        return rectangle.length == length && rectangle.breadth == breadth;
    }

    @Override
    public String toString() {
        return "Rectangle: [length = " + length + ", breadth = " + breadth + ", x = " + x + ", y = " + y + ", color = "
                + color + "]";
    }
}

// Concrete class Circle
class Circle extends Shape {
    int radius;

    // Copy constructor
    public Circle(Circle circle) {
        super(circle);
        if (circle != null) {
            this.radius = circle.radius;
        }
    }

    public Circle() {
        // Default constructor
    }

    @Override
    public Shape clone() {
        return new Circle(this);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Circle) || !super.equals(object))
            return false;
        Circle circle = (Circle) object;
        return circle.radius == radius;
    }

    @Override
    public String toString() {
        return "Circle: [radius = " + radius + ", x = " + x + ", y = " + y + ", color = " + color + "]";
    }
}

// Demo class with main method
public class Demo {
    public static void main(String[] args) {

        // ******* IMPORTANT **********/
        // We're creating the List of type Shape and adding the concrete class objects
        // in it.
        List<Shape> shapes = new ArrayList<>();
        List<Shape> shapesCopy = new ArrayList<>();

        // Create a Circle object and clone it
        Circle circle = new Circle();
        circle.x = 10;
        circle.y = 10;
        circle.radius = 10;
        circle.color = "Red";
        shapes.add(circle);

        Circle circle2 = (Circle) circle.clone();
        shapes.add(circle2);

        // Create a Rectangle object and clone it
        Rectangle rectangle = new Rectangle();
        rectangle.x = 20;
        rectangle.y = 20;
        rectangle.length = 15;
        rectangle.breadth = 25;
        rectangle.color = "Blue";
        shapes.add(rectangle);

        Rectangle rectangle2 = (Rectangle) rectangle.clone();
        shapes.add(rectangle2);

        cloneAndCompare(shapes, shapesCopy);
    }

    private static void cloneAndCompare(List<Shape> shapes, List<Shape> shapesCopy) {
        // ******* IMPORTANT **********/
        // Cloning the abstract/interface class list instead of cloning their each
        // concrete class objects
        for (Shape shape : shapes) {
            // here we're cloning
            shapesCopy.add(shape.clone());
        }

        for (int i = 0; i < shapes.size(); i++) {

            if (shapes.get(i) != shapesCopy.get(i)) { // Comparing the object references, if both the ref. are equal then it is the same object, not a clone
                System.out.println(i + ": Shapes are different objects (yay!)");
                if (shapes.get(i).equals(shapesCopy.get(i))) { // comparing the values of the objects
                    System.out.println(i + ": And they are identical (yay!)"); // cloned successfully
                } else {
                    System.out.println(i + ": But they are not identical (booo!)");
                }
            } else {
                System.out.println(i + ": Shape objects are the same (booo!)");
            }
        }
    }
}
