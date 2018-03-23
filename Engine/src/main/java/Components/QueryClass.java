package Components;

import java.sql.*;

public class QueryClass {
    private String dbDriver = "com.mysql.jdbc.Driver";
    private String dbAddress = "jdbc:mysql://localhost:3306/CSC480Data";
    private String dbUser = "root";
    private String dbPass = "blueteam";

    public QueryClass() {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException ce) {
            throw new RuntimeException("JDBC driver not found, jar is probably missing or in wrong folder");
        }
    }

    /**
     * Get the top player's names and cumulative scores
     *
     * @return ResultSet the set of the top 5 (or less) players by cumulative score, or null if error
     */
    public ResultSet getTopPlayers() {
        int num = 5; // Number of top players to retrieve
        String query = "SELECT uid, cumulative_score FROM PLAYER_TABLE ORDER BY cumulative_score DESC LIMIT ?";

        try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,num);
            ResultSet rs = preparedStmt.executeQuery();
            return rs;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
    }

    /**
     * Add a new user to the database.
     * @param uname The user's name
     * @param mac The user's mac address
     * @param team The user's team ('gold' or 'green' as a String)
     * @return True if added to database, false if user exists, null if error
     * @throws RuntimeException if database consistency problem occurs
     */
    public Boolean addNewUser(String uname, String mac, String team){
        String query1 = "INSERT INTO USER_TABLE (uid, mac_addr) VALUES (?, ?)"; //Will automatically return false if user or mac exists
        String query2 = "INSERT INTO PLAYER_TEAM (uid, teamid) VALUES (?, ?)";
        String query3 = "INSERT INTO PLAYER_TABLE (uid, cumulative_score, longest_word, bonuses, highest_word_score) "
                + "VALUES (?, 0, '', 0, 0)";
        boolean pastQ1 = false; //If an exception occurs after query1 is executed, the database will not be consistent.
        
        if(userAlreadyExists(uname)){
            return false;
        }
        try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
            PreparedStatement preparedStmt = con.prepareStatement(query1);
            preparedStmt.setString(1, uname);
            preparedStmt.setString(2, mac);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            preparedStmt.execute();
            pastQ1 = true;
            preparedStmt = con.prepareStatement(query2);
            preparedStmt.setString(1, uname);
            preparedStmt.setString(2, team);
            preparedStmt.execute();
            preparedStmt = con.prepareStatement(query3);
            preparedStmt.setString(1, uname);
            preparedStmt.execute();
        } catch (SQLException se) {
            se.printStackTrace();
            if(pastQ1){
                throw new RuntimeException("Database consistency error");
            }
            return null;
        }
        return true;
    }

    /**
     * Check if a username already is taken
     * @param uname The user's name
     * @return Boolean true if username taken, false if not, null if error
     */
    public Boolean userAlreadyExists(String uname){
        String query = "SELECT uid FROM USER_TABLE WHERE uid=?";

        try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, uname);
            ResultSet rs = preparedStmt.executeQuery();
            if(rs.next()){ //If this executes, it found a user. I don't check to see if it finds more than 1, it shouldn't
                return true;
            }
            return false;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
    }

    /**
     * Find a username given their mac address
     * @param mac The mac address to find a related user from
     * @return String the username related to the mac, null if nonexistent user or error
     */
    public String[] findUser(String mac){
        String query = "SELECT uid FROM USER_TABLE where mac_addr=?";
        try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, mac);
            ResultSet rs = preparedStmt.executeQuery();
            if(rs.next()){ //If this executes, it found a user. I don't check to see if it finds more than 1, it shouldn't

                //using user name to create a query to find team id
                String userid = rs.getString("uid");
                String query2 = "SELECT teamid FROM PLAYER_TEAM where uid=?";
                PreparedStatement preparedStmt2 = con.prepareStatement(query2);
                preparedStmt2.setString(1,userid);
                ResultSet rs2 = preparedStmt2.executeQuery();
                if(rs2.next()) {//if this executes then a team preference was found for the user and both a username and team is returned
                    String userInfo[] = {userid, rs2.getString("teamid")};
                    return userInfo;
                }
                else {//if no team is found then this returns a null as the team, This should never actually be executed based on the setup of the registration but figured it couldn't hurt
                    return new String[]{userid, null};
                }
            }
            return null;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
    }

    /**
     * get total number of rows in the game table
     * @return an integer indicates game table count
     */
    public int getGameTableCount(){
        Statement aStatement = null;
        int count = -1;
        try {
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            String sqlQuery = "SELECT COUNT(*) FROM GAME_TABLE";
            aStatement = connection.createStatement();
            ResultSet rs = aStatement.executeQuery(sqlQuery);
            while(rs.next()) {
                count = rs.getInt(1);
                return count;
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }
    }

    /**
     * get total number of rows in the valid word table
     * @return an integer indicates valid word table count
     */
    public int getValidWordTableCount(){
        Statement aStatement = null;
        int count = 0;
        try {
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            String sqlQuery = "SELECT COUNT(*) FROM VALID_WORD_TABLE";
            aStatement = connection.createStatement();
            ResultSet rs = aStatement.executeQuery(sqlQuery);
            while(rs.next()) {
                count = rs.getInt(1);
                return count;
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }
    }

    /**
     * Add a new game table to the database
     * @param game_id The game id
     * @param gold_team_score final Gold team score as an integer
     * @param green_team_score  final Greem team score as an integer
     */
    public void addNewToGameTable(int game_id, int gold_team_score, int green_team_score){

        try{
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            String sqlQuery = "INSERT INTO GAME_TABLE VALUES (?,?,?);";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);

            ps.setInt(1,game_id);
            ps.setInt(2,gold_team_score);
            ps.setInt(3,green_team_score);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new valid word to the database
     * @param word_id word id as an integer
     * @param word the word itself as a String
     * @param value how much point the word worth in the game, as an integer
     * @param length length of the word, as an integer
     * @param is_extension whether the word has been used as an extension, as a boolean
     * @param bonuses_used how many times the word has been used as a bonus word, as an integer
     */
    public void addNewToValidWordTable(int word_id, String word, int value, int length, boolean is_extension, int bonuses_used){

        try{
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            String sqlQuery = "INSERT INTO VALID_WORD_TABLE VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);

            ps.setInt(1,word_id);
            ps.setString(2,word);
            ps.setInt(3,value);
            ps.setInt(4,length);
            ps.setBoolean(5,is_extension);
            ps.setInt(6,bonuses_used);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * get cumulative game score of a team
     * @return an integer indicating the cumulative score of specified team
     */
    public int getTeamCumulative(String teamname){
        String query = "SELECT cumulative_game_score FROM TEAM_TABLE WHERE team_name = ?";
        
        try(Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)){
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, teamname);
            ResultSet rs = preparedStmt.executeQuery();
            int teamCumulative = rs.getInt("cumulative_game_score");
            return teamCumulative;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        }
    }
    
    /**
     * get highest word score of a team
     * @return an integer indicating the highest word score of specified team
     */
    public int getHighestWordScore(String teamname){
        String query = "SELECT highest_word_score FROM TEAM_TABLE WHERE team_name = ?";
        
        try(Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)){
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, teamname);
            ResultSet rs = preparedStmt.executeQuery();
            int teamHighestW = rs.getInt("highest_word_score");
            return teamHighestW;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        }
    }
    
    /**
     * get highest game session score of a team
     * @return an integer indicating the cumulative score of specified team
     */
    public int getHighestGameSessionScore(String teamname){
        String query = "SELECT highest_game_session_score FROM TEAM_TABLE WHERE team_name = ?";
        
        try(Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)){
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, teamname);
            ResultSet rs = preparedStmt.executeQuery();
            int teamHighest = rs.getInt("highest_game_session_score");
            return teamHighest;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        }
    }
    
    /**
     * get team win count
     * @return an integer indicating the win count of specified team
     */
    public int getTeamWinCount(String teamname){
        String query = "SELECT win_count FROM TEAM_TABLE WHERE team_name = ?";
        
        try(Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)){
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, teamname);
            ResultSet rs = preparedStmt.executeQuery();
            int teamWin = rs.getInt("win_count");
            return teamWin;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        }
    }
    
    /**
     * get team lose count
     * @return an integer indicating the lose count of specified team
     */
    public int getTeamLoseCount(String teamname){
        String query = "SELECT lose_count FROM TEAM_TABLE WHERE team_name = ?";
        
        try(Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)){
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, teamname);
            ResultSet rs = preparedStmt.executeQuery();
            int teamLose = rs.getInt("lose_count");
            return teamLose;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        }
    }
    
    /**
     * get team tie count
     * @return an integer indicating the tie count of specified team
     */
    public int getTeamTieCount(String teamname){
        String query = "SELECT tie_count FROM TEAM_TABLE WHERE team_name = ?";
        
        try(Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)){
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, teamname);
            ResultSet rs = preparedStmt.executeQuery();
            int teamTie = rs.getInt("tie_count");
            return teamTie;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        }
    }
    
    /**
     * get longest word of a team
     * @return a String indicating the Longest Word of specified team
     */
    public String getTeamLongestWord(String teamname){
        String query = "SELECT longest_word FROM TEAM_TABLE WHERE team_name = ?";
        
        try(Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)){
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, teamname);
            ResultSet rs = preparedStmt.executeQuery();
            String teamLongWord = rs.getString("longest_word");
            return teamLongWord;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
    }
    
    /**
     * get number of bonuses a team used
     * @return an integer indicating the number of bonuses a specified team used
     */
    public int getTeamBonuses(String teamname){
        String query = "SELECT bonuses FROM TEAM_TABLE WHERE team_name = ?";
        
        try(Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)){
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, teamname);
            ResultSet rs = preparedStmt.executeQuery();
            int teamBonuses = rs.getInt("bonuses");
            return teamBonuses;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        }
    }

    /**
     * get the number of dirty word attempt of a team
     * @return an integer indicating the number of times a specified team tried using a "dirty word"
     */
    public int getTeamDirtyWordAttempt(String teamname){
        String query = "SELECT dirty_word FROM TEAM_TABLE WHERE team_name = ?";
        
        try(Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)){
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, teamname);
                        ResultSet rs = preparedStmt.executeQuery();
            int teamDirtyCount = rs.getInt("dirty_word");
            return teamDirtyCount;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        }
	}
}