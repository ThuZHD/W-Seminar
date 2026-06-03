import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Food {

    BufferedImage baseImage;

    ArrayList<Ingredient> ingredientArrayList = new ArrayList<Ingredient>();

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
        // check hitbox
        // System.out.println((xPos - x > -40 && xPos - x < 40) && (yPos - y > -40 && yPos - y < 40));
        if (
            (xPos - 50 - x > -40 && xPos - 50 - x < 40) &&
            (yPos - 50 - y > -40 && yPos - 50 - y < 40)
        ) {
            isFollowingMouse = !isFollowingMouse;
        }
    }

    public Food() {
        try {
            baseImage = ImageIO.read(
                    new File("/home/thuz/Development/W-Seminar/src/resources/Döner/Brot.png")
            );


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(baseImage, x, y, 100, 100, null);

        for (int i = 0; i < ingredientArrayList.size(); i++) {
            g.drawImage(ingredientArrayList.get(i).base, x, y, 100, 100, null);
        }

        for (int i = 0; i < ingredientArrayList.size(); i++) {
            g.drawImage(ingredientArrayList.get(i).top, x, y, 100, 100, null);
        }
    }

    public void addIngredient(String base, String top) {
        ingredientArrayList.add(new Ingredient());
        ingredientArrayList
            .get(ingredientArrayList.size() - 1)
            .setImages(
                    base,
                    top
            );
    }
}
