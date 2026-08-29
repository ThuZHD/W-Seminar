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
    BufferedImage foodBaseImage;
    BufferedImage foodTopImage;

    ArrayList<BufferedImage> baseIngredientImages = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> topIngredientImages = new ArrayList<BufferedImage>();

    ArrayList<String> order = new ArrayList<String>();

    public int getXPos() {return xPos;}
    public int getYPos() {return yPos;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}

    public void setup(int xPos, int yPos, int width, int height, String imagePath, String speechImagePath, JSONObject possibleFood) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;

        try {
            customerImage = ImageIO.read(new File(imagePath));
            speech = ImageIO.read(new File(speechImagePath));
            foodBaseImage = ImageIO.read(new File(possibleFood.getString("baseImageBase")));
            foodTopImage = ImageIO.read(new File(possibleFood.getString("baseImageTop")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < possibleFood.getJSONArray("ingredients").length(); i++) {

            if(Math.random() < 0.8) {
                order.add(possibleFood.getJSONArray("ingredients").getJSONObject(i).getString("name"));

                try {
                    baseIngredientImages.add(ImageIO.read(new File(possibleFood.getJSONArray("ingredients").getJSONObject(i).getString("baseImage"))));
                    topIngredientImages.add(ImageIO.read(new File(possibleFood.getJSONArray("ingredients").getJSONObject(i).getString("topImage"))));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(customerImage, xPos, yPos, width, height, null);
        g.drawImage(speech, xPos, yPos - height + 20, width, height, null);
        g.drawImage(foodBaseImage, (int) (xPos + width * 0.4), (int) (yPos * 0.35), width/5, height/5, null);
        g.drawImage(foodTopImage, (int) (xPos + width * 0.4), (int) (yPos * 0.35), width/5, height/5, null);

        for (BufferedImage image : baseIngredientImages) {
            g.drawImage(image, (int) (xPos + width * 0.4), (int) (yPos * 0.35), width/5, height/5, null);
        }

        for (BufferedImage image : topIngredientImages) {
            g.drawImage(image, (int) (xPos + width * 0.4), (int) (yPos * 0.35), width/5, height/5, null);
        }
    }

    // returns true if the submitted food is as wanted by the customer
    public boolean submitFood(ArrayList<String> ingredients) {
        boolean isOrderWrong = false;
        System.out.println(ingredients);
        System.out.println(order);


        for (String orderedIngredient : order) {
            if(!ingredients.contains(orderedIngredient)) {
                System.out.println("item missing");
                isOrderWrong = true;
            }
        }

        for (String providedIngredient : ingredients) {
            if(!order.contains(providedIngredient)) {
                System.out.println("item not wanted");
                isOrderWrong = true;
            }
        }

        if(!isOrderWrong) {
            return true;
        } else {
            return false;
        }
    }
}
