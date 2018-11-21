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
var PrevHand = "";
var PrevPile = "";


websocket.onopen = function(){ wsOpen() };

function wsOpen(){
    UserId = 1;

    if(UserId == 0) {
        echoarea.value += "Connected as Player1\n";
    }
    else{
        echoarea.value += "Connected as Player2\n";
    }

}

websocket.onmessage = function processMessage(message){
    //echoarea.value += message.data + "\n";
    echoarea.scrollTop = echoarea.scrollHeight;
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
        PMessage.innerHTML = processPlayerMessage(jsonData.P1Message);
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
        PMessage.innerHTML = processPlayerMessage(jsonData.P2Message);
    }
};

function processPlayerMessage(string){
    var retVal = "Message: ";

    if(string != null){
        retVal += string;
    }
    else{
        retVal = "Play: "
        switch(PrevHand){
            case "Hand0": retVal += Hand0.src.slice(-6, -4);
                break;
            case "Hand1": retVal += Hand1.src.slice(-6, -4);
                break;
            case "Hand2": retVal += Hand2.src.slice(-6, -4);
                break;
            case "Hand3": retVal += Hand3.src.slice(-6, -4);
                break;
            case "Hand4": retVal += Hand4.src.slice(-6, -4);
                break;
            default:
                break;
        }
        switch(PrevPile){
            case "Pile0": retVal += " played on " + Pile0.src.slice(-6, -4);
                break;
            case "Pile1": retVal += " played on " + Pile1.src.slice(-6, -4);
                break;
            case "Pile2": retVal += " played on " + Pile2.src.slice(-6, -4);
                break;
            case "Pile3": retVal += " played on " + Pile3.src.slice(-6, -4);
                break;
            default:
                break;
        }

        retVal = retVal.replace(/0/g, 10);
        retVal += "\n";
    }

    return retVal;
}

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
    PrevHand = data;
    PrevPile = ev.target.id;

    echoarea.value += processPlayerMessage();
    PMessage.innerHTML = processPlayerMessage();

    websocket.send(getJsonMessage(1));
}

function draw(){
    websocket.send(getJsonMessage(2));
}