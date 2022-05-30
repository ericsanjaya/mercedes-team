var websocketClient;

var myVehicleId;
var maxSpeed = 300;
var speed = 0;
var brakeAction = 0;
var gear = 1;
var minGear = 1;
var maxGear = 6;

// Gear max Speed
var gearMaxSpeed = [0, 30, 50, 80, 160, 230, 300]

var worker;

var nextChangeGear = 0;
var nextBreak = 0;
function randomData(vehicleId) {

    // gear position
    if(Date.now() > nextChangeGear) {
        var gearAction = Math.floor(Math.random() * 3) -1
        if (gearAction == 1 && gear < maxGear) {
            gear += 1;
            if(speed > gearMaxSpeed[gear]) {
                speed = gearMaxSpeed[gear];
            }
        }
        if (gearAction == -1 && gear > minGear) {
            gear -= 1;
            if(speed > gearMaxSpeed[gear]) {
                speed = gearMaxSpeed[gear];
            }
        }

        var randomNumber = Math.floor(Math.random() * 5);
        nextChangeGear = Date.now() + (randomNumber * 100);
    }

    // speed
    if (speed < maxSpeed) {
        var newSpeed = speed + (gear * 3);
        if (newSpeed < maxSpeed) {
            speed = newSpeed;
            if(speed > gearMaxSpeed[gear]) {
                speed = gearMaxSpeed[gear];
            }
        } else {
            speed = maxSpeed;
        }
    }

    // brake
    var newSpeed = speed - (brakeAction);
    if (newSpeed > 0) {
        speed = newSpeed;
    } else {
        speed = 0;
    }

    if(Date.now() > nextBreak) {
        brakeAction = Math.floor(Math.random() * 3);
        var randomNumber = Math.floor(Math.random() * 5);
        nextBreak = Date.now() + (randomNumber * 50);
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
