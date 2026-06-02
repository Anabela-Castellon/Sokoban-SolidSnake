package sokoban.patterns.factory;

import sokoban.model.Board;
import sokoban.model.Level;
import sokoban.model.Position;
import sokoban.model.boxes.FragileBox;
import sokoban.model.boxes.KeyBox;
import sokoban.model.boxes.NormalBox;
import sokoban.model.elements.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class txtLevelFactory extends LevelFactory {

    @Override
    public Level createLevel(String resourcePath, int levelNumber) throws IOException {
        List<String> lines = readLines(resourcePath);

        int rows = lines.size();
        int cols = getMaxColumns(lines);

        Board board = new Board(rows, cols);

        fillWithFloor(board);

        for (int row = 0; row < rows; row++) {
            String line = lines.get(row);

            for (int col = 0; col < line.length(); col++) {
                char symbol = line.charAt(col);
                Position position = new Position(row, col);

                switch (symbol) {
                    case '#':
                        board.setElementAt(position, new Wall());
                        break;

                    case '@':
                        board.setElementAt(position, new Floor());
                        board.setPlayer(new Player(position));
                        break;

                    case '$':
                        board.setElementAt(position, new Floor());
                        board.addBox(new NormalBox(position));
                        break;

                    case '.':
                        board.setElementAt(position, new Destination());
                        break;

                    case 'F':
                        board.setElementAt(position, new Floor());
                        board.addBox(new FragileBox(position, 3));
                        break;

                    case '5':
                        board.setElementAt(position, new Floor());
                        board.addBox(new FragileBox(position, 5));
                        break;

                    case 'K':
                        board.setElementAt(position, new Floor());
                        board.addBox(new KeyBox(position));
                        break;

                    case 'C':
                        board.setElementAt(position, new LockCell());
                        break;

                    case 'M':
                        board.setElementAt(position, new ClosedWall());
                        break;

                    case 'O':
                        board.setElementAt(position, new OpenWall());
                        break;

                    case '~':
                        board.setElementAt(position, new SlipperyTerrain());
                        break;

                    case ' ':
                        board.setElementAt(position, new Floor());
                        break;

                    default:
                        board.setElementAt(position, new Floor());
                        break;
                }
            }
        }

        return new Level(levelNumber, board);
    }

    private List<String> readLines(String resourcePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IOException("No se encontró el archivo: " + resourcePath);
        }

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

    private int getMaxColumns(List<String> lines) {
        int max = 0;

        for (String line : lines) {
            if (line.length() > max) {
                max = line.length();
            }
        }

        return max;
    }

    private void fillWithFloor(Board board) {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                board.setElementAt(new Position(row, col), new Floor());
            }
        }
    }
}