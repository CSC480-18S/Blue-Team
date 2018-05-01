package Session;

import Models.*;
import Views.*;
import Components.*;
import Components.Log;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;

import java.util.logging.Level;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static boolean aiRun = true;
    private Difficulty difficultySetting = Difficulty.MEDIUM;

    private enum Difficulty{
        EASY, MEDIUM, HARD
    }

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
                }else if(param[0].equals("aiRun")){
                    if(param[1].equals("true")){
                        aiRun = true;
                    }else if(param[1].equals("false")){
                        aiRun = false;
                    }
                }
                else if(param[0].equals("difficulty")){
                    if(param[1].equalsIgnoreCase("EASY"))
                        difficultySetting = Difficulty.EASY;
                    else if(param[1].equalsIgnoreCase("MEDIUM"))
                        difficultySetting = Difficulty.MEDIUM;
                    else if(param[1].equalsIgnoreCase("HARD"))
                        difficultySetting = Difficulty.HARD;
                }
            }
        } catch (Exception e ){
            Log.getLogger().logException(e);
            e.printStackTrace();
        }

        /* this runs when program is closing */
        Runtime.getRuntime().addShutdownHook(new Thread(() -> timer.cancel())
        );


    }

    public static Session getSession() {
        if (session == null) {
            session = new Session();
            if(aiRun) {
                //adding SkyCats initially
                for (int i = 0; i < session.users.length; i++) {
                    session.users[i] = new SkyCat(session);
                }
                session.gui.updateUsers(session.users);
                session.gui.setTurn(session.currentTurn);
                //play first move to start the game
                SkyCat skyCat1 = (SkyCat) session.users[0];
                session.setAiTimer();
                session.gui.updateHand(session.users);
            }
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

        //Validating Username
        String pattern = "^[a-zA-Z]{3,12}$";
        Pattern re;
        Matcher matcher;
        re = Pattern.compile(pattern);
        matcher = re.matcher(username);

        HashSet<String> badWords;
        badWords = Dictionaries.getDictionaries().getBadWords();
        if(!matcher.matches()) {
            return "Invalid Username";
        } else if (badWords.contains(username.toUpperCase())) {
            return "Dirty words not allowed";
        }

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
            if ((users[i] == null || users[i].getClass() == SkyCat.class)
                    && (((i == 0 || i == 2) && team.toUpperCase().equals("GREEN"))
                    || ((i == 1 || i == 3) && team.toUpperCase().equals("GOLD"))))
            {
                //cancel ai timer
                if(i == currentTurn && users[currentTurn] != null && users[i].getClass() == SkyCat.class){
                    timer.cancel();
                }
                if(users[currentTurn] != null && users[i].getClass() == SkyCat.class) {
                    //return generated tiles back in bag
                    TileGenerator.getInstance().putInBag(newPlayer.getHand());
                    Tile[] hand = users[i].getHand();
                    newPlayer.setHand(hand);
                }
                users[i] = newPlayer;
                if(currentTurn == i) {
                    setPlayerTimer();
                }
                gui.updateUsers(users);
                gui.printGameLog(newPlayer.getUsername() + " has joined " + team + " Team");
                return "JOINED";
            }
        }

        // Try adding to opposite team
        //check if any users are not initialized yet
        for (int i = 0; i < users.length; i++) {
            if ((users[i] == null || users[i].getClass() == SkyCat.class)
                    && (((i == 0 || i == 2) && team.toUpperCase().equals("GOLD"))
                    || ((i == 1 || i == 3) && team.toUpperCase().equals("GREEN"))))
            {
                //cancel ai timer
                if(i == currentTurn && users[currentTurn] != null && users[i].getClass() == SkyCat.class){
                    timer.cancel();
                }
                if(users[currentTurn] != null && users[i].getClass() == SkyCat.class) {
                    //return generated tiles back in bag
                    TileGenerator.getInstance().putInBag(newPlayer.getHand());
                    Tile[] hand = users[i].getHand();
                    newPlayer.setHand(hand);
                }
                users[i] = newPlayer;
                if(currentTurn == i) {
                    setPlayerTimer();
                }
                gui.updateUsers(users);
                gui.printGameLog(newPlayer.getUsername() + " has joined " + team + " Team");
                return "JOINED";
            }
        }

        return "Sorry - Game is full.";
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
        String difficulty =  "";
        switch(difficultySetting){
            case EASY: difficulty = "EASY"; break;
            case MEDIUM: difficulty = "MEDIUM"; break;
            case HARD: difficulty = "HARD"; break;
            default: difficulty = "MEDIUM"; break;
        }
        Move move = skyCat.chooseMove(difficulty);
        if (move != null) {
            String wordToPlay = "";
            for(Tile each : move.getWord()){
                if(each.getValue() == 0)
                    wordToPlay += "_" + each.getLetter();
                else
                    wordToPlay += each.getLetter();
            }
            String temp = session.playWord(move.getStartX(), move.getStartY(), move.isHorizontal(), wordToPlay, skyCat);
            if(temp.compareTo("Invalid") == 0){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    // Validate word and place on board
    public String playWord(int startX, int startY, boolean horizontal, String word, User user) {
        //restart players timer
        if(user.getClass() == Player.class){
            timer.cancel();
            setPlayerTimer();
        }

        String initialLetters = word;
        TileGenerator tg = TileGenerator.getInstance();
        // Check if word length is less than 11,
        // it will cause errors if too big.
        // This should never happen but just in case..
        String tempWord = word.replace("_","");
        if (tempWord.length() > 11) {
            return "Please play a shorter word?...";
        }

        if (tempWord.length() == 1) {
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
        ArrayList<Tile> wordTileBuilder = new ArrayList<>();
        StringBuilder tilesForChecking = new StringBuilder();
        boolean wildCard = false;
        for(char each : word.toCharArray()){
            if(each == '_'){
                wildCard = true;
            }
            else if(wildCard){
                wordTileBuilder.add(new Tile(each, 0));
                tilesForChecking.append("-");
                wildCard = false;
            }
            else {
                wordTileBuilder.add(tg.getTile(each));
                tilesForChecking.append(Character.toString(each));
            }
        }

        //check if user has enough tiles
        if(!hasEnoughTiles(user, tilesForChecking.toString())){
            System.out.println(user.getUsername() + " doesn't have required tiles");
            System.out.println("Word trying to play: " + tilesForChecking.toString());
            System.out.println("X:Y " + startX + ":" + startY);
            System.out.print("Tiles on hand: ");
            Tile[] hand = user.getHand();
            for(Tile tile : hand){
                System.out.print(tile.getLetter() + " ");
            }
            //if ai tries to use extra tiles - exchange all and skip
            if(user.getClass() == SkyCat.class){
                timer.cancel();
                exchangeAllTiles(user);
            }
            return "You don't have required tiles";
        }

        Tile[] wordTiles = new Tile[wordTileBuilder.size()];
        String wordToPlaceOnBoard = "";
        for(int i = 0; i < wordTileBuilder.size(); i++){
            wordTiles[i] = wordTileBuilder.get(i);
            wordToPlaceOnBoard += wordTileBuilder.get(i).getLetter();
        }

        Object[] result = validator.isValidPlay(new Move(startX, startY, horizontal, wordTiles, user));

        Move validMove = (Move)result[1];
        word = validMove.getWordString();
        if ((int) result[0] == 1) {
            board.placeWord(validMove.getStartX(), validMove.getStartY(), horizontal, word);
            int score = calculateMovePoints((Move)result[1]);
            displayMoveStats((Move) result[1], score);
            user.setScore(user.getScore() + score);
            if (user instanceof Player) {
                Player temp = (Player) user;
                updateTeamScore(score, temp.getTeam());
                updateTeamHighestWordScore(temp.getTeam(), score);
                updateLongestWord(temp.getTeam(), (Move) result[1]);
            }
            replaceTiles(user, tilesForChecking.toString());
            gui.updateBoard(board.getBoard());
            playedMoves.add((Move) result[1]);
            skippedTimes = 0;
            Move move = (Move)result[1];
            String temp = move.getWordString();
            gui.printGameLog(user.getUsername() + " played " + temp + " (" + score + " points)");
            nextTurn();
            return "VALID";
        } else if ((int) result[0] == 2) {
            board.placeWord(validMove.getStartX(), validMove.getStartY(), horizontal, word);
            int score = calculateMovePoints((Move)result[1]) * 2;
            displayMoveStats((Move) result[1], score);
            user.setScore(user.getScore() + score);
            if (user instanceof Player) {
                Player temp = (Player) user;
                updateTeamScore(score, temp.getTeam());
                updateBonusWord(temp.getTeam());
                updateTeamHighestWordScore(temp.getTeam(), score);
                updateLongestWord(temp.getTeam(), (Move) result[1]);
            }
            replaceTiles(user, tilesForChecking.toString());
            gui.updateBoard(board.getBoard());
            playedMoves.add((Move) result[1]);
            skippedTimes = 0;
            Move move = (Move)result[1];
            String temp = move.getWordString();
            gui.printGameLog(user.getUsername() + " played BONUS " + temp + " (" + score + " points)");
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
                    //DB Stats Call
                    sendScoreStat(users[i]);
                    if(aiRun) {
                        Tile[] hand = users[i].getHand();
                        //replace player with skycat
                        SkyCat skyCat = new SkyCat(Session.getSession());
                        //put generated tiles back
                        TileGenerator.getInstance().putInBag(skyCat.getHand());
                        skyCat.setHand(hand);
                        users[i] = skyCat;
                    } else {
                        users[i] = null;
                    }
                    gui.setTurn(currentTurn);
                    gui.updateUsers(users);
                    if(currentTurn == i && users[i] != null && users[i].getClass() == SkyCat.class) {
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
        timer.schedule(new playerTimerTask(session, turnTimeSec), 0, 1000);
    }

    private void setAiTimer(){
        //clear user timer
        gui.setTimerText(4,0);

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
        timer.schedule(new aiTimerTask(session),delay * 1000);
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
                if (users[turn] != null && users[turn].getClass() == Player.class)
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
        session.sendTeamStat();
        session.updateWTLCount();
        session.gui.closeFrame();
        session = null;
        Session.getSession();

    }

    private void sendScoreStat(User player) {
        Player temp = (Player) player;
        dbQueries.updatePlayerCumulative("test", temp.getScore());
        System.out.println(player.getUsername() + " " + player.getScore());
    }

    private void sendTeamStat() {
        int[] ts;
        ts = getTeamScores();
        System.out.println("What is being sent to db: " + ts[1] + "  " + ts[0]);
        dbQueries.addNewToGameTable(ts[1], ts[0]);
        dbQueries.updateTeamCumulative("green", ts[0]);
        dbQueries.updateTeamCumulative("gold", ts[1]);
    }

    private void updateWTLCount() {
        int[] ts;
        ts = getTeamScores();
        if(ts[1] > ts[0]) {
            dbQueries.updateWin("gold");
            dbQueries.updateLose("green");
        } else if(ts[0] > ts[1]) {
            dbQueries.updateWin("green");
            dbQueries.updateLose("gold");
        } else if(ts[0] == ts[1]) {
            dbQueries.updateTie();
        }
    }

    private void updateTeamHighestWordScore(String team, int score) { dbQueries.updateHighestWordScore(team, score); }

    private void updateLongestWord(String team, Move move) { dbQueries.updateTeamLongestWord(team, move.getWordString()); }

    private void updateBonusWord(String team) { dbQueries.updateTeamBonusCount(team, 1); }

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
        //cancel looping timer
        timer.cancel();
        skippedTimes ++;
        //check if player skipped 3 times
        if (users[currentTurn].getSkipped() == 2) {
            //DB Stats Call
            sendScoreStat(users[currentTurn]);
            if(aiRun) {
                //replace player with skycat
                Tile[] hand = users[currentTurn].getHand();
                SkyCat skyCat = new SkyCat(Session.getSession());
                //put generated tiles back in bag
                TileGenerator.getInstance().putInBag(skyCat.getHand());
                skyCat.setHand(hand);
                users[currentTurn] = skyCat;
                setAiTimer();
            }
            gui.printGameLog(users[currentTurn].getUsername() + " leaves the game");
        } else {
            users[currentTurn].setSkipped(users[currentTurn].getSkipped() + 1);
            gui.printGameLog(users[currentTurn].getUsername() + " was thinking for too long");
            nextTurn();
        }
    }

    private boolean hasEnoughTiles(User user, String letters){
        ArrayList<Character> replace = new ArrayList<>();
        for (int i = 0; i < letters.length(); i++) {
            replace.add(letters.charAt(i));
        }

        Tile[] userHand = user.getHand();

        ArrayList<Character> hand = new ArrayList<>();
        for (int i = 0; i < userHand.length; i++) {
            hand.add(userHand[i].getLetter());
        }

        for(int i =0; i < replace.size(); i++){
            if(hand.contains(replace.get(i))){
                hand.remove(replace.get(i));
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if user with provided mac address exists in database
     * @param mac
     * @return
     */
    public String amIregistered(String mac){
        Gson gson = new Gson();
        String[] result = dbQueries.findUser(mac);
        if(result != null){
            return gson.toJson(result);
        } else {
            String[] response = {"FALSE"};
            return gson.toJson(response);
        }
    }

    private Difficulty getDifficultySetting(){return this.difficultySetting;}

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
        int timeLeft;

        playerTimerTask(Session session, int timeLeft) {
            this.session = session;
            this.timeLeft = timeLeft;
        }

        public void run() {
            if(timeLeft > 0){
                session.gui.setTimerText(session.getCurrentTurn(), timeLeft);
                timeLeft--;
            }else {
                session.playerTimeExpired();
            }
        }
    }
}
