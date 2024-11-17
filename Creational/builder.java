/*
 Builder is a creation design pattern that lets you construct complex objects step by step. The pattern allows you to produce different types and 
 representations of an object using the same construction code.

 Problem:
 Now lets say you want to build a House, building it would have series of steps such as Laying the foundation, building walls, putting windows and doors, 
 building roof etc. This would make us having lot of sub classes and adding any new thing to the house would make the list of subclasses keep growing.
 There's is one approach, you can create a giant constructor with all parameters in the base House class, this would eliminate the sub classes but at the end
 our constructor may have lot of unused parameters and makes the constructor call pretty ugly.
 Not only subclasses, Now let's say a house has parking and garden, and other has only parking and the other has only garden, so to create objects for these three houses
 we would create three different constructor or even more if we find more different houses. This is a cumbersome process.

 Solution:
 The Builder pattern suggests that you extract the object construction code out of its own class and move it to separate objects called builders.
 This pattern organizes several steps in creating an object (build walls, build roof). To create an object we need execute these series of steps 
 that are necessary for us.

 Director class (optional) can be used to define series of steps in it and it can be executed based on the use case. We can also go directly to the 
 client class and call the methods from there, but having director class helps us in keeping various construction routines in it, so that we can reuse them.

 Below example explanation:
 We have many types of Cars manufactured by company, each has different set of features. As the unique features list grows from car to car, the number of different
 constructor combinations also grows which is very challenging to maintain. To solve this Builder Design pattern comes into picture.
 Here we have two different cars, one has both GPS and Sunroof while other doesn't. Instead of creating two different constructors, we've a static CarBuilder class to
 do it based on the car we want to build. We've SetGPS, SetModel and SetSunroof methods in it which creates the objects based on car to car.
 We also have Director class which has two methods, one to create Economical car and the other create Luxury car. These methods have pre defined step for creation of
 different objects so that we can reuse them.

 Pros:
 ````
 1. Easy to maintain as the number of constructors will reduce.
 2. Immutable, if the fields in the Car class declared with final keyword.
 3. Easily supports complex objects creation.

 Cons:
 `````
 1. Returned object can be immutable as if we want to modify any property.
 2. Introduces more classes like Builder and Director
 3. If Director class is used, it is a tight coupling between Builder and Director
 4. Increases testing effort for testing Builder and Directory classes
  */

 public class Car {
    private String model;
    private String color;

    private boolean sunRoof;
    private boolean gps;

    private Car(CarBuilder carBuilder) {
        this.model = carBuilder.model;
        this.color = carBuilder.color;
        this.sunRoof = carBuilder.sunRoof;
        this.gps = carBuilder.gps;
    }

    public String getModel()
    {
        return model;
    }

    public String getColor()
    {
        return color;
    }

    public boolean getSunRoof()
    {
        return sunRoof;
    }

    public boolean getGps()
    {
        return gps;
    }

    // nested class that builds the object
    public static class CarBuilder {
        private String model;
        private String color;

        private boolean sunRoof = false;
        private boolean gps = false;

        public CarBuilder(String color) {
            this.color = color;
        }

        public CarBuilder setSunroof(boolean sunRoof) {
            this.sunRoof = sunRoof;
            return this;
        }

        public CarBuilder setGps(boolean gps) {
            this.gps = gps;
            return this;
        }
        
        public CarBuilder setModel(String model) {
            this.model = model;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    @Override
    public String toString() {
        return "Model: "+model+" Color: "+color+" Has sunroof: "+sunRoof+" Has gps: "+gps;
    }

    public static void main(String[] args) {
        // Car car = new Car.CarBuilder("Blue").setModel("Lexus").setSunroof(true).setGps(true).build();

        // System.out.println((car));

        Car.CarBuilder luxuryBuilder = new Car.CarBuilder("Blue");
        VehicleBuildingDirector luxuryVd = new VehicleBuildingDirector(luxuryBuilder);// using Directory class to easily call methods which has steps defined already
        Car luxuryCar = luxuryVd.buildBlueLuxuryCar();
        System.out.println("Luxury car: " +luxuryCar);
        
        Car.CarBuilder economyBuilder = new Car.CarBuilder("Blue");
        VehicleBuildingDirector economyVd = new VehicleBuildingDirector(economyBuilder);// using Directory class to easily call methods which has steps defined already
        Car economyCar = economyVd.buildBlueEconomyCar();
        System.out.println("Economy car: "+economyCar);
    }
}

// Director class which has set of steps already defined, so based on needs we can directly reuse them or we can also build them in main class directly
class VehicleBuildingDirector {
    public Car.CarBuilder builder;
    
    public VehicleBuildingDirector(Car.CarBuilder builder) {
        this.builder = builder;
    }
    
    public Car buildBlueLuxuryCar() {
        return builder.setModel("Lexus").setSunroof(true).setGps(true).build(); // building luxury car which has all features
    }
    
    public Car buildBlueEconomyCar() {
        return builder.setModel("Lexus").build(); // building economy car which has limited features
    }
}