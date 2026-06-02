package sokoban.model;

import sokoban.model.boxes.Box;

public class Game {
    private final Board board;
    private int movements;
    private int pushes;
    private int currentLevel;
    private boolean victory;

    public Game(Board board) {
        this.board = board;
        this.movements = 0;
        this.pushes = 0;
        this.currentLevel = 1;
        this.victory = false;
    }

    public boolean movePlayer(Direction direction) {
        if (victory) {
            return false;
        }

        Position currentPosition = board.getPlayer().getPosition();
        Position nextPosition = currentPosition.move(direction);

        if (!board.isInside(nextPosition)) {
            return false;
        }

        Box box = board.getBoxAt(nextPosition);

        if (box != null) {
            if (!box.canBePushed()) {
                return false;
            }

            Position nextBoxPosition = box.getPosition().move(direction);

            if (!board.isWalkable(nextBoxPosition)) {
                return false;
            }

            Position finalBoxPosition = board
                    .getElementAt(nextBoxPosition)
                    .getMovementStrategy()
                    .calculateFinalPosition(board, nextBoxPosition, direction);

            box.setPosition(finalBoxPosition);
            box.onPushed(board);

            board.getPlayer().setPosition(nextPosition);
            movements++;
            pushes++;

            checkVictory();
            return true;
        }

        if (board.isWalkable(nextPosition)) {
            board.getPlayer().setPosition(nextPosition);
            movements++;

            checkVictory();
            return true;
        }

        return false;
    }

    private void checkVictory() {
        for (Box box : board.getBoxes()) {
            if (!box.isCorrectlyPlaced(board)) {
                victory = false;
                return;
            }
        }

        victory = true;
    }

    public Board getBoard() {
        return board;
    }

    public int getMovements() {
        return movements;
    }

    public int getPushes() {
        return pushes;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public boolean isVictory() {
        return victory;
    }

    public void restoreCounters(int movements, int pushes) {
        this.movements = movements;
        this.pushes = pushes;
    }
}