package sokoban.model;

public class Level {
    private final int number;
    private final Board board;

    public Level(int number, Board board) {
        this.number = number;
        this.board = board;
    }

    public int getNumber() {
        return number;
    }

    public Board getBoard() {
        return board;
    }
}
