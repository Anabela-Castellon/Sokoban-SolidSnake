package sokoban.controller;

import sokoban.model.Direction;
import sokoban.model.Game;
import sokoban.model.Level;
import sokoban.model.Score;
import sokoban.patterns.factory.LevelFactory;
import sokoban.patterns.factory.txtLevelFactory; // Respetamos tu nombre de clase actual
import sokoban.patterns.memento.GameSnapshot;
import sokoban.patterns.memento.HistoryCaretaker;
import sokoban.view.GameWindow;

import java.io.IOException;

/**
 * Controlador principal del juego Sokoban (MVC).
 * Coordina la carga de niveles mediante Factory Method y gestiona la
 * reversibilidad atómica del estado mediante el patrón Memento.
 */
public class GameController {

    private final LevelFactory levelFactory;
    private final String[] levelPaths = {
            "levels/nivel1.txt",
            "levels/nivel2.txt",
            "levels/nivel3.txt"
    };

    private Game game;
    private HistoryCaretaker caretaker;
    private int currentLevelIndex;
    private GameWindow window;

    public GameController() throws IOException {
        // Inicializa la fábrica y arranca el nivel cero
        this.levelFactory = new txtLevelFactory();
        this.currentLevelIndex = 0;
        loadCurrentLevel();
    }

    public void setWindow(GameWindow window) {
        this.window = window;
        refreshView();
    }

    /**
     * Intenta desplazar al jugador en la dirección indicada.
     * Saca un snapshot preventivo para el historial de Memento.
     */
    public void move(Direction direction) {
        if (game == null) return;

        // 1. CAPTURA: Sacamos la foto del estado exacto ANTES de mutar el modelo
        GameSnapshot snapshot = game.createSnapshot();

        // 2. EJECUCIÓN: Intentamos realizar el movimiento en el modelo
        boolean moved = game.movePlayer(direction);

        if (moved) {
            // 3. REGISTRO: Si el movimiento fue válido, el caretaker guarda la foto
            caretaker.addSnapshot(snapshot);
            refreshView();

            // Verificación de victoria transaccional
            if (game.isVictory()) {
                handleVictory();
            }
        }
    }

    /**
     * Aplica la rutina de deshacer retrocediendo 5 pasos atómicos
     * bajo las restricciones de HistoryCaretaker (máximo 3 usos consecutivos).
     */
    public void undo() {
        if (caretaker == null) return;

        // El cuidador centraliza las reglas de tamaño mínimo de pila y usos consecutivos
        if (caretaker.canUndo()) {
            GameSnapshot previousState = caretaker.undoLastFive();
            if (previousState != null) {
                // Inyectamos de golpe el estado pasado directamente en el juego
                game.restoreFromSnapshot(previousState);
                refreshView();
            } else {
                showError("No hay más movimientos en el historial para deshacer.");
            }
        } else {
            showError("¡Límite de Undo alcanzado! Requiere más movimientos o máximo 3 usos consecutivos.");
        }
    }

    private String buildLevelSummary() {
        int movements = game.getMovements();
        int pushes = game.getPushes();
        int undoUses = caretaker != null ? caretaker.getTotalUndoUses() : 0;
        int score = Score.calculate(movements, pushes, undoUses);

        return "Resumen del nivel " + getCurrentLevelNumber() + ":\n"
                + "Movimientos: " + movements + "\n"
                + "Empujes: " + pushes + "\n"
                + "Undo usados: " + undoUses + "\n"
                + "Puntaje final: " + score;
    }

    /**
     * Reinicia el nivel actual regenerando el tablero y limpiando el historial.
     */
    public void resetLevel() {
        try {
            loadCurrentLevel();
            refreshView();
        } catch (IOException e) {
            showError("Error crítico: No se pudo reiniciar el nivel.");
        }
    }

    /**
     * Método interno para instanciar el nivel a través de la fábrica de texto.
     */
    private void loadCurrentLevel() throws IOException {
        Level level = levelFactory.createLevel(levelPaths[currentLevelIndex], currentLevelIndex + 1);
        this.game = new Game(level.getBoard());
        this.caretaker = new HistoryCaretaker(); // Nace un historial limpio para el nivel
    }

    /**
     * Controla la transición de niveles delegando los diálogos a la vista.
     */
    private void handleVictory() {
        String summary = buildLevelSummary();

        if (currentLevelIndex < levelPaths.length - 1) {
            // MVC Limpio: La vista decide cómo mostrar el cartel y devuelve la opción elegida
            boolean nextLevel = window.showVictoryDialog(summary);

            if (nextLevel) {
                currentLevelIndex++;
                try {
                    loadCurrentLevel();
                    refreshView();
                } catch (IOException e) {
                    showError("Error: No se pudo cargar el siguiente nivel.");
                }
            } else {
                resetLevel(); // Si cancela, reinicia el nivel actual
            }
        } else {
            // Si es el último mapa, mostramos el cierre definitivo con resumen
            window.showGameCompletedDialog(summary);
        }
    }

    private void refreshView() {
        if (window != null) {
            window.refresh();
        }
    }

    private void showError(String message) {
        if (window != null) {
            window.showErrorMessage(message);
        }
    }

    // Getters pasivos para cumplimiento estricto de UI reactiva
    public Game getGame() {
        return game;
    }

    public HistoryCaretaker getCaretaker() {
        return caretaker;
    }

    public int getCurrentLevelNumber() {
        return currentLevelIndex + 1;
    }

    public int getUndoUses() {
        return caretaker != null ? caretaker.getTotalUndoUses() : 0;
    }
}