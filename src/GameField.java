import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.TextUI;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.Vector;

public class GameField extends JPanel {

    private final Dimension prefSize = new Dimension(400, 90);
    public int test = 0;
    public Coordinate debug = new Coordinate(0,0);
    BufferedImage iconeNave;

    int screenWidth;
    int screenHeight;



    Food food = new Food();
    Ingredient brot = new Ingredient();

    public GameField() {
        setPreferredSize(prefSize);
        setBackground(Color.cyan);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        System.out.println(getSize());

        BufferedImage cursorImg;

        cursorImg = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                food.toggleIsFollowingMouse(debug.getX(), debug.getY());
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Farbe setzen
        g.setColor(Color.BLUE);
        g.fillRect(0, getSize().height/3*2, getSize().width, screenHeight/3);
        food.setPos(debug.getX()-50, debug.getY()-50);
        food.draw((Graphics2D) g);

        // Viereck zeichnen (x, y, Breite, Höhe)

//        g.fillOval(debug.getX(), debug.getY(), 67, 67);
//       BufferedImage iconeNave = ImageIO.read(getClass().getResource("/resources/img_1.png"));
        g.drawImage(iconeNave, debug.getX()-20, debug.getY()-20, 160, 200, null);

        brot.draw((Graphics2D) g);
    }
}
