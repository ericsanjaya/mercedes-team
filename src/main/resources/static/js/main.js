init();

function init () {
	var vehicleId = localStorage.getItem("vehicleId");
	if(vehicleId != "") {
		document.getElementById("vehicleId").value = vehicleId;
	}
}