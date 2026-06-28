package sokoban.patterns.strategy;

public class NormalPushEnergyStrategy implements PushEnergyStrategy {
    @Override
    public int getEnergyCost() {
        return 1;
    }
}
