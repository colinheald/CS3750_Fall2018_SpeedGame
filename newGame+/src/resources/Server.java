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
	Deck d;
	JsonObject state;
	
	@OnOpen
	public void onOpen(Session userSession) throws IOException {
        if(users.size() > 2)
        {
            return;
        }
        d = new Deck();
        users.add(userSession);
        userId = users.size() -1;
		game = new GameManager();
		state = game.getGameState();
		//state = Json.createObjectBuilder().add("userId", userId).build();
        //userSession.getUserProperties().put("Pile0", "2H.jpg");
		userSession.getBasicRemote().sendText(d.GameState());
		//two users max

	}
	
	@OnMessage
	public void onMessage(String message, Session userSession) throws IOException {
		//state = game.getGameState(message);
		String s = d.GameState(message);
		Iterator<Session> iterator = users.iterator();
		while (iterator.hasNext()) {
			//Session reciever = iterator.next();
			//reciever.getBasicRemote().sendText(message);
			iterator.next().getBasicRemote().sendText(s);
			//iterator.next().getBasicRemote().sendText(state.toString());
		}
		//userSession.getBasicRemote().sendText(s);
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