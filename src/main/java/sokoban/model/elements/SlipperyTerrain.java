package sokoban.model.elements;

import sokoban.patterns.strategy.SlipperyMovementStrategy;

public class SlipperyTerrain extends BoardElement {

    public SlipperyTerrain() {
        super("Terreno resbaladizo", true, "~", new SlipperyMovementStrategy());
    }
}
