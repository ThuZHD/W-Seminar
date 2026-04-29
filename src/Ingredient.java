import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Ingredient {
    public BufferedImage top;
    public BufferedImage base;

    public Ingredient() {
        try {
            base = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/Döner/Fleisch.png")));
            top = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/Döner/Fleisch.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawBase(Graphics2D g) {
        g.drawImage(base, 67, 67, 100, 100, null);
    }

    public void drawTop(Graphics2D g) {
        g.drawImage(top, 67, 67, 100, 100, null);
    }

    public void setImages(String baseImage, String topImage) {
        System.out.println(baseImage);

        try {
            base = ImageIO.read(Objects.requireNonNull(getClass().getResource(baseImage)));
            top = ImageIO.read(Objects.requireNonNull(getClass().getResource(topImage)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
