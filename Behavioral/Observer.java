/*
    Observer Pattern
    ````````````````
    Observer is a behavioral design pattern that notifies multiple objects observing if there in any events or change that happen in the object they're observing.

    Problem:
    Imagine that you have two types of objects: a Customer and a Store. The customer is very interested in a particular brand of product (say, it’s a new model of the iPhone) 
    which should become available in the store very soon.
    The customer could visit the store every day and check product availability. But while the product is still en route, most of these trips would be pointless.
    On the other hand, the store could send tons of emails (which might be considered spam) to all customers each time a new product becomes available. This would save some customers 
    from endless trips to the store. At the same time, it’d upset other customers who aren’t interested in new products.

    Solution:
    The object that has some interesting state is often called subject, but since it’s also going to notify other objects about the changes to its state, we’ll call it publisher. 
    All other objects that want to track changes to the publisher’s state are called subscribers.

    The Observer pattern suggests that you add a subscription mechanism to the publisher class so individual objects can subscribe to or unsubscribe from a stream of events coming 
    from that publisher. Fear not! Everything isn’t as complicated as it sounds. In reality, this mechanism consists of 1) an array field for storing a list of references to subscriber objects 
    and 2) several public methods which allow adding subscribers to and removing them from that list.

    Pros:
    1. Open/Closed Principle. You can introduce new subscriber classes without having to change the publisher’s code (and vice versa if there’s a publisher interface).
    2. You can establish relations between objects at runtime.

    Cons:
    1. Subscribers are notified in random order.

*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Application to configure publishers and subscribers
public class Observer {

    public static void main(String[] args) {
        Editor editor = new Editor();

        LoggingListener logger = new LoggingListener("log.txt", "Someone has opened the file: %s");
        editor.events.subscribe("open", logger);

        EmailAlertsListener emailAlerts = new EmailAlertsListener("admin@example.com", "Someone has changed the file: %s");
        editor.events.subscribe("save", emailAlerts);

        // Simulate events
        editor.openFile("test_file.txt");
        editor.saveFile();
    }
}

// Observer interface
interface EventListener {

    void update(String filename);
}

// Concrete Observer: LoggingListener
class LoggingListener implements EventListener {

    private File log;
    private String message;

    public LoggingListener(String logFilename, String message) {
        this.log = new File(logFilename);
        this.message = message;
    }

    @Override
    public void update(String filename) {
        try (FileWriter writer = new FileWriter(log, true)) {
            writer.write(message.replace("%s", filename) + "\n");
            System.out.println("Writing the logs: " + message.replace("%s", filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Concrete Observer: EmailAlertsListener
class EmailAlertsListener implements EventListener {

    private String email;
    private String message;

    public EmailAlertsListener(String email, String message) {
        this.email = email;
        this.message = message;
    }

    @Override
    public void update(String filename) {
        System.out.println("Sending email to " + email + ": " + message.replace("%s", filename));
    }
}

// Publisher: EventManager
class EventManager {

    private Map<String, List<EventListener>> listeners = new HashMap<>();

    public void subscribe(String eventType, EventListener listener) {
        listeners.putIfAbsent(eventType, new ArrayList<>());
        listeners.get(eventType).add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        if (users != null) {
            users.remove(listener);
        }
    }

    public void notify(String eventType, String data) {
        List<EventListener> users = listeners.get(eventType);
        if (users != null) {
            for (EventListener listener : users) {
                listener.update(data);
            }
        }
    }
}

// Concrete Publisher: Editor
class Editor {

    public EventManager events;
    private File file;

    public Editor() {
        this.events = new EventManager();
    }

    public void openFile(String path) {
        this.file = new File(path);
        events.notify("open", file.getName());
    }

    public void saveFile() {
        if (file != null) {
            // Assume file.write() logic here
            events.notify("save", file.getName());
        }
    }
}
