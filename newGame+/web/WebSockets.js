var websocket = new WebSocket("wss://speedgamecs3750.azurewebsites.net/speedgame");
//var websocket = new WebSocket("ws://localhost:8080/speedgame");
var echoarea = document.getElementById("EchoArea");
//var echoSub = document.getElementById("echoSub");
//var MDL = document.getElementById("MDL");
echoarea.value = "";
var message = document.getElementById("message");

websocket.onopen = function(message){ wsOpen(message) };

function wsOpen(message){
    echoarea.value += "Connected ... \n";
    /*var image = document.createElement("IMG");
    image.src="images/Blank.jpg";
    echoarea.appendChild(image);
    echoSub.innerHTML = "Flag wsOpen";
    MDL.src = "images/2H.jpg";*/
}

websocket.onmessage = function processMessage(message){
    //echoarea.value += message.data + "\n";
    echoarea.value += "onMessage called\n";
    var jsonData = JSON.parse(message.data);
    if(jsonData.message != null) echoarea.value += jsonData.message + "\n";
};

function wsSendMessage(){
    websocket.send(message.value);
    document.getElementById("echoSub").innerHTML = "wsSendMessage Called";
    message.value = "";
    echoarea.value += "Message sent to server: " + message.value + "\n";

    /*echoarea.value += "Message sent to the server : " + message.value + "\n";
    message.value = "";*/
}