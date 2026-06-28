package sokoban.model.elements;

public class OpenWall extends BoardElement {

    public java.lang.Object isClosedWall;

    public OpenWall() {
        // 'true' define que la celda es caminable, permitiendo que Snake la atraviese
        super("Muro abierto", true, "O");
    }

    @Override
    public boolean isClosedWall() {
        // Explícitamente le confirmamos al motor que este bloque YA NO es un muro cerrado
        return false;
    }
}