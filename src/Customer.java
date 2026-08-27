import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Customer {
    int xPos;
    int yPos;
    int width;
    int height;

    BufferedImage customerImage;
    BufferedImage speech;
    BufferedImage kys;
    BufferedImage fml;

    ArrayList<BufferedImage> baseIngredientImages = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> topIngredientImages = new ArrayList<BufferedImage>();

    ArrayList<String> order = new ArrayList<String>();

    public void setup(int xPos, int yPos, int width, int height, String imagePath, String speechImagePath, JSONObject possibleFood) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;

        try {
            customerImage = ImageIO.read(new File(imagePath));
            speech = ImageIO.read(new File(speechImagePath));
//            speech = ImageIO.read(new File("/Users/brunobeuttler/Desktop/everything/Code/W-Sem/Mods/Döner Updated/speech.png"));
            kys = ImageIO.read(new File(possibleFood.getString("baseImageBase")));
            fml = ImageIO.read(new File(possibleFood.getString("baseImageTop")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < possibleFood.getJSONArray("ingredients").length(); i++) {

            if(Math.random() < 0.7) {
                order.add(possibleFood.getJSONArray("ingredients").getJSONObject(i).getString("name"));

                try {
                    baseIngredientImages.add(ImageIO.read(new File(possibleFood.getJSONArray("ingredients").getJSONObject(i).getString("baseImage"))));
                    topIngredientImages.add(ImageIO.read(new File(possibleFood.getJSONArray("ingredients").getJSONObject(i).getString("topImage"))));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }

        System.out.println("I ordered: " + order);
    }

    public void draw(Graphics2D g) {
        g.drawImage(customerImage, xPos, yPos, width, height, null);
        g.drawImage(speech, xPos, yPos - height + 20, width, height, null);
        g.drawImage(kys, (int) (xPos + width * 0.4), (int) (yPos * 0.35), width/5, height/5, null);
        g.drawImage(fml, (int) (xPos + width * 0.4), (int) (yPos * 0.35), width/5, height/5, null);

        for (BufferedImage image : baseIngredientImages) {
            g.drawImage(image, (int) (xPos + width * 0.4), (int) (yPos * 0.35), width/5, height/5, null);
        }

        for (BufferedImage image : topIngredientImages) {
            g.drawImage(image, (int) (xPos + width * 0.4), (int) (yPos * 0.35), width/5, height/5, null);
        }
    }

    public void submitFood(ArrayList<String> ingredients) {
        boolean isOrderWrong = false;

        System.out.println("here in Customer.java");
        System.out.println(ingredients);
        System.out.println(order);


        for (String orderedIngredient : order) {
            if(!ingredients.contains(orderedIngredient)) {
                System.out.println("item missing");
            }
        }

        for (String providedIngredient : ingredients) {
            if(!order.contains(providedIngredient)) {
                System.out.println("item not wanted");
            }
        }
    }
}
