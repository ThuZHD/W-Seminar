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

    // unneceseary
    private final Dimension prefSize = new Dimension(400, 90);
    public Coordinate mouseCoordinate = new Coordinate(0,0);

    int screenWidth;
    int screenHeight;

    boolean isGrabbingIngredient = false;
    boolean isGrabbingFood = false;

    // activeGrabbedFood might be "-1", in this case no food is being grabed
    int activeGrabbedFood = -1;
    int activeGrabbedIngredient = -1;

    ArrayList<Food> foodArrayList = new ArrayList<Food>();

    ArrayList<String> log = new ArrayList<String>();

    void addIngedientToFood(String Base, String Top) {
        for(Food food : foodArrayList) {
            if (
                    (mouseCoordinate.getX() - 50 - food.getXPos() > -40 && mouseCoordinate.getX() - 50 - food.getYPos() < 40) &&
                            (mouseCoordinate.getY() - 50 - food.getYPos() > -40 && mouseCoordinate.getY() - 50 - food.getYPos() < 40) && isGrabbingIngredient
            ) {
                food.addIngredient("/resources/Döner/Tomate Base.png", "/resources/Döner/Tomate Top.png");
                tomatenSpawner.reset();
            }
        }
    }

    int checkMouseOverItem(int mouseXPos, int mouseYPos) {
        for (int i = 0; i < foodArrayList.size(); i++) {
            if (
                    (mouseCoordinate.getX() - 50 - foodArrayList.get(i).getXPos() > -40 && mouseCoordinate.getX() - 50 - foodArrayList.get(i).getXPos() < 40) &&
                            (mouseCoordinate.getY() - 50 - foodArrayList.get(i).getYPos() > -40 && mouseCoordinate.getY() - 50 - foodArrayList.get(i).yPos < 40)
            ) {
                log.add("yes " + i);
                if(foodArrayList.get(i).disableSpawn()) {
                    Food newFoodSpawner = new Food();
                    newFoodSpawner.setup(foodArrayList.get(i).getXPos(), foodArrayList.get(i).getYPos(), foodArrayList.get(i).getWidth(), foodArrayList.get(i).getHeight(), foodArrayList.get(i).getImage());
                    foodArrayList.add(newFoodSpawner);
                }
                return i;
            }
        }
        return -1;
    }

    IngredientSpawner tomatenSpawner = new IngredientSpawner();

    ArrayList<String> availableIngredientsNameList = new ArrayList<String>();

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
                if(!isGrabbingFood) {
                    // checkMouseOverItem may return -1, this means no food is being grabbed 
                    activeGrabbedFood = checkMouseOverItem(mouseCoordinate.getX(), mouseCoordinate.getY());
                    if(activeGrabbedFood != -1) {
                        isGrabbingFood = true;
                        System.out.println("here");
                    }
                } else {
                    isGrabbingFood = false;
                }

                if(!isGrabbingIngredient) {

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

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
        // Farbe setzen
        g.setColor(Color.BLUE);
        g.fillRect(0, getSize().height/3*2, getSize().width, screenHeight/3);

        tomatenSpawner.drawSpawner((Graphics2D) g);
        tomatenSpawner.drawIngredient((Graphics2D) g);
        tomatenSpawner.setPos(mouseCoordinate.getX()-50, mouseCoordinate.getY()-50);

        g.setColor(Color.BLACK);

        for (int i = 0; i < log.size(); i++) {
            g.drawString(log.get(i), 20, 50 + i * 20);
        }

        if(isGrabbingFood) {
            foodArrayList.get(activeGrabbedFood).setPos(mouseCoordinate.getX() - 50, mouseCoordinate.getY() - 50);
//            System.out.println("here, but why?");
        }

        for(Food food : foodArrayList) {
            food.draw((Graphics2D) g);
        }
    }

    public void setAvailableIngredients(JSONArray ingredients) {
        System.out.println("these are all the available ingredients: " + ingredients);
//        ingredients.forEach();
        ingredients.forEach(item -> {
            log.add("ingredient found: " + item.toString());
        });
    }

    public void setupSpawnersInField(JSONArray spawners, String relativePath) {
        for (int i = 0; i < spawners.length(); i++) {
//            System.out.println(spawners.getJSONObject(i).getInt("xPos"));
            Food newFoodSpawner = new Food();
            newFoodSpawner.setup(spawners.getJSONObject(i).getInt("xPos"), spawners.getJSONObject(i).getInt("yPos"), spawners.getJSONObject(i).getInt("width"), spawners.getJSONObject(i).getInt("height"), Path.of(relativePath, spawners.getJSONObject(i).getString("path")).toString());
            foodArrayList.add(newFoodSpawner);
        }
    }
}
