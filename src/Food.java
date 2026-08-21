import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Food {

    boolean isSpawnBase = true;

    BufferedImage baseImage;
    BufferedImage topImage;

    ArrayList<BufferedImage> baseIngredientImages = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> topIngredientImages = new ArrayList<BufferedImage>();

    int xPos = 100;
    int yPos = 100;

    int width = 100;
    int height = 100;

    String basePath;
    String topPath;

    ArrayList<String> addedIngredients = new ArrayList<String>();

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getXPos() {return this.xPos;}
    public int getYPos() {return this.yPos;}
    public int getWidth() {return this.width;}
    public int getHeight() {return this.height;}
    public String getTopImagePath() {return this.topPath;}
    public String getBaseImagePath() {return this.basePath;}


    public boolean disableSpawn() {
        if(isSpawnBase) {
            isSpawnBase = false;
            System.out.println("spawner disabled");
            return true;
        }
        return false;
    }

    public void setup(int xPos, int yPos, int width, int height, String baseImagePath, String topImagePath) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.basePath = baseImagePath;
        this.topPath = topImagePath;
        try {
            baseImage = ImageIO.read(new File(baseImagePath));
            topImage = ImageIO.read(new File(topImagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawBase(Graphics2D g) {
        g.drawImage(baseImage, xPos, yPos, width, height, null);
        for(BufferedImage image: baseIngredientImages) {
            g.drawImage(image, xPos, yPos, height, width, null);
        }

        for(BufferedImage image: topIngredientImages) {
            g.drawImage(image, xPos, yPos, height, width, null);
        }
    }

    public void drawTop(Graphics2D g) {
        g.drawImage(topImage, xPos, yPos, width, height, null);
    }

    public void addIngredient(String name, BufferedImage baseImage, BufferedImage topImage) {
        System.out.println("here im Food");

        baseIngredientImages.add(baseImage);
        topIngredientImages.add(topImage);
        addedIngredients.add(name);
        System.out.println("added new Ingredient: " + name);
    }
}
