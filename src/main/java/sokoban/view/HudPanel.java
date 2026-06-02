package sokoban.view;

import sokoban.controller.GameController;
import sokoban.model.Game;
import sokoban.patterns.command.CommandHistory;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;

public class HudPanel extends JPanel {

    private final GameController controller;

    private final JLabel levelLabel;
    private final JLabel movementsLabel;
    private final JLabel pushesLabel;
    private final JLabel undoInfoLabel;

    public HudPanel(GameController controller) {
        this.controller = controller;

        setLayout(new FlowLayout(FlowLayout.LEFT, 14, 10));
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        Font font = new Font("SansSerif", Font.BOLD, 14);

        levelLabel = new JLabel();
        movementsLabel = new JLabel();
        pushesLabel = new JLabel();
        undoInfoLabel = new JLabel();

        levelLabel.setFont(font);
        movementsLabel.setFont(font);
        pushesLabel.setFont(font);
        undoInfoLabel.setFont(font);

        JButton undoButton = new JButton("Undo");
        JButton resetButton = new JButton("Reiniciar");

        undoButton.addActionListener(e -> controller.undo());
        resetButton.addActionListener(e -> controller.resetLevel());

        add(levelLabel);
        add(movementsLabel);
        add(pushesLabel);
        add(undoInfoLabel);
        add(undoButton);
        add(resetButton);

        refresh();
    }

    public void refresh() {
        Game game = controller.getGame();
        CommandHistory history = controller.getHistory();

        levelLabel.setText("Nivel: " + controller.getCurrentLevelNumber());
        movementsLabel.setText("Movimientos: " + game.getMovements());
        pushesLabel.setText("Empujes: " + game.getPushes());
        undoInfoLabel.setText(
                "Undo usados seguidos: " + history.getConsecutiveUndoUses()
                        + " | Comandos: " + history.getAvailableCommands()
        );
    }
}