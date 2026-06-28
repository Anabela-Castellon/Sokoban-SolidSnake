package sokoban.patterns.memento;

import java.util.Stack;

public class HistoryCaretaker {

    private final Stack<GameSnapshot> mementoStack = new Stack<>();
    private int consecutiveUndoUses = 0; // Control de la consigna UADE para el límite de 3
    private int totalUndoUses = 0;    // Seguimiento del uso histórico de deshacer

    /**
     * Guarda un nuevo snapshot en la pila de historia.
     * Al realizar un movimiento válido, el contador de usos consecutivos se resetea.
     */
    public void addSnapshot(GameSnapshot snapshot) {
        mementoStack.push(snapshot);
        if (mementoStack.size() > 15) {
            mementoStack.remove(0);
        }
        consecutiveUndoUses = 0;
    }

    /**
     * Realiza el retroceso de a 5 movimientos descartando las 4 fotos superiores
     * y retornando la quinta.
     */
    public GameSnapshot undoLastFive() {
        // Validación obligatoria: Necesitamos al menos 5 estados para retroceder 5 pasos,
        // y no haber superado el límite de 3 usos consecutivos.
        if (mementoStack.size() < 5 || consecutiveUndoUses >= 3) {
            return null;
        }

        // 1. Descartamos las 4 fotos del pasado inmediato
        for (int i = 0; i < 4; i++) {
            if (!mementoStack.isEmpty()) {
                mementoStack.pop();
            }
        }

        // 2. Extraemos la 5ta foto que es el estado exacto al que queremos regresar
        GameSnapshot targetSnapshot = null;
        if (!mementoStack.isEmpty()) {
            targetSnapshot = mementoStack.pop();
        }

        // 3. Registramos el uso consecutivo para el bloqueo perimetral
        if (targetSnapshot != null) {
            consecutiveUndoUses++;
            totalUndoUses++;
        }

        return targetSnapshot;
    }

    /**
     * Determina si el botón Undo debe estar habilitado en la interfaz.
     */
    public boolean canUndo() {
        return mementoStack.size() >= 5 && consecutiveUndoUses < 3;
    }

    public int getTotalUndoUses() {
        return totalUndoUses;
    }

    /**
     * Limpia el historial por completo al reiniciar el nivel.
     */
    public void clear() {
        mementoStack.clear();
        consecutiveUndoUses = 0;
        totalUndoUses = 0;
    }
}