
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

// Join box and overlay visibility
function loginBoxOn() {
    document.getElementById("overlay").style.display = "block";
}

function loginBoxOff() {
    document.getElementById("overlay").style.display = "none";
}

