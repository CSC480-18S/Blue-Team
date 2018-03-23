
//fix so images for icons arent grabbable
//maybe change tile images to different class
// $("img").click(function(){
//	$(this).addClass('borderClass');
//	$img = $(this).get();
//});
$("#img0").click(function() {
    $(this).addClass('borderClass');
    $img = $(this).get();
});
$("#img1").click(function() {
    $(this).addClass('borderClass');
    $img = $(this).get();
});
$("#img2").click(function() {
    $(this).addClass('borderClass');
    $img = $(this).get();
});
$("#img3").click(function() {
    $(this).addClass('borderClass');
    $img = $(this).get();
});
$("#img4").click(function() {
    $(this).addClass('borderClass');
    $img = $(this).get();
});
$("#img5").click(function() {
    $(this).addClass('borderClass');
    $img = $(this).get();
});
$("#img6").click(function() {
    $(this).addClass('borderClass');
    $img = $(this).get();
});
$(".drag").click(function() {
    $(this).addClass('borderClass');
    $(this).append($img);
});
$(".drop").click(function() {
    $(this).append($img);
    $($img).removeClass('borderClass');
});


//random number for shuffle
var numRand = Math.floor(Math.random() * 7);
//array for images in tile hand
var imgArray = new Array();
imgArray[0] = "#img0";
imgArray[1] = "#img1";
imgArray[2] = "#img2";
imgArray[3] = "#img3";
imgArray[4] = "#img4";
imgArray[5] = "#img5";
imgArray[6] = "#img6";
//array of divs
var divArray = new Array();
//shuffles image array
function shuffleMe(array) {
    let counter = array.length;
    // While there are elements in the array
    while (counter > 0) {
        // Pick a random index
        let index = Math.floor(Math.random() * counter);
        // Decrease counter by 1
        counter--;
        // And swap the last element with it
        let temp = array[counter];
        array[counter] = array[index];
        array[index] = temp;
    }
    return array;
}
//give random letters back from bag. we need to have an array full of letters
//with a weighted distribution that we can randomly draw from
//then we need to be able to get that image src
//and put it into the div
//must be able to make img id= a variable for referencing image in div
//or just place image in div in a different way
//make array and get which they choose and put in random tile into those spots
$(function exchange() {
    $("#exchange").click(function() {
        var exit = prompt("Do you want to exchange some tiles?", "Yes");
        switch (exit.toUpperCase()) {
            case "NO":
                alert("Continue playing");
                break;
            case "YES":
                alert("Select how many tiles you want to exchange");
                switchTiles();
                break;
        }
    });
});
//grab new tiles from bag
function switchTiles() {

    /*            //This is the Exchanging tiles request. Requires a string of tiles to be passed to the server.
                $.post("Servlet", {
                    request: "exchange",
                    tiles: _tiles, //sends the tiles to be exchanged, in a string named tiles
                }, function(data, status) { // execute ajax get request on url of "someservlet" and execute the following function with                                                ajax response text...
                    alert("Data: " + data + "\nStatus: " + status); // response text.
                });*/

}
//only problem is if you have words in play it recalls all
//the letters on the board
$(function shuffle() {
    $("#shuffle").click(function() {
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
//get array of current tile hand layout then
//without this above then it unshuffles if you had shuffled
$(function recall() {
    //var currentHand = new Array();
    $("#recall").click(function() {
        $('#div0').prepend($(imgArray[0]));
        $('#div1').prepend($(imgArray[1]));
        $('#div2').prepend($(imgArray[2]));
        $('#div3').prepend($(imgArray[3]));
        $('#div4').prepend($(imgArray[4]));
        $('#div5').prepend($(imgArray[5]));
        $('#div6').prepend($(imgArray[6]));
    });
});
//need to store tile placement and check if they are in valid spots
//change to buttons to click on for yes or no
$(function exit() {
    $("#exit").click(function() {
        var text;
        var exit = prompt("Are you sure you want to exit?", "Yes");
        switch (exit.toUpperCase()) {
            case "NO":
                alert("Continue playing");
                break;
            case "YES":
                alert("Bye!");
                //leave page
                //window.open('','_parent','');
                var win = window.open("about:blank", "_self")

                //This is the Leave Game request.
                $.post("Servlet", {
                    request: "leave",
                    username: uname
                }, function(data, status) {
                    alert("Data: " + data + "\nStatus: " + status); // response text.
                });
                win.close()
                break;
        }
    });
});
//need to store tile placement and check if they are in valid spots
//place tiles
//can be annoying to do this everytime
$(function place() {
});
//first tile placed needs xy coordinates (will always be lowest or highest coordinate)
//if x increased then vertical if y horizontal
//send h or v for horizontal or vertical
//string of word played
//if x-x = 0 then its horizontal
//change to buttons to click on for yes or no
$(function confirmed() {
    $("#confirmed").click(function() {
        var xyCoord = [];
        i = 0;
        var div;
        var wordPlayed = new Array();
        //send these 3 to engine
        var wordString;
        var firstCoord;
        var orientationWord;
        //this get me every image that is on board
        $(".drop").find("img").length
        //checks to see where each image is placed
        $(".drop").find("#img0");
        $(".drop").find("#img1");
        $(".drop").find("#img2");
        $(".drop").find("#img3");
        $(".drop").find("#img4");
        $(".drop").find("#img5");
        $(".drop").find("#img6");
        //gets the coordinate id for the div containing an image
        var div0 = $(".drop:has(#img0)").attr('id');
        var div1 = $(".drop:has(#img1)").attr('id');
        var div2 = $(".drop:has(#img2)").attr('id');
        var div3 = $(".drop:has(#img3)").attr('id');
        var div4 = $(".drop:has(#img4)").attr('id');
        var div5 = $(".drop:has(#img5)").attr('id');
        var div6 = $(".drop:has(#img6)").attr('id');
        //gets coordinate
        //loops through board and gets all the coordinates
        $(".drop:has(img)").each(function() {
            alert($(this).attr("id"));
            xyCoord[i++] = $(this).attr('id');
        });
        //gives me last
        var firstCoord = xyCoord[0];
        var secondCoord = xyCoord[1]
        var xy1 = firstCoord.split(",");
        var x1 = xy1[0];
        var y1 = xy1[1];
        var xy2 = secondCoord.split(",");
        var x2 = xy2[0];
        var y2 = xy2[1];
        var x = x1 - x2;
        if (x == 0) {
            orientationWord = ("h");
        } else {
            orientationWord = ("v");
        }
        //gets letters that are on board
        $(".drop").find("img").each(function() {
            // grab the src "attribute"
            //this gets id of image
            var id = $(this).attr("class");
            // alert(id);
            wordPlayed[i++] = $(this).attr('class');
            //send to engine
            //this is the word that was played
            //var wordPlayed = new Array();
        });
        var wordString = wordPlayed.join("");
        alert(wordString);



        //This is the Play request
        $.post("Servlet", { //needs variables for word, coordinates and direction (h or v)
            request: "play",
            word: wordString,
            coords: firstCoord + "," + secondCoord,
            direction: orientationWord
        }, function(responsetext) {
            alert(responsetext); // response text.
        });
    });
});