package resources;

import java.io.*;
import java.util.*;
import javax.json.*;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ServerEndpoint("/SpeedGame")
public class Server {
	static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
	GameManager g;
	
	private String buildJsonData(String username, String message) {
		//System.out.println("username: " + username + "\nmessage: " + message);
		JsonObject object = Json.createObjectBuilder().add("message", message).add("username", username).build();
		JsonWriter writer = Json.createWriter(object);
		//return "h";//object.toString();
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(object);}
		return stringWriter.toString();
		//return "h";
	}
	
	@OnOpen
	public void onOpen(Session userSession) {
		users.add(userSession);
		//g = new GameManager();
		
		System.out.println("handleOpen Called");
		System.out.println(userSession);
		//two users max
	}
	
	@OnMessage
	public void onMessage(String message, Session userSession) throws IOException {
		String username = (String) userSession.getUserProperties().get("username");
		
		
		System.out.println("handleMessage Called");
		
		//System.out.println(buildJsonData("username", message));
		
		if (username == null) {
			userSession.getUserProperties().put("username", message);
			System.out.println("onMessage username: " + username);
			userSession.getBasicRemote().sendText(message);
			//userSession.getBasicRemote().sendText(Json.createObjectBuilder().add("username", message).build().toString());
		}
		else {
			/*Iterator<Session> iterator = users.iterator();
			while (iterator.hasNext()) {
				iterator.next().getBasicRemote()
				.sendText(Json.createObjectBuilder().add(username, message).build().toString());
			}*/
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