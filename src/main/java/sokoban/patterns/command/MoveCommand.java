package sokoban.patterns.command;

import sokoban.model.Direction;
import sokoban.model.Game;
import sokoban.model.Position;
import sokoban.model.boxes.Box;
import sokoban.model.boxes.FragileBox;

import java.util.HashMap;
import java.util.Map;

public class MoveCommand implements Command {
    private final Game game;
    private final Direction direction;

    private Position previousPlayerPosition;
    private final Map<Box, Position> previousBoxPositions;
    private final Map<FragileBox, Integer> previousFragileResistances;

    private int previousMovements;
    private int previousPushes;

    private boolean executed;

    public MoveCommand(Game game, Direction direction) {
        this.game = game;
        this.direction = direction;
        this.previousBoxPositions = new HashMap<>();
        this.previousFragileResistances = new HashMap<>();
        this.executed = false;
    }

    @Override
    public void execute() {
        savePreviousState();
        executed = game.movePlayer(direction);
    }

    @Override
    public void undo() {
        if (!executed) {
            return;
        }

        game.getBoard().getPlayer().setPosition(previousPlayerPosition);

        for (Map.Entry<Box, Position> entry : previousBoxPositions.entrySet()) {
            Box box = entry.getKey();
            Position previousPosition = entry.getValue();
            box.setPosition(previousPosition);
        }

        for (Map.Entry<FragileBox, Integer> entry : previousFragileResistances.entrySet()) {
            FragileBox fragileBox = entry.getKey();
            Integer previousResistance = entry.getValue();
            fragileBox.restoreResistance(previousResistance);
        }

        game.restoreCounters(previousMovements, previousPushes);
    }

    @Override
    public boolean wasExecuted() {
        return executed;
    }

    private void savePreviousState() {
        previousPlayerPosition = game.getBoard().getPlayer().getPosition();

        previousBoxPositions.clear();
        previousFragileResistances.clear();

        for (Box box : game.getBoard().getBoxes()) {
            previousBoxPositions.put(box, box.getPosition());

            if (box instanceof FragileBox) {
                FragileBox fragileBox = (FragileBox) box;
                previousFragileResistances.put(fragileBox, fragileBox.getResistance());
            }
        }

        previousMovements = game.getMovements();
        previousPushes = game.getPushes();
    }
}