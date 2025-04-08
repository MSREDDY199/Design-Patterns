/*
Definition:
    Abstract Factory pattern is creation design pattern that lets you build families of related objects without specifying their concrete classes.

Problem:
    Imagine we're at Mc Donald's, we see there different combo meal options available, Veg and Non-Veg combos. Each combo has it's respective burger and common french fries.
    Veg combo will have Veg burger and fries, Non-veg combo will have Non-veg burger and fries. We have to ensure that Veg combo will have Veg and Non-veg combo will have 
    Non-veg burgers. Customers can become mad if they receive different item in their plate.

Solution:
    Step 1: Think about the possible products, which are Burger, Fries, so create interfaces of them
    Step 2: Create variants of the products, for Burger create variants like Veg-burger, Non-Veg burger, Mutton burger etc., for French fries create variants like 
            Salted fries, peri peri fries, normal fries etc..
    Step 3: Create an abstract Factory interface which has the possible products that it can create or make.
    Step 4: Create Combos (families) by implementing the abstract factory interface which implements above interface's members.
    Step 5: Factory class which creates the objects based on client's needs

Alternatives to class registry 
````````````````````````````````
1. Dependency Injection frameworks (Spring) manage the creation and injection of objects. In this case, we don't need registry, as the DI container manages creation
and registration automatically.
2. Configuration files: We can use files like JSON, XML etc to define which classes would be created for certain types.

Examples:
    1. Cross-Platform UI Components, You're building a GUI application that needs to work on multiple platforms (Windows, MacOS, Linux). 
    Each platform has its own style for UI components like buttons, checkboxes, and scrollbar. The Abstract Factory creates families of UI components for each platform.
    2. Your application supports multiple themes (e.g., Light Theme, Dark Theme). Each theme has its own set of colors, fonts, and icons. The Abstract Factory creates the appropriate set 
    of theme-specific objects.
    3. You're developing software for a vehicle manufacturing system that produces cars and trucks. Each type of vehicle (e.g., electric or petrol) requires specific parts like engines, 
    tires, and gearboxes.
    4. Furniture ordering system, it has various sets of furniture like Victorian, Italian, Indian etc., each set will it's own type of chairs, beds, coffee tables etc.

Use cases:
    1. Use the Abstract Factory when your code needs to work with various families of related products, but you don’t want it to depend on the concrete classes of those 
    products—they might be unknown beforehand or you simply want to allow for future extensibility.
    2. Consider implementing the Abstract Factory when you have a class with a set of Factory Methods that blur its primary responsibility.

Pros:
    1. You can be sure that the products you’re getting from a factory are compatible with each other.
    2. You avoid tight coupling between concrete products and client code.
    3. Single Responsibility Principle. You can extract the product creation code into one place, making the code easier to support.
    4. Open/Closed Principle. You can introduce new variants of products without breaking existing client code.

Cons:
    1. The code may become more complicated than it should be, since a lot of new interfaces and classes are introduced along with the pattern.
                  
*/
import java.util.*;

// Factory class
class McDonald {
    public static void main(String[] args) {
        FactoryRegistry.register("Veg", VegCombo.class);
        FactoryRegistry.register("NonVeg", NonVegCombo.class);


        ComboMealFactory vegCombo = FactoryRegistry.getComboMeal("Veg");
        Burger veg = vegCombo.orderBurger();
        veg.prepareBurger();
        Fries normalFries = vegCombo.orderFries();
        normalFries.prepareFries();

        ComboMealFactory nonVegCombo = FactoryRegistry.getComboMeal("NonVeg");
        Burger nonVeg = nonVegCombo.orderBurger();
        nonVeg.prepareBurger();
        Fries normalFries2 = nonVegCombo.orderFries();
        normalFries2.prepareFries();
    }
}

// registry to store the classes, this helps us in avoiding the factory code modifications if we had to extend our system
class FactoryRegistry {
    public static Map<String, Class<? extends ComboMealFactory>> registry = new HashMap<>();

    public static void register(String combomealType, Class<? extends ComboMealFactory> combomeal) {
        registry.put(combomealType, combomeal);
    }

    public static ComboMealFactory getComboMeal(String combomealType) {
        try {
            return registry.get(combomealType).newInstance(); // Create instance via reflection
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

// ---------- STEP 1 -----------

// product interface for burger
interface Burger {
    void prepareBurger();
}
// product interface for french fries
interface Fries {
    void prepareFries();
}

// ---------- STEP 2 -----------

// concrete product variant to create Burger of non-veg type
class NonVegBurger implements Burger {
    public void prepareBurger() {
        System.out.println("Preparing non veg burger");
    }
}
// concrete product variant to create Burger of veg type
class VegBurger implements Burger {
    @Override
    public void prepareBurger() {
        System.out.println("Preparing veg burger");
    }
}
// concrete product variant to create french fries
class NormalFries implements Fries {
    public void prepareFries() {
        System.out.println("Preparing fries");
    }
}

// ---------- STEP 3 -----------

// Abstract factory interface
interface ComboMealFactory {
    Burger orderBurger();
    Fries orderFries();
}

// ---------- STEP 4 -----------

// veg combo meal (family)
class VegCombo implements ComboMealFactory {
    public Burger orderBurger() {
        return new VegBurger();
    }

    public Fries orderFries() {
        return new NormalFries();
    }
}

// non-veg combo meal (family)
class NonVegCombo implements ComboMealFactory {
    public Burger orderBurger() {
        return new NonVegBurger();
    }

    public Fries orderFries() {
        return new NormalFries();
    }
}


