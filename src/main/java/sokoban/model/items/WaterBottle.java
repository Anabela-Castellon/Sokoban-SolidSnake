package sokoban.model.items;

import sokoban.model.Position;
import sokoban.model.elements.Player;

public class WaterBottle implements Item {
    private static final int ENERGY_RESTORE = 12;
    private final Position position;

    public WaterBottle(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String getType() {
        return "WATER_BOTTLE";
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    @Override
    public void apply(Player player) {
        player.addEnergy(ENERGY_RESTORE);
    }
}
