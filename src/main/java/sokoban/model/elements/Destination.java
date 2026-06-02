package sokoban.model.elements;

public class Destination extends BoardElement {

    public Destination() {
        super("Destino", true, ".");
    }

    @Override
    public boolean isDestinationCell() {
        return true;
    }
}