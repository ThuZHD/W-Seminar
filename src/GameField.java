import org.json.JSONArray;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;


public class GameField extends JPanel {

    private final Dimension prefSize = new Dimension(400, 90);
    public Coordinate mouseCoordinate = new Coordinate(0,0);

    int screenWidth;
    int screenHeight;

    boolean isGrabbingIngredient = false;
    boolean isGrabbingFood = false;

    // activeGrabbedFood might be "-1", in this case no food is being grabbed
    int activeGrabbedFood = -1;
    int activeGrabbedIngredient = -1;

    ArrayList<Food> foodArrayList = new ArrayList<Food>();
    ArrayList<Ingredient> ingredientArrayList = new ArrayList<Ingredient>();

    void addIngedientToFood() {
        for (int i = 0; i < foodArrayList.size(); i++) {
            if (
                    (mouseCoordinate.getX() - 50 - foodArrayList.get(i).getXPos() > -40 && mouseCoordinate.getX() - 50 - foodArrayList.get(i).getXPos() < 40) &&
                            (mouseCoordinate.getY() - 50 - foodArrayList.get(i).getYPos() > -40 && mouseCoordinate.getY() - 50 - foodArrayList.get(i).getYPos() < 40)
            ) {
                foodArrayList.get(i).addIngredient(ingredientArrayList.get(activeGrabbedIngredient).getName(), ingredientArrayList.get(activeGrabbedIngredient).getBaseBufferedImage(), ingredientArrayList.get(activeGrabbedIngredient).getTopBufferedImage());
                ingredientArrayList.remove(activeGrabbedIngredient);
            }
        }
    }

    int checkMouseOverFoodItem(int mouseXPos, int mouseYPos) {
        for (int i = 0; i < foodArrayList.size(); i++) {
            if (
                    (mouseCoordinate.getX() - 50 - foodArrayList.get(i).getXPos() > -40 && mouseCoordinate.getX() - 50 - foodArrayList.get(i).getXPos() < 40) &&
                            (mouseCoordinate.getY() - 50 - foodArrayList.get(i).getYPos() > -40 && mouseCoordinate.getY() - 50 - foodArrayList.get(i).getYPos() < 40)
            ) {
                if(foodArrayList.get(i).disableSpawn()) {
                    Food newFoodSpawner = new Food();
                    newFoodSpawner.setup(foodArrayList.get(i).getXPos(), foodArrayList.get(i).getYPos(), foodArrayList.get(i).getWidth(), foodArrayList.get(i).getHeight(), foodArrayList.get(i).getBaseImagePath(), foodArrayList.get(i).getTopImagePath());
                    foodArrayList.add(newFoodSpawner);
                }
                return i;
            }
        }
        return -1;
    }

    int checkMouseOverIngredientItem(int mouseXPos, int mouseYPos) {
        for (int i = 0; i < ingredientArrayList.size(); i++) {
            if (
                    (mouseCoordinate.getX() - 50 - ingredientArrayList.get(i).getXPos() > -40 && mouseCoordinate.getX() - 50 - ingredientArrayList.get(i).getXPos() < 40) &&
                            (mouseCoordinate.getY() - 50 - ingredientArrayList.get(i).getYPos() > -40 && mouseCoordinate.getY() - 50 - ingredientArrayList.get(i).getYPos() < 40)
            ) {
                if(ingredientArrayList.get(i).disableSpawn()) {
                    Ingredient newIngredientSpawner = new Ingredient();
                    newIngredientSpawner.setup(ingredientArrayList.get(i).getName(), ingredientArrayList.get(i).getXPos(), ingredientArrayList.get(i).getYPos(), ingredientArrayList.get(i).getWidth(), ingredientArrayList.get(i).getHeight(), ingredientArrayList.get(i).getBaseImagePath(), ingredientArrayList.get(i).getTopImagePath());
                    ingredientArrayList.add(newIngredientSpawner);
                }
                return i;
            }
        }
        return -1;
    }

