// Click borders
var currentImg = null;
var blankReplacement = null;

// Figure out what screen to show at load up
// Send post request when HTML is loaded incase user already has been added
$.post("Servlet", {request: "join", username: "", team: ""}, function (responsetext) {   // execute ajax get request on url of "someservlet" and execute the following function with ajax response text...
    // if success, show board, otherwise displayed message
    if (responsetext.toUpperCase() == "JOINED") {
        // Remove Wait box
        waitBoxOff();
        // Switch joined flag and set hand
        joined = true;
        setHand();
    }
    else
    {
        // Check if user is already registered
        $.post("Servlet", {request: "amiregistered"}, function (response)
        {
            var strArray = $.parseJSON(response);
           if (strArray != null && strArray != "")
           {
               if (strArray[0].toUpperCase()== "FALSE")
               {
                   // Remove wait box
                   waitBoxOff();
                   // Show Login Box
                   loginBoxOn();
               }
               else
               {
                   // Remove wait box
                   waitBoxOff();
                   // Show Welcome screen with username and team
                   welcomeBoxOn(strArray[0], strArray[1]);
               }
           }
        });
    }
});

function isBlankTile(el) {
    var currentImgSrc = el.getElementsByTagName('img')[0].src;
    var s = currentImgSrc.search(/.png/i);
    var thisLetter = currentImgSrc.slice(s - 1, -4);

    if (thisLetter === "-")
        return true;

    return false;

}

function submitBlank()
{
    // if a tile was selected
    if (blankReplacement != null
          && blankReplacement.style.border !== "")
    {
        // place in hand with red border
        for (var i = 0; i < 7; i++)
        {
            if (document.getElementById('div'+i).getElementsByTagName('img')[0]
                  === currentImg)
            {
                $('#div'+i).empty().append(blankReplacement);
                currentImg = blankReplacement;
                break;
            }
        }
    }

    // close popup
    specialTileOff();
}

// function when we click a hand tile
function addBorder(el) {

    // hand tile slot has an image add board and mark as currentimg
    if (el.getElementsByTagName('img').length > 0)
    {
        var exch = document.getElementById('exchange');
        // Clear border on current img if there is one
        // But if exchange is selected allow for multiple tiles to be selected
        if (currentImg != null && currentImg != el.getElementsByTagName('img')[0]
           && !(exch.style.border === "5px solid red"))
            currentImg.style.border = "";
        // Change this element to the selected img
        currentImg = el.getElementsByTagName('img')[0];

        // If no border add one else remove it
        if (currentImg.style.border == "")
            currentImg.style.border = "5px solid red";
        else
            currentImg.style.border = "";

        // Check if blank tile and not in exchange mode
        if (isBlankTile(el) && !(exch.style.border === "5px solid red"))
        {
            //popup letter selector
            currentImg = el.getElementsByTagName('img')[0];
            specialTileOn();
            return; // get out and then we'll handle the tile if they select one.
        }


    }
    // else user may be trying to put the tile back
    else
    {
        if (currentImg != null)
        {
            el.append(currentImg);
        }
    }

}

function specialTileBorder(el)
{
    if (el != null)
    {
        // uncheck the other
        if (blankReplacement != null && blankReplacement != el)
            blankReplacement.style.border = "";

        // set new selection
        blankReplacement = el;

        // If no border add one else remove it
        if (blankReplacement.style.border == "")
            blankReplacement.style.border = "5px solid red";
        else
            blankReplacement.style.border = "";
    }
}

$(".drop").click(function () {
    // check null
    if (currentImg == null) return;
    // check if there is already a tile there
    if ($(this).children('img').length < 1)
    {
        // no image here so place the current
        currentImg.style.border = "";
        $(this).append(currentImg);
    }
    // theres an image here
    else if ($(this).children('img').length > 0)
    {
        // set the clicked image as the current if it hasn't been played
        //loops through board and gets all the coordinates
        var thisTile = $(this);
        var i = 0; //****
        var xyCoord = [];
        $(".drop:has(img)").each(function () {
            xyCoord[i++] = $(this).attr('id');
        });

        //gets difference between the state of board now and the previous state
        var newPlay = xyCoord.diff(boardState);
        // check if this tile is in the newplay
        newPlay.forEach(function (coord) {
            if (coord == thisTile.attr("id"))
            {
                // give border and set as current img
                currentImg.style.border = "";
                currentImg = document.getElementById(coord).getElementsByTagName('img')[0];
                currentImg.style.border = "5px solid red";
            }
        })
    }
});

