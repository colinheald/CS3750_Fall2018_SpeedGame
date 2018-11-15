<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
	pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.*, java.lang.*, java.io.*, resources.*, org.json.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Speed Game</title>
<script src="js/ClickNDrag.js"></script>
</head>
<body>
	<%
		String DeckBack = "<img src ='images/Gray_back.jpg' draggable='false' height='10%' width='10%'>";
	
		GameManager g = new GameManager();
	%>

	<!-- Play piles -->
	<div id="piles">
		<%
			out.println(DeckBack);
		%>
		<%
			String[] playPiles = g.getPiles();
		
			for (int i = 0; i < playPiles.length; ++i)
			{
				out.println(playPiles[i]);
			}
		%>
		<%
			out.println(DeckBack);
		%>
	</div>
	
	<!-- Your hand and deck -->
	<div id="player">
		<%
			if(g.getDeck(0))
			{
				out.println(DeckBack);
			}
		%>
		<%
			String[] p1Hand = g.getPlayerHand(0);
			
			for(int i = 0; i < p1Hand.length; ++i)
			{
				out.println(p1Hand[i]);
			}
		%>
	</div>
	
	<div>
		<h1 id="test">
			Drop Here
		</h1>
	</div>
	
	<textarea id="EchoArea" rows="10" cols="45"></textarea>
		<br>
	<form>
		<input type="text" id="message">
		<input type="button" value="Echo" onclick="wsSendMessage();">
	</form>

	<script type="text/javascript">
		var websocket = new WebSocket("wss://speedgamecs3750.azurewebsites.net/SpeedGame");
		//var websocket = new WebSocket("ws://localhost:8080/SomeName/SpeedGame");
		var echoarea = document.getElementById("EchoArea");
		echoarea.value = "";
		var message = document.getElementById("message");
		
		websocket.onopen = function(message){ wsOpen(message) }
		
		function wsOpen(message){
			echoarea.value += "Connected ... \n";
		}
		
		websocket.onmessage = function processMessage(message){
			var jsonData = JSON.parse(message.data);
			if(jsonData.message != null) echoarea.value += jsonData.message + "\n";
		}
		
		function wsSendMessage(){
			websocket.send(message.value);
			echoarea.value += "Message sent to the server : " + message.value + "\n";
			message.value = "";
		}
	</script>
	
</body>
</html>