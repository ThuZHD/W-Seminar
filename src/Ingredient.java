import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ingredient {
    BufferedImage top;
    BufferedImage base;

    public Ingredient() {
        try {
            top = ImageIO.read(getClass().getResource("/resources/Döner/Brot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(top, 67, 67, 100, 100, null);
    }
}
