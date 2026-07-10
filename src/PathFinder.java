import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FilenameFilter;

public class PathFinder {
    public static String getPath() {
        // 'null' für ein eigenständiges Fenster, danach der Titel des Dialogs
        FileDialog fileDialog = new FileDialog((Frame) null, "JSON Datei auswählen");
        fileDialog.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(java.io.File dir, String name) {
                return name.toLowerCase().endsWith(".json");
            }
        });
        fileDialog.setMode(FileDialog.LOAD); // LOAD für Öffnen, SAVE für Speichern
        fileDialog.setVisible(true);

        // Pfad und Datei abfangen
        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();

        if (directory != null && filename != null) {
            String fullPath = directory + filename;
            System.out.println("Echter nativer Pfad: " + fullPath);
            return fullPath;
        } else {
            System.out.println("Auswahl abgebrochen. ❌");
            return "error";
        }
    }
}