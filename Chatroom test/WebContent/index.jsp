<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
		var websocket = new WebSocket("ws://localhost:8080/chat");
		
		websocket.onmessage = function processMessage(message){
			var jsonData = JSON.parse(message.data);
			if(jsonData.message != null) messagesTextArea.value += jsonData.message + "\n";
		}
		
		function sendMessage(){
			websocket.send(messageText.value);
			messageText.value = "";
		}
	</script>
	</head>
	<body>
		<textarea id="messagesTextArea" readonly = "readonly" rows = "10" cols = "45"></textarea><br/>
		<input type="text" id="messageText" size = "50" /><input type="button" value="Send" onclick="sendMessage();" />
		
	</body>
</html>