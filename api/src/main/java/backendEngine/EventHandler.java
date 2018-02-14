/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backendEngine;

/**
 *  Event Handler class that processes servlet API calls
 * @author ulocal
 */
public final class EventHandler {
    
    /*
        Final Class, does not need instantiating. DO NOT CALL.
    */
    private EventHandler() {}
    
    public static String joinHandler(String username, String mac) {
        return "joinHandler username: " + username + " MAC: " + mac;
    }
    
    public static String playHandler(int startX, int startY, int endX, int endY,
            String word) {
        return "playHandler startX: " + startX + " startY: " + startY + 
                " endX: " + endX + " endY: " + endY + " word: " + word;
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