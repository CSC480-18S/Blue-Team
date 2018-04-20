/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandlers;

import Models.Player;
import Models.Tile;
import Models.User;
import Session.Session;
import com.google.gson.Gson;

/**
 *  Event Handler class that processes servlet API calls
 * @author ulocal
 */
public final class EventHandler {
    
    /*
        Final Class, does not need instantiating. DO NOT CALL.
    */
    private EventHandler() {}
    
    public static String joinHandler(String username, String macAddress, String team) {
        String response = Session.getSession().addPlayer(username, macAddress, team);
        return response;

    }
    
    public static String playHandler(int startX, int startY, boolean horizontal,
            String word, String macAddress) {

        //searching for user
        Session session = Session.getSession();
        String result = "User unauthorized";
        User[]users = session.getUsers();
        int currentTurn = session.getCurrentTurn();
        for(int i =0; i < users.length; i++){
            if(users[i] != null && users[i].getClass() == Player.class) {
                Player player = (Player) users[i];
                if (player.getMacAddress().equals(macAddress)) {
                    if(currentTurn == i) {
                        result = Session.getSession().playWord(startX, startY, horizontal, word, users[i]);
                    } else {
                        result = "Not your turn";
                    }
                    break;
                }
            }
        }
        return result;
    }

    public static String getHandHandler(String macAddress){
        Player player = getThisPlayerByMac(macAddress);
        if (player != null)
        {
            Tile[] hand = player.getHand();
            Gson gson = new Gson();
            String jsonHand = gson.toJson(hand);
            return jsonHand;
        }
        return "Error: user not found";
    }
    
    public static String leaveHandler( String mac) {
        String response = Session.getSession().removePlayer(mac);
        return " MAC: " + mac + "\nResult: " + response;
    }
    
    public static String forfeitHandler(String username, String mac) {
        return "forfeitHandler username: " + username + " MAC: " + mac;
    }
    
    public static String loginHandler(String username, String pass) {
        return "loginHandler username: " + username + " password: " + pass;
    }
    
    public static String exchangeHandler(String mac, String tiles) {
        tiles = tiles.toUpperCase();

        //searching for user
        Session session = Session.getSession();
        String result = "User unauthorized";
        User[]users = session.getUsers();
        int currentTurn = session.getCurrentTurn();
        for(int i =0; i < users.length; i++){
            if(users[i] != null && users[i].getClass() == Player.class) {
                Player player = (Player) users[i];
                if (player.getMacAddress().equals(mac)) {
                    if(currentTurn == i) {
                        result = Session.getSession().exchange(mac,tiles);
                    } else {
                        result = "Not your turn";
                    }
                    break;
                }
            }
        }
        return result;
    }

    public static String getBoardJSON(){
        String result = Session.getSession().getBoardJSON();
        return result;
    }


    public static String passHandler(String username) {
        return "passHandler username: " + username;
    }
    
    public static String statsHandler() {
        return "statsHandler";
    }
    
    public static String unknownHandler() {
        return "unknownHandler";
    }

    public static String scoreHandler(String mac) {
        Player p = getThisPlayerByMac(mac);
        if (p != null)
            return Integer.toString(p.getScore());
        else
            return "0";
    }

    // Take mac address, check if it is this players turn
    // If it is this players turn return 1, else 0
    public static String turnHandler(String mac) {
        String output = "";
        try
        {
            output = Session.getSession().isMyTurn(mac);
        }
        catch (Exception e) {}

        return output;
    }


    public static Player getThisPlayerByMac(String mac)
    {
        User[] users = Session.getSession().getUsers();
        for(User user : users){
            if(user != null && user.getClass() == Player.class){
                Player player = (Player) user;
                if (player.getMacAddress().equals(mac))
                {
                    return player;
                }
            }
        }
        return null;
    }

    public static String amIregisteredHandler(String mac){
        String response = Session.getSession().amIregistered(mac);
        return response;
    }
}
