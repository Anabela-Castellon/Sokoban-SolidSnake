package sokoban.model.elements;

public class LockCell extends BoardElement {

    public LockCell() {
        super("Casilla cerrojo", true, "C");
    }

    @Override
    public boolean isLockCell() {
        return true;
    }
}