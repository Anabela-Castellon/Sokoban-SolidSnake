package sokoban.patterns.state;

import sokoban.model.boxes.FragileBox;

public class BrokenState implements FragileBoxState {

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public void handlePush(FragileBox box) {
        // La caja rota ya no puede ser empujada.
    }

    @Override
    public String getStateName() {
        return "Rota";
    }
}