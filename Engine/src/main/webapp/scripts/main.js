//var letters = new Array();
var currentHand = new Array();
var joined = false;

//random number for shuffle
//var numRand = Math.floor(Math.random() * 7);


//method to shuffle array
function shuffleMe(array) {
    var counter = array.length;

    // While there are elements in the array
    while (counter > 0) {
        // Pick a random index
        var index = Math.floor(Math.random() * counter);

        // Decrease counter by 1
        counter--;

        // And swap the last element with it
        var temp = array[counter];
        array[counter] = array[index];
        array[index] = temp;
    }

    return array;
}

function getSelectedTilesAsString() {
    //var current = new Array();
    var current = []; var file;
    var i = 0; var s;
    $(".drag").find("img").each(function (e) {
        // only selecteds
        var color = $(this).css("border-color");
        if (color != null && color == "rgb(255, 0, 0)") // that is what red comes back as
        {
            file = $(this).attr('src');
            // Get the start point of the file
            s = file.search(/.png/i);
            // now we have the letter of just the latest word
            current[i++] = file.slice(s - 1, -4);
            i++;
        }
    });
    return current.join("");
}
function exchange()
{
    var output = prompt("Exchange selected tiles?", "Yes");
    switch (output.toUpperCase()) {
        case "YES":
            // Unused but I will leave it incase we want this code
            //var randLet = Math.floor(Math.random() * 26);
            var exchangedTiles = getSelectedTilesAsString();
            var i = 0;

            if (exchangedTiles.length > 0)
            {
                $.post("Servlet", {
                    request: "exchange",
                    tiles: exchangedTiles, //tiles to be exchanged
                }, function () {
                    // Update the hand after server is done
                    setHand();
                });

            }
            break;
    }


}

function getCurrentHand() {
    var hand = [];
    hand[0] = document.getElementById('div0').getElementsByTagName('img')[0];
    hand[1] = document.getElementById('div1').getElementsByTagName('img')[0];
    hand[2] = document.getElementById('div2').getElementsByTagName('img')[0];
    hand[3] = document.getElementById('div3').getElementsByTagName('img')[0];
    hand[4] = document.getElementById('div4').getElementsByTagName('img')[0];
    hand[5] = document.getElementById('div5').getElementsByTagName('img')[0];
    hand[6] = document.getElementById('div6').getElementsByTagName('img')[0];
    return hand;
    // //var current = new Array();
    // var current = new Array();
    // var i = 0;
    // $(".drag").find("img").each(function () {
    //     current[i] = $(this).attr('src');
    //     i++;
    // });
    // return current;
}

function getCurrentHandAsString() {
    //var current = new Array();
    var current = []; var file;
    var i = 0; var s;
    $(".drag").find("img").each(function () {
        file = $(this).attr('src');
        // Get the start point of the file
        s = file.search(/.png/i);
        // now we have the letter of just the latest word
        current[i++] = file.slice(s - 1, -4);
        i++;
    });
    return current.join("");
}

//only problem is if you have words in play it recalls all
//the letters on the board
function shuffle()
{
    var hand = getCurrentHand();

    var shuffled = shuffleMe(hand);

    $('#div0').empty().prepend($(shuffled[0]));
    $('#div1').empty().prepend($(shuffled[1]));
    $('#div2').empty().prepend($(shuffled[2]));
    $('#div3').empty().prepend($(shuffled[3]));
    $('#div4').empty().prepend($(shuffled[4]));
    $('#div5').empty().prepend($(shuffled[5]));
    $('#div6').empty().prepend($(shuffled[6]));
}

function recall() {
    // Diff board
    var xyCoord = [];
    var i = 0;
    $(".drop:has(img)").each(function () {
        xyCoord[i++] = $(this).attr('id');
    });

    var newPlay = xyCoord.diff(boardState);
    // Remove tiles
    for (var i = 0; i < newPlay.length; i++)
    {
        $('#' + newPlay[i] + " img").remove();
    }
    // Update hand
    setHand();
}


