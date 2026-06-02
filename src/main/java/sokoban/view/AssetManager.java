package sokoban.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    private static final Map<String, BufferedImage> CACHE = new HashMap<>();

    private AssetManager() {
    }

    public static BufferedImage getImage(String resourcePath) {
        if (CACHE.containsKey(resourcePath)) {
            return CACHE.get(resourcePath);
        }

        try (InputStream inputStream = AssetManager.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                CACHE.put(resourcePath, null);
                return null;
            }

            BufferedImage image = ImageIO.read(inputStream);
            CACHE.put(resourcePath, image);
            return image;
        } catch (Exception e) {
            CACHE.put(resourcePath, null);
            return null;
        }
    }
}