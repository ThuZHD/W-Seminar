import org.json.JSONArray;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Food {

    int debugIndex;

    boolean isSpawnBase = true;
    BufferedImage baseImage; // the foundation image of the food
    BufferedImage topImage; // the image layer which is rendered on top of all ingredients

    ArrayList<BufferedImage> baseIngredientImages = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> topIngredientImages = new ArrayList<BufferedImage>();

    // positions and dimensions
    int xPos = 100;
    int yPos = 100;

    int width = 100;
    int height = 100;

    String baseImagePath; // path of the top layer image
    String topImagePath; // path of the foundation image

    ArrayList<String> addedIngredients = new ArrayList<String>(); // all ingredients added to the food

    //
    // SET-GET Functions
    //

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getXPos() {return this.xPos;}
    public int getYPos() {return this.yPos;}
    public int getWidth() {return this.width;}
    public int getHeight() {return this.height;}
    public String getTopImagePath() {return this.topImagePath;}
    public String getBaseImagePath() {return this.baseImagePath;}
    public int getDebugIndex() {return this.debugIndex;}

    //
    // Functions
    //

    // checks if food is still a spawner and returns the value, if isSpawnBase, the spawner gets disabled
    public boolean disableSpawn() {
        if(isSpawnBase) {
            isSpawnBase = false;
            System.out.println("spawner disabled");
            return true;
        }
        return false;
    }

    // sets all values and loads the starting images
    public void setup(int xPos, int yPos, int width, int height, String baseImagePath, String topImagePath, int debugIndex) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.baseImagePath = baseImagePath;
        this.topImagePath = topImagePath;
        try {
            baseImage = ImageIO.read(new File(baseImagePath));
            topImage = ImageIO.read(new File(topImagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.debugIndex = debugIndex;
    }

    // executed every tick, draws the foundation image and all ingredient images
    public void drawBase(Graphics2D g) {
        g.drawImage(baseImage, xPos, yPos, width, height, null);
        for(BufferedImage image: baseIngredientImages) {
            g.drawImage(image, xPos, yPos, width, height, null);
        }

        for(BufferedImage image: topIngredientImages) {
            g.drawImage(image, xPos, yPos, width, height, null);
        }
    }

    // executed every tick, draws the top layer image
    public void drawTop(Graphics2D g) {
        g.drawImage(topImage, xPos, yPos, width, height, null);
//        g.drawString("Index " + debugIndex, xPos, yPos);
    }

    // adds the ingredient name and images to the food
    public void addIngredient(String name, BufferedImage baseImage, BufferedImage topImage) {
        baseIngredientImages.add(baseImage);
        topIngredientImages.add(topImage);
        addedIngredients.add(name);

        System.out.println("added new Ingredient: " + name);
        System.out.println("this is added to the kebab: " + addedIngredients);
    }

    public ArrayList<String> getIngredients() {
        return addedIngredients;
    }
}
