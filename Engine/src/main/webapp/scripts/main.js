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

$(function exchange() {
    //jquery remove?
    $("#exchange").click(function () {

        var exit = prompt("Do you want to exchange some tiles?", "Yes");
        switch (exit.toUpperCase()) {
            case "NO":
                alert("Continue playing");
                break;
            case "YES":
                alert("Select how many tiles you want to exchange");

                //var letters = new Array();

                // Unused but I will leave it incase we want this code
                //var randLet = Math.floor(Math.random() * 26);
                var exchangedTiles = [];
                var i = 0;

                $('img').click(function () {
                    $(this).addClass('borderClass');
                    $img = $(this).get();
                    alert($img);
                    exchangedTiles[i++] = $img;

                });

                $.post("Servlet", {
                    request: "exchange",
                    tiles: exchangedTiles, //sends the tiles to be exchanged, in a string named tiles
                }, function (data, status) { // execute ajax get request on url of "someservlet" and execute the following function with                                                ajax response text...
                    alert("Exchange - Data: " + data + "\nStatus: " + status); // response text.                var i = 0;
                    var hand = [];
                    var tiles = $.parseJSON(data);
                    for (var tile in tiles) {
                        hand[i++] = tile.letter;
                    }

                    $('#div0').empty().prepend($("imgs/" + hand[0] + ".png"));
                    $('#div1').empty().prepend($("imgs/" + hand[1] + ".png"));
                    $('#div2').empty().prepend($("imgs/" + hand[2] + ".png"));
                    $('#div3').empty().prepend($("imgs/" + hand[3] + ".png"));
                    $('#div4').empty().prepend($("imgs/" + hand[4] + ".png"));
                    $('#div5').empty().prepend($("imgs/" + hand[5] + ".png"));
                    $('#div6').empty().prepend($("imgs/" + hand[6] + ".png"));

                });

                $('img').click(function () {
                    $(this).addClass('borderClass');
                    $img = $(this).get();
                });
//                $("#img1").click(function () {
//                    $(this).addClass('borderClass');
//                    $img = $(this).get();
//                });
//                $("#img2").click(function () {
//                    $(this).addClass('borderClass');
//                    $img = $(this).get();
//                });
//                $("#img3").click(function () {
//                    $(this).addClass('borderClass');
//                    $img = $(this).get();
//                });
//                $("#img4").click(function () {
//                    $(this).addClass('borderClass');
//                    $img = $(this).get();
//                });
//                $("#img5").click(function () {
//                    $(this).addClass('borderClass');
//                    $img = $(this).get();
//                });
//                $("#img6").click(function () {
//                    $(this).addClass('borderClass');
//                    $img = $(this).get();
//                });
//                xyHand[i++] = $(this).attr('id');


                break;
        }


    });
});
//only problem is if you have words in play it recalls all
//the letters on the board
$(function shuffle() {
    $("#shuffle").click(function () {

        var imgArray = new Array();
        imgArray[0] = "#img0";
        imgArray[1] = "#img1";
        imgArray[2] = "#img2";
        imgArray[3] = "#img3";
        imgArray[4] = "#img4";
        imgArray[5] = "#img5";
        imgArray[6] = "#img6";
        var shuffled = new Array();
        shuffled = shuffleMe(imgArray);
        $('#div0').prepend($(shuffled[0]));
        $('#div1').prepend($(shuffled[1]));
        $('#div2').prepend($(shuffled[2]));
        $('#div3').prepend($(shuffled[3]));
        $('#div4').prepend($(shuffled[4]));
        $('#div5').prepend($(shuffled[5]));
        $('#div6').prepend($(shuffled[6]));
    });
});

$(function recall() {
    $("#recall").click(function () {
        var i = 0;
        currentHand = new Array();
        $(".drag").find("img").each(function () {
            // grab the src "attribute"
            //this gets id of image
            var id = $(this).attr("src");
            // alert(id);
            //array full of srcs
            currentHand[i++] = $(this).attr('src');

            //$(letters[i]).width(55);
            //$(letters[i]).height(55);

        });
        var imArray = new Array();
        imArray[0] = "#img0";
        imArray[1] = "#img1";
        imArray[2] = "#img2";
        imArray[3] = "#img3";
        imArray[4] = "#img4";
        imArray[5] = "#img5";
        imArray[6] = "#img6";

        $('#div0').prepend($(imArray[0]));
        $('#div1').prepend($(imArray[1]));
        $('#div2').prepend($(imArray[2]));
        $('#div3').prepend($(imArray[3]));
        $('#div4').prepend($(imArray[4]));
        $('#div5').prepend($(imArray[5]));
        $('#div6').prepend($(imArray[6]));
    });
});

//get array of current tile hand layout then
//without this above then it unshuffles if you had shuffled
$(function getCurrentHand() {
    //var current = new Array();

    var current = new Array();
    var i = 0;
// 		$(".drag:has(img)").each(function () {
// 			current[i++] = $(this).attr('id');

//         });
    $(".drag").find("img").each(function () {
        // grab the src "attribute"
        //this gets id of image
        var id = $(this).attr("id");
        // alert(id);
        //array full of ids
        current[i++] = $(this).attr('id');
    });

});


//need to store tile placement and check if they are in valid spots
//change to buttons to click on for yes or no
$(function exit() {
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

});

Array.prototype.diff = function (a) {
    return this.filter(function (i) {
        return a.indexOf(i) < 0;
    });
};
//change to buttons to click on for yes or no

//cant just play one word
$(function confirmed() {
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

        // $(".drag").not(":has(img)").each(function () {
        //     //need to make new image clickable
        //     //need to add a state for the hand
        //     //every time play is pressed hand upadtes
        //     //var letters = new Array();
        //
        //     //fills in random letters after you play hand
        //     //var shuffled = shuffleMe(letters);
        //     $.post("Servlet", {
        //         request: "gethand",
        //     }, function (data, status) {
        //         alert("Data: " + data + "\nStatus: " + status); // response text.
        //         var i = 0;
        //         var hand = [];
        //         var tiles = $.parseJSON(data);
        //         for (var tile in tiles) {
        //             hand[i++] = tile.letter;
        //         }
        //         $(this).prepend("imgs/" + hand[i] + ".png");
        //     });
        // });

        // $('img').click(function () {
        //     $(this).addClass('borderClass');
        //     $img = $(this).get();
        // });

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
});

// For when we want to have a dedicated message box
// and we don't have alerts every where
function showMessage(str)
{
    alert(str);
}