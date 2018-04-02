// Update webpage board at page load
updateBoard();

// Use webworkers to update board

// Flag for updating board
var isUpdating = false;

/*
// Use web work to time board updates
if(window.Worker)
{
    var myWorker = new Worker('worker.js');
    // Signal worker to start timeout
    myWorker.postMessage("");

    myWorker.onmessage = function (ev)
    {
        if (!isUpdating)
        {
            updateBoard();
            // Signal worker to start timeout
            myWorker.postMessage("");
        }
    }
}
*/

function updateBoard()
{
    // Set a flag so we don't run into loop conflicts
    isUpdating = true;

    // Get board as JSON from Servlet
    // Note: this comes back as a javascript object so no need to parse it
    $.get("Servlet", {request:"getboard"}, function (data)
    {
        var boardArr = $.parseJSON(data);
        if (boardArr != null && boardArr != "")
        {
            // For each row in board 2d array
            for (row = 0; row < boardArr.board.length; row++)
            {
                // For each column in row array
                for (col = 0; col < boardArr.board[row].length; col++)
                {

                    // Check if there is a tile here
                    if (boardArr.board[row][col].contents != null)
                    {
                        var letter = boardArr.board[row][col].contents.letter;
                        // Add this tile to the webpage board
                        var coord = row + "_" + col;
                        var imgFile = "imgs/" + letter + ".png";

                        $('#' + coord + " img").remove();
                        $('#' + coord).append('<img src=' + imgFile + ' width="55" height="55"/>');
                    }
                }
            }
        }

    });

    // Reset flag so we can start updating again
    isUpdating = false;

    // TODO update the global array boardState

}