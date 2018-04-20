package Session;

import Models.*;
import Views.*;
import Components.*;
import Components.Log;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import static Models.GameConstants.BOARD_WIDTH;

/**
 * @author Bohdan Yevdokymov
 */
public class Session {

    private static Session session;
    private Log log;
    private Board board;
    private BoardGUI gui;
    private Validator validator;
    private User[] users;
    private ArrayList<Move> playedMoves;
    private QueryClass dbQueries;
    private int currentTurn;
    private int greenScore;
    private int goldScore;
    private Timer timer;
    private int turnTimeSec;
    private  int aiWaitSec;
    private int aiWaitNoPlayers;
    private int skippedTimes;

    private Session() {
        //Session.LogInfo("Initializing objects");
        board = new Board();
        gui = new BoardGUI();
        validator = new Validator();
        users = new User[4];
        playedMoves = new ArrayList();
        dbQueries = new QueryClass();
        timer = new Timer();
        currentTurn = 0;
        turnTimeSec = 60;
        aiWaitSec = 3;
        aiWaitNoPlayers = 60;
        skippedTimes = 0;
        try{
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream file = classloader.getResourceAsStream("settings.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                line = line.replace(" ", "");
                String [] param = line.split("=");
                if(param[0].equals("aiWaitNoPlayers")){
                    aiWaitNoPlayers = Integer.parseInt(param[1]);
                } else if(param[0].equals("turnTimeSec")){
                    turnTimeSec = Integer.parseInt(param[1]);
                } else if(param[0].equals("aiWaitSec")){
                    aiWaitSec = Integer.parseInt(param[1]);
                }
            }
        } catch (Exception e ){
            Log.getLogger().logException(e);
            e.printStackTrace();
        }

        //this runs when program is closing
        Runtime.getRuntime().addShutdownHook(new Thread() {
             public void run() {
                 timer.cancel();
             }
         }
        );


    }

    public static Session getSession() {
        if (session == null) {
            session = new Session();
            //adding SkyCats initially
            for(int i =0; i < session.users.length; i++){
                session.users[i] = new SkyCat(session);
            }
            session.gui.updateUsers(session.users);
            session.gui.setTurn(session.currentTurn);
            //play first move to start the game
                SkyCat skyCat1 = (SkyCat) session.users[0];
               session.setAiTimer();
            session.gui.updateHand(session.users);
        }
        return session;
    }

    public Log getLogger() {
        // Just incase session hasn't been instantiated yet
        if (session == null) {
            getSession();
        }

        if (session.log == null) {
            // Initialize logger
            try {
                session.log = new Log();
               // session.log.logger.setLevel(Level.INFO);
            } catch (Exception e) {
                System.out.println("Error creating logger: \n" + e);
                e.printStackTrace();
                Log.getLogger().logException(e);

            }
        }

        return session.log;
    }


    /// Setup Player object and join the game
    public String addPlayer(String username, String macAddress, String team) {
        Player newPlayer = null;

        //validating the team name
        if (team.toUpperCase().compareTo("GREEN") != 0 && team.toUpperCase().compareTo("GOLD") != 0
                && team != "" && team != null) {
            return "Invalid team name.";
        }

        //check if username already exists in game first
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {
                if (users[i].getClass() == Player.class) {
                    Player player = (Player) users[i];
                    if (player.getMacAddress().equals(macAddress)) {
                        return "JOINED";
                    }
                }
            }
        }

        // If user hasn't joined yet make sure they have entered a username
        // This prevents the user from auto joining at page load
        if (username == "")
            return "";

        /*checking for mac address in the user database to see if user already has a user profile. If a profile exists then userInfo will have the
        username stored in index 0 and team stored in index 1*/
        String[] userInfo = dbQueries.findUser(macAddress);

        // Use entered username
        if (userInfo != null) {
            newPlayer = new Player(username, macAddress, userInfo[1]);

        } //creating a new user profile in the database if the mac address is not already registered
        else {

            if (!dbQueries.userAlreadyExists(username)) {
                if (username.compareTo("") != 0) {
                    dbQueries.addNewUser(username, macAddress, team);
                } else {
                    return "empty user name";
                }
            } else {
                return "The username chosen is already in use.";
            }

            newPlayer = new Player(username, macAddress, team);
        }

        //check if any users are not initialized yet
        for (int i = 0; i < users.length; i++) {
            if (users[i].getClass() == SkyCat.class && ((i == 0 || i == 2) && team.toUpperCase().equals("GREEN"))
                    || ((i == 1 || i == 3) && team.toUpperCase().equals("GOLD"))) {
                //cancel ai timer
                if(i == currentTurn){
                    timer.cancel();
                }
                //return generated tiles back in bag
                TileGenerator.getInstance().putInBag(newPlayer.getHand());
                Tile[] hand = users[i].getHand();
                newPlayer.setHand(hand);
                users[i] = newPlayer;
                setPlayerTimer();
                gui.updateUsers(users);
                gui.printGameLog(newPlayer.getUsername() + " has joined " + team + " Team");
                return "JOINED";
            }
        }

        return "Could not join game.";
    }

    // Check if this is the first move
    public boolean firstMove() {
        return playedMoves.isEmpty();
    }

    private void displayMoveStats(Move move, int points) {
        if (move != null) {
            System.out.println(move.getUser().getUsername());
            System.out.println("x: " + move.getStartX());
            System.out.println("y: " + move.getStartY());
            System.out.println("horizontal: " + move.isHorizontal());
            System.out.println("word: " + move.getWordString());
            if (!move.getOffshootMoves().isEmpty()) {
                System.out.println("Move creates auxiliary words of : ");
                for (Move aMove : move.getOffshootMoves()) {
                    System.out.print(aMove.getWordString() + " ");
                }
                System.out.println();
            }
            System.out.println("points: " + points);
        }
    }

    private boolean aiPlayWord(SkyCat skyCat) {
        Move move = skyCat.chooseMove();
        if (move != null) {
            session.playWord(move.getStartX(), move.getStartY(), move.isHorizontal(), move.getWordString(), skyCat);
            return true;
        } else {
            return false;
        }
    }

    // Validate word and place on board
    public String playWord(int startX, int startY, boolean horizontal, String word, User user) {
        StringBuilder wordForScoring = new StringBuilder("");
        StringBuilder wordForValidating = new StringBuilder("");
        boolean wildCard = false;
        for(char c : word.toCharArray()){
            if(c == '_') {
                wordForScoring.append("-");
                wildCard = true;
            }
            else if(wildCard){
                wildCard = false;
                wordForValidating.append(c);
            }
            else{
                wordForValidating.append(c);
                wordForScoring.append(c);
            }
        }
        word = wordForValidating.toString();
        String initialLetters = word;
        TileGenerator tg = TileGenerator.getInstance();
        // Check if word length is less than 11,
        // it will cause errors if too big.
        // This should never happen but just in case..
        if (word.length() > 11) {
            return "Please play a shorter word?...";
        }

        if (word.length() == 1) {
            if ((startX + 1 < BOARD_WIDTH && board.getBoard()[startX + 1][startY].getTile() != null) || (startX > 0  && board.getBoard()[startX - 1][startY].getTile() != null)) {
                horizontal = true;
            }
        }

        // If first move check
        if (firstMove()) {
            int boardCenter = BOARD_WIDTH / 2;
            if ((horizontal ? startX : startY) > boardCenter
                    || ((horizontal ? startX : startY) + word.length() - 1) < boardCenter
                    || (horizontal ? startY : startX) != boardCenter) {
                return "Please start in the center of the board.";
            }

        }

        //this part appends middle part of the word, ai gives full word, so only for players
        if(user.getClass() == Player.class) {
            ArrayList<Character> chars = new ArrayList<>();
            char[] arr = word.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                chars.add(arr[i]);
            }

            word = "";

            int count = 0;
            StringBuilder wordBuilder = new StringBuilder(word);
            //walk through the board and if tile is already placed - append it
            while (!chars.isEmpty()) {
                if (horizontal) {
                    if ((startX + count) < BOARD_WIDTH && board.getBoard()[startX + count][startY].getTile() != null) {
                        wordBuilder.append(board.getBoard()[startX + count][startY].getTile().getLetter());
                    } else {
                        wordBuilder.append(chars.get(0));
                        chars.remove(0);
                    }
                } else {
                    if ((startY + count) < BOARD_WIDTH && board.getBoard()[startX][startY + count].getTile() != null) {
                        wordBuilder.append(board.getBoard()[startX][startY + count].getTile().getLetter());
                    } else {
                        wordBuilder.append(chars.get(0));
                        chars.remove(0);
                    }
                }

                count++;
            }
            word = wordBuilder.toString();
        }
        Tile[] wordTiles = new Tile[word.length()];

        for (int i = 0; i < word.length(); i++) {

            wordTiles[i] = tg.getTile(word.charAt(i));
        }

        Object[] result = validator.isValidPlay(new Move(startX, startY, horizontal, wordTiles, user));

        if ((int) result[0] == 1) {
            board.placeWord(startX, startY, horizontal, word);
            String scoringWord = wordForScoring.toString();
            Tile[] tilesForScoring = new Tile[scoringWord.length()];
            for(int i = 0; i < scoringWord.length(); i++){
                tilesForScoring[i] = tg.getTile(scoringWord.charAt(i));
            }
            Move scoringMove = (Move) result[1];
            scoringMove.setWord(tilesForScoring);
            int score = calculateMovePoints(scoringMove);
            displayMoveStats((Move) result[1], score);
            user.setScore(user.getScore() + score);
            if (user instanceof Player) {
                Player temp = (Player) user;
                updateTeamScore(score, temp.getTeam());
            }
            replaceTiles(user, initialLetters);
            gui.updateBoard(board.getBoard());
            playedMoves.add((Move) result[1]);
            skippedTimes = 0;
            gui.printGameLog(user.getUsername() + " played " + word + " (" + score + " points)");
            nextTurn();
            return "VALID";
        } else if ((int) result[0] == 2) {
            board.placeWord(startX, startY, horizontal, word);
            String scoringWord = wordForScoring.toString();
            Tile[] tilesForScoring = new Tile[scoringWord.length()];
            for(int i = 0; i < scoringWord.length(); i++){
                tilesForScoring[i] = tg.getTile(scoringWord.charAt(i));
            }
            Move scoringMove = (Move) result[1];
            scoringMove.setWord(tilesForScoring);
            int score = calculateMovePoints(scoringMove) * 2;
            displayMoveStats((Move) result[1], score);
            user.setScore(user.getScore() + score);
            if (user instanceof Player) {
                Player temp = (Player) user;
                updateTeamScore(score, temp.getTeam());
            }
            replaceTiles(user, initialLetters);
            gui.updateBoard(board.getBoard());
            playedMoves.add((Move) result[1]);
            skippedTimes = 0;
            gui.printGameLog(user.getUsername() + " played BONUS " + word + " (" + score + " points)");
            nextTurn();
            return "bonus";
        } else if ((int) result[0] == -1) {
            gui.printGameLog(user.getUsername() + " is uncultured");
            return "profane";
        }
        return "Invalid";

    }

    public Space[][] getBoardAsSpaces() {
        return board.getBoard();
    }

    public User[] getUsers() {
        return users;
    }

    public String removePlayer(String mac) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].getClass() == Player.class) {
                Player player = (Player) users[i];
                if (player.getMacAddress().equals(mac)) {
                    //cancel timer if it's player's turn
                    if(currentTurn == i){
                        timer.cancel();
                    }
                    Tile[] hand = users[i].getHand();
                    //DB Stats Call
                    sendScoreStat(users[i]);
                    //replace player with skycat
                    SkyCat skyCat = new SkyCat(Session.getSession());
                    //put generated tiles back
                    TileGenerator.getInstance().putInBag(skyCat.getHand());
                    skyCat.setHand(hand);
                    users[i] = skyCat;
                    gui.setTurn(currentTurn);
                    gui.updateUsers(users);
                    if(currentTurn == i) {
                        //play instead of player
                        setAiTimer();
                    }
                    gui.printGameLog(player.getUsername() + " leaves the game");
                    return "removed";
                }
            }
        }
        return "user not found";
    }

    public String exchange(String mac, String letters) {
        gui.updateBoard(board.getBoard());
        //reset skip count
        users[currentTurn].setSkipped(0);
        letters = letters.toUpperCase();
        TileGenerator tg = TileGenerator.getInstance();
        int count = 0;
        //add letter to ArrayList
        ArrayList<Character> replace = new ArrayList<>();
        for (int i = 0; i < letters.length(); i++) {
            replace.add(letters.charAt(i));
        }

        //find corresponding user
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].getClass() == Player.class) {
                Player player = (Player) users[i];
                if (player.getMacAddress().equals(mac)) {
                    Tile[] hand = player.getHand();
                    //check each tile in hand for match
                    for (int k = 0; k < hand.length; k++) {
                        if (replace.contains(hand[k].getLetter())) {
                            Tile tileToExchange = tg.getTile(hand[k].getLetter());
                            //generate new tile instead
                            replace.remove((Character) hand[k].getLetter());

                            hand[k] = tg.exchangeTile(tileToExchange);
                            count++;
                        }
                    }
                    player.setHand(hand);
                    nextTurn();
                    skippedTimes ++;
                    gui.printGameLog(player.getUsername() + " exchanged some tiles");
                    return "Exchanged: " + count + " tiles";
                }
            }
        }
        return "Username not found";
    }

    private void exchangeAllTiles(User user){
        TileGenerator tg = TileGenerator.getInstance();
        Tile[] hand = user.getHand();
        for (int i=0; i < hand.length; i++){
            hand[i] = tg.exchangeTile(hand[i]);
        }
        user.setHand(hand);
        gui.updateBoard(board.getBoard());
        gui.printGameLog(user.getUsername() + " exchanged all tiles");
        skippedTimes ++;
        nextTurn();
    }

    public String getBoardJSON() {
        Gson gson = new Gson();
        String result = gson.toJson(board);
        return result;
    }

    public int calculateMovePoints(Move move) {
        Space[][] boardLocal = board.getBoard();
        boolean horizontal = move.isHorizontal();
        ArrayList<Space> usedSpaces = new ArrayList();
        int points = 0;
        int wordMult = 1;
        int letterMult = 1;
        Multiplier mult = Multiplier.NONE;
        for (int i = 0; i < move.getWordString().length(); i++) {

            Space current = null;
            if (horizontal && (move.getStartX() + i) < boardLocal.length) {
                mult = boardLocal[move.getStartX() + i][move.getStartY()].
                        getMultiplier();
                current = boardLocal[move.getStartX() + i][move.getStartY()];
                usedSpaces.add(current);
            } else if(move.getStartY() + i < boardLocal.length){
                mult = boardLocal[move.getStartX()][move.getStartY() + i].
                        getMultiplier();
                current = boardLocal[move.getStartX()][move.getStartY() + i];
                usedSpaces.add(current);
            }
            if (current != null && current.getUsed() == false) {
                switch (mult) {
                    case NONE:
                        letterMult = 1;
                        break;
                    case DOUBLE_LETTER:
                        letterMult = 2;
                        break;
                    case DOUBLE_WORD:
                        wordMult *= 2;
                        break;
                    case TRIPLE_LETTER:
                        letterMult = 3;
                        break;
                    case TRIPLE_WORD:
                        wordMult *= 3;
                        break;
                }
            }
            points += letterMult * move.getWord()[i].getValue();
        }
        points *= wordMult;
        //calculating the total number of points from offshoot moves
        ArrayList<Move> offshootMoves = move.getOffshootMoves();
        if (offshootMoves != null && !offshootMoves.isEmpty()) {
            for (Move aMove : offshootMoves) {
                if (aMove != null)
                    points += calculateMovePoints(aMove);
            }
            for (Space space : usedSpaces) {
                space.setUsed();
            }

        }
        return points;
    }

    /**
     * Set's next player's turn
     */
    private void nextTurn() {
        if(users[currentTurn].getClass() == Player.class) {
            timer.cancel();
        }

        //if skipped 4 times - game ended
        if(skippedTimes >= 4){
            System.out.println("Game Ended");
            restartGame();
            return;
        }

        boolean done = false;
        while (!done) {
            //increment player number
            if (currentTurn == 3) {
                currentTurn = 0;
            } else {
                currentTurn++;
            }
            if (users[currentTurn] != null) {
                gui.setTurn(currentTurn);
                gui.updateUsers(users);
                done = true;
            }
        }
        if(users[currentTurn].getClass() == SkyCat.class){
            setAiTimer();
         } else { //next user is real player
            setPlayerTimer();
        }

    }

    private void setPlayerTimer(){
        timer = new Timer();
        timer.schedule(new playerTimerTask(session), turnTimeSec * 1000);
    }

    private void setAiTimer(){
        //get the right delay for AI
        //default - no players
        int delay = aiWaitNoPlayers;
        boolean arePlayersJoined = false;
        for(int i =0; i < users.length; i++){
            if(users[i] != null && users[i].getClass() == Player.class){
                arePlayersJoined = true;
                break;
            }
        }
        //if player is in game - AI delay is lower
        if(arePlayersJoined){
            delay = aiWaitSec;
        }
        timer = new Timer();
        timer.schedule(new aiTimerTask(session), delay * 1000);
    }

    private void replaceTiles(User user, String letters) {
        TileGenerator tg = TileGenerator.getInstance();
        Tile[] hand = user.getHand();
        ArrayList<Character> replace = new ArrayList<>();
        for (int i = 0; i < letters.length(); i++) {
            replace.add(letters.charAt(i));
        }

        for (int i = 0; i < users.length; i++) {
            if (users[i] == user) {
                for (int k = 0; k < hand.length; k++) {
                    if (replace.contains(hand[k].getLetter())) {
                        //generate new tile instead
                        replace.remove((Character) hand[k].getLetter());
                        hand[k] = tg.getRandTile();
                    }
                }
                users[i].setHand(hand);
                break;
            }
        }
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    // Check whos turn it is and return their username
    public String isMyTurn(String mac)
    {
        try
        {
            // make sure users length lines up with current turn
            int turn = getCurrentTurn();
            if (users.length >= turn)
            {
                // Check if real live player
                if (users[turn].getClass() == Player.class)
                {
                    // Check if its this players turn
                    if (((Player)users[turn]).getMacAddress().equals(mac)) {
                        return "You!";
                    }
                    // Must be another player, give their username
                    return users[turn].getUsername();

                }
                // Its Skycat's turn
                else
                {
                    return "Skycat";
                }
            }
        } catch (Exception e) {
            Log.getLogger().logException(e);
            e.printStackTrace();
            }
        // Default to AI's turn
        return "Skycat";
    }

    private void updateTeamScore(int score, String team) {
        switch (team) {
            case "GREEN":
                greenScore += score;
                break;
            case "GOLD":
                goldScore += score;
                break;
            default:
                break;
        }
    }

    public int[] getTeamScores() {
        int[] teamScores = {greenScore, goldScore};
        return teamScores;
    }

    private static void restartGame(){
        session = null;
        Session.getSession();

    }

    private void sendScoreStat(User player) {
        Player temp = (Player) player;
        dbQueries.updatePlayerCumulative("test", temp.getScore());
        System.out.println(player.getUsername() + " " + player.getScore());
    }

    private void sendMoveStat() {

    }

    private void aiRun(){
        SkyCat skyCat = (SkyCat) session.users[currentTurn];
        boolean played = aiPlayWord(skyCat);
        if(played) {
            //reset skip counter
            skyCat.setSkipped(0);
        } else {
            System.out.println("AI Skipped");
            exchangeAllTiles(users[currentTurn]);
        }
    }

    private void playerTimeExpired(){
        skippedTimes ++;
        //check if player skipped 3 times
        if (users[currentTurn].getSkipped() == 2) {
            //DB Stats Call
            sendScoreStat(users[currentTurn]);
            //replace player with skycat
            Tile[] hand = users[currentTurn].getHand();
            SkyCat skyCat = new SkyCat(Session.getSession());
            //put generated tiles back in bag
            TileGenerator.getInstance().putInBag(skyCat.getHand());
            skyCat.setHand(hand);
            gui.printGameLog(users[currentTurn].getUsername() + " leaves the game");
            users[currentTurn] = skyCat;
            setAiTimer();
        } else {
            users[currentTurn].setSkipped(users[currentTurn].getSkipped() + 1);
            gui.printGameLog(users[currentTurn].getUsername() + " was thinking for too long");
            nextTurn();
        }
    }

    /**
     * Checks if user with provided mac address exists in database
     * @param mac
     * @return
     */
    public String amIregistered(String mac){
        if(dbQueries.findUser(mac) != null){
            return "TRUE";
        } else {
            return "FALSE";
        }
    }

    class aiTimerTask extends TimerTask {
        Session session;

        aiTimerTask(Session session) {
            this.session = session;
        }

        public void run() {
            session.aiRun();
        }
    }

    class playerTimerTask extends TimerTask {
        Session session;

        playerTimerTask(Session session) {
            this.session = session;
        }

        public void run() {
            session.playerTimeExpired();
        }
    }
}
