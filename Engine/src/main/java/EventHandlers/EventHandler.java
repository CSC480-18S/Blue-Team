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
    
    public static String joinHandler(String username, String macAddress) {
        String response = Session.getSession().addPlayer(username, macAddress);
        return "joinHandler username: " + username + " MAC: " + macAddress + " result: " + response;

    }
    
    public static String playHandler(int startX, int startY, boolean horizontal,
            String word, String macAddress) {

        //searching for user
        Session session = Session.getSession();
        String result = "unauthorized";
        User[]users = session.getUsers();
        for(int i =0; i < users.length; i++){
            if(users[i] != null && users[i].getClass() == Player.class) {
                Player player = (Player) users[i];
                if (player.getMacAddress().equals(macAddress)) {
                    result = Session.getSession().playWord(startX, startY, horizontal, word, users[i]);
                    break;
                }
            }
        }
        return "playHandler startX: " + startX + " startY: " + startY + 
                " horizontal: "+ horizontal + " word: " + word + "\nResult: " + result;
    }

    public static String getHandHandler(String macAddress){
        User[] users = Session.getSession().getUsers();
        for(User user : users){
            if(user != null && user.getClass() == Player.class){
                Player player = (Player) user;
                Tile[] hand = player.getHand();
                Gson gson = new Gson();
                String jsonHand = gson.toJson(hand);
                return jsonHand;
            }
        }
        return "Error: user not found";
    }
    
    public static String leaveHandler(String username, String mac) {
        return "leaveHandler username: " + username + " MAC: " + mac;
    }
    
    public static String forfeitHandler(String username, String mac) {
        return "forfeitHandler username: " + username + " MAC: " + mac;
    }
    
    public static String loginHandler(String username, String pass) {
        return "loginHandler username: " + username + " password: " + pass;
    }
    
    public static String exchangeHandler(String tiles) {
        return "exchangeHandler tiles: " + tiles;
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
    
}
