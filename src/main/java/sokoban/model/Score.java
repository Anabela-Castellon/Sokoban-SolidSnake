package sokoban.model;

public class Score {
    /**
     * Calcula el puntaje final de un nivel.
     *
     * Fórmula utilizada:
     *   1000 - 5 * movimientos - 10 * empujes - 20 * undoUsos
     * Se garantiza que el puntaje no sea negativo.
     */
    public static int calculate(int movements, int pushes, int undoUses) {
        int score = 1000 - (movements * 5) - (pushes * 10) - (undoUses * 20);
        return Math.max(score, 0);
    }
}
