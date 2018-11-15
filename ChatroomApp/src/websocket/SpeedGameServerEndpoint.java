package websocket;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import Deck;

@ServerEndpoint("/speedGameServerEndpoint")

public class SpeedGameServerEndpoint {
    static Set<Session> gamePlayers = Collections.synchronizedSet(new HashSet<Session>());
    Deck deck;

    @OnOpen
    //add users to the session, until there are two.  Make sure we can determine which player (0 or 1) is sending every message

    //when the game is ready to start:
    deck = new Deck();
    @OnMessage
    public void handleMessage(String message, Session userSession) throws IOException {

        int player = 0;//get the player number from the userSession somehow.  hard coding a value for now.

        JsonObject messageJson = jsonFromString(message);
        String method = messageJson.method;
        if(method == 'Play'){

        }
        else if(method == 'Draw'){

        }
        if (username == null) {
            userSession.getUserProperties().put("username", message);
            userSession.getBasicRemote().sendText(buildJsonData("System", "You are now connected as " + message));
        }
        else {
            Iterator<Session> iterator = gamePlayers.iterator();
            while (iterator.hasNext()) iterator.next().getBasicRemote().sendText(buildJsonData(username, message));
        }
    }

    @OnClose
    public void handleClose(Session userSession) {
        gamePlayers.remove(userSession);
    }

    private String buildJsonData(String username, String message) {
        JsonObject jsonObject = Json.createObjectBuilder().add("message", username+": " + message).build();
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(jsonObject);}
        return stringWriter.toString();
    }

    private JsonObject jsonFromString(String jsonObjectStr) {

        JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        return object;
    }
}
