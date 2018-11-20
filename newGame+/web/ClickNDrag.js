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
    document.getElelmentById("test").innerHTML = "0";
    test.innerHTML = "0";
    test.innerHTML += data.toString().substr(4);
    test.innerHTML += ev.target.id.toString().substr(4);
    
}