package Models;

import Session.Session;

/**
 * @author Bohdan
 * This class represents human player
 */
public class Player extends User {
    private String macAddress;
    private String team;


    public Player(String username, String macAddress, String team, 
            Session session){
        super(username, session);
        this.macAddress = macAddress;
        this.team = team;
    }

    @Override
    String getType() {
        return "human";
    }


    public String getMacAddress(){
        return macAddress;
    }

    public String getTeam(){return team;}
}
