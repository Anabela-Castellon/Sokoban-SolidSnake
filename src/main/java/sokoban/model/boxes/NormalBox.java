package sokoban.model.boxes;

import sokoban.model.Position;

public class NormalBox extends Box {

    public NormalBox(Position position) {
        super(position);
    }

    @Override
    public String getType() {
        return "Caja normal";
    }

    @Override
    public String getSymbol() {
        return "$";
    }
}