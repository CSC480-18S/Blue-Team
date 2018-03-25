// Send post request when HTML is loaded incase user already has been added
//$.post("Servlet", {request:"join", username:"", team:""}, function(responsetext)
//{   // execute ajax get request on url of "someservlet" and execute the following function with ajax response text...
//    // if success, show board, otherwise displayed message
//    if (responsetext.toUpperCase() == "JOINED")
//    {
//        loginBoxOff();
//    }
//});

function join()
{
    var uname = document.getElementById('txtUserName').value;
    var team = "GREEN";
    window.alert("green");
    if ($('input.loginbox.toggle-switch').is(':checked'))
    {
        window.alert("gold");
        team = "GOLD";
    }
    window.alert("if statement passed");

    $.post("Servlet", {request:"join", username:uname, team:team}, function(responsetext)
    {   // execute ajax get request on url of "someservlet" and execute the following function with ajax response text...
        // if success, show board, otherwise displayed message
        window.alert("new request");
        if (responsetext.toUpperCase() == "JOINED")
        {
            window.alert("joined");
            loginBoxOff();
        }
        else
        {
            window.alert("not joined");
            // Display to user why they couldn't join.
            $("#joinResult").text(responsetext);
        }

        window.alert("nothing");

    });
}

// Join box and overlay visibility
function loginBoxOn() {
    document.getElementById("overlay").style.display = "block";
}

function loginBoxOff() {
    document.getElementById("overlay").style.display = "none";
}

