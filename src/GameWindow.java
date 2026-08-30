import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameWindow extends JFrame {
    GameField gameField = new GameField();

    // PathFinder is responsible for opening a file selector window and returning the path of the main JSON file
    PathFinder pf = new PathFinder();
    String path = pf.getPath(true);

    String fullPath = path;

    Path relativePath = Paths.get(fullPath);
    String directoryPath = relativePath.getParent().toString();

    // main tick timer for the whole game
    Timer t = new Timer(1/240, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            doOnTick();
        }
    });

    // responsible for returning finished and easier JSON objects from the main JSON file
    ModManager modManager = new ModManager();

    public GameWindow() {
        setTitle("Kitchen Master");
        setMinimumSize(new Dimension(1280, 750));
        setFocusable(true);
        add(gameField);
        pack();
        setResizable(false);
        setVisible(true);

        gameField.repaint();
        t.start();

        if(path != "error" && path.endsWith(".json")) {
            System.out.println(modManager.setupFoodSpawners(path));
            gameField.setupFoodSpawnersInGameField(modManager.setupFoodSpawners(path), directoryPath);
            gameField.setupIngredientSpawnersInGameField(modManager.setupIngredientSpawners(path), directoryPath);
            gameField.setUpBackgroundImage(Paths.get(directoryPath, "background.png").toString());
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
