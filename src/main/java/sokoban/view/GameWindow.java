package sokoban.view;

import sokoban.controller.GameController;
import sokoban.model.Direction;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class GameWindow extends JFrame {

    private final GameController controller;
    private final BoardPanel boardPanel;
    private final HudPanel hudPanel;

    public GameWindow(GameController controller) {
        this.controller = controller;

        setTitle("Sokoban 2D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        this.hudPanel = new HudPanel(controller);
        this.boardPanel = new BoardPanel(controller);

        add(hudPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        registerKeyBindings();

        pack();
        setLocationRelativeTo(null);

        controller.setWindow(this);
    }

    public void refresh() {
        boardPanel.refreshSize();
        boardPanel.repaint();
        hudPanel.refresh();
        pack();
    }

    private void registerKeyBindings() {
        JComponent root = getRootPane();

        bind(root, "UP", () -> controller.move(Direction.UP));
        bind(root, "DOWN", () -> controller.move(Direction.DOWN));
        bind(root, "LEFT", () -> controller.move(Direction.LEFT));
        bind(root, "RIGHT", () -> controller.move(Direction.RIGHT));

        bind(root, "W", () -> controller.move(Direction.UP));
        bind(root, "S", () -> controller.move(Direction.DOWN));
        bind(root, "A", () -> controller.move(Direction.LEFT));
        bind(root, "D", () -> controller.move(Direction.RIGHT));

        bind(root, "U", controller::undo);
        bind(root, "R", controller::resetLevel);
    }

    private void bind(JComponent component, String key, Runnable action) {
        String actionName = "action_" + key;

        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(key), actionName);

        component.getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }
}
