package sokoban.view;

import sokoban.controller.GameController;
import sokoban.model.Game;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class HudPanel extends JPanel {

    private final GameController controller;
    private final JLabel lblLevel;
    private final JLabel lblMovements;
    private final JLabel lblPushes;
    private final JButton btnUndo;
    private final JButton btnReset;

    public HudPanel(GameController controller) {
        this.controller = controller;
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        lblLevel = new JLabel();
        lblMovements = new JLabel();
        lblPushes = new JLabel();

        btnUndo = new JButton("Undo (U)");
        btnUndo.addActionListener(e -> controller.undo());
        // Evitamos que el botón robe el foco del teclado para no romper los KeyBindings
        btnUndo.setFocusable(false);

        btnReset = new JButton("Reiniciar (R)");
        btnReset.addActionListener(e -> controller.resetLevel());
        btnReset.setFocusable(false);

        add(lblLevel);
        add(lblMovements);
        add(lblPushes);
        add(btnUndo);
        add(btnReset);

        refresh();
    }

    /**
     * Sincroniza las etiquetas de texto con el estado en tiempo real del modelo
     * y habilita/deshabilita el botón de deshacer según el Caretaker.
     */
    public void refresh() {
        Game game = controller.getGame();

        lblLevel.setText("Nivel: " + controller.getCurrentLevelNumber());

        if (game != null) {
            lblMovements.setText("Movimientos: " + game.getMovements());
            lblPushes.setText("Empujes: " + game.getPushes());
        } else {
            lblMovements.setText("Movimientos: 0");
            lblPushes.setText("Empujes: 0");
        }

        // MVC Puro: Si el Caretaker bloqueó el deshacer (llegó a 3 consecutivos),
        // desactivamos visualmente el botón en la interfaz.
        if (controller.getCaretaker() != null) {
            btnUndo.setEnabled(controller.getCaretaker().canUndo());
        } else {
            btnUndo.setEnabled(false);
        }
    }
}