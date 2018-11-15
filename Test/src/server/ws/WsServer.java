package server.ws;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketendpoint")
public class WsServer {
	private Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session userSession) {
		users.add(userSession);
	}
	
	@OnClose
	public void onClose(Session userSession) {
		users.remove(userSession);
	}
	
	public static String buildJsonData(String username, String message) {
		JsonObject jsonObject = Json.createObjectBuilder().add("message", username+": " + message).build();
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(jsonObject);}
		return stringWriter.toString();
	}
	
	@OnMessage
	public void onMessage(String message, Session userSession) throws IOException {
		String username = (String) userSession.getUserProperties().get("username");
	
		if (username == null) {
			userSession.getUserProperties().put("username", message);
			userSession.getBasicRemote().sendText(buildJsonData("System", "You are now connected as " + message));
		}
		else {
			Iterator<Session> iterator = users.iterator();
			while (iterator.hasNext()) iterator.next().getBasicRemote().sendText(buildJsonData(username, message));
		}
		
		System.out.println("Message from the client: " + message);
		//String echoMsg = "Echo from the server : " + message;
		return;
	}

	@OnError
	public void onError(Throwable e){
		//e.printStackTrace();
	}

}
