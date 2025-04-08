/*
Definition:
    The Command Design Pattern turns an action (or request) into an independent object. 
    This object contains all the information needed to execute the action, making it reusable, storable, and flexible.
    
    By converting a request into a stand-alone object:

    1. We Can Pass It Around:
        You can treat commands like data and pass them as arguments to methods.
        Example: A button doesn't need to "know" the action; it just holds a command object and triggers it when clicked.

    2. We Can Delay Execution:
        The command can be queued and executed later.
        Example: "Print" a document, but the printer is busy, so the command is stored and executed later.

    3. We Can Undo Operations:
        The command can store information about the action, enabling undo functionality.
        Example: Undo "Copy" or "Delete" in a text editor.

Problem:
    Imagine you're building a text editor (like Notepad).

    You have buttons (or keyboard shortcuts) for actions like Copy, Paste, Undo, etc.
    Each button should do something different when clicked.
    The problem arises because:
        If you directly attach the action's code to the button, you must create new button classes for each action (like CopyButton, PasteButton, etc.), which leads to too many classes.
        Some actions, like Copy, might be triggered from multiple places (e.g., toolbar button, context menu, or shortcut). Repeating the same code everywhere makes it hard to maintain.
        If you want to support Undo, it becomes harder to track what action happened last and reverse it.

Solution:
    The Command Design Pattern solves this by:

    1. Separating Actions into Command Objects:
        Each action (like Copy, Paste, Undo) is turned into a separate command object.
        These command objects contain the logic for performing the action and reversing it (if needed).

    2. Buttons Just Trigger Commands:
        Instead of buttons (or menus) knowing what to do, they simply call a command’s execute() method.
        This keeps buttons generic and reusable.

    3. Track Commands with a Stack:
        Whenever a command is executed, it’s stored in a stack.
        To undo, you simply pop the last command from the stack and call its undo() method.

    Client --> Invoker --> Command --> Receiver

Use cases:
    1. Use the Command pattern when you want to parametrize objects with operations.
    2.  Use the Command pattern when you want to queue operations, schedule their execution, or execute them remotely.
    3. Use the Command pattern when you want to implement reversible operations.

Pros:
    1. Single Responsibility Principle. You can decouple classes that invoke operations from classes that perform these operations.
    2.Open/Closed Principle. You can introduce new commands into the app without breaking existing client code.
    3. You can implement undo/redo.
    4. You can implement deferred execution of operations.
    5. You can assemble a set of simple commands into a complex one.

Cons:
    1. The code may become more complicated since you’re introducing a whole new layer between senders and receivers.

*/

// Client
public class Main {
    public static void main(String[] args) {
        Light light = new Light();

        RemoteControl remote = new RemoteControl();

        // Execute commands
        remote.executeCommand(new LightOnCommand(light));  // Output: The light is ON
        remote.executeCommand(new LightOffCommand(light)); // Output: The light is OFF

        // Undo commands
        remote.undo();  // Output: The light is ON (undoes "lightOff")
        remote.undo();  // Output: The light is OFF (undoes "lightOn")
        remote.undo();  // Output: Nothing to undo!
    }
}


// Invoker
class RemoteControl {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    public void executeCommand(Command command) {
        command.execute();  // Execute the command
        undoStack.push(command);  // Push to undo stack
        redoStack.clear();  // Clear redo stack since we're starting a new action sequence
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command lastCommand = undoStack.pop();  // Get the last executed command
            lastCommand.undo();  // Undo it
            redoStack.push(lastCommand);  // Push to redo stack
        } else {
            System.out.println("Nothing to undo!");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command lastUndone = redoStack.pop();  // Get the last undone command
            lastUndone.execute();  // Re-execute it
            undoStack.push(lastUndone);  // Push back to undo stack
        } else {
            System.out.println("Nothing to redo!");
        }
    }
}

// command
interface Command {
    void execute(); // Perform the action
    void undo();    // Undo the action
}

// concrete command
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn(); // Executes the command
    }

    @Override
    public void undo() {
        light.turnOff(); // Undoes the command
    }
}

// concrete command
class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn();
    }
}

// Receiver
class Light {
    public void turnOn() {
        System.out.println("The light is ON");
    }

    public void turnOff() {
        System.out.println("The light is OFF");
    }
}