//need to store tile placement and check if they are in valid spots
//change to buttons to click on for yes or no
function exit() {
    $("#exit").click(function () {
        var text;
        var exit = prompt("Are you sure you want to exit?", "Yes");
        switch (exit.toUpperCase()) {
            case "NO":
                alert("Continue playing");
                break;
            case "YES":
                alert("Bye!");

                $.post("Servlet", {
                    request: "leave",
                    username: uname
                }, function (data, status) {
                    alert("Leave - Data: " + data + "\nStatus: " + status); // response text.
                });
                //leave page
                //window.open('','_parent','');
                var win = window.open("about:blank", "_self")

                win.close()
                break;
        }
    });

}

Array.prototype.diff = function (a) {
    return this.filter(function (i) {
        return a.indexOf(i) < 0;
    });
};
//change to buttons to click on for yes or no

//cant just play one word
function confirmed() {
    $("#confirmed").click(function () {

        // Instance variables
        var xyCoord = [];
        var xyHand = [];
        var wordPlayed = new Array();
        //send these to engine (not secondCoord though)
        var wordString;
        var firstCoord;
        var secondCoord;
        var orientationWord = true; //default to true incase only one letter is played

        //gets coordinate
        //loops through board and gets all the coordinates
        var i = 0; //****
        $(".drop:has(img)").each(function () {
            xyCoord[i++] = $(this).attr('id');
        });

        //gets difference between the state of board now and the previous state
        var newPlay = xyCoord.diff(boardState);

        firstCoord = newPlay[0];
        secondCoord = newPlay[1];
        // Get coordinate of first letter
        if (firstCoord == null) return; // back out if no word available
        var xy1 = firstCoord.split("_");
        var y1 = xy1[1];

        // secondCoord will be null if only one letter was played
        if (secondCoord != null) {
            // If more than one letter played,
            // get the second coord to determine orientation
            var xy2 = secondCoord.split("_");
            // alert(x2);
            var y2 = xy2[1];
            // alert(y2);
            var y = y1 - y2;
            if (y == 0) {
                orientationWord = ("h");
            } else {
                orientationWord = ("v");
            }
        }

        //goes through only letters played this turn
        for (var n = 0; n < newPlay.length; n++) {
            // get the file name (src) by the id (coordinate)
            //may need to change this or change split
            var file = document.getElementById(newPlay[n]).getElementsByTagName('img')[0].src;
            // Get the start point of the file
            var s = file.search(/.png/i);
            // now we have the letter of just the latest word
            wordPlayed[i++] = file.slice(s - 1, -4);

        }

        wordString = wordPlayed.join("");
        alert("Word Played: " + wordString);

        //This is the Play request
        $.post("Servlet", { //needs variables for word, coordinates and direction (h or v)
            request: "play",
            word: wordString,
            coords: firstCoord,
            direction: orientationWord
        }, function (responsetext) {
            var result = responsetext.toUpperCase();
            if (result == "VALID")
            {
                showMessage("Success");
            }
            else if (result == "BONUS")
            {
                showMessage("Bonus word !");
            }
            else if (result == "PROFANE")
            {
                // If play was invalid remove played tiles from board
                for (var i = 0; i < newPlay.length; i++)
                {
                    $('#' + newPlay[i] + " img").remove();
                }
                showMessage("Profanity !");
            }
            else
            {
                // If play was invalid remove played tiles from board
                for (var i = 0; i < newPlay.length; i++)
                {
                    $('#' + newPlay[i] + " img").remove();
                }
                // Give user message
                showMessage(responsetext);
            }
            // Update hand after play
            setHand();
        });
        // Reset current tile select
        currentImg = null;
        // Deep copy new board state global array
        boardState = [...xyCoord];
    });
}

// For when we want to have a dedicated message box
// and we don't have alerts every where
function showMessage(str)
{
    alert(str);
}