// Click borders
var currentImg = null;
function addBorder(el) {
    // hand tile slot has an image add board and mark as currentimg
    if (el.getElementsByTagName('img').length > 0)
    {
        // Change this element to the selected img
        currentImg = el.getElementsByTagName('img')[0];

        // If no border add one else remove it
        if (currentImg.style.border == "")
            currentImg.style.border = "5px solid red";
        else
            currentImg.style.border = "";
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

$(".drop").click(function () {
    if (currentImg == null) return;
    currentImg.style.border = "";
    $(this).append(currentImg);
    //$($img).removeClass('borderClass');
});

// Send post request when HTML is loaded incase user already has been added
$.post("Servlet", {request: "join", username: "", team: ""}, function (responsetext) {   // execute ajax get request on url of "someservlet" and execute the following function with ajax response text...
                                                                                         // if success, show board, otherwise displayed message
    if (responsetext.toUpperCase() == "JOINED") {
        // Remove the Login Box and Display the board
        loginBoxOff();
        // Switch joined flag and set hand
        joined = true;
        setHand()
    }
});

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

