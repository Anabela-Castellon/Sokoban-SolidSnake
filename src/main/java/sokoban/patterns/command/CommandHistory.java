package sokoban.patterns.command;

import java.util.Stack;

public class CommandHistory {
    private final Stack<Command> history;
    private int consecutiveUndoUses;

    public CommandHistory() {
        this.history = new Stack<>();
        this.consecutiveUndoUses = 0;
    }

    public void add(Command command) {
        if (command.wasExecuted()) {
            history.push(command);
            consecutiveUndoUses = 0;
        }
    }

    public void undoLastFive() {
        if (consecutiveUndoUses >= 3) {
            System.out.println("No se puede usar undo más de 3 veces consecutivas.");
            return;
        }

        int undone = 0;

        while (!history.isEmpty() && undone < 5) {
            Command command = history.pop();
            command.undo();
            undone++;
        }

        if (undone > 0) {
            consecutiveUndoUses++;
            System.out.println("Se deshicieron " + undone + " movimientos.");
        }
    }

    public int getConsecutiveUndoUses() {
        return consecutiveUndoUses;
    }

    public int getAvailableCommands() {
        return history.size();
    }
}
