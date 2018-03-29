import java.sql.*;

public class QueryClass {
    private String dbDriver = "com.mysql.jdbc.Driver";
    private String dbAddress = "jdbc:mysql://localhost:3306/csc480data";
    private String dbUser = "csc";
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
        String query = "SELECT uid, cumulative_score FROM player_table ORDER BY cumulative_score DESC LIMIT ?";

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
        String query1 = "INSERT INTO user_table (uid, mac_addr) VALUES (?, ?)"; //Will automatically return false if user or mac exists
        String query2 = "INSERT INTO player_team (uid, teamid) VALUES (?, ?)";
        String query3 = "INSERT INTO player_table (uid, cumulative_score, longest_word, bonuses, highest_word_score) "
                + "VALUES (?, 0, '', 0, 0)";
        boolean pastQ1 = false; //If an exception occurs after query1 is executed, the database will not be consistent.
        
        if(userAlreadyExists(uname)){
            return false;
        }
        try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
            PreparedStatement preparedStmt = con.prepareStatement(query1);
            preparedStmt.setString(1, uname);
            preparedStmt.setString(2, mac);
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
        String query = "SELECT uid FROM user_table WHERE uid=?";

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
    public String findUser(String mac){
        String query = "SELECT uid FROM user_table where mac_addr=?";

        try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, mac);
            ResultSet rs = preparedStmt.executeQuery();
            if(rs.next()){ //If this executes, it found a user. I don't check to see if it finds more than 1, it shouldn't
                String uname = rs.getString("uid");
                return uname;
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
            String sqlQuery = "SELECT COUNT(*) FROM csc480data.game_table ";
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
            String sqlQuery = "SELECT COUNT(*) FROM csc480data.valid_word_table ";
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
         * @return true = operation successful ; false = operation failed because of game_id already exists;
         * @throws RuntimeException if database error
         */
        public Boolean addNewToGameTable(int game_id, int gold_team_score, int green_team_score){

            try{
                Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
                if(!this.gameIDAlreadyExists(game_id)){ //if game_id doesnt exist
                    String sqlQuery = "INSERT INTO game_table VALUES (?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(sqlQuery);

                    ps.setInt(1, game_id);
                    ps.setInt(2, gold_team_score);
                    ps.setInt(3, green_team_score);
                    ps.executeUpdate();
                    ps.close();
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return false;
        }

        /**
         * Check if a game_id already exist in the game_table
         * @param game_id The game id
         * @return Boolean true if username taken, false if not, null if error
         */
        public Boolean gameIDAlreadyExists(int game_id){
            String query = "SELECT game_id FROM game_table WHERE game_id=?";

            try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, game_id);
                ResultSet rs = preparedStmt.executeQuery();
                if(rs.next()){
                    return true;
                }
                return false;
            } catch (SQLException se) {
                se.printStackTrace();
                return null;
            }
        }



        /**
         * Check if a word already exist in the valid_word_table
         * @param word The word
         * @return Boolean true if word already exist, false if not, null if error
         */
        public Boolean wordAlreadyExistsInValidWordTable(String word){
            String query = "SELECT word FROM valid_word_table WHERE word=?";
            try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, word);
                ResultSet rs = preparedStmt.executeQuery();
                if(rs.next()){
                    return true;
                }
                return false;
            } catch (SQLException se) {
                se.printStackTrace();
                return null;
            }
        }
        /**
         * Check if a word_id already exist in the valid_word_table
         * @param word_id The game id
         * @return Boolean true if word_id taken, false if not, null if error
         */
        public Boolean wordIDAlreadyExistsInValidWordTable(int word_id){
            String query = "SELECT word_id FROM valid_word_table WHERE word_id=?";

            try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, word_id);
                ResultSet rs = preparedStmt.executeQuery();
                if(rs.next()){
                    return true;
                }
                return false;
            } catch (SQLException se) {
                se.printStackTrace();
                return null;
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
         * @return Boolean indicates whether operation is successful. true if successful, false if word and/or word_id already exist, null if database error occur
         */
        public Boolean addNewToValidWordTable(int word_id, String word, int value, int length, boolean is_extension, int bonuses_used){

            try{
                Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
                if( this.wordIDAlreadyExistsInValidWordTable(word_id) || this.wordAlreadyExistsInValidWordTable(word)) {
                    return false;
                }else{
                    String sqlQuery = "INSERT INTO valid_word_table VALUES (?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(sqlQuery);

                    ps.setInt(1, word_id);
                    ps.setString(2, word);
                    ps.setInt(3, value);
                    ps.setInt(4, length);
                    ps.setBoolean(5, is_extension);
                    ps.setInt(6, bonuses_used);
                    ps.executeUpdate();
                    ps.close();
                    return true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * updating bonuses_used for a word in the valid_word_table
         * @param updateWord  the word that needs an update on its bonuses_used value
         * @param newValue the new value needs to be updated
         * @return true if operation is successful, false if word doesn't exist; null if database error
         */
        public Boolean updateValidWordBonusesUsed(String updateWord, int newValue){
            try{
                Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
                if(!wordAlreadyExistsInValidWordTable(updateWord)){
                    return false;
                }else{
                    String sqlQuery = "UPDATE valid_word_table SET bonuses_used = ? WHERE word = ?;";
                    PreparedStatement ps = connection.prepareStatement(sqlQuery);
                    ps.setInt(1,newValue);
                    ps.setString(2,updateWord);
                    ps.executeUpdate();
                    ps.close();
                    return true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         *  updating whether a valid word is used as an extension
         * @param updateWord the word that needs an update on its is_extension value
         * @param newValue the new boolean that gets updated to the database
         * @return true if successful; false if word doesn't exist in the valid_word_table; null if database error
         */
        public Boolean updateValidWordIsExtension(String updateWord, boolean newValue){
            try{
                Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
                if(!wordAlreadyExistsInValidWordTable(updateWord)){
                    return false;
                }else{
                    String sqlQuery = "UPDATE valid_word_table SET is_extension = ? WHERE word = ?;";
                    PreparedStatement ps = connection.prepareStatement(sqlQuery);
                    ps.setBoolean(1,newValue);
                    ps.setString(2,updateWord);
                    ps.executeUpdate();
                    ps.close();
                    return true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    /**
     * get cumulative game score of a team
     * @return an integer indicating the cumulative score of specified team
     */
    public int getTeamCumulative(String teamname){
        String query = "SELECT cumulative_game_score FROM team_table WHERE team_name = ?";
        
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
        String query = "SELECT highest_word_score FROM team_table WHERE team_name = ?";
        
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
        String query = "SELECT highest_game_session_score FROM team_table WHERE team_name = ?";
        
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
        String query = "SELECT win_count FROM team_table WHERE team_name = ?";
        
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
        String query = "SELECT lose_count FROM team_table WHERE team_name = ?";
        
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
        String query = "SELECT tie_count FROM team_table WHERE team_name = ?";
        
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
        String query = "SELECT longest_word FROM team_table WHERE team_name = ?";
        
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
        String query = "SELECT bonuses FROM team_table WHERE team_name = ?";
        
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
        String query = "SELECT dirty_word FROM team_table WHERE team_name = ?";
        
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
