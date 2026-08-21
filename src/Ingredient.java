import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Ingredient {
    boolean isSpawnBase = true;

    String name = "";

    public BufferedImage top;
    public BufferedImage base;

    int xPos = 100;
    int yPos = 100;

    int width = 100;
    int height = 100;

    String baseImagePath;
    String topImagePath;

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String getName() {return this.name;}
    public int getXPos() {return this.xPos;}
    public int getYPos() {return this.yPos;}
    public int getWidth() {return this.width;}
    public int getHeight() {return this.height;}
    public String getBaseImagePath() {return this.baseImagePath;}
    public String getTopImagePath() {return this.topImagePath;}

    public BufferedImage getBaseBufferedImage() {return this.base;}
    public BufferedImage getTopBufferedImage() {return this.top;}

    public boolean disableSpawn() {
        if(isSpawnBase) {
            isSpawnBase = false;
            System.out.println("spawner disabled");
            return true;
        }
        return false;
    }

    public void setup(String name, int xPos, int yPos, int width, int height, String baseImagePath, String topImagePath) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.baseImagePath = baseImagePath;
        this.topImagePath = topImagePath;

        try {
            base = ImageIO.read(new File(baseImagePath));
            top = ImageIO.read(new File(topImagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawBase(Graphics2D g) {
        g.drawImage(base, xPos, yPos, width, height, null);
    }

    public void drawTop(Graphics2D g) {
        g.drawImage(top, xPos, yPos, width, height, null);
    }
}
