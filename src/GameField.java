import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;


public class GameField extends JPanel {
    public Coordinate mouseCoordinate = new Coordinate(0,0);

    boolean isGrabbingFood = false;
    boolean isGrabbingIngredient = false;

    int activeGrabbedFood = 0;
    int activeGrabbedIngredient = 0;

    JSONObject possibleFood = new JSONObject();

    ArrayList<Food> foodArrayList = new ArrayList<Food>();
    ArrayList<Ingredient> ingredientArrayList = new ArrayList<Ingredient>();
    ArrayList<Customer> customers = new ArrayList<Customer>(Arrays.asList(null, null, null));

    BufferedImage backgroundImage;

    // used for images in mod folder
    String modRelativePath;

    // spawns a new customer every 10 seconds
    Timer customerTimer = new Timer(10000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 3; i++) {
                if(customers.get(i) == null) {
                    System.out.println("spawning customer on slot " + i);
                    Customer debugCustomer = new Customer();
                    debugCustomer.setup(200 + (i * 300), 110, 250, 250, Path.of(modRelativePath, "Customer.png").toString(), Path.of(modRelativePath, "speech.png").toString(), possibleFood);
                    customers.set(i, debugCustomer);
                    return;
                }
                System.out.println("game over, to many customers");
            }
            customerTimer.start();
        }
    });

    //
    // Functions
    //

    public GameField() {
        // self explaining
        {
            setBackground(Color.cyan);

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
                mouseHandler();
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

//        playAudio("/Users/brunobeuttler/Downloads/yusuf.wav", 6, true);
    }

    // used to check if a coordinate is in a certain area, mostly used for food and ingredient position checking with the mouse
    boolean areaChecker(int areaBeginPosX, int areaBeginPosY, int areaWidth, int areaHeight, int checkerPosX, int checkerPosY) {
        if (
                (checkerPosX - areaWidth/2 - areaBeginPosX > -areaWidth/2 && checkerPosX - areaWidth/2 - areaBeginPosX < areaWidth/2) &&
                        (checkerPosY - areaHeight/2 - areaBeginPosY > -areaHeight/2 && checkerPosY - areaHeight/2 - areaBeginPosY < areaHeight/2)
        ) {
            return true;
        } else {
            return false;
        }
    }

    private void mouseHandler() {
        // nothing grabbed by player
        if(!isGrabbingFood && !isGrabbingIngredient) {

            for (int i = 0; i < foodArrayList.size(); i++) {
                if(areaChecker(foodArrayList.get(i).getXPos(), foodArrayList.get(i).getYPos(), foodArrayList.get(i).getWidth(), foodArrayList.get(i).getHeight(), mouseCoordinate.getX(), mouseCoordinate.getY())) {
                    if (foodArrayList.get(i).disableSpawn()) {
                        Food newFoodSpawner = new Food();
                        newFoodSpawner.setup(foodArrayList.get(i).getXPos(), foodArrayList.get(i).getYPos(), foodArrayList.get(i).getWidth(), foodArrayList.get(i).getHeight(), foodArrayList.get(i).getBaseImagePath(), foodArrayList.get(i).getTopImagePath(), foodArrayList.size());
                        foodArrayList.add(newFoodSpawner);
                    }

                    activeGrabbedFood = i;
                    isGrabbingFood = true;
                    return;
                }
            }
            isGrabbingFood = false;

            for (int i = 0; i < ingredientArrayList.size(); i++) {
                if(areaChecker(ingredientArrayList.get(i).getXPos(), ingredientArrayList.get(i).getYPos(), ingredientArrayList.get(i).getWidth(), ingredientArrayList.get(i).getHeight(), mouseCoordinate.getX(), mouseCoordinate.getY())) {

                    if (ingredientArrayList.get(i).disableSpawn()) {
                        Ingredient newIngredientSpawner = new Ingredient();
                        newIngredientSpawner.setup(ingredientArrayList.get(i).getName(), ingredientArrayList.get(i).getXPos(), ingredientArrayList.get(i).getYPos(), ingredientArrayList.get(i).getWidth(), ingredientArrayList.get(i).getHeight(), ingredientArrayList.get(i).getBaseImagePath(), ingredientArrayList.get(i).getTopImagePath());
                        ingredientArrayList.add(newIngredientSpawner);
                    }

                    activeGrabbedIngredient = i;
                    isGrabbingIngredient = true;
                    return;
                }
            }
            isGrabbingIngredient = false;
        }

        // food grabbed by player
        if(isGrabbingFood) {
            for (int i = 0; i < customers.size(); i++) {
                if(customers.get(i) != null){
                    // check if food is in customer hitbox
                    if(areaChecker(customers.get(i).getXPos(), customers.get(i).getYPos(), customers.get(i).getWidth(), customers.get(i).getHeight(), mouseCoordinate.getX(), mouseCoordinate.getY())) {
                        // check if food is as ordered by customer
                        if(customers.get(i).submitFood(foodArrayList.get(activeGrabbedFood).getIngredients())) {
                            System.out.println("customer is happy");
                            foodArrayList.remove(activeGrabbedFood);
                            customers.set(i, null);
                        }
                    }
                }

            }
            isGrabbingFood = false;
        }

        // ingredient grabbed by player
        if(isGrabbingIngredient) {
            isGrabbingIngredient = false;

            for (int i = 0; i < foodArrayList.size(); i++) {
                if(areaChecker(foodArrayList.get(i).getXPos(), foodArrayList.get(i).getYPos(), foodArrayList.get(i).getWidth(), foodArrayList.get(i).getHeight(), mouseCoordinate.getX(), mouseCoordinate.getY())) {
                    Ingredient activeIngredient = ingredientArrayList.get(activeGrabbedIngredient);
                    foodArrayList.get(i).addIngredient(activeIngredient.getName(), activeIngredient.getBaseBufferedImage(), activeIngredient.getTopBufferedImage());
                    ingredientArrayList.remove(activeGrabbedIngredient);
                    return;
                }
            }
        }
    }

    private Clip playAudio(String dateipfad, float lautstarke, boolean loop) {
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

    public void setupFoodSpawnersInField(JSONArray spawners, String relativePath) {
        modRelativePath = relativePath;

        for (int i = 0; i < spawners.length(); i++) {
            if(i == 0) {
                possibleFood.put("baseImageBase", Path.of(relativePath, spawners.getJSONObject(i).getString("base")).toString());
                possibleFood.put("baseImageTop", Path.of(relativePath, spawners.getJSONObject(i).getString("top")).toString());
            }

            Food newFoodSpawner = new Food();
            newFoodSpawner.setup(
                    spawners.getJSONObject(i).getInt("xPos"),
                    spawners.getJSONObject(i).getInt("yPos"),
                    spawners.getJSONObject(i).getInt("width"),
                    spawners.getJSONObject(i).getInt("height"),
                    Path.of(relativePath, spawners.getJSONObject(i).getString("base")).toString(),
                    Path.of(relativePath, spawners.getJSONObject(i).getString("top")).toString(),
                    i
            );
            foodArrayList.add(newFoodSpawner);
        }
    }

    public void setupIngredientSpawnersInField(JSONArray spawners, String relativePath) {
        possibleFood.put("ingredients", new JSONArray());


        for (int i = 0; i < spawners.length(); i++) {
            JSONObject newPossibleIngredient = new JSONObject();
            newPossibleIngredient.put("name", spawners.getJSONObject(i).getString("name"));
            newPossibleIngredient.put("baseImage", Path.of(relativePath, spawners.getJSONObject(i).getString("base")).toString());
            newPossibleIngredient.put("topImage", Path.of(relativePath, spawners.getJSONObject(i).getString("top")).toString());

            possibleFood.getJSONArray("ingredients").put(newPossibleIngredient);

            Ingredient newIngredient = new Ingredient();
            newIngredient.setup(
                    spawners.getJSONObject(i).getString("name"),
                    spawners.getJSONObject(i).getInt("xPos"),
                    spawners.getJSONObject(i).getInt("yPos"),
                    spawners.getJSONObject(i).getInt("width"),
                    spawners.getJSONObject(i).getInt("height"),
                    Path.of(relativePath, spawners.getJSONObject(i).getString("base")).toString(),
                    Path.of(relativePath, spawners.getJSONObject(i).getString("top")).toString()
            );
            ingredientArrayList.add(newIngredient);
        }

        customerTimer.start();
    }

    public void setUpBackgroundImage(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        for (Customer customer : customers) {
            if(customer != null) {
                customer.draw((Graphics2D) g);
            }

        }


        if(isGrabbingFood) {
            foodArrayList.get(activeGrabbedFood).setPos(mouseCoordinate.getX() - 50, mouseCoordinate.getY() - 50);
        }

        if(isGrabbingIngredient) {
            ingredientArrayList.get(activeGrabbedIngredient).setPos(mouseCoordinate.getX() - 50, mouseCoordinate.getY() - 50);
        }

        g.drawImage(backgroundImage, 0, 0, 1280, 720, null);

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
}
