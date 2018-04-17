function worker_function() {
    onmessage = function(e) {
        // TODO - We can use this function to ping servlet for updates
        // and pass to boardUpdater javascript
        setTimeout(function () {
            // send message back after 5 seconds to signal updateBoard()
            postMessage("");
        }, 3000)
    }
}

// block when loaded from html
if(window!=self)
    worker_function();