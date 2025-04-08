/*
Definition:
    The Iterator Design Pattern is a behavioral design pattern that provides a way to access elements of a collection (like lists, trees, or arrays) sequentially without exposing 
    the underlying structure of the collection. It decouples the iteration logic from the collection itself, making it easier to traverse complex data structures in a uniform way.

Problem:
    Imagine you have different types of data structures like ArrayList, LinkedList, and Tree. Each of these data structures has a different way of storing and accessing its elements. 
    If you want to iterate over their elements in a consistent manner, you would have to implement the traversal logic for each structure manually, 
    making the client code tightly coupled to the specific implementation of each data structure.

Solution:
    The Iterator Pattern separates the iteration logic from the data structure itself. It introduces an Iterator interface or class that provides methods like:
    1. hasNext(): Checks if there are more elements to iterate.
    2. next(): Returns the next element and advances the iterator.
    3. remove(): (Optional) Removes the last element returned by the iterator.

Use cases:
    1. Use the Iterator pattern when your collection has a complex data structure under the hood, but you want to hide its complexity from clients (either for convenience or security reasons).
    2. Use the pattern to reduce duplication of the traversal code across your app.
    3. Use the Iterator when you want your code to be able to traverse different data structures or when types of these structures are unknown beforehand.

Pros:
    1. Single Responsibility Principle. You can clean up the client code and the collections by extracting bulky traversal algorithms into separate classes.
    2. Open/Closed Principle. You can implement new types of collections and iterators and pass them to existing code without breaking anything.
    3. You can iterate over the same collection in parallel because each iterator object contains its own iteration state.
    4. For the same reason, you can delay an iteration and continue it when needed.

Cons:
    1. Applying the pattern can be an overkill if your app only works with simple collections.
    2. Using an iterator may be less efficient than going through elements of some specialized collections directly.

*/

import java.util.*;

// Iterator interface
interface Iterator<T> {
    boolean hasNext();
    T next();
}

// Book class
class Book {
    private String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

// BookCollection class
class BookCollection {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public Iterator<Book> createIterator() {
        return new BookIterator(books);
    }
}

// Magazine class
class Magazine {
    private String name;

    public Magazine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// MagazineCollection class
class MagazineCollection {
    private Set<Magazine> magazines = new HashSet<>();

    public void addMagazine(Magazine magazine) {
        magazines.add(magazine);
    }

    public Iterator<Magazine> createIterator() {
        return new MagazineIterator(magazines);
    }
}

// BookIterator implementation
class BookIterator implements Iterator<Book> {
    private List<Book> books;
    private int position = 0;

    public BookIterator(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean hasNext() {
        return position < books.size();
    }

    @Override
    public Book next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return books.get(position++);
    }
}

// MagazineIterator implementation
class MagazineIterator implements Iterator<Magazine> {
    private List<Magazine> magazines;
    private int position = 0;

    public MagazineIterator(Set<Magazine> magazines) {
        this.magazines = new ArrayList<>(magazines);
    }

    @Override
    public boolean hasNext() {
        return position < magazines.size();
    }

    @Override
    public Magazine next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return magazines.get(position++);
    }
}

// Main class for testing
public class Main {
    public static void main(String[] args) {
        // Create and test BookCollection
        BookCollection bookCollection = new BookCollection();
        bookCollection.addBook(new Book("Design Patterns"));
        bookCollection.addBook(new Book("Clean Code"));
        bookCollection.addBook(new Book("Refactoring"));

        Iterator<Book> bookIterator = bookCollection.createIterator();
        System.out.println("Books:");
        while (bookIterator.hasNext()) {
            System.out.println(bookIterator.next().getTitle());
        }

        // Create and test MagazineCollection
        MagazineCollection magazineCollection = new MagazineCollection();
        magazineCollection.addMagazine(new Magazine("Tech Today"));
        magazineCollection.addMagazine(new Magazine("Software Weekly"));
        magazineCollection.addMagazine(new Magazine("Coding Digest"));

        Iterator<Magazine> magazineIterator = magazineCollection.createIterator();
        System.out.println("\nMagazines:");
        while (magazineIterator.hasNext()) {
            System.out.println(magazineIterator.next().getName());
        }
    }
}
