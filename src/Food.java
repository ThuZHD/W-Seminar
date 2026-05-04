import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Food {

    BufferedImage brot;

    ArrayList<Ingredient> test = new ArrayList<Ingredient>();

    int x = 6;
    int y = 7;

    boolean isFollowingMouse = false;

    public void setPos(int xPos, int yPos) {
        if (isFollowingMouse) {
            x = xPos;
            y = yPos;
        }
    }

    public void toggleIsFollowingMouse(int xPos, int yPos) {
        System.out.println(
            (xPos - x > -40 && xPos - x < 40) &&
                (yPos - y > -40 && yPos - y < 40)
        );
        if (
            (xPos - 50 - x > -40 && xPos - 50 - x < 40) &&
            (yPos - 50 - y > -40 && yPos - 50 - y < 40)
        ) {
            isFollowingMouse = !isFollowingMouse;
        }
    }

    public Food() {
        try {
            brot = ImageIO.read(
                getClass().getResource("/resources/Döner/Brot.png")
            );


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(brot, x, y, 100, 100, null);

        for (int i = 0; i < test.size(); i++) {
            g.drawImage(test.get(i).base, x, y, 100, 100, null);
        }

        for (int i = 0; i < test.size(); i++) {
            g.drawImage(test.get(i).top, x, y, 100, 100, null);
        }
    }

    public void addIngredient(String base, String top) {
        test.add(new Ingredient());
        test
            .get(test.size() - 1)
            .setImages(
                    base,
                    top
            );
    }
}
