package sokoban.patterns.state;

import sokoban.model.boxes.FragileBox;

public interface FragileBoxState {
    boolean canBePushed();

    void handlePush(FragileBox box);

    String getStateName();
}