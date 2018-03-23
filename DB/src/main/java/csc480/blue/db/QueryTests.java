import java.sql.*;

public class QueryTests {
    private String dbDriver = "com.mysql.jdbc.Driver";
    private String dbAddress = "jdbc:mysql://localhost:3306/csc480data";
    private String dbUser = "csc";
    private String dbPass = "blueteam";

    public QueryTests() {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException ce) {
            throw new RuntimeException("JDBC driver not found, jar is probably missing or in wrong folder");
        }
    }

    /**
     * print out the game table ( for testing purposes )
     */
    public void printGameTable(){
        System.out.println("===== start of game_table ====");
        int game_id, gold_team_score, green_team_score;
        try{
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            String sqlQuery = "SELECT * FROM csc480data.game_table; ";
            Statement aStatement = connection.createStatement();
            ResultSet rs = aStatement.executeQuery(sqlQuery);
            boolean more = rs.next();
            while (more) {
                // extract the data & display
                game_id = rs.getInt(1);
                gold_team_score = rs.getInt(2);
                green_team_score = rs.getInt(3);

                System.out.print("game_id: " + game_id);
                System.out.print("  gold_team_score: " + gold_team_score);
                System.out.print("  green_team_score: " + green_team_score);
                System.out.println();

                more = rs.next();
            }
            System.out.println("===== end of game_table =====");
            rs.close();
            aStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * prints out valid word table (for testing purposes)
     */
    public void printValidWordTable(){
        System.out.println("===== start of valid_word_table ====");
        int word_id,value,length,bonuses_used;
        String word;
        boolean is_extension;
        try{
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            String sqlQuery = "SELECT * FROM csc480data.valid_word_table; ";
            Statement aStatement = connection.createStatement();
            ResultSet rs = aStatement.executeQuery(sqlQuery);
            boolean more = rs.next();
            while (more) {
                // extract the data & display
                word_id = rs.getInt(1);
                word = rs.getString(2);
                value = rs.getInt(3);
                length = rs.getInt(4);
                is_extension = rs.getBoolean(5);
                bonuses_used = rs.getInt(6);

                System.out.print("word_id: " + word_id);
                System.out.print("  word: " + word);
                System.out.print("  value: " + value);
                System.out.print("  length: " + length);
                System.out.print("  is_extension: " + is_extension);
                System.out.print("  bonuses_used: " + bonuses_used);

                System.out.println();

                more = rs.next();
            }
            System.out.println("===== end of valid_word_table =====");
            rs.close();
            aStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * deletes a game from the game table ( for testing purposes)
     * @param i game id
     */
    public void deleteGameByID(int i ){
        try{
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            String sqlQuery = "DELETE FROM game_table where game_id = ?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1,i);
            ps.execute();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * deletes a valid word by id from the valid word table
    * @param i valid word id
    */
    public void deleteValidWordByID(int i ){
        try{
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            String sqlQuery = "DELETE FROM valid_word_table where game_id = ?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1,i);
            ps.execute();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Deletes ALL valid words from the valid word table without deleting the table itself
     * FOR TESTING PURPOSES
    */

    public void deleteAllValidWords(){

        String sqlQuery = "DELETE * FROM csc480data.valid_word_table; ";
        Statement aStatement = null;
        try {
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            aStatement = connection.createStatement();
            ResultSet rs =aStatement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes all games from the game table without deleting the table itself
     * FOR TESTING PURPOSES
    */
    public void deleteAllGames(){

        String sqlQuery = "DELETE * FROM csc480data.game_table; ";
        Statement aStatement = null;
        try {
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            aStatement = connection.createStatement();
            ResultSet rs =aStatement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes all dirty words from the dirty word table without deleting the table itself
     * FOR TESTING PURPOSES
     */
    public void deleteAllDirtyWords(){

        String sqlQuery = "DELETE * FROM csc480data.dirty_word_table; ";
        Statement aStatement = null;
        try {
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            aStatement = connection.createStatement();
            ResultSet rs = aStatement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes all teams from the team table without deleting the table itself
     * FOR TESTING PURPOSES
     */
    public void deleteAllTeams(){

        String sqlQuery = "DELETE * FROM csc480data.team_table; ";
        Statement aStatement = null;
        try {
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            aStatement = connection.createStatement();
            ResultSet rs =aStatement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes all users from the dirty word table without deleting the table itself
     * FOR TESTING PURPOSES
     */
    public void deleteAllUsers(){

        String sqlQuery = "DELETE * FROM csc480data.users_table; ";
        Statement aStatement = null;
        try {
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            aStatement = connection.createStatement();
            ResultSet rs =aStatement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes all player teams from the player team table without deleting the table itself
     * FOR TESTING PURPOSES
     */
    public void deleteAllPlayerTeams(){

        String sqlQuery = "DELETE * FROM csc480data.player_team; ";
        Statement aStatement = null;
        try {
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            aStatement = connection.createStatement();
            ResultSet rs =aStatement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes all players from the player table without deleting the table itself
     * FOR TESTING PURPOSES
     */
    public void deleteAllPlayers(){

        String sqlQuery = "DELETE * FROM csc480data.player_table; ";
        Statement aStatement = null;
        try {
            Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
            aStatement = connection.createStatement();
            ResultSet rs =aStatement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * A wrapper method for emptying out the entire database
     * for testing purposes
     */
    public void deleteAll(){
        this.deleteAllDirtyWords();
        this.deleteAllValidWords();
        this.deleteAllTeams();
        this.deleteAllGames();
        this.deleteAllPlayers();
        this.deleteAllPlayerTeams();
        this.deleteAllUsers();
    }

}

