import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class GameField extends JPanel {

    // unneceseary 
    private final Dimension prefSize = new Dimension(400, 90);
    public Coordinate mouseCoordinate = new Coordinate(0,0);

    int screenWidth;
    int screenHeight;

    String activeIngredientBase;
    String activeIngredientTop;

    boolean isGrabbingIngredient = false;

    Food debugKebab = new Food();

    void addIngedientToFood(String Base, String Top) {
        if (
                (mouseCoordinate.getX() - 50 - debugKebab.x > -40 && mouseCoordinate.getX() - 50 - debugKebab.x < 40) &&
                        (mouseCoordinate.getY() - 50 - debugKebab.y > -40 && mouseCoordinate.getY() - 50 - debugKebab.y < 40) && isGrabbingIngredient
        ) {
            debugKebab.addIngredient("/resources/Döner/Tomate Base.png", "/resources/Döner/Tomate Top.png");
            tomatenSpawner.reset();
        }
    }

    IngredientSpawner tomatenSpawner = new IngredientSpawner();

    public GameField() {
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

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickHandler(mouseCoordinate.getX(), mouseCoordinate.getY());
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
        });

//        food.addIngredient("/resources/Döner/Tomate Base.png", "/resources/Döner/Tomate Top.png");
        debugKebab.addIngredient("/resources/Döner/Zwiebel Base.png", "/resources/Döner/Zwiebel Top.png");
        debugKebab.addIngredient("/resources/Döner/Salat Base.png", "/resources/Döner/Salat Top.png");

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Farbe setzen
        g.setColor(Color.BLUE);
        g.fillRect(0, getSize().height/3*2, getSize().width, screenHeight/3);
        debugKebab.setPos(mouseCoordinate.getX()-50, mouseCoordinate.getY()-50);
        debugKebab.draw((Graphics2D) g);

        tomatenSpawner.drawSpawner((Graphics2D) g);
        tomatenSpawner.drawIngredient((Graphics2D) g);
        tomatenSpawner.setPos(mouseCoordinate.getX()-50, mouseCoordinate.getY()-50);
        g.drawString("test", 100, 100);
    }

    void clickHandler(int xMousePos, int yMousePos) {
        debugKebab.toggleIsFollowingMouse(xMousePos, yMousePos);
        isGrabbingIngredient = tomatenSpawner.toggleIsFollowingMouse(xMousePos, yMousePos);
        addIngedientToFood("", "");
    }
}
