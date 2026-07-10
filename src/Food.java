import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Food {

    boolean isSpawnBase = true;

    BufferedImage baseImage;

    ArrayList<Ingredient> ingredientArrayList = new ArrayList<Ingredient>();

    int xPos = 100;
    int yPos = 100;

    int width = 100;
    int height = 100;

    String imagePath;

    boolean isFollowingMouse = false;

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getXPos() {return this.xPos;}
    public int getYPos() {return this.yPos;}
    public int getWidth() {return this.width;}
    public int getHeight() {return this.height;}
    public String getImage() {return this.imagePath;}

    public boolean disableSpawn() {
        if(isSpawnBase) {
            isSpawnBase = false;
            System.out.println("spawner disabled");
            return true;
        }
        return false;
    }

    public void setup(int xPos, int yPos, int width, int height, String imagePath) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
        try {
            baseImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(baseImage, xPos, yPos, width, height, null);

        for (int i = 0; i < ingredientArrayList.size(); i++) {
            g.drawImage(ingredientArrayList.get(i).base, xPos, yPos, height, width, null);
        }

        for (int i = 0; i < ingredientArrayList.size(); i++) {
            g.drawImage(ingredientArrayList.get(i).top, xPos, yPos, width, height, null);
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
