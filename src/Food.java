import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Food {

    BufferedImage brot;

    BufferedImage tomate_base;
    BufferedImage tomate_top;
    BufferedImage zwiebel_top;
    BufferedImage zwiebel_base;

    BufferedImage salat_top;
    BufferedImage salat_base;

    BufferedImage fleisch;

    int x =6;
    int y=7;

    boolean isFollowingMouse = false;

    public void setPos(int xPos, int yPos) {
        if(isFollowingMouse) {
            x = xPos;
            y = yPos;
        }
    }

    public void toggleIsFollowingMouse(int xPos, int yPos) {
        System.out.println((xPos - x > -40 && xPos - x < 40) && (yPos - y > -40 && yPos - y < 40));
        if((xPos-50 - x > -40 && xPos-50 - x < 40) && (yPos-50 - y > -40 && yPos-50 - y < 40)) {
            isFollowingMouse = !isFollowingMouse;
        }

    }

    public Food() {
        try {
            brot = ImageIO.read(getClass().getResource("/resources/Döner/Brot.png"));

            tomate_base = ImageIO.read(getClass().getResource("/resources/Döner/Tomate Base.png"));
            tomate_top = ImageIO.read(getClass().getResource("/resources/Döner/Tomate Top.png"));

            zwiebel_top = ImageIO.read(getClass().getResource("/resources/Döner/Zwiebel Top.png"));
            zwiebel_base = ImageIO.read(getClass().getResource("/resources/Döner/Zwiebel Base.png"));

//            salat_top = ImageIO.read(getClass().getResource("/resources/Döner/Salat Top.png"));
//            salat_base = ImageIO.read(getClass().getResource("/resources/Döner/Salat Base.png"));

            fleisch = ImageIO.read(getClass().getResource("/resources/Döner/Fleisch.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void draw(Graphics2D g) {
        g.drawImage(brot, x, y, 100, 100, null);
        g.drawImage(tomate_base, x, y, 100, 100, null);
        g.drawImage(zwiebel_base, x, y, 100, 100, null);
        g.drawImage(salat_base, x, y, 100, 100, null);

        g.drawImage(fleisch, x, y, 100, 100, null);
        g.drawImage(zwiebel_top, x, y, 100, 100, null);
        g.drawImage(salat_top, x, y, 100, 100, null);
        g.drawImage(tomate_top, x, y, 100, 100, null);
    }
}
