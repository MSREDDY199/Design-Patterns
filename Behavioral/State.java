/*
Definition:
    State is a behavioral design pattern that lets an object alter its behavior when its internal state changes. It appears as if the object changed its class.

Problem:
    State machines are usually implemented with lots of conditional statements (if or switch) that select the appropriate behavior depending on the current state of the object.
    The biggest weakness of a state machine based on conditionals reveals itself once we start adding more and more states and state-dependent behaviors to the 
    Document class (Draft, Moderation and Published). Code like this is very difficult to maintain because any change to the transition logic may require changing 
    state conditionals in every method.

Solution:
    The State pattern suggests that you create new classes for all possible states of an object and extract all state-specific behaviors into these classes.
    To transition the context into another state, replace the active state object with another object that represents that new state. This is possible only if all state classes 
    follow the same interface and the context itself works with these objects through that interface.

Use cases:
    1. Use the State pattern when you have an object that behaves differently depending on its current state, the number of states is enormous, and the state-specific code changes frequently.
    2. Use the pattern when you have a class polluted with massive conditionals that alter how the class behaves according to the current values of the class’s fields.
    3. Use State when you have a lot of duplicate code across similar states and transitions of a condition-based state machine.

Pros:
    1. Single Responsibility Principle, You can decouple classes that invoke operations from classes that perform operations.
    2. Open/Closed Principle, You can introduce new handlers into the app without breaking the existing client code. 
    3. Simplify the code of the context by eliminating bulky state machine conditionals.

Cons:
    1. Applying the pattern can be overkill if a state machine has only a few states or rarely changes.

*/

public class State {
    public static void main(String[] args) {
        MediaPlayer player = new MediaPlayer();

        player.pressPlay();   // Output: Starting playback.
        player.pressPause();  // Output: Pausing playback.
        player.pressPlay();   // Output: Resuming playback.
        player.pressStop();   // Output: Stopping playback.
        player.pressPause();  // Output: Cannot pause. Media is already stopped.
    }
}

class MediaPlayer {
    private State state;

    public MediaPlayer() {
        // Initial state is "Stopped"
        this.state = new StoppedState();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void pressPlay() {
        state.play(this);
    }

    public void pressPause() {
        state.pause(this);
    }

    public void pressStop() {
        state.stop(this);
    }
}

// State interface defines the behavior for each state.
interface State {
    void play(MediaPlayer player);
    void pause(MediaPlayer player);
    void stop(MediaPlayer player);
}


// Playing State
class PlayingState implements State {
    @Override
    public void play(MediaPlayer player) {
        System.out.println("Already playing.");
    }

    @Override
    public void pause(MediaPlayer player) {
        System.out.println("Pausing playback.");
        player.setState(new PausedState());
    }

    @Override
    public void stop(MediaPlayer player) {
        System.out.println("Stopping playback.");
        player.setState(new StoppedState());
    }
}

// Paused State
class PausedState implements State {
    @Override
    public void play(MediaPlayer player) {
        System.out.println("Resuming playback.");
        player.setState(new PlayingState());
    }

    @Override
    public void pause(MediaPlayer player) {
        System.out.println("Already paused.");
    }

    @Override
    public void stop(MediaPlayer player) {
        System.out.println("Stopping playback.");
        player.setState(new StoppedState());
    }
}

// Stopped State
class StoppedState implements State {
    @Override
    public void play(MediaPlayer player) {
        System.out.println("Starting playback.");
        player.setState(new PlayingState());
    }

    @Override
    public void pause(MediaPlayer player) {
        System.out.println("Cannot pause. Media is already stopped.");
    }

    @Override
    public void stop(MediaPlayer player) {
        System.out.println("Already stopped.");
    }
}
