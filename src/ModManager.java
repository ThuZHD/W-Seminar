import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ModManager {

    // returns a cleaned up JSON array of all food spawners from the main file
    public JSONArray setupFoodSpawners(String path) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject object = new JSONObject(jsonContent);

            JSONArray spawners = new JSONArray();
            spawners = object.getJSONArray("spawners");

            for (int i = 0; i < spawners.length(); i++) {
                spawners.getJSONObject(i).put("width", object.getInt("width"));
                spawners.getJSONObject(i).put("height", object.getInt("height"));
                spawners.getJSONObject(i).put("base", object.getString("base"));
                spawners.getJSONObject(i).put("top", object.getString("top"));
            }

            return spawners;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // returns a cleaned up JSON array of all ingredient spawners from the main file
    public JSONArray setupIngredientSpawners(String path) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject object = new JSONObject(jsonContent);

            JSONArray ingredients = new JSONArray();
            ingredients = object.getJSONArray("ingredients");

            for (int i = 0; i < ingredients.length(); i++) {
                ingredients.getJSONObject(i).put("width", object.getInt("width"));
                ingredients.getJSONObject(i).put("height", object.getInt("height"));
                ingredients.getJSONObject(i).put("path", object.getString("base"));
            }

            return ingredients;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
