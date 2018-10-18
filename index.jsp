<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" 
    import="java.util.*, java.lang.*, javax.swing.JTextPane" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Java Hello World on Azure</title>
</head>
<body>
	<% 
		Random r = new Random();
		int num = r.nextInt(13) + 1; //Generate 13 rand numbers starting with 1 
		int suit = r.nextInt(4); //Generate 4 rand numbers starting with 0
		
		String s = "";
		
		switch(num)
		{
		case 1:
			s = "A";
			break;
		case 2:
			s = "2";
			break;
		case 3:
			s = "3";
			break;
		case 4:
			s = "4";
			break;
		case 5:
			s = "5";
			break;
		case 6:
			s = "6";
			break;
		case 7:
			s = "7";
			break;
		case 8:
			s = "8";
			break;
		case 9:
			s = "9";
			break;
		case 10:
			s = "10";
			break;
		case 11:
			s = "J";
			break;
		case 12:
			s = "Q";
			break;
		case 13:
			s = "K";
			break;
		default:
			s = "";
		}
		
		switch(suit)
		{
		case 0:
			s += "D";
			break;
		case 1:
			s += "H";
			break;
		case 2:
			s += "S";
			break;
		case 3:
			s += "C";
			break;
		default:
			s += "aces";
		}
	%>
	<%
		JTextPane jtp = new JTextPane();
		jtp.setContentType("text/html");
		jtp.setText("<img src ='/images/Gray_back.jpg' height='10%' width='10%'>");
		String DeckBack = "<img src ='/images/Gray_back.jpg' height='10%' width='10%'>";
		int opponentCardCount = 4;
	%>
	<h1><% out.println("Hello Java Web Application!"); %></h1>
	
	<!-- other player's hand and deck -->
	<div align="center">
		<%
			for (int i = 0; i < opponentCardCount; ++i)
			{
				out.println(DeckBack);
			}
		%>
	</div>
	
	<!-- Play pile -->
	<div align="center">
		
	</div>
	
	<!-- Your hand and deck -->
	<div align="center">
		<img src="/images/<%= s %>.jpg" height="10%" width="10%">
	</div>
</body>
</html>