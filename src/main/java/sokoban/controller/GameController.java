package sokoban.controller;

import sokoban.model.Direction;
import sokoban.model.Game;
import sokoban.model.Level;
import sokoban.patterns.command.Command;
import sokoban.patterns.command.CommandHistory;
import sokoban.patterns.command.MoveCommand;
import sokoban.patterns.factory.LevelFactory;
import sokoban.patterns.factory.txtLevelFactory;
import sokoban.view.GameWindow;

import javax.swing.JOptionPane;
import java.io.IOException;

public class GameController {

    private final LevelFactory levelFactory;
    private final String[] levelPaths = {
            "levels/nivel1.txt",
            "levels/nivel2.txt",
            "levels/nivel3.txt"
    };

    private Game game;
    private CommandHistory history;
    private int currentLevelIndex;
    private GameWindow window;

    public GameController() throws IOException {
        this.levelFactory = new txtLevelFactory();
        this.currentLevelIndex = 0;
        loadCurrentLevel();
    }

    public void setWindow(GameWindow window) {
        this.window = window;
        refreshView();
    }

    public Game getGame() {
        return game;
    }

    public CommandHistory getHistory() {
        return history;
    }

    public int getCurrentLevelNumber() {
        return currentLevelIndex + 1;
    }

    public void move(Direction direction) {
        Command command = new MoveCommand(game, direction);
        command.execute();
        history.add(command);

        refreshView();

        if (game.isVictory()) {
            handleVictory();
        }
    }

    public void undo() {
        history.undoLastFive();
        refreshView();
    }

    public void resetLevel() {
        try {
            loadCurrentLevel();
            refreshView();
        } catch (IOException e) {
            showError("No se pudo reiniciar el nivel.");
        }
    }

    private void loadCurrentLevel() throws IOException {
        Level level = levelFactory.createLevel(levelPaths[currentLevelIndex], currentLevelIndex + 1);
        this.game = new Game(level.getBoard());
        this.history = new CommandHistory();
    }

    private void handleVictory() {
        if (currentLevelIndex < levelPaths.length - 1) {
            int option = JOptionPane.showConfirmDialog(
                    window,
                    "¡Nivel completado!\n¿Querés pasar al siguiente nivel?",
                    "Victoria",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {
                currentLevelIndex++;
                try {
                    loadCurrentLevel();
                    refreshView();
                } catch (IOException e) {
                    showError("No se pudo cargar el siguiente nivel.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    window,
                    "¡Felicitaciones!\nCompletaste todos los niveles.",
                    "Juego completado",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void refreshView() {
        if (window != null) {
            window.refresh();
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                window,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}