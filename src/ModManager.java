import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ModManager {

    public ModManager() {

    }

    public JSONArray testFoodObject() {
        String dir = System.getProperty("user.dir");
        JSONArray debugArray = new JSONArray();

        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(dir+ "/src/resources/Döner/kebab.json")));
            JSONObject object = new JSONObject(jsonContent);
//            System.out.println(object.getJSONArray("ingredients"));

            JSONArray test = object.getJSONArray("ingredients");

            for (int i = 0; i < test.length(); i++) {
                debugArray.put(test.getJSONObject(i).getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return debugArray;
    };
}
