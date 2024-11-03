
/*
Problem:
Now lets say you're head of transport company which curently has only transporation through Road, Now as part of business
expansion, you're planning to introduce transportation through planes. 
Introduction of planes in the system will attract lot of code changes as it is tightly coupled with Road transportation.

Solution:
 Factory pattern is a creational desing pattern, which suggests let the sub classes decide what objects they wanted to create based client requirements.
 There will be an interface which  has price calculation method and is being implemented by concrete classes like Road and Plane transports.
 We also have Factory method class which returns prices of different ways of transportation based on client interests.

 From the below example, in the main method, client need not to know about concrete classes and if tomorrow you wanted to introduce Transport though Ships,
 then the client code need not to know about concrete class like RoadTransport and AirTransport, instead only Factory class will change.

 But changing the Factory class will violate the SOLID principle's Open/Closed priciple, in that case we can have a registry where we can register each concrete class.
 
 Alternatives to registration
 ````````````````````````````
 1. Dependency Injection frameworks (Spring) manage the creation and injection of objects. In this case, we don't need registry, as the DI container manages creation
    and registration automatically.
 2. Configuration files: We can use files like JSON, XML etc to define which classes would be created for certain types.

*/
import java.util.*;

// main class
class TransportCompany {
    public static void main(String[] args) {
        TransportFactory.register("Road", RoadTransport.class);
        RoadTransport transport = (RoadTransport) TransportFactory.getTransport("Road");
        System.out.println("Transport cost: "+transport.getTransportCost());
    }
}

// interface
interface Transport {
    int getTransportCost();
}

// factory class registry
class TransportFactory {
    public static Map<String, Class<? extends Transport>> registry = new HashMap<>();

    public static void register(String transportType, Class<? extends Transport> transport) {
        registry.put(transportType, transport);
    }

    public static Transport getTransport(String transportType) {
        try {
            return registry.get(transportType).newInstance(); // Create instance via reflection
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

// concrete class of road transportation
class RoadTransport implements Transport {
    public int getTransportCost() {
        return 1000;
    }
}

// concrete class of air transportation
class AirTransport implements Transport {
    public int getTransportCost() {
        return 10000;
    }
}