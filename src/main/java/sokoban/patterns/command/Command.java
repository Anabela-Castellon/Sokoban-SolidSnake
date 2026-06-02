package sokoban.patterns.command;

public interface Command {
    void execute();

    void undo();

    boolean wasExecuted();
}