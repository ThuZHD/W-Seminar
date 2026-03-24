import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.TextUI;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import java.awt.*;
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
    private final Dimension prefSize = new Dimension(1400, 900);
    public int test = 0;
    public Coordinate debug = new Coordinate(0,0);

    int screenWidth;
    int screenHeight;

    public GameField() {
        setPreferredSize(prefSize);
        setBackground(Color.cyan);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        System.out.println(getSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println(getSize());
        // Farbe setzen
        g.setColor(Color.BLUE);

        // Viereck zeichnen (x, y, Breite, Höhe)
        g.fillRect(0, getSize().height/3*2, getSize().width, screenHeight/3);
//        g.fillOval(debug.getX(), debug.getY(), 67, 67);
        try {
            BufferedImage iconeNave = ImageIO.read(getClass().getResource("/resources/img_1.png"));
            g.drawImage(iconeNave, debug.getX()+10, debug.getY()+30, 40, 40, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
