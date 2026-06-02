package sokoban.model.boxes;

import sokoban.model.Board;
import sokoban.model.Position;
import sokoban.patterns.state.BrokenState;
import sokoban.patterns.state.DamagedState;
import sokoban.patterns.state.FragileBoxState;
import sokoban.patterns.state.IntactState;

public class FragileBox extends Box {
    private int resistance;
    private FragileBoxState state;

    public FragileBox(Position position, int resistance) {
        super(position);
        this.resistance = resistance;
        updateStateByResistance();
    }

    public int getResistance() {
        return resistance;
    }

    public FragileBoxState getState() {
        return state;
    }

    public void reduceResistance() {
        resistance--;
    }

    public void restoreResistance(int resistance) {
        this.resistance = resistance;
        updateStateByResistance();
    }

    public void updateStateByResistance() {
        if (resistance <= 0) {
            state = new BrokenState();
        } else if (resistance <= 2) {
            state = new DamagedState();
        } else {
            state = new IntactState();
        }
    }

    @Override
    public String getType() {
        return "Caja frágil";
    }

    @Override
    public String getSymbol() {
        if (resistance <= 0) {
            return "X";
        }

        return String.valueOf(resistance);
    }

    @Override
    public boolean canBePushed() {
        return state.canBePushed();
    }

    @Override
    public void onPushed(Board board) {
        state.handlePush(this);
    }
}