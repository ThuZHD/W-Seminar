import org.json.*;

public class OrderManager {
    // JSON Arrays nutzen doppelte Anführungszeichen
    String test = "[\"here\", \"auch here\"]";

    public OrderManager () {
        try {
            // Wir nutzen JSONArray statt JSONObject
            JSONArray array = new JSONArray(test);

            // Jetzt kannst du auf die Daten zugreifen:
            String firstEntry = array.getString(1);
            System.out.println("Erster Eintrag: " + firstEntry);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}