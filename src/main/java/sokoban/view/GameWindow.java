package sokoban.view;

import sokoban.controller.GameController;
import sokoban.model.Direction;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane; // Añadido para gestionar los diálogos en la vista
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

    /**
     * Muestra un diálogo de confirmación de victoria preguntando si se quiere avanzar de nivel.
     * @return true si el usuario presiona "Sí", false en caso contrario.
     */
    public boolean showVictoryDialog(String summaryMessage) {
        int option = JOptionPane.showConfirmDialog(
                this,
                summaryMessage + "\n\n¿Querés pasar al siguiente nivel?",
                "Victoria",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );
        return option == JOptionPane.YES_OPTION;
    }

    /**
     * Muestra un resumen final cuando se completa el último nivel.
     */
    public void showGameCompletedDialog(String summaryMessage) {
        JOptionPane.showMessageDialog(
                this,
                summaryMessage + "\n\n¡Felicitaciones! Completaste todos los niveles.",
                "Juego completado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Muestra una ventana emergente de error personalizada.
     * @param message Mensaje detallado del error.
     */
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
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