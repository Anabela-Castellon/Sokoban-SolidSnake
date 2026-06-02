package sokoban.model.elements;

public class ClosedWall extends BoardElement {

    public ClosedWall() {
        super("Muro cerrado", false, "M");
    }

    @Override
    public boolean isClosedWall() {
        return true;
    }
}