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

@ServerEndpoint("/speedgame")
public class Server {
	static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
	static Deck d = new Deck();
	
	@OnOpen
	public void onOpen(Session userSession) throws IOException {
		if(users.size() == 0) {
			users.add(userSession);
			userSession.getUserProperties().put("uId", 0);
			userSession.getUserProperties().put("stalemate", false);
			int uId = (int) userSession.getUserProperties().get("uId");

			userSession.getBasicRemote().sendText(Json.createObjectBuilder().add("uId", uId).build().toString());
		}
		else if(users.size() == 1) {
			users.add(userSession);
			userSession.getUserProperties().put("uId", 1);
			userSession.getUserProperties().put("stalemate", false);
			int uId = (int) userSession.getUserProperties().get("uId");

			userSession.getBasicRemote().sendText(Json.createObjectBuilder().add("uId", uId).build().toString());

			String s = d.GameState();

			Iterator<Session> iterator = users.iterator();
			while (iterator.hasNext()) {
				iterator.next().getBasicRemote().sendText(s);
			}
		}
		else {
			return;
		}
	}

	private void setStalemateFalse()
	{
		Iterator<Session> iterator = users.iterator();
		while(iterator.hasNext()){
			iterator.next().getUserProperties().put("stalemate", false);
		}
	}

	@OnMessage
	public void onMessage(String message, Session userSession) throws IOException {
		String s;
		Iterator<Session> iterator = users.iterator();

		if(message.contains("Stalemate")) {
			userSession.getUserProperties().put("stalemate", true);

			while (iterator.hasNext()) {
				if ((boolean) iterator.next().getUserProperties().get("stalemate")) {
					if ((boolean) iterator.next().getUserProperties().get("stalemate")) {
						s = d.GameState(message);

						Iterator<Session> iterator2 = users.iterator();
						while (iterator2.hasNext()) {
							iterator2.next().getBasicRemote().sendText(s);
						}
						setStalemateFalse();
					}
				}
			}
		}
		else {
			s = d.GameState(message);
			while (iterator.hasNext()) {
				userSession.getId();
				iterator.next().getBasicRemote().sendText(s);
			}

			setStalemateFalse();
		}
	}
	
	@OnClose
	public void handleClose(Session userSession) {
		users.remove(userSession);
		if(users.size() == 0)
		{
			d = new Deck();
		}
	}
	
	@OnError
	public void onError(Throwable e){
		System.out.println("Error thrown");
		e.printStackTrace();
	}
}