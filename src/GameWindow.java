import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.Vector;

public class GameWindow extends JFrame {
    private final Dimension prefSize = new Dimension(800, 400);
    GameField gameField = new GameField();

    Vector testVector = new Vector(0, 0);
    Timer t = new Timer(16, new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            doOnTick();
        }
    });

    public GameWindow() {
        setFocusable(true);
        setPreferredSize(prefSize);
        this.add(gameField);
        pack();
        setVisible(true);
        setTitle("Kitchen Master");
        gameField.repaint();
        t.start();
    }

    private void doOnTick() {
        Point mouse = getMousePosition();

        if (mouse != null) {
            System.out.println(mouse.x + " | " + mouse.y);
            gameField.debug.setX(mouse.x - 33);
            gameField.debug.setY(mouse.y - 60);
            gameField.repaint();
        }
    }
}
