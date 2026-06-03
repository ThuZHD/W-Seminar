import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ModManager {

    public ModManager() {
//        String dir = System.getProperty("user.dir");
//
//        try {
//            String jsonContent = new String(Files.readAllBytes(Paths.get(dir+ "/src/resources/Döner/kebab.json")));
//            JSONObject object = new JSONObject(jsonContent);
////            System.out.println(object.getJSONArray("ingredients"));
//
//            JSONArray test = object.getJSONArray("ingredients");
////            System.out.println(test.getJSONObject(10));
//
//            int cycle = 0;
//            while (true) {
//                try {
//                    System.out.println(test.getJSONObject(cycle));
//                    cycle++;
//                } catch (JSONException e) {
//                    System.out.println("stoped json cycle");
//                    break;
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        System.out.println("current dir = " + dir);
    }

    public JSONArray testFoodObject() {
        String dir = System.getProperty("user.dir");
        JSONArray debugArray = new JSONArray();

        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(dir+ "/src/resources/Döner/kebab.json")));
            JSONObject object = new JSONObject(jsonContent);
            System.out.println(object.getJSONArray("ingredients"));

            JSONArray test = object.getJSONArray("ingredients");

            int cycle = 0;
            while (true) {
                try {
                    debugArray.put(test.getJSONObject(cycle));
//                    System.out.println(test.getJSONObject(cycle));
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
        return debugArray;
    };
}
