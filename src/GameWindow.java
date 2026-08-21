import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameWindow extends JFrame {
    GameField gameField = new GameField();
    PathFinder pf = new PathFinder();
    String path = pf.getPath();

    String fullPath = path;

    Path relativePath = Paths.get(fullPath);
    String directoryPath = relativePath.getParent().toString();

    Timer t = new Timer(1/240, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            doOnTick();
        }
    });

    ModManager modManager = new ModManager();

    public GameWindow() {
        setTitle("Kitchen Master");
        setMinimumSize(new Dimension(1280, 720));
        setFocusable(true);
        add(gameField);
        pack();

        setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        setVisible(true);

        gameField.repaint();
        t.start();

        if(path != "error" && path.endsWith(".json")) {
            gameField.setupFoodSpawnersInField(modManager.setupFoodSpawners(path), directoryPath);
            gameField.setupIngredientSpawnersInField(modManager.setupIngredientSpawners(path), directoryPath);
        }
    }

    private void doOnTick() {
        Point mouse = getMousePosition();
        if (mouse != null) {
            Point relativeMouse = SwingUtilities.convertPoint(this, mouse, gameField);

            gameField.mouseCoordinate.setX(relativeMouse.x);
            gameField.mouseCoordinate.setY(relativeMouse.y);
            gameField.repaint();
        }
    }
}