    public GameField() {
        // self explaining
        {
            setPreferredSize(prefSize);
            setBackground(Color.cyan);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            screenWidth = screenSize.width;
            screenHeight = screenSize.height;

            BufferedImage cursorImg;

            cursorImg = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    cursorImg, new Point(0, 0), "blank cursor");
            setCursor(blankCursor);
        }


        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(!isGrabbingFood && !isGrabbingIngredient) {
                    // checkMouseOverItem may return -1, this means no food is being grabbed
                    activeGrabbedFood = checkMouseOverFoodItem(mouseCoordinate.getX(), mouseCoordinate.getY());
                    if(activeGrabbedFood != -1) {
                        isGrabbingFood = true;
                    }
                } else {
                    isGrabbingFood = false;
                }

                if(isGrabbingIngredient) {
                    addIngedientToFood();
                }

                if(!isGrabbingIngredient && !isGrabbingFood) {
                    // checkMouseOverItem may return -1, this means no food is being grabbed
                    activeGrabbedIngredient = checkMouseOverIngredientItem(mouseCoordinate.getX(), mouseCoordinate.getY());
                    if(activeGrabbedIngredient != -1) {
                        isGrabbingIngredient = true;
                    }
                } else {
                    isGrabbingIngredient = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });;

//        spieleSound("/Users/brunobeuttler/Downloads/yusuf.wav", 6, true);
    }

    private Clip spieleSound(String dateipfad, float lautstarke, boolean loop) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(dateipfad));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            setzeLautstaerke(clip, lautstarke);
            //das nur für die Hintergrundmusik
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();
            return clip;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private void setzeLautstaerke(Clip clip, float lautstaerke) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(lautstaerke);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(0, getSize().height/3*2, getSize().width, screenHeight/3);


        g.setColor(Color.BLACK);

        if(isGrabbingFood) {
            foodArrayList.get(activeGrabbedFood).setPos(mouseCoordinate.getX() - 50, mouseCoordinate.getY() - 50);
        }

        if(isGrabbingIngredient) {
            ingredientArrayList.get(activeGrabbedIngredient).setPos(mouseCoordinate.getX() - 50, mouseCoordinate.getY() - 50);
        }

        for(Food food : foodArrayList) {
            food.drawBase((Graphics2D) g);
        }

        for(Ingredient ingredient : ingredientArrayList) {
            ingredient.drawBase((Graphics2D) g);
        }

        for(Ingredient ingredient : ingredientArrayList) {
            ingredient.drawTop((Graphics2D) g);
        }

        for(Food food : foodArrayList) {
            food.drawTop((Graphics2D) g);
        }
    }

    public void setupFoodSpawnersInField(JSONArray spawners, String relativePath) {
        for (int i = 0; i < spawners.length(); i++) {
            Food newFoodSpawner = new Food();
            newFoodSpawner.setup(spawners.getJSONObject(i).getInt("xPos"), spawners.getJSONObject(i).getInt("yPos"), spawners.getJSONObject(i).getInt("width"), spawners.getJSONObject(i).getInt("height"), Path.of(relativePath, spawners.getJSONObject(i).getString("base")).toString(), Path.of(relativePath, spawners.getJSONObject(i).getString("top")).toString());
            foodArrayList.add(newFoodSpawner);
        }
    }

    public void setupIngredientSpawnersInField(JSONArray spawners, String relativePath) {
        System.out.println(spawners);
        for (int i = 0; i < spawners.length(); i++) {
            Ingredient newIngredient = new Ingredient();
            newIngredient.setup(spawners.getJSONObject(i).getString("name"), spawners.getJSONObject(i).getInt("xPos"), spawners.getJSONObject(i).getInt("yPos"), spawners.getJSONObject(i).getInt("width"), spawners.getJSONObject(i).getInt("height"), Path.of(relativePath, spawners.getJSONObject(i).getString("base")).toString(), Path.of(relativePath, spawners.getJSONObject(i).getString("top")).toString());
            ingredientArrayList.add(newIngredient);
        }
    }
}
