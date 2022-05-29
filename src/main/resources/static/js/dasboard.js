var websocketClient;

function sendMessage() {
	websocketClient.send(JSON.stringify(randomData()));
}

function connect() {
    if (websocketClient != null && websocketClient.readyState == 1) {
        return;
    }

	var vehicleId = document.getElementById("vehicleId").value;
	localStorage.setItem("vehicleId", vehicleId);

	var url = "ws://localhost:7076/ws/dashboard/" + vehicleId;

	websocketClient = new WebSocket(url, "mercedes-net");
	console.log(websocketClient.readyState);
	if (websocketClient.readyState == "CONNECTING") {
		console.log("Connecting");
	}

	websocketClient.onopen = function (event) {
		//websocketClient.send("88888");
	};

	websocketClient.onmessage = function (event) {
	var res = JSON.parse(event.data);

	var speed = res["speed"] + " Km/h";

	var brakeCondition;
	switch(res["brakeCondition"]) {
	    case 0:
	        brakeCondition = "Cold";
	        break;
	    case 1:
	        brakeCondition = "Warm";
	        break;
	    case 2:
            brakeCondition = "Hot";
            break;
	}

    var gearPosition;
	switch(res["gear"]) {
	    case 6 :
	        gearPosition = "R";
	        break;
	    default:
	        gearPosition = res["gear"];
	        break;
	}

	document.getElementById("speed").innerHTML  = speed;
	document.getElementById("break").innerHTML  = brakeCondition;
	document.getElementById("gear").innerHTML  = gearPosition;
	}

	websocketClient.onclose = function (event) {
		console.log("colsed");
	}
}

function wsClose() {
	if (websocketClient.readyState == 1) {
	    websocketClient.close();
    }
}

export {connect, sendMessage, wsClose};