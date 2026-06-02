package sokoban;

import sokoban.controller.GameController;
import sokoban.view.GameWindow;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GameController controller = new GameController();
                GameWindow window = new GameWindow(controller);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();

                JOptionPane.showMessageDialog(
                        null,
                        "No se pudo iniciar el juego:\n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}