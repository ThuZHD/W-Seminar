import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusikPlayer {

    // Die Methode ist statisch (static), damit wir sie von überall aufrufen können
    public static void soundAbspielen(String pfad) {
        try {
            System.out.println("jere");
            java.net.URL url = MusikPlayer.class.getResource("resources/tiki.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception e) {
            System.out.println("Fehler beim Abspielen: " + e.getMessage());
        }
    }
}