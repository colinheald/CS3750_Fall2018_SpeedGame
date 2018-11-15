<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.*, java.lang.*, java.io.*, resources.*, resources.GameManager.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="js/ClickNDrag.js"></script>
<script>
	var websocket = new WebSocket("http://localhost:8080/SomeName/index.jsp");
</script>
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

	
</body>
</html>