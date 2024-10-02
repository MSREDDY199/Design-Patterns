package builders;

import components.Engine;
import components.GPSNavigator;
import components.Transmission;
import components.TripComputer;
import products.CarType;

public interface Builder {
    void setCarType(CarType carType);
    void setEngine(Engine engine);
    void setTransmission(Transmission transmission);
    void setTripComputer(TripComputer tripComputer);
    void setGPSNavigator(GPSNavigator gpsNavigator);
    void setSeats(int seats);
}
