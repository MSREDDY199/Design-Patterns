/*
Abstract Factory pattern is creational desing pattern that lets you build families of realated objects without specifying their concrete classes.

Problem:
 Imagine that you’re creating a furniture shop simulator. Your code consists of classes that represent:
    A family of related products, say: Chair + Sofa + CoffeeTable.
    Several variants of this family. For example, products Chair + Sofa + CoffeeTable are available in these variants: Modern, Victorian, ArtDeco.

 You need a way to create individual furniture objects so that they match other objects of the same family. Customers get quite mad when they receive 
 non-matching furniture.
 Also, you don’t want to change existing code when adding new products or families of products to the program. Furniture vendors update their catalogs 
 very often, and you wouldn’t want to change the core code each time it happens.

Solution:
 Step 1: Think about the possible products, which are Sofa, Chair and Coffee table so create interfaces of them. And methods based what client can do with them.
 Step 2: Create variants of the products, for Chair create variants like Victorian chair, Art chair, Modern chair etc., for Sofa create variants like 
        Victorian Sofa, Art Sofa, Modern Sofa etc. same for Coffee table as well.
 Step 3: Create an abstract Factory interface which has the possible products that it can create or make.
 Step 4: Create Combos (families) by implementing the abstract factory interface which implements above interface's memebers.
 Step 5: Factory class which creates the objects based on client's needs
 Step 6: Factory registry

   Alternatives to class registry 
 ````````````````````````````````
 1. Dependency Injection frameworks (Spring) manage the creation and injection of objects. In this case, we don't need registry, as the DI container manages creation
    and registration automatically.
 2. Configuration files: We can use files like JSON, XML etc to define which classes would be created for certain types.
                  
*/
import java.util.*;

// factory class
class Furniture {
    public static void main(String[] args) {

        FactoryRegistry.register("Victorian", VictorianFurniture.class);
        FactoryRegistry.register("ArtDeco", ArtFurniture.class);
        FactoryRegistry.register("Modern", ModernFurniture.class);

        System.out.println("****Victorian Furniture****");
        FurnitureFactory victoranFurniture = FactoryRegistry.getFurnitureFactory("Victorian");
        // victorian chair
        Chair victorianChair = victoranFurniture.chair();
        victorianChair.siton();
        // victorian sofa
        Sofa victorianSofa = victoranFurniture.sofa();
        victorianSofa.lieon();
        // victorian coffee table
        CoffeeTable victorianCoffeeTable = victoranFurniture.coffeetable();
        victorianCoffeeTable.keepThings();
        System.out.println();
        
        
        System.out.println("****Modern Furniture****");
        FurnitureFactory modernFurniture = FactoryRegistry.getFurnitureFactory("ArtDeco");
        // victorian chair
        Chair modernChair = modernFurniture.chair();
        modernChair.siton();
        // victorian sofa
        Sofa modernSofa = modernFurniture.sofa();
        modernSofa.lieon();
        // victorian coffee table
        CoffeeTable modernCoffeeTable = modernFurniture.coffeetable();
        modernCoffeeTable.keepThings();
        System.out.println();
        
        
        System.out.println("****Art Deco Furniture****");
        FurnitureFactory artFurniture = FactoryRegistry.getFurnitureFactory("Modern");
        // victorian chair
        Chair artChair = artFurniture.chair();
        artChair.siton();
        // victorian sofa
        Sofa artSofa = artFurniture.sofa();
        artSofa.lieon();
        // victorian coffee table
        CoffeeTable artCoffeeTable = artFurniture.coffeetable();
        artCoffeeTable.keepThings();
        System.out.println();
    }
}

// registry to store the classes, this helps us in avoiding the factory code modifications if we had to extend our system
class FactoryRegistry {
    public static Map<String, Class<? extends FurnitureFactory>> registry = new HashMap<>();

    public static void register(String furnitureStyle, Class<? extends FurnitureFactory> furnitureFactory) {
        registry.put(furnitureStyle, furnitureFactory);
    }

    public static FurnitureFactory getFurnitureFactory(String furnitureStyle) {
        try {
            return registry.get(furnitureStyle).newInstance(); // Create instance via reflection
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

// ---------- STEP 1 -----------

// Chair product interface
interface Chair {
    void siton(); // chair is used to sit
}

// Sofa product interface
interface Sofa {
    void lieon(); // sofa to lie and relax
}

// Coffee table product interface
interface CoffeeTable {
    void keepThings(); // keep things like coffee mug etc.
}

// ---------- STEP 2 -----------

// Each type will implement each product separately
// Art chair variant
class ArtChair implements Chair {
    public void siton() {
        System.out.println("Sitting on Art Chair");
    }
}
// Art sofa variant
class ArtSofa implements Sofa {
    public void lieon() {
        System.out.println("Lieing on Art Sofa");
    }
}
// Art Coffee table variant
class ArtCoffeeTable implements CoffeeTable {
    public void keepThings() {
        System.out.println("Keeping cups on Art Coffee table");
    }
}

// Vivtorian chair variant
class VictorianChair implements Chair {
    public void siton() {
        System.out.println("Sitting on Victorian Chair");
    }
}
// Vivtorian sofa variant
class VictorianSofa implements Sofa {
    public void lieon() {
        System.out.println("Lieing on Victorian Sofa");
    }
}
// Vivtorian coffee table variant
class VictorianCoffeeTable implements CoffeeTable {
    public void keepThings() {
        System.out.println("Keeping cups on Victorian Coffee table");
    }
}

// Modern chair variant
class ModernChair implements Chair {
    public void siton() {
        System.out.println("Sitting on Modern Chair");
    }
}
// Modern sofa variant
class ModernSofa implements Sofa {
    public void lieon() {
        System.out.println("Lieing on Modern Sofa");
    }
}
// Modern coffee table variant
class ModernCoffeeTable implements CoffeeTable {
    public void keepThings() {
        System.out.println("Keeping cups on Modern Coffee table");
    }
}

// ---------- STEP 3 -----------

// abstract  factory interface that creates chairs, sofas and coffee tables
interface FurnitureFactory {
    Chair chair();
    Sofa sofa();
    CoffeeTable coffeetable();
}

// ---------- STEP 3 -----------

// Victorian furniture factory (family)
class VictorianFurniture implements FurnitureFactory {
    public Chair chair() {
        return new VictorianChair();
    }
    
    public Sofa sofa() {
        return new VictorianSofa();
    }
    
    public CoffeeTable coffeetable() {
        return new VictorianCoffeeTable();
    }
}

// Art deco furniture factory (family)
class ArtFurniture implements FurnitureFactory {
    public Chair chair() {
        return new ArtChair();
    }
    
    public Sofa sofa() {
        return new ArtSofa();
    }
    
    public CoffeeTable coffeetable() {
        return new ArtCoffeeTable();
    }
}

// Modern furniture factory (family)
class ModernFurniture implements FurnitureFactory {
    public Chair chair() {
        return new ModernChair();
    }
    
    public Sofa sofa() {
        return new ModernSofa();
    }
    
    public CoffeeTable coffeetable() {
        return new ModernCoffeeTable();
    }
}