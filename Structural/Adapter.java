/*

Definition: Adapter is a structural design pattern that allows objects with incompatible interfaces to collaborate.

Problem: 
    Let's say you have a stock market monitoring system, for which you get data from exchange in XML format to show on chart. Now, let's say you wanted to integrate
    third-party analytics into the system, but it supports only JSON format data not XML.
    You can try to change the library to work with XML, but what about the existing code that relies on this library. And the worse is, you might not have the
    access to the library to change or modify, making this approach impossible.

Solution:
    You can create an adapter. This is a special object that converts the interface of one object so that another object can understand it.
    An adapter wraps one of the objects to hide the complexity of conversion happening behind the scenes. The wrapped object isn’t even aware of the adapter. 
    For example, you can wrap an object that operates in meters and kilometers with an adapter that converts all of the data to imperial units such as feet and miles.
    Let’s get back to our stock market app. To solve the dilemma of incompatible formats, you can create XML-to-JSON adapters for every class of the analytics library 
    that your code works with directly. Then you adjust your code to communicate with the library only via these adapters. 
    When an adapter receives a call, it translates the incoming XML data into a JSON structure and passes the call to the appropriate methods of a wrapped analytics object.

Use cases:
    1. Use Adapter class as middle layer when you want to use some interface in your code but isn't compatible
    2. Use the pattern when you want to reuse several existing subclasses that lack some common functionality that can’t be added to the superclass.

Pros:
    1. Single Responsibility Principle. You can separate the interface or data conversion code from the primary business logic of the program.
    2. Open/Closed Principle. You can introduce new types of adapters into the program without breaking the existing client code, as long as they work with the adapters through the client interface.

Cons:
    1. The overall complexity of the code increases because you need to introduce a set of new interfaces and classes. 
        Sometimes it’s simpler just to change the service class so that it matches the rest of your code.

*/

// RoundHole class
class RoundHole {
    double radius;

    public RoundHole(double radius) {
        this.radius = radius;
    }

    public boolean fits(RoundPeg peg) {
        return radius >= peg.getRadius();
    }
}

// Round peg that fits in hole
class RoundPeg {
    double radius;

    public RoundPeg(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}

// square peg that may or may not fit in hole
class SquarePeg {
    double width;

    public SquarePeg (double width) {
        this.width = width;
    }

    public double getWidth() {
        return width;
    }
}

// adapter to convert square peg to round peg to make it fit
class SquarePegAdapter extends RoundPeg {
    SquarePeg squarePeg;

    public SquarePegAdapter (SquarePeg squarePeg) {
        this.squarePeg = squarePeg;
    }

    public double getRadius() {
        return squarePeg.getWidth() * Math.sqrt(2)/2;
    }
}

class Main {
    public static void main(String[] args) {
        RoundHole hole = new RoundHole(5);
        RoundPeg peg = new RoundPeg(5);
        System.out.println("does peg fits: "+hole.fits(peg));

        SquarePeg squarePeg = new SquarePeg(5);
        SquarePegAdapter adapter = new SquarePegAdapter(squarePeg);

        System.out.println("does peg fits: "+hole.fits(adapter));
    }
}