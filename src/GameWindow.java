import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GameWindow extends JFrame {
    private final Dimension prefSize = new Dimension(800, 400);
    GameField gameField = new GameField();

    Vector testVector = new Vector(0, 0);
    Timer t = new Timer(1/240, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            doOnTick();
        }
    });

    // OrderManager orderManager = new OrderManager();

    public GameWindow() {
        setFocusable(true);
        add(gameField);
        // setUndecorated(true);          // entfernt Rahmen + Titelleiste
        pack();

        GraphicsDevice gd = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        // gd.setFullScreenWindow(this);  // echtes Fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setTitle("Kitchen Master");

        gameField.repaint();
        t.start();
    }

    private void doOnTick() {
        Point mouse = getMousePosition();

        if (mouse != null) {
            gameField.debug.setX(mouse.x - 33);
            gameField.debug.setY(mouse.y - 60);
            gameField.repaint();
        }
    }
}
