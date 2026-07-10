import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ModManager {
    public JSONArray testFoodObject(String path) {
        String dir = System.getProperty("user.dir");
        JSONArray debugArray = new JSONArray();
        System.out.println("here and " + path);

        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
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

    public JSONArray setupSpawners(String path) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject object = new JSONObject(jsonContent);

            JSONArray spawners = new JSONArray();
            spawners = object.getJSONArray("spawners");

            for (int i = 0; i < spawners.length(); i++) {
                spawners.getJSONObject(i).put("width", object.getInt("width"));
                spawners.getJSONObject(i).put("height", object.getInt("height"));
                spawners.getJSONObject(i).put("path", object.getString("base"));

            }

            return spawners;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
