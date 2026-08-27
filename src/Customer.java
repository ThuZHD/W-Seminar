import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Customer {
    int xPos;
    int yPos;
    int width;
    int height;

    BufferedImage customerImage;
    BufferedImage speech;
    BufferedImage kys;
    BufferedImage fml;

    public void setup(int xPos, int yPos, int width, int height, String imagePath, JSONObject possibleFood) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;

        System.out.println(possibleFood);

        try {
            customerImage = ImageIO.read(new File(imagePath));
            speech = ImageIO.read(new File("/Users/brunobeuttler/Desktop/everything/Code/W-Sem/Mods/Döner Updated/speech.png"));
            kys = ImageIO.read(new File(possibleFood.getString("baseImageBase")));
            fml = ImageIO.read(new File(possibleFood.getString("baseImageTop")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(customerImage, xPos, yPos, width, height, null);
        g.drawImage(speech, xPos, yPos - height + 20, width, height, null);
        g.drawImage(kys, (int) (xPos + width * 0.4), (int) (yPos * 0.35), width/5, height/5, null);
        g.drawImage(fml, (int) (xPos + width * 0.4), (int) (yPos * 0.35), width/5, height/5, null);
    }
}
