// Update webpage board
updateBoard();

// Send post request when HTML is loaded incase user already has been added
$.post("Servlet", {request:"join", username:"", team:""}, function(responsetext)
{   // execute ajax get request on url of "someservlet" and execute the following function with ajax response text...
    // if success, show board, otherwise displayed message
    if (responsetext.toUpperCase() == "JOINED")
    {
        // Remove the Login Box and Display the board
        loginBoxOff();
    }
});

function join()
{
    var uname = document.getElementById('txtUserName').value;
    var team = "GREEN";

    if($('#teamToggle:checked').length > 0)
    {
        team = "GOLD";
    }

    $.post("Servlet", {request:"join", username:uname, team:team}, function(responsetext)
    {   // execute ajax get request on url of "someservlet" and execute the following function with ajax response text...
        // if success, show board, otherwise displayed message

        if (responsetext.toUpperCase() == "JOINED")
        {
            loginBoxOff();
        }
        else
        {
            // Display to user why they couldn't join.
            $("#joinResult").text(responsetext);

        }
    });
}

function updateBoard()
{
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

                        $('#' + coord).append('<img src=' + imgFile + ' width="55" height="55"/>');
                    }
                }
            }
        }

    });

}

// Join box and overlay visibility
function loginBoxOn() {
    document.getElementById("overlay").style.display = "block";
}

function loginBoxOff() {
    document.getElementById("overlay").style.display = "none";
}

