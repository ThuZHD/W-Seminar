import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ModManager {

    public ModManager() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get("/Users/brunobeuttler/Desktop/everything/Code/W-Sem/src/resources/Döner/kebab.json")));
            JSONObject object = new JSONObject(jsonContent);
            System.out.println(object.getJSONArray("ingredients"));

            JSONArray test = object.getJSONArray("ingredients");
//            System.out.println(test.getJSONObject(10));

            int cycle = 0;
            while (true) {
                try {
                    System.out.println(test.getJSONObject(cycle));
                    cycle++;
                } catch (JSONException e) {
                    System.out.println("stoped json cycle");
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
    }
}
