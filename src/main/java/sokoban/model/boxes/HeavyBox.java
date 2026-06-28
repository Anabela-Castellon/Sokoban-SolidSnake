package sokoban.model.boxes;

import sokoban.model.Position;
import sokoban.patterns.strategy.HighPushEnergyStrategy;

public class HeavyBox extends Box {

    public HeavyBox(Position position) {
        super(position, new HighPushEnergyStrategy());
    }

    @Override
    public String getType() {
        return "HEAVY";
    }

    @Override
    public String getSymbol() {
        return "H";
    }
}
