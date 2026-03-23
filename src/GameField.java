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
    private final Dimension prefSize = new Dimension(800, 400);
    public int test = 0;
    public Coordinate debug = new Coordinate(0,0);


    public GameField() {
        setPreferredSize(prefSize);
        setBackground(Color.cyan);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Farbe setzen
        g.setColor(Color.BLUE);

        // Viereck zeichnen (x, y, Breite, Höhe)
        g.fillRect(0, 200, 800, 200);
//        g.fillOval(debug.getX(), debug.getY(), 67, 67);
        try {
            BufferedImage iconeNave = ImageIO.read(getClass().getResource("/resources/Bild.png"));
            g.drawImage(iconeNave, debug.getX()-200, debug.getY()-200, 670, 670, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
