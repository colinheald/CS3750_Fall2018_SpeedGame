function allowDrop(ev) {
    ev.preventDefault();
    document.getElementById("test").innerHTML = "dragging";
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);

    //document.getElementById("test").innerHTML = ev.target.id;
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    document.getElementById("test").innerHTML = 0;
    document.getElementById("test").innerHTML += data.substring(0, 1);
    document.getElementById("test").innerHTML += ev.target.id.substring(0,1);
    
}