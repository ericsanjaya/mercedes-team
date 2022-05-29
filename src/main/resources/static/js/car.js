var websocketClient;

var myVehicleId;
var maxSpeed = 300;
var speed = 0;
var gear = 1;
var minGear = 1;
var maxGear = 6;

var worker;

function randomData(vehicleId) {

    // gear position
    var gearAction = Math.floor(Math.random() * 3) -1
    if (gearAction == 1 && gear < maxGear) {
        gear += 1;
    }
    if (gearAction == -1 && gear > minGear) {
        gear -= 1;
    }

    // speed
    if (speed < maxSpeed) {
        var newSpeed = speed + gear;
        if (newSpeed < maxSpeed) {
            speed = newSpeed;
        } else {
            speed = maxSpeed;
        }
    }

    // brake
    var brakeAction = Math.floor(Math.random() * 3);
    var newSpeed = speed - (brakeAction * 3);
    if (newSpeed > 0) {
        speed = newSpeed;
    } else {
        speed = 0;
    }

    document.getElementById("speed").innerHTML  = speed;
    document.getElementById("break").innerHTML  = brakeAction;
    document.getElementById("gear").innerHTML  = gear;

    return {
        "vehicleId": vehicleId,
        "speed": speed,
        "brakeCondition": brakeAction,
        "gear": gear
    }
}

function sendMessage () {
    var json = JSON.stringify(randomData(myVehicleId));
    if (websocketClient.readyState == 1) {
        websocketClient.send(json);
    } else {
        console.log("websocket close");
    }
}

function createWorker () {
    if (typeof(worker) == "undefined") {
        worker = new Worker("js/timer.js");
    }
    worker.onmessage = function(event){
        sendMessage();
    };
}

function connect() {
    myVehicleId = document.getElementById("vehicleId").value;
    localStorage.setItem("vehicleId", myVehicleId);

    if (websocketClient != null && websocketClient.readyState == 1) {
        return;
    }

    var url = "ws://localhost:7076/ws/car/" + myVehicleId;
    websocketClient = new WebSocket(url, "mercedes-net");

    websocketClient.onopen = function (event) {
    console.log("connected");
        createWorker();
    };

    websocketClient.onmessage = function (event) {
        console.log(event.data);
    }

    websocketClient.onclose = function (event) {
        wsClose();
        console.log("closed");
    }
}

function wsClose() {
    if (websocketClient.readyState == 1) {
        websocketClient.close();
        worker.terminate();
        worker = undefined;
    } else if(worker != undefined) {
        worker.terminate();
        worker = undefined;
    }
}

export {connect, wsClose};
