
class TransportCompany {
    public static void main(String[] args) {
        TransportFactory tf = new TransportFactory();
        Transport road = tf.getTransport("Road");
        System.out.println("Road: "+road.getTransportCost());
    }
}

interface Transport {
    int getTransportCost();
}

class TransportFactory {
    public Transport getTransport(String type) {
        if (type == null) return null;
        else if (type == "Road") return new RoadTransport();
        else if (type == "Air") return new AirTransport();
        else return null;
    }
}

class RoadTransport implements Transport {
    public int getTransportCost() {
        return 1000;
    }
}

class AirTransport implements Transport {
    public int getTransportCost() {
        return 100000;
    }
}