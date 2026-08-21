import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FilenameFilter;

public class PathFinder {
    public static String getPath() {
        FileDialog fileDialog = new FileDialog((Frame) null, "JSON Datei auswählen");
        fileDialog.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(java.io.File dir, String name) {
                return name.toLowerCase().endsWith(".json");
            }
        });
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();

        if (directory != null && filename != null) {
            String fullPath = directory + filename;
            return fullPath;
        } else {
            return "error";
        }
    }
}