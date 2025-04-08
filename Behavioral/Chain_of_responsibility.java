/*
Definition:
    Chain of Responsibility is a behavioral design pattern that lets you pass requests along a chain of handlers. Upon receiving a request, each handler decides either to 
    process the request or to pass it to the next handler in the chain.

Note: The request will be processed by either one of the handler or none of them.

Problem:
    Let's take the an example of ordering system, initially we will have authentication feature which checks the authentication of the user as only log-in users can place the orders,
    and we will also have authorization which allows the admin user to access all orders. After sometime, we will try to add validations check of incoming request data before 
    passing on to the server. 
    Like this as time passes we will tend to add more features or checks which will soon make our system a mess. Sometimes changing one check will may affect other checks. 
    And this will make very hard to maintain the system.

Solution:
    Each check should be extracted by each handler class which will have single method that performs the check.
    The pattern suggests that you link these handlers into a chain. Each linked handler has a field for storing a reference to the next handler in the chain.

Use cases:
    1. Use the Chain of Responsibility pattern when your program is expected to process different kinds of requests in various ways, 
        but the exact types of requests and their sequences are unknown beforehand.
    2. Use the pattern when it’s essential to execute several handlers in a particular order.
    3. Use the CoR pattern when the set of handlers and their order are supposed to change at runtime.
Pros:
    1. You can control the order of request processing.
    2. Single Responsibility Principle, You can decouple classes that invoke operations from classes that perform operations.
    3. Open/Closed Principle, You can introduce new handlers into the app without breaking the existing client code. 

Cons:
    1. Some requests may end up unhandled.

Examples:
    1. Handling user authentication by chaining different checks, such as verifying user credentials, checking roles, and verifying permissions.
    2. Handling customer support tickets based on their priority. Low-priority tickets are handled by a junior support team, medium-priority by a senior team, and high-priority by managers.
    3. A logging framework where logs can have different levels (DEBUG, INFO, ERROR). Each level is handled by a specific logger.
*/

// Interface for all components that can show help
interface Component {
    void showHelp();
}

// Base component class with a reference to the container (parent in the chain)
abstract class BaseComponent implements Component {
    protected BaseComponent container; // The component’s container (parent)

    public void setContainer(BaseComponent container) {
        this.container = container;
    }

    // Show help text or pass it to the container if unavailable
    public void showHelp() {
        if (container != null) {
            container.showHelp(); // Pass request up the chain
        }
    }
}

// Button component, can optionally have tooltip text
class Button extends BaseComponent {
    private String tooltipText;

    public Button(String tooltipText) {
        this.tooltipText = tooltipText;
    }

    @Override
    public void showHelp() {
        if (tooltipText != null) {
            System.out.println("Button Help: " + tooltipText);
        } else {
            super.showHelp(); // Pass request up the chain
        }
    }
}

// Panel component, can optionally have modal help text
class Panel extends BaseComponent {
    private String modalHelpText;

    public Panel(String modalHelpText) {
        this.modalHelpText = modalHelpText;
    }

    @Override
    public void showHelp() {
        if (modalHelpText != null) {
            System.out.println("Panel Help: " + modalHelpText);
        } else {
            super.showHelp(); // Pass request up the chain
        }
    }
}

// Dialog component, can optionally have wiki page URL
class Dialog extends BaseComponent {
    private String wikiPageURL;

    public Dialog(String wikiPageURL) {
        this.wikiPageURL = wikiPageURL;
    }

    @Override
    public void showHelp() {
        if (wikiPageURL != null) {
            System.out.println("Dialog Help: Opening wiki page at " + wikiPageURL);
        } else {
            super.showHelp(); // Pass request up the chain
        }
    }
}

// Application to set up the chain and demonstrate the Chain of Responsibility
class Chain_of_responsibility {
    public static void main(String[] args) {
        // Create components
        Dialog dialog = new Dialog("http://help.wiki/page"); // Dialog has a wiki page URL
        Panel panel = new Panel(null);                       // Panel has no help text
        Button button = new Button(null);                    // Button has no tooltip text

        // Set up the chain
        button.setContainer(panel); // Button's container is Panel
        panel.setContainer(dialog); // Panel's container is Dialog

        // Simulate pressing F1 while focused on the button
        button.showHelp(); // Start the chain from the button
    }
}
