package sokoban.model.boxes;

import sokoban.model.Position;

public class KeyBox extends Box {

    public KeyBox(Position position) {
        super(position);
    }

    @Override
    public String getType() {
        return "Caja llave";
    }

    @Override
    public String getSymbol() {
        return "K";
    }
}