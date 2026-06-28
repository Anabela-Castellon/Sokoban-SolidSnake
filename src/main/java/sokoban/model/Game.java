package sokoban.model;

import sokoban.model.boxes.Box;
import sokoban.model.boxes.FragileBox;
import sokoban.model.boxes.KeyBox;
import sokoban.model.boxes.NormalBox;
import sokoban.model.elements.BoardElement;
import sokoban.patterns.memento.BoxMementoData;
import sokoban.patterns.memento.GameSnapshot;

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

    /**
     * Crea una captura instantánea (Memento) del estado actual del juego
     * antes de realizar cualquier mutación en el turno.
     */
    public GameSnapshot createSnapshot() {
        return new GameSnapshot(
                board.getBoxes(),
                movements,
                pushes,
                board.getPlayer().getPosition(),
                board.getCellsCopy()
        );
    }

    /**
     * Restaura el estado del juego de forma determinista a partir de un Snapshot del pasado.
     */
    public void restoreFromSnapshot(GameSnapshot snapshot) {
        if (snapshot == null) return;

        // 1. Restauramos los contadores del HUD
        this.movements = snapshot.getMovements();
        this.pushes = snapshot.getPushes();

        // 2. Restauramos la grilla de celdas si hubo cambios (por ejemplo, paredes abiertas)
        BoardElement[][] savedCells = snapshot.getSavedCells();
        if (savedCells != null) {
            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getCols(); col++) {
                    board.setElementAt(new Position(row, col), savedCells[row][col]);
                }
            }
        }

        // 3. Limpiamos las cajas actuales de la grilla del tablero
        this.board.getBoxes().clear();

        // 4. Reconstruimos y reinsertamos las cajas en el tablero a partir de sus datos históricos
        for (BoxMementoData boxData : snapshot.getSavedBoxes()) {
            switch (boxData.getBoxType()) {
                case "FRAGILE" -> this.board.addBox(new FragileBox(boxData.getPosition(), boxData.getCurrentResistance()));
                case "KEY" -> this.board.addBox(new KeyBox(boxData.getPosition()));
                default -> this.board.addBox(new NormalBox(boxData.getPosition()));
            }
        }

        // 5. Restauramos la posición del jugador
        this.board.getPlayer().setPosition(snapshot.getPlayerPosition());

        // 6. Recalculamos la victoria para asegurar que el flag visual quede sincronizado
        checkVictory();
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
}