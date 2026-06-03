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

//     OrderManager orderManager = new OrderManager();
    ModManager modManager = new ModManager();

    public GameWindow() {
        setTitle("Kitchen Master");
        setMinimumSize(new Dimension(400, 400));
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

        gameField.repaint();
        t.start();
        gameField.setAvailableIngredients(modManager.testFoodObject());
    }

    private void doOnTick() {
        Point mouse = getMousePosition();
        if (mouse != null) {
            // Wandelt die Fenster-Koordinaten in lokale Koordinaten des GameFields um
            Point relativeMouse = SwingUtilities.convertPoint(this, mouse, gameField);

            gameField.mouseCoordinate.setX(relativeMouse.x);
            gameField.mouseCoordinate.setY(relativeMouse.y);
            gameField.repaint();
        }
    }
}
