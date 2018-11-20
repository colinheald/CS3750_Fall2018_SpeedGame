package resources;

import java.io.*;
import java.util.*;
import javax.json.*;
import javax.json.JsonObject.*;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ServerEndpoint("/speedgame")
public class Server {
	static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
	int userId;
	GameManager game;
	JsonObject state;
	
	private String buildJsonData(String username, String message) {
		/*JsonObject object = Json.createObjectBuilder().add("message", message).add("username", username).build();
		JsonWriter writer = Json.createWriter(object);
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(object);}
		return stringWriter.toString();*/
		return "h";
        //Json.createObjectBuilder().add("Pile0": )
	}
	
	@OnOpen
	public void onOpen(Session userSession) {
        if(users.size() > 2)
        {
            return;
        }
        users.add(userSession);
        //userId = (int) userSession.getUserProperties().get("ID");
        userId = users.size() -1;
		game = new GameManager();
		state = game.getGameState();
		state = Json.createObjectBuilder().add("userId", userId).build();
        //userSession.getUserProperties().put("Pile0", "2H.jpg");
		//userSession.getBasicRemote().sendText(state.toString());
		//two users max
	}
	
	@OnMessage
	public void onMessage(String message, Session userSession) throws IOException {

		String username = (String) userSession.getUserProperties().get("username");
		
		if (username == null) {
			userSession.getUserProperties().put("username", message);
			username = (String) userSession.getUserProperties().get("username");
			userSession.getBasicRemote().sendText(username + ": " + message);
			//userSession.getBasicRemote().sendText(message);
			//userSession.getBasicRemote().sendText(Json.createObjectBuilder().add("username", message).toString());
		}
		else {
			Iterator<Session> iterator = users.iterator();
			while (iterator.hasNext()) {
				iterator.next().getBasicRemote().sendText(username + ": UserId is " + userId);
			}
		}
	}
	
	@OnClose
	public void handleClose(Session userSession) {
		users.remove(userSession);
	}
	
	@OnError
	public void onError(Throwable e){
		System.out.println("Error thrown");
		e.printStackTrace();
	}
}