package sokoban.model.boxes;

import sokoban.model.Board;
import sokoban.model.elements.BoardElement;

public class KeyBox extends Box {

    public KeyBox(sokoban.model.Position position) {
        super(position);
    }

    @Override
    public String getType() {
        return "KEY";
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    @Override
    public void onPushed(Board board) {
        // 1. Le preguntamos al tablero qué elemento hay justo abajo de nuestra posición actual
        BoardElement currentCell = board.getElementAt(this.getPosition());

        // 2. Usamos el flag polimórfico isLockCell() que programamos en los elementos
        if (currentCell != null && currentCell.isLockCell()) {
            // 3. ¡Boom! Si es un cerrojo, le ordenamos al tablero que abra las compuertas
            board.openClosedWalls();
        }
    }
}