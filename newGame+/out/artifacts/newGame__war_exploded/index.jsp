<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Speed Game</title>
</head>
<body>

<!-- Play piles -->
<div id="piles" align="center">
  <img id="PileDeckL" src="images/Gray_back.jpg" height="10%" width="10%" draggable="false">
  <img id="Pile0" src="images/Blank.jpg" height="10%" width="10%" draggable="false" ondrop="drop(event)" ondragover="allowDrop(event)">
  <img id="Pile1" src="images/Blank.jpg" height="10%" width="10%" draggable="false" ondrop="drop(event)" ondragover="allowDrop(event)">
  <img id="Pile2" src="images/Blank.jpg" height="10%" width="10%" draggable="false" ondrop="drop(event)" ondragover="allowDrop(event)">
  <img id="Pile3" src="images/Blank.jpg" height="10%" width="10%" draggable="false" ondrop="drop(event)" ondragover="allowDrop(event)">
  <img id="PileDeckR" src="images/Gray_back.jpg" height="10%" width="10%" draggable="false">
</div>

<br><br><br>

<!-- Your hand and deck -->
<div id="player" align="center">
  <img id="PDeck" src="images/Gray_back.jpg" height="10%" width="10%" draggable="false">
  <img id="Hand0" src="images/Blank.jpg" height="10%" width="10%" draggable="true" ondragstart="drag(event);">
  <img id="Hand1" src="images/Blank.jpg" height="10%" width="10%" draggable="true" ondragstart="drag(event)">
  <img id="Hand2" src="images/Blank.jpg" height="10%" width="10%" draggable="true" ondragstart="drag(event)">
  <img id="Hand3" src="images/Blank.jpg" height="10%" width="10%" draggable="true" ondragstart="drag(event)">
  <img id="Hand4" src="images/Blank.jpg" height="10%" width="10%" draggable="true" ondragstart="drag(event)">
</div>

<!-- play messages -->
<div align="center">
  <h1 id="test"></h1>
  <!--<h1 id="p2message">
    p1Message
  </h1>
  <h1 id="p1message">
    p2Message
  </h1>-->
</div>

<textarea id="EchoArea" rows="10" cols="45"></textarea>
<form align="center">
  <input type="text" id="message">
  <input type="button" value="Echo" onclick="wsSendMessage()">
</form>

<script>
    var websocket = new WebSocket("wss://speedgamecs3750.azurewebsites.net/speedgame");
    //var websocket = new WebSocket("ws://localhost:8080/speedgame");
    var echoarea = document.getElementById("EchoArea");
    echoarea.value = "";
    //var echoSub = document.getElementById("echoSub");
    var PDL = document.getElementById("PileDeckL");
    var Pile0 = document.getElementById("Pile0");
    var Pile1 = document.getElementById("Pile1");
    var Pile2 = document.getElementById("Pile2");
    var Pile3 = document.getElementById("Pile3");
    var PDR = document.getElementById("PileDeckR");
    var PDeck = document.getElementById("PDeck");
    var Hand0 = document.getElementById("Hand0");
    var Hand1 = document.getElementById("Hand1");
    var Hand2 = document.getElementById("Hand2");
    var Hand3 = document.getElementById("Hand3");
    var Hand4 = document.getElementById("Hand4");
    var PMessage = document.getElementById("PMessage");

    var message = document.getElementById("message");

    websocket.onopen = function(){ wsOpen() };

    function wsOpen(){
        echoarea.value += "Connected ... \n";
        /*var image = document.createElement("IMG");
        image.src="images/Blank.jpg";
        echoarea.appendChild(image);
        echoSub.innerHTML = "Flag wsOpen";*/
        //PDL.src = "images/2H.jpg"
    }

    websocket.onmessage = function processMessage(message){
        echoarea.value += message.data + "\n";
        var jsonData = JSON.parse(message.data);
        if(jsonData.message != null) echoarea.value += jsonData.message + "\n";

        //echoarea.value += message.data + "\n";
        //echoarea.value += "onMessage called\n";
        //var jsonData = JSON.parse(this.responseText);
        //Pile0.src = "images/" + jsonData.Pile0;
        //var jsonData = JSON.parse(message.data);
        //Pile0.src = "images/" + jsonData.Pile0;
        //if(jsonData.message != null) echoarea.value += jsonData.message + "\n";
    };

    function wsSendMessage(){
        websocket.send(message.value);

        echoarea.value += "Message sent to server: " + message.value + "\n";
        message.value = "";

        /*echoarea.value += "Message sent to the server : " + message.value + "\n";
        message.value = "";*/
    }

    var test = document.getElementById("test");

    function allowDrop(ev) {
        ev.preventDefault();
        test.innerHTML = "dragging";
    }

    function drag(ev) {
        ev.dataTransfer.setData("text", ev.target.id);
    }

    function drop(ev) {
        ev.preventDefault();
        var data = ev.dataTransfer.getData("text");
        //document.getElelmentById("test").innerHTML = "0";
        test.innerHTML = "1";
        test.innerHTML += data.substring(4);
        test.innerHTML += ev.target.id.substring(4);
    }
</script>


</body>
</html>