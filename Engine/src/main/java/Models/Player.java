package Models;

/**
 * @author Bohdan
 * This class represents human player
 */
public class Player extends User {
    private String macAddress;


    public Player(String username, String macAddress){
        setUsername(username);
        this.macAddress = macAddress;
    }
    @Override
    int playWord(String word, int startX, int startY, int endX, int endY, Board board) {
        return 0;
    }

    @Override
    String getType() {
        return "human";
    }


    public String getMacAddress(){
        return macAddress;
    }

}
