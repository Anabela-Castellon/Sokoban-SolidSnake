package sokoban.patterns.state;

import sokoban.model.boxes.FragileBox;

public class DamagedState implements FragileBoxState {

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public void handlePush(FragileBox box) {
        box.reduceResistance();
        box.updateStateByResistance();
    }

    @Override
    public String getStateName() {
        return "Dañada";
    }
}