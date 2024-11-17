/*
Definition:
    Facade is a structural design pattern that provides a simplified interface to a library, a framework, or any other complex set of classes.
    The Facade Design Pattern is a structural pattern that provides a simplified interface to a larger and more complex system. 
    It acts as a front-facing interface, masking the more complicated underlying code.
Problem:
    When a system is complex with many interdependent classes and methods, interacting with it can become difficult and error-prone. And clients are tightly coupled
    with internal implementation of the system, making it hard to maintain.

Solution:
    A facade is a class that provides a simple interface to a complex subsystem which contains lots of moving parts. A facade might provide limited functionality in 
    comparison to working with the subsystem directly. However, it includes only those features that clients really care about.
    By interacting only with the facade, clients are decoupled from the internal implementation of the system, making the code more maintainable and flexible.
    The facade makes code more readable and less complex by providing a straightforward way to accomplish tasks that involve multiple subsystems.

Example to understand:
    Let's say we have a entertainment system, to make it work we have to switch it on, we have to insert disk/pen drive, set the volume and play the movie.
    tv.turnOn();
    soundSystem.setVolume(20);
    dvdPlayer.insertDisk("movie");
    dvdPlayer.play();

    A facade class with with following method can simplify the process:
    public void watchMovie(String movie) {
        tv.turnOn();
        soundSystem.setVolume(20);
        dvdPlayer.insertDisk(movie);
        dvdPlayer.play();
    }

Use cases:
    1. Use the Facade pattern when you need to have a limited but straightforward interface to a complex subsystem.
    2. Use the Facade when you want to structure a subsystem into layers.

Pros:
    1. You can isolate your code from the complexity of a subsystem.

Cons:
    1. A facade can become a god object coupled to all classes of an app.
*/

// Complex subsystem classes
class TV {
    public void turnOn() {
        System.out.println("TV is turned on");
    }
    public void setInputChannel() {
        System.out.println("TV channel set to DVD");
    }
}

class DVDPlayer {
    public void turnOn() {
        System.out.println("DVD Player is turned on");
    }
    public void play() {
        System.out.println("DVD is playing");
    }
}

class SoundSystem {
    public void turnOn() {
        System.out.println("Sound System is turned on");
    }
    public void setVolume(int level) {
        System.out.println("Sound System volume set to " + level);
    }
}

class Projector {
    public void turnOn() {
        System.out.println("Projector is turned on");
    }
}

// Facade class
class HomeTheaterFacade {
    private TV tv;
    private DVDPlayer dvdPlayer;
    private SoundSystem soundSystem;
    private Projector projector;

    public HomeTheaterFacade(TV tv, DVDPlayer dvdPlayer, SoundSystem soundSystem, Projector projector) {
        this.tv = tv;
        this.dvdPlayer = dvdPlayer;
        this.soundSystem = soundSystem;
        this.projector = projector;
    }

    public void watchMovie() {
        System.out.println("Getting ready to watch a movie...");
        tv.turnOn();
        tv.setInputChannel();
        dvdPlayer.turnOn();
        soundSystem.turnOn();
        soundSystem.setVolume(20);
        projector.turnOn();
        dvdPlayer.play();
        System.out.println("Movie is now playing!");
    }
}

// Application class
public class Facade {
    public static void main(String[] args) {
        TV tv = new TV();
        DVDPlayer dvdPlayer = new DVDPlayer();
        SoundSystem soundSystem = new SoundSystem();
        Projector projector = new Projector();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, dvdPlayer, soundSystem, projector);
        homeTheater.watchMovie();
    }
}
