package sokoban.model.elements;

import sokoban.model.Position;

public class Player {
    private Position position;
    private int energy;
    private final int maxEnergy;

    public Player(Position position) {
        this(position, 20);
    }

    public Player(Position position, int maxEnergy) {
        this.position = position;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public boolean hasEnergy(int cost) {
        return energy >= cost;
    }

    public void consumeEnergy(int cost) {
        if (cost < 0) {
            return;
        }
        this.energy = Math.max(0, this.energy - cost);
    }

    public void addEnergy(int amount) {
        if (amount < 0) {
            return;
        }
        this.energy = Math.min(maxEnergy, this.energy + amount);
    }

    public void setEnergy(int energy) {
        if (energy < 0) {
            this.energy = 0;
        } else {
            this.energy = Math.min(maxEnergy, energy);
        }
    }
}
