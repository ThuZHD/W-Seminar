import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class IngredientSpawner {
    BufferedImage spawner;
    BufferedImage ingredient;
    boolean isFollowingMouse = false;

    int x = 600;
    int y = 700;

    public IngredientSpawner() {
        try {
            spawner = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/Döner/Tomate Top.png")));
            ingredient = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/Döner/Tomate Top.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawSpawner(Graphics2D g) { g.drawImage(spawner, 600, 700, 100, 100, null);}
    public void drawIngredient(Graphics2D g) {
        g.drawImage(ingredient, x, y, 100, 100, null);
    }

    public void setPos(int xPos, int yPos) {
        if (isFollowingMouse) {
            x = xPos;
            y = yPos;
        }
    }

    public void toggleIsFollowingMouse(int xPos, int yPos) {
        System.out.println("here");
        if (
                (xPos - 50 - x > -40 && xPos - 50 - x < 40) &&
                        (yPos - 50 - y > -40 && yPos - 50 - y < 40)
        ) {
            isFollowingMouse = !isFollowingMouse;
            System.out.println("toggled following");
        }
    }
}
