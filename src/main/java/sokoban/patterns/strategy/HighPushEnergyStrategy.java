package sokoban.patterns.strategy;

public class HighPushEnergyStrategy implements PushEnergyStrategy {
    @Override
    public int getEnergyCost() {
        return 3;
    }
}