function joinFromWelcome()
{
    var uname = $("#wUsername").text();
    var team = $("#wTeam").text();

    if (uname !== null && team !== null)
    {
        $.post("Servlet", {request: "join", username: uname, team: team}, function (responsetext) {   // execute ajax get request on url of "someservlet" and execute the following function with ajax response text...
            // if success, show board, otherwise displayed message

            if (responsetext.toUpperCase() == "JOINED") {
                welcomeBoxOff();
                waitBoxOff();

                // Switch joined flag and set hand
                joined = true;
                setHand()
            }
            else {
                // Display to user why they couldn't join.
                $("#joinResult").text(responsetext);
            }
        });
    }
    else
    {
        // Not sure how this would happen refresh the page I guess ?
        location.reload();
    }

}

function join() {
    var uname = document.getElementById('txtUserName').value;
    var team = "GREEN";

    if ($('#teamToggle:checked').length > 0) {
        team = "GOLD";
    }

    $.post("Servlet", {request: "join", username: uname, team: team}, function (responsetext) {   // execute ajax get request on url of "someservlet" and execute the following function with ajax response text...
        // if success, show board, otherwise displayed message

        if (responsetext.toUpperCase() == "JOINED") {
            loginBoxOff();
            waitBoxOff();

            // Switch joined flag and set hand
            joined = true;
            setHand()
        }
        else {
            // Display to user why they couldn't join.
            $("#joinResult").text(responsetext);
        }
    });
}


// Called after joining
function setHand() {
    $(document).ready(function () {
        // if the user has not joined yet, cannot set hand
        // this will cause a null exception in the servlet if so
        if (!joined) return;

        $.post("Servlet", {request: "gethand",}, function (data, status) {
            var hand = [];
            try
            {
                var tiles = $.parseJSON(data);

                var imgFile = "";
                // just replace all tiles in the hand instead of worrying about which ones are empty
                for (var i = 0; i < tiles.length; i++) {
                    imgFile = "imgs/" + tiles[i].letter + ".png";
                    $("#div" + i).empty().prepend('<img img="img"' + i + ' src=' + imgFile + ' width="55" height="55"/>');
                }

            } catch (e) {}
        });
    });
}

// Join box and overlay visibility
function loginBoxOn() {
    document.getElementById("overlay").style.display = "block";
}

function loginBoxOff() {
    document.getElementById("overlay").style.display = "none";
}

function specialTileOn() {
    try {
        // Check each img in the pop up, if there's a blank re-add the img
        $('.special').each(function () {
            if ($(this).find('img').length < 1)
            {
                // get id so we know what letter
                var id = $(this).attr('id');
                var letter = id.slice(id.length-1, id.length);
                // append the appropriate image to the div
                var imgFile = "imgs/blankletters/_" + letter + ".png";;
                $(this).append('<img src=' + imgFile + ' onclick="specialTileBorder(this)" width="55" height="55"/>');
            }
        });
        document.getElementById("specialOverlay").style.display = "block";
    }
    catch (e) {}
    finally {
        // show popup
        document.getElementById("specialOverlay").style.display = "block";
    }
}

function specialTileOff() {
    document.getElementById("specialOverlay").style.display = "none";
}

function cancelSpecialTile()
{
    document.getElementById("specialOverlay").style.display = "none";
    currentImg.style.border = "";
    currentImg = null;
}

function welcomeBoxOn(user, team)
{
    $("#wUsername").text(user);
    $("#wTeam").text(team);
    document.getElementById("welcomeOverlay").style.display = "block";
}

function welcomeBoxOff()
{
    document.getElementById("welcomeOverlay").style.display = "none";
}

function waitBoxOn()
{
    document.getElementById("waitOverlay").style.display = "block";
}

function waitBoxOff()
{
    document.getElementById("waitOverlay").style.display = "none";
}

