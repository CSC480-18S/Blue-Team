// ****
// Global var holding board state
var boardState = [];

// Update webpage board at page load
updateBoard();


// Flag for updating board
var isUpdating = false;


// Use web worker to look for board updates
var isPlaying = false;
if (window.Worker)
{
    // yea...
    var myWorker = new Worker(URL.createObjectURL(new Blob(["(" + worker_function.toString() + ")()"], {type: 'text/javascript'})));
    // Signal worker to start
    myWorker.postMessage("");

    // TODO - Can use ev to handle other events (ie score updates, turn checks)
    myWorker.onmessage = function (ev)
    {
        // if we're playing a word skip all this
        if (!isPlaying)
        {
            try
            {
                // check for updates
                $.get("Servlet", {request: "getupdates"}, function (data)
                {
                    if (data != "")
                    {
                        // check if score changed
                        var update = data.split("_");
                        if (update[0] != getScore())
                        {
                            updateScore(update[0]);
                        }
                        // check if turn is different
                        if (update[1] != getTurn())
                        {
                            updateTurn(update[1]);
                        }
                        // update board
                        updateBoard();
                    }
                }); // end get request
            }
            catch
                (e) {
            }
        }
        // signal for more updates
        myWorker.postMessage("");
    }// myworker.onmessage
}


function updateBoard() {
    // Set a flag so we don't run into loop conflicts
    isUpdating = true;

    // Get board as JSON from Servlet
    // Note: this comes back as a javascript object so no need to parse it
    try {
        $.get("Servlet", {request: "getboard"}, function (data) {
            var boardArr = $.parseJSON(data);
            if (boardArr != null && boardArr != "") {
                // For each row in board 2d array
                for (row = 0; row < boardArr.board.length; row++) {
                    // For each column in row array
                    for (col = 0; col < boardArr.board[row].length; col++) {

                        // Check if there is a tile here
                        if (boardArr.board[row][col].contents != null) {
                            var letter = boardArr.board[row][col].contents.letter;
                            // Add this tile to the webpage board
                            var coord = row + "_" + col;
                            var imgFile = "imgs/" + letter + ".png";

                            $('#' + coord + " img").remove();
                            $('#' + coord).append('<img src=' + imgFile + ' width="55" height="55"/>');
                            // add to global boardstate
                            if (boardState != null && !boardState.includes(coord)) {
                                boardState.push(coord);
                            }

                        }
                    }
                }
            }

        });
    }
    catch (err) {

    }


    // Reset flag so we can start updating again
    isUpdating = false;

}

function updateScore(score) {
    $('.score-box').text(score);
}

function getScore() {
    var score = $('.score-box').text();
    return score;
}

function updateTurn(turn) {
    $('.turn-box').text(turn);
}

function getTurn()
{
    var turn = $('.turn-box').text();
    return turn;
}
