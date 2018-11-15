package resources;
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

@ServerEndpoint("/SpeedGame")

public class Server {
	static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());	
	
	@OnOpen
	public void handleOpen(Session userSession) {
		users.add(userSession);
		//two users max
	}
	
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
		String username = (String) userSession.getUserProperties().get("username");
		/*if (username == null) {
			userSession.getUserProperties().put("username", message);
			userSession.getBasicRemote().sendText(buildJsonData("System", "You are now connected as " + message));
		}
		else {
			
		}*/
		Iterator<Session> iterator = users.iterator();
		while (iterator.hasNext()) iterator.next().getBasicRemote().sendText(buildJsonData());
	}
	
	@OnClose
	public void handleClose(Session userSession) {
		users.remove(userSession);
	}
	
	private String buildJsonData(String username, String message) {
		JsonObject jsonObject = Json.createObjectBuilder().add("message", username+": " + message).build();
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(jsonObject);}
		return stringWriter.toString();
	}
}