import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


public class Main {

    public static void main(String[] args) {

        Deck cm = new Deck();
        String json = cm.GameState();
        //System.out.println(json);

        JsonObjectBuilder jsonObject = Json.createObjectBuilder();

        jsonObject.add("Method","Play");
        jsonObject.add("Card",2);
        jsonObject.add("Pile",1);
        jsonObject.add("Player",1);

        String input = jsonObject.build().toString();
        json = cm.GameState(input);
        System.out.println(json);
        int i = 2;


    }
}
