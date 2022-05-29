// 1 ms timer worker
function timer() {
    var lastTime = 0;
    do {
        // javascript date based in ms timestamp
        var now = Date.now()
        if(now > lastTime) {
            postMessage("");
            lastTime = now;
        }
    }
    while (true);
}

timer();