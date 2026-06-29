package sokoban.view;

import sokoban.controller.GameController;
import sokoban.model.Board;
import sokoban.model.Game;
import sokoban.model.Position;
import sokoban.model.boxes.Box;
import sokoban.model.boxes.FragileBox;
import sokoban.model.boxes.HeavyBox;
import sokoban.model.boxes.KeyBox;
import sokoban.model.boxes.NormalBox;
import sokoban.model.items.Item;
import sokoban.model.items.WaterBottle;
import sokoban.model.elements.BoardElement;
import sokoban.model.elements.ClosedWall;
import sokoban.model.elements.Destination;
import sokoban.model.elements.LockCell;
import sokoban.model.elements.OpenWall;
import sokoban.model.elements.SlipperyTerrain;
import sokoban.model.elements.Wall;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;

public class BoardPanel extends JPanel {

    private static final int TILE_SIZE = 48;

    private final GameController controller;

    public BoardPanel(GameController controller) {
        this.controller = controller;
        setBackground(new Color(35, 35, 35));
        refreshSize();
    }

    public void refreshSize() {
        Game game = controller.getGame();

        if (game == null) {
            return;
        }

        Board board = game.getBoard();
        setPreferredSize(new Dimension(board.getCols() * TILE_SIZE, board.getRows() * TILE_SIZE));
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Game game = controller.getGame();

        if (game == null) {
            return;
        }

        Board board = game.getBoard();

        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Position position = new Position(row, col);
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                BoardElement element = board.getElementAt(position);
                drawBaseTile(g2, element, x, y);

                Item item = board.getItemAt(position);
                if (item != null) {
                    drawItem(g2, item, x, y);
                }

                Box box = board.getBoxAt(position);
                if (box != null) {
                    drawBox(g2, box, x, y);
                }

                if (board.getPlayer() != null && board.getPlayer().getPosition().equals(position)) {
                    drawPlayer(g2, x, y);
                }
            }
        }

        g2.dispose();
    }

    private void drawBaseTile(Graphics2D g2, BoardElement element, int x, int y) {
        if (element instanceof Wall) {
            drawWallTile(g2, x, y);
            return;
        }

        if (element instanceof ClosedWall) {
            drawClosedWallTile(g2, x, y);
            return;
        }

        if (element instanceof OpenWall) {
            drawOpenWallTile(g2, x, y);
            return;
        }

        if (element instanceof SlipperyTerrain) {
            drawIceTile(g2, x, y);
            return;
        }

        if (element instanceof Destination) {
            drawDestinationTile(g2, x, y);
            return;
        }

        drawGrassTile(g2, x, y);

        if (element instanceof LockCell) {
            drawLockMark(g2, x, y);
        }
    }

    private boolean drawImage(Graphics2D g2, String resourcePath, int x, int y, int width, int height) {
        Image image = AssetManager.getImage(resourcePath);
        if (image != null) {
            // For floor tiles, stretch to fill completely
            if (resourcePath.contains("PisoCemento")) {
                g2.drawImage(image, x, y, width, height, null);
                return true;
            }

            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);

            if (imageWidth > 0 && imageHeight > 0) {
                double scale = Math.min((double) width / imageWidth, (double) height / imageHeight);
                int drawWidth = (int) Math.round(imageWidth * scale);
                int drawHeight = (int) Math.round(imageHeight * scale);
                int drawX = x + (width - drawWidth) / 2;
                int drawY = y + (height - drawHeight) / 2;
                g2.drawImage(image, drawX, drawY, drawWidth, drawHeight, null);
                return true;
            }

            g2.drawImage(image, x, y, width, height, null);
            return true;
        }
        return false;
    }

    private void drawGrassTile(Graphics2D g2, int x, int y) {
        if (drawImage(g2, "assets/images/PisoCemento.png", x, y, TILE_SIZE, TILE_SIZE)) {
            return;
        }

        g2.setColor(new Color(150, 150, 150));
        g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    }

    private void drawWallTile(Graphics2D g2, int x, int y) {
        if (drawImage(g2, "assets/images/MuroLadrillo.png", x, y, TILE_SIZE, TILE_SIZE)) {
            return;
        }

        g2.setColor(new Color(148, 41, 38));
        g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        g2.setColor(new Color(95, 20, 18));
        g2.drawRect(x, y, TILE_SIZE - 1, TILE_SIZE - 1);

        g2.drawLine(x, y + 16, x + TILE_SIZE, y + 16);
        g2.drawLine(x, y + 32, x + TILE_SIZE, y + 32);

        g2.drawLine(x + 12, y, x + 12, y + 16);
        g2.drawLine(x + 35, y, x + 35, y + 16);

        g2.drawLine(x + 24, y + 16, x + 24, y + 32);

        g2.drawLine(x + 12, y + 32, x + 12, y + TILE_SIZE);
        g2.drawLine(x + 35, y + 32, x + 35, y + TILE_SIZE);
    }

    private void drawClosedWallTile(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        g2.setColor(new Color(90, 90, 90));
        g2.drawRect(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8);

        g2.setColor(new Color(140, 140, 140));
        g2.drawLine(x + 10, y + 10, x + TILE_SIZE - 10, y + TILE_SIZE - 10);
        g2.drawLine(x + TILE_SIZE - 10, y + 10, x + 10, y + TILE_SIZE - 10);
    }

    private void drawOpenWallTile(Graphics2D g2, int x, int y) {
        drawGrassTile(g2, x, y);

        g2.setColor(new Color(177, 109, 71));
        g2.fillRect(x + 8, y + 6, TILE_SIZE - 16, 8);
        g2.fillRect(x + 8, y + TILE_SIZE - 14, TILE_SIZE - 16, 8);
        g2.fillRect(x + 6, y + 8, 8, TILE_SIZE - 16);
        g2.fillRect(x + TILE_SIZE - 14, y + 8, 8, TILE_SIZE - 16);
    }

    private void drawIceTile(Graphics2D g2, int x, int y) {
        if (drawImage(g2, "assets/images/PisoHielo.png", x, y, TILE_SIZE, TILE_SIZE)) {
            return;
        }

        drawGrassTile(g2, x, y);

        g2.setColor(new Color(220, 245, 255, 180));
        g2.fillRect(x + 4, y + 6, TILE_SIZE - 10, 8);
        g2.fillRect(x + 12, y + 23, TILE_SIZE - 18, 6);

        g2.setColor(new Color(120, 200, 240));
        g2.drawLine(x + 10, y + 35, x + 26, y + 18);
        g2.drawLine(x + 26, y + 18, x + 39, y + 28);
        g2.drawLine(x + 18, y + 40, x + 37, y + 36);
    }

    private void drawDestinationTile(Graphics2D g2, int x, int y) {
        // Draw cement floor as base
        if (drawImage(g2, "assets/images/PisoCemento.png", x, y, TILE_SIZE, TILE_SIZE)) {
            // Draw destination box on top
            drawImage(g2, "assets/images/CasilleroCaja.png", x + 6, y + 6, TILE_SIZE - 12, TILE_SIZE - 12);
            return;
        }

        g2.setColor(new Color(150, 150, 150));
        g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);

        // Draw destination box on top
        if (drawImage(g2, "assets/images/CasilleroCaja.png", x + 6, y + 6, TILE_SIZE - 12, TILE_SIZE - 12)) {
            return;
        }

        // Fallback visual marker
        drawDestinationMark(g2, x, y);
    }

    private void drawDestinationMark(Graphics2D g2, int x, int y) {
        int centerX = x + TILE_SIZE / 2;
        int centerY = y + TILE_SIZE / 2;

        g2.setColor(new Color(255, 230, 86));

        int[] xPoints = {
                centerX,
                centerX + 13,
                centerX,
                centerX - 13
        };

        int[] yPoints = {
                centerY - 13,
                centerY,
                centerY + 13,
                centerY
        };

        g2.fillPolygon(xPoints, yPoints, 4);

        g2.setColor(new Color(180, 140, 30));
        g2.drawPolygon(xPoints, yPoints, 4);

        g2.setColor(new Color(255, 248, 180));
        g2.fillOval(centerX - 4, centerY - 4, 8, 8);
    }

    private void drawLockMark(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(201, 64, 182));
        g2.fillRoundRect(x + 10, y + 10, 28, 28, 8, 8);

        g2.setColor(new Color(250, 214, 63));
        g2.fillOval(x + 18, y + 15, 12, 12);
        g2.fillRect(x + 22, y + 24, 4, 10);
    }

    private void drawBox(Graphics2D g2, Box box, int x, int y) {
        if (box instanceof HeavyBox) {
            drawHeavyBox(g2, x, y);
            return;
        }

        if (box instanceof NormalBox) {
            drawNormalBox(g2, x, y);
            return;
        }

        if (box instanceof KeyBox) {
            drawKeyBox(g2, x, y);
            return;
        }

        if (box instanceof FragileBox) {
            drawFragileBox(g2, (FragileBox) box, x, y);
            return;
        }
    }

    private void drawHeavyBox(Graphics2D g2, int x, int y) {
        if (drawImage(g2, "assets/images/caja_normal.png", x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8)) {
            g2.setColor(new Color(255, 255, 255, 200));
            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            g2.drawString("H", x + 17, y + 29);
            return;
        }

        drawCrate(g2, x, y, new Color(123, 123, 153), new Color(63, 63, 93));

        g2.setColor(new Color(210, 210, 255));
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        g2.drawString("H", x + 18, y + 30);
    }

    private void drawNormalBox(Graphics2D g2, int x, int y) {
        if (drawImage(g2, "assets/images/caja_normal.png", x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8)) {
            return;
        }

        drawCrate(g2, x, y, new Color(174, 127, 73), new Color(109, 74, 38));
    }

    private void drawKeyBox(Graphics2D g2, int x, int y) {
        drawCrate(g2, x, y, new Color(95, 145, 204), new Color(47, 84, 124));

        g2.setColor(new Color(255, 230, 86));
        g2.fillOval(x + 15, y + 15, 12, 12);
        g2.fillRect(x + 26, y + 19, 10, 4);
        g2.fillRect(x + 33, y + 19, 3, 8);
    }

    private void drawFragileBox(Graphics2D g2, FragileBox fragileBox, int x, int y) {
        if (fragileBox.getResistance() <= 0) {
            if (drawImage(g2, "assets/images/CajaFragil.png", x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8)) {
                g2.setColor(new Color(35, 35, 35));
                g2.drawLine(x + 10, y + 10, x + TILE_SIZE - 10, y + TILE_SIZE - 10);
                g2.drawLine(x + TILE_SIZE - 10, y + 10, x + 10, y + TILE_SIZE - 10);
                return;
            }

            drawCrate(g2, x, y, new Color(110, 110, 110), new Color(60, 60, 60));

            g2.setColor(new Color(35, 35, 35));
            g2.drawLine(x + 10, y + 10, x + TILE_SIZE - 10, y + TILE_SIZE - 10);
            g2.drawLine(x + TILE_SIZE - 10, y + 10, x + 10, y + TILE_SIZE - 10);
            return;
        }

        if (drawImage(g2, "assets/images/CajaFragil.png", x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8)) {
            g2.setColor(new Color(255, 255, 255, 220));
            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            String text = String.valueOf(fragileBox.getResistance());
            g2.drawString(text, x + 20, y + 28);
            return;
        }

        drawCrate(g2, x, y, new Color(243, 219, 87), new Color(177, 153, 45));

        g2.setColor(new Color(110, 90, 20));
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        String text = String.valueOf(fragileBox.getResistance());
        g2.drawString(text, x + 20, y + 28);

        if (fragileBox.getResistance() <= 2) {
            g2.drawLine(x + 12, y + 10, x + 26, y + 25);
            g2.drawLine(x + 26, y + 25, x + 18, y + 37);
            g2.drawLine(x + 26, y + 25, x + 36, y + 17);
        }
    }

    private void drawCrate(Graphics2D g2, int x, int y, Color fill, Color border) {
        g2.setColor(fill);
        g2.fillRoundRect(x + 8, y + 8, TILE_SIZE - 16, TILE_SIZE - 16, 8, 8);

        g2.setColor(border);
        g2.drawRoundRect(x + 8, y + 8, TILE_SIZE - 16, TILE_SIZE - 16, 8, 8);

        g2.drawLine(x + 8, y + 18, x + TILE_SIZE - 8, y + 18);
        g2.drawLine(x + 8, y + TILE_SIZE - 18, x + TILE_SIZE - 8, y + TILE_SIZE - 18);
    }

    private void drawItem(Graphics2D g2, Item item, int x, int y) {
        if (item instanceof WaterBottle) {
            drawWaterBottle(g2, x, y);
        }
    }

    private void drawWaterBottle(Graphics2D g2, int x, int y) {
        if (drawImage(g2, "assets/images/BotellaPoder.png", x + 6, y + 4, TILE_SIZE - 12, TILE_SIZE - 8)) {
            return;
        }

        g2.setColor(new Color(45, 145, 255));
        g2.fillRoundRect(x + 14, y + 10, 20, 28, 8, 8);
        g2.setColor(new Color(220, 240, 255));
        g2.fillRoundRect(x + 17, y + 13, 14, 22, 6, 6);
        g2.setColor(new Color(255, 255, 255, 160));
        g2.fillOval(x + 18, y + 10, 8, 6);
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(x + 20, y + 24, 8, 8);
        g2.setColor(new Color(255, 230, 86));
        g2.fillOval(x + 22, y + 26, 4, 4);
    }

    private void drawPlayer(Graphics2D g2, int x, int y) {
        if (drawImage(g2, "assets/images/Personaje.png", x + 6, y + 3, TILE_SIZE - 12, TILE_SIZE - 8)) {
            return;
        }

        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillOval(x + 12, y + 34, 22, 8);

        g2.setColor(new Color(72, 116, 201));
        g2.fillRoundRect(x + 16, y + 18, 16, 16, 6, 6);

        g2.setColor(new Color(255, 218, 185));
        g2.fillOval(x + 16, y + 8, 16, 16);

        g2.setColor(new Color(230, 153, 52));
        g2.fillArc(x + 15, y + 6, 18, 12, 0, 180);

        g2.setColor(new Color(45, 45, 45));
        g2.fillOval(x + 20, y + 14, 2, 2);
        g2.fillOval(x + 26, y + 14, 2, 2);

        g2.setColor(new Color(60, 60, 60));
        g2.drawLine(x + 20, y + 34, x + 18, y + 42);
        g2.drawLine(x + 28, y + 34, x + 30, y + 42);
    }
}
