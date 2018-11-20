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
  <img id="PDeck" src="images/Gray_back.jpg" height="10%" width="10%" draggable="false" onclick="draw()">
  <img id="Hand0" src="images/Blank.jpg" height="10%" width="10%" draggable="true" ondragstart="drag(event)">
  <img id="Hand1" src="images/Blank.jpg" height="10%" width="10%" draggable="true" ondragstart="drag(event)">
  <img id="Hand2" src="images/Blank.jpg" height="10%" width="10%" draggable="true" ondragstart="drag(event)">
  <img id="Hand3" src="images/Blank.jpg" height="10%" width="10%" draggable="true" ondragstart="drag(event)">
  <img id="Hand4" src="images/Blank.jpg" height="10%" width="10%" draggable="true" ondragstart="drag(event)">
</div>

<!-- play messages -->
<div align="center">
  <h1 id="pMessage"></h1>
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
    //var websocket = new WebSocket("wss://speedgamecs3750.azurewebsites.net/speedgame");
    var websocket = new WebSocket("ws://localhost:8080/speedgame");
    var echoarea = document.getElementById("EchoArea");
    echoarea.value = "";

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
    var UserId;

    var HandPlay;
    var PilePlay;

    var message = document.getElementById("message");

    websocket.onopen = function(){ wsOpen() };

    function wsOpen(){
        echoarea.value += "Connected ... \n";
        UserId = 1;
    }

    websocket.onmessage = function processMessage(message){
        echoarea.value += message.data + "\n";
        var jsonData = JSON.parse(message.data);

        Pile0.src = "images/" + jsonData.Pile0;
        Pile1.src = "images/" + jsonData.Pile1;
        Pile2.src = "images/" + jsonData.Pile2;
        Pile3.src = "images/" + jsonData.Pile3;

        if(UserId == 0) {
            if (jsonData.P1Deck) {
                PDeck.src = "images/Gray_back.jpg";
            }
            else {
                PDeck.src = "images/Blank.jpg";
            }

            Hand0.src = "images/" + jsonData.P1Hand0;
            Hand1.src = "images/" + jsonData.P1Hand1;
            Hand2.src = "images/" + jsonData.P1Hand2;
            Hand3.src = "images/" + jsonData.P1Hand3;
            Hand4.src = "images/" + jsonData.P1Hand4;
            //PMessage.innerHTML = "Message: " + jsonData.P1Message;
        }
        else{
            if (jsonData.P2Deck) {
                PDeck.src = "images/Gray_back.jpg";
            }
            else {
                PDeck.src = "images/Blank.jpg";
            }

            Hand0.src = "images/" + jsonData.P2Hand0;
            Hand1.src = "images/" + jsonData.P2Hand1;
            Hand2.src = "images/" + jsonData.P2Hand2;
            Hand3.src = "images/" + jsonData.P2Hand3;
            Hand4.src = "images/" + jsonData.P2Hand4;
            //PMessage.innerHTML = "Message: " + jsonData.P2Message;
        }
    };

    function getJsonMessage(i){
        var jsonToSend;

        if(i == 1) {
            jsonToSend = '{"Method":"Play","Card":' + HandPlay + ',"Pile":' + PilePlay + ',"Player":' + UserId + '}';
        }
        else if(i == 2){
            jsonToSend = '{"Method":"Draw","Player":' + UserId + '}';
        }
        else{
            jsonToSend = '{"Method":"Stalemate"}';
        }

        return jsonToSend;
    }

    function wsSendMessage(){
        websocket.send(getJsonMessage((1)));

        //echoarea.value += "Message sent to server: " + message.value + "\n";
        //message.value = "";

        /*echoarea.value += "Message sent to the server : " + message.value + "\n";
        message.value = "";*/
    }

    function allowDrop(ev){
        ev.preventDefault();
    }

    function drag(ev){
        ev.dataTransfer.setData("text", ev.target.id);
    }

    function drop(ev){
        ev.preventDefault();
        var data = ev.dataTransfer.getData("text");
        HandPlay = data.substring(4);
        PilePlay = ev.target.id.substring(4);

        //echoarea.value += "User: " + UserId + "\tHand: " + HandPlay + "\tPile: " + PilePlay;
        echoarea.value += getJsonMessage(1);

        websocket.send(getJsonMessage(1));
    }

    function draw(){
        //echoarea.value += "called draw method";
        //echoarea.value += getJsonMessage(2);

        websocket.send(getJsonMessage(2));
    }
</script>


</body>
</html>