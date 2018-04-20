import java.sql.*;
import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.TTest;

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
		initializeTeams();
	}

	/**
	 * Initialize the green and gold team tables. This should only actually update on an
	 * empty database
	 */
	private void initializeTeams() {
		String query = "SELECT * FROM TEAM_TABLE";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) { // Team exists, the other should too
				return;
			} else { // No teams, this should be a new database
				query = "INSERT INTO TEAM_TABLE (team_name, cumulative_game_score, "
						+ "highest_word_score, highest_game_session_score, win_count, "
						+ "lose_count, tie_count, longest_word, bonuses, dirty_word) VALUES "
						+ "(?, 0, 0, 0, 0, 0, 0, '', 0, 0)";

				preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, "green");
				preparedStmt.execute();
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, "gold");
				preparedStmt.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the n highest word scores from players
	 *
	 * @param num
	 *            the number of top players/words to get
	 * @return String[][] where the first index is a player and the second index is
	 *         their uid, team, and associated high word score
	 */
	public String[][] getHighestWordScoresAllPlayers(int num) {
		String query = "SELECT PLAYER_TABLE.uid, PLAYER_TABLE.highest_word_score, teamid FROM PLAYER_TABLE "
				+ "INNER JOIN PLAYER_TEAM ON PLAYER_TABLE.uid = PLAYER_TEAM.uid ORDER BY highest_word_score DESC LIMIT ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, num);
			ResultSet rs = preparedStmt.executeQuery();
			String[][] players = new String[num][3];
			int i = 0;
			while (rs.next()) {
				players[i][0] = rs.getString("uid");
				players[i][1] = rs.getString("teamid");
				players[i][2] = String.valueOf(rs.getInt("highest_word_score"));
				i++;
			}
			return players;
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the top player's names and cumulative scores by team
	 *
	 * @param team
	 *            gold or green for the top team members to get
	 * @param num
	 *            the number of top players to get
	 * @return String[][] where the first index is a player and the second index is
	 *         their uid, team, and cumulative score
	 */
	public String[][] getTopPlayersByTeam(int num, String team) {
		String query = "SELECT PLAYER_TABLE.uid, PLAYER_TABLE.cumulative_score, teamid FROM PLAYER_TABLE "
				+ "INNER JOIN PLAYER_TEAM ON PLAYER_TABLE.uid = PLAYER_TEAM.uid WHERE PLAYER_TEAM.teamid=? "
				+ "ORDER BY cumulative_score DESC LIMIT ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, team);
			preparedStmt.setInt(2, num);
			ResultSet rs = preparedStmt.executeQuery();
			String[][] players = new String[num][3];
			int i = 0;
			while (rs.next()) {
				players[i][0] = rs.getString("uid");
				players[i][1] = rs.getString("teamid");
				players[i][2] = String.valueOf(rs.getInt("cumulative_score"));
				i++;
			}
			return players;
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the top player's names and cumulative scores
	 *
	 * @param num
	 *            the number of top players to get
	 * @return String[][] where the first index is a player and the second index is
	 *         their uid, team, and cumulative score
	 */
	public String[][] getTopPlayers(int num) {
		String query = "SELECT PLAYER_TABLE.uid, PLAYER_TABLE.cumulative_score, teamid FROM PLAYER_TABLE "
				+ "INNER JOIN PLAYER_TEAM ON PLAYER_TABLE.uid = PLAYER_TEAM.uid ORDER BY cumulative_score DESC LIMIT ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, num);
			ResultSet rs = preparedStmt.executeQuery();
			String[][] players = new String[num][3];
			int i = 0;
			while (rs.next()) {
				players[i][0] = rs.getString("uid");
				players[i][1] = rs.getString("teamid");
				players[i][2] = String.valueOf(rs.getInt("cumulative_score"));
				i++;
			}
			return players;
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
	}

	/**
	 * Add a new user to the database.
	 *
	 * @param uname
	 *            The user's name
	 * @param mac
	 *            The user's mac address
	 * @param team
	 *            The user's team ('gold' or 'green' as a String)
	 * @return True if added to database, false if user exists, null if error
	 * @throws RuntimeException
	 *             if database consistency problem occurs
	 */
	public Boolean addNewUser(String uname, String mac, String team) {
		String query1 = "INSERT INTO USER_TABLE (uid, mac_addr) VALUES (?, ?)"; // Will
																				// automatically
																				// return
																				// false
																				// if
																				// user
																				// or
																				// mac
																				// exists
		String query2 = "INSERT INTO PLAYER_TEAM (uid, teamid) VALUES (?, ?)";
		String query3 = "INSERT INTO PLAYER_TABLE (uid, cumulative_score, longest_word, bonuses, highest_word_score) "
				+ "VALUES (?, 0, '', 0, 0)";
		boolean pastQ1 = false; // If an exception occurs after query1 is
								// executed, the database will not be
								// consistent.

		if (userAlreadyExists(uname)) {
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
			if (pastQ1) {
				throw new RuntimeException("Database consistency error");
			}
			return null;
		}
		return true;
	}

	/**
	 * Check if a username already is taken
	 *
	 * @param uname
	 *            The user's name
	 * @return Boolean true if username taken, false if not, null if error
	 */
	public Boolean userAlreadyExists(String uname) {
		String query = "SELECT uid FROM USER_TABLE WHERE uid=?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, uname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) { // If this executes, it found a user. I don't check
								// to see if it finds more than 1, it shouldn't
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
	 *
	 * @param mac
	 *            The mac address to find a related user from
	 * @return String array of the username and team related to the mac, null if
	 *         nonexistent user or error
	 */
	public String[] findUser(String mac) {
		String query = "SELECT uid FROM USER_TABLE where mac_addr=?";
		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, mac);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) { // If this executes, it found a user. I don't check
								// to see if it finds more than 1, it shouldn't

				// using user name to create a query to find team id
				String userid = rs.getString("uid");
				String query2 = "SELECT teamid FROM PLAYER_TEAM where uid=?";
				PreparedStatement preparedStmt2 = con.prepareStatement(query2);
				preparedStmt2.setString(1, userid);
				ResultSet rs2 = preparedStmt2.executeQuery();
				if (rs2.next()) {// if this executes then a team preference was
									// found for the user and both a username
									// and team is returned
					String userInfo[] = { userid, rs2.getString("teamid") };
					return userInfo;
				} else {// if no team is found then this returns a null as the
						// team, This should never actually be executed based on
						// the setup of the registration but figured it couldn't
						// hurt
					return new String[] { userid, null };
				}
			}
			return null;
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
	}

	/**
	 * Add new dirty word or update if exists when a user plays a dirty word
	 *
	 * @param word
	 *            The dirty word plays
	 */
	public void dirtyWordPlayed(String word) {
		String query = "SELECT * from DIRTY_WORD_TABLE WHERE dirty_word=?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, word);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) { // This dirty word has been played before, update
								// its usage count
				rs.updateInt("attempt_usage_count", (rs.getInt("attempt_usage_count") + 1));
				rs.updateRow();
			} else { // First time this dirty word was played. Create a new
						// entry
				String query2 = "INSERT INTO DIRTY_WORD_TABLE (dirty_word_id, dirty_word, attempt_usage_count) "
						+ "VALUES (?, ?, 1)";
				int nextId = getDirtyWordUniqueCount();
				preparedStmt.close();
				preparedStmt = con.prepareStatement(query2);
				preparedStmt.setInt(1, nextId);
				preparedStmt.setString(2, word);
				preparedStmt.execute();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * get total number of rows in the dirty word table
	 *
	 * @return an integer indicates dirty word count
	 */
	public int getDirtyWordUniqueCount() {
		Statement aStatement = null;
		int count = -1;
		try (Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			String sqlQuery = "SELECT COUNT(*) FROM DIRTY_WORD_TABLE";
			aStatement = connection.createStatement();
			ResultSet rs = aStatement.executeQuery(sqlQuery);
			while (rs.next()) {
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
	 * Updates the cumulative score of a player
	 *
	 * @param uname
	 *            the user to update
	 * @param points
	 *            the score to add to the cumulative score
	 */
	public void updatePlayerCumulative(String uname, int points) {
		String query = "SELECT * FROM PLAYER_TABLE WHERE uid=?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, uname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				rs.updateInt("cumulative_score", (rs.getInt("cumulative_score") + points));
				rs.updateRow();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * Updates the number of times a player played a bonus
	 *
	 * @param uname
	 *            the user to update
	 * @param bonus
	 *            the bonus to add to the cumulative bonuses used
	 */
	public void updatePlayerBonusCount(String uname, int bonus) {
		String query = "SELECT * FROM PLAYER_TABLE WHERE uid=?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, uname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				rs.updateInt("bonuses", (rs.getInt("bonuses") + bonus));
				rs.updateRow();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * Checks to see if the word a player played is a new longest for them, or a new
	 * highest word score, updates it if it is tied for longest or longer
	 *
	 * @param uname
	 *            the user to update
	 * @param word
	 *            the word played
	 * @param points
	 *            the point value of that word
	 */
	public void updatePlayerLongestWordAndHighestScore(String uname, String word, int points) {
		String query = "SELECT * FROM PLAYER_TABLE WHERE uid=?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, uname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				String currentLong = rs.getString("longest_word");
				if (currentLong.length() <= word.length()) {
					rs.updateString("longest_word", word);
					rs.updateRow();
				}
				int currentHigh = rs.getInt("highest_word_score");
				if (currentHigh <= points) {
					rs.updateInt("highest_word_score", points);
					rs.updateRow();
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * get total number of rows in the game table
	 *
	 * @return an integer indicates game table count
	 */
	public int getGameTableCount() {
		Statement aStatement = null;
		int count = -1;
		try {
			Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
			String sqlQuery = "SELECT COUNT(*) FROM GAME_TABLE";
			aStatement = connection.createStatement();
			ResultSet rs = aStatement.executeQuery(sqlQuery);
			while (rs.next()) {
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
	 *
	 * @return an integer indicates valid word table count
	 */
	public int getValidWordTableCount() {
		Statement aStatement = null;
		int count = 0;
		try {
			Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
			String sqlQuery = "SELECT COUNT(*) FROM VALID_WORD_TABLE";
			aStatement = connection.createStatement();
			ResultSet rs = aStatement.executeQuery(sqlQuery);
			while (rs.next()) {
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
	 *
	 * @param gold_team_score
	 *            final Gold team score as an integer
	 * @param green_team_score
	 *            final Greem team score as an integer
	 * @return true = operation successful ; false = operation failed because of
	 *         game_id already exists;
	 * @throws RuntimeException
	 *             if database error
	 */
	public Boolean addNewToGameTable(int gold_team_score, int green_team_score) {
		try {
			int game_id = this.getGameTableCount();
			Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
			if (!this.gameIDAlreadyExists(game_id)) { // if game_id doesnt exist
				String sqlQuery = "INSERT INTO GAME_TABLE VALUES (?,?,?);";
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
	 * Add a new valid word to the database
	 *
	 * @param word
	 *            the word itself as a String
	 * @param value
	 *            how much point the word worth in the game, as an integer
	 * @param length
	 *            length of the word, as an integer
	 * @param is_extension
	 *            whether the word has been used as an extension, as a boolean
	 * @param bonuses_used
	 *            how many times the word has been used as a bonus word, as an
	 *            integer
	 * @return Boolean indicates whether operation is successful. true if
	 *         successful, false if word and/or word_id already exist, null if
	 *         database error occur
	 */
	public Boolean addNewToValidWordTable(String word, int value, int length, boolean is_extension, int bonuses_used) {

		try {
			int word_id = this.getValidWordTableCount();
			Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
			if (this.wordIDAlreadyExistsInValidWordTable(word_id) || this.wordAlreadyExistsInValidWordTable(word)) {
				return false;
			} else {
				String sqlQuery = "INSERT INTO VALID_WORD_TABLE VALUES (?,?,?,?,?,?);";
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
	 * Check if a game_id already exist in the game_table
	 *
	 * @param game_id
	 *            The game id
	 * @return Boolean true if username taken, false if not, null if error
	 */
	public Boolean gameIDAlreadyExists(int game_id) {
		String query = "SELECT game_id FROM GAME_TABLE WHERE game_id=?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, game_id);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
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
	 *
	 * @param word
	 *            The word
	 * @return Boolean true if word already exist, false if not, null if error
	 */
	public Boolean wordAlreadyExistsInValidWordTable(String word) {
		String query = "SELECT word FROM VALID_WORD_TABLE WHERE word=?";
		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, word);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
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
	 *
	 * @param word_id
	 *            The game id
	 * @return Boolean true if word_id taken, false if not, null if error
	 */
	public Boolean wordIDAlreadyExistsInValidWordTable(int word_id) {
		String query = "SELECT word_id FROM VALID_WORD_TABLE WHERE word_id=?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, word_id);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
	}

	/**
	 * updating bonuses_used for a word in the valid_word_table
	 *
	 * @param updateWord
	 *            the word that needs an update on its bonuses_used value
	 * @param newValue
	 *            the new value needs to be updated
	 * @return true if operation is successful, false if word doesn't exist; null if
	 *         database error
	 */
	public Boolean updateValidWordBonusesUsed(String updateWord, int newValue) {
		try {
			Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
			if (!wordAlreadyExistsInValidWordTable(updateWord)) {
				return false;
			} else {
				String sqlQuery = "UPDATE VALID_WORD_TABLE SET bonuses_used = ? WHERE word = ?;";
				PreparedStatement ps = connection.prepareStatement(sqlQuery);
				ps.setInt(1, newValue);
				ps.setString(2, updateWord);
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
	 * updating whether a valid word is used as an extension
	 *
	 * @param updateWord
	 *            the word that needs an update on its is_extension value
	 * @param newValue
	 *            the new boolean that gets updated to the database
	 * @return true if successful; false if word doesn't exist in the
	 *         valid_word_table; null if database error
	 */
	public Boolean updateValidWordIsExtension(String updateWord, boolean newValue) {
		try {
			Connection connection = DriverManager.getConnection(dbAddress, dbUser, dbPass);
			if (!wordAlreadyExistsInValidWordTable(updateWord)) {
				return false;
			} else {
				String sqlQuery = "UPDATE VALID_WORD_TABLE SET is_extension = ? WHERE word = ?;";
				PreparedStatement ps = connection.prepareStatement(sqlQuery);
				ps.setBoolean(1, newValue);
				ps.setString(2, updateWord);
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
	 * Get average game score of all games played throughout the history by a
	 * specified team
	 *
	 * @param teamname
	 *            a String indicates the teamname, either "green" or "gold"
	 * @return Double indicates the average game score; null if teamname is neither
	 *         "green" nor "gold"
	 */
	public Double getTotalGameScoreAverageForTeam(String teamname) {
		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			String query = null;
			if (teamname.equalsIgnoreCase("green")) {
				query = "SELECT SUM(green_team_score) FROM GAME_TABLE;";
			} else if (teamname.equalsIgnoreCase("gold")) {
				query = "SELECT SUM(gold_team_score) FROM GAME_TABLE;";
			} else {
				return null;
			}
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			Double sum = result.getDouble(1);
			int gameCount = this.getGameTableCount();
			return sum / gameCount;

		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}

	}

	/**
	 * get cumulative game score of a team
	 *
	 * @return an integer indicating the cumulative score of specified team
	 */
	public int getTeamCumulative(String teamname) {
		String query = "SELECT cumulative_game_score FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, teamname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				int teamCumulative = rs.getInt("cumulative_game_score");
				return teamCumulative;
			} else {
				return 0;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return 0;
		}
	}

	/**
	 * get highest word score of a team
	 *
	 * @return an integer indicating the highest word score of specified team
	 */
	public int getHighestWordScore(String teamname) {
		String query = "SELECT highest_word_score FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, teamname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				int teamHighestW = rs.getInt("highest_word_score");
				return teamHighestW;
			} else {
				return 0;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return 0;
		}
	}

	/**
	 * get highest game session score of a team
	 *
	 * @return an integer indicating the cumulative score of specified team
	 */
	public int getHighestGameSessionScore(String teamname) {
		String query = "SELECT highest_game_session_score FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, teamname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				int teamHighest = rs.getInt("highest_game_session_score");
				return teamHighest;
			} else {
				return 0;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return 0;
		}
	}

	/**
	 * get team win count
	 *
	 * @return an integer indicating the win count of specified team
	 */
	public int getTeamWinCount(String teamname) {
		String query = "SELECT win_count FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, teamname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				int teamWin = rs.getInt("win_count");
				return teamWin;
			} else {
				return 0;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return 0;
		}
	}

	/**
	 * get team lose count
	 *
	 * @return an integer indicating the lose count of specified team
	 */
	public int getTeamLoseCount(String teamname) {
		String query = "SELECT lose_count FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, teamname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				int teamLose = rs.getInt("lose_count");
				return teamLose;
			} else {
				return 0;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return 0;
		}
	}

	/**
	 * get team tie count
	 *
	 * @return an integer indicating the tie count of specified team
	 */
	public int getTeamTieCount(String teamname) {
		String query = "SELECT tie_count FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, teamname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				;
				int teamTie = rs.getInt("tie_count");
				return teamTie;
			} else {
				return 0;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return 0;
		}
	}

	/**
	 * get longest word of a team
	 *
	 * @return a String indicating the Longest Word of specified team
	 */
	public String getTeamLongestWord(String teamname) {
		String query = "SELECT longest_word FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, teamname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				String teamLongWord = rs.getString("longest_word");
				return teamLongWord;
			} else {
				return null;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
	}

	/**
	 * get number of bonuses a team used
	 *
	 * @return an integer indicating the number of bonuses a specified team used
	 */
	public int getTeamBonuses(String teamname) {
		String query = "SELECT bonuses FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, teamname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				int teamBonuses = rs.getInt("bonuses");
				return teamBonuses;
			} else {
				return 0;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return 0;
		}
	}

	/**
	 * get the number of dirty word attempt of a team
	 *
	 * @return an integer indicating the number of times a specified team tried
	 *         using a "dirty word"
	 */
	public int getTeamDirtyWordAttempt(String teamname) {
		String query = "SELECT dirty_word FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, teamname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				int teamDirtyCount = rs.getInt("dirty_word");
				return teamDirtyCount;
			} else {
				return 0;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return 0;
		}
	}

	/*
	 * Updates cumulative_game_score for TEAM_TABLE
	 */
	public void updateTeamCumulative(String tname, int points) {
		String query = "SELECT * FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, tname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				rs.updateInt("cumulative_game_score", (rs.getInt("cumulative_game_score") + points));
				rs.updateRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Checks if new score is better than old score Updates as required
	 */
	public void updateHighestWordScore(String tname, int points) {
		String query = "SELECT * FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, tname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("highest_word_score") < points) {
					rs.updateInt("highest_word_score", points);
					rs.updateRow();
				} else {

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Checks if new score is better than old score Updates as required
	 */
	public void updateHighestGameSessionScore(String tname, int points) {
		String query = "SELECT * FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, tname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("highest_game_session_score") < points) {
					rs.updateInt("highest_game_session_score", points);
					rs.updateRow();
				} else {

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Winning team itterate win count tname = winning team
	 */
	public void updateWin(String tname) {
		String query = "SELECT * FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, tname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				rs.updateInt("win_count", (rs.getInt("win_count") + 1));
				rs.updateRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * losing team itterate lose count tname = losing team
	 */
	public void updateLose(String tname) {
		String query = "SELECT * FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, tname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				rs.updateInt("lose_count", (rs.getInt("lose_count") + 1));
				rs.updateRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Tie teams itterate tie count tname = tie team 1 tnname = tie team 2
	 */
	public void updateTie(String tname, String tnname) {
		String query = "SELECT * FROM TEAM_TABLE WHERE team_name = ?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			for (int i = 0; i < 2; i++) {
				if (i == 0) {
					preparedStmt.setString(1, tname);
					ResultSet rs = preparedStmt.executeQuery();
					if (rs.next()) {
						rs.updateInt("tie_count", (rs.getInt("tie_count") + 1));
						rs.updateRow();
					}
				} else {
					preparedStmt.setString(1, tnname);
					ResultSet rs = preparedStmt.executeQuery();
					if (rs.next()) {
						rs.updateInt("tie_count", (rs.getInt("tie_count") + 1));
						rs.updateRow();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Use if word played is new longest word
	 */
	public void updateTeamLongestWord(String tname, String word) {
		String query = "SELECT * FROM TEAM_TABLE WHERE team_name=?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, tname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				String currentLong = rs.getString("longest_word");
				if (currentLong.length() <= word.length()) {
					rs.updateString("longest_word", word);
					rs.updateRow();
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public void updateTeamBonusCount(String tname, int bonus) {
		String query = "SELECT * FROM TEAM_TABLE WHERE team_name=?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, tname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				rs.updateInt("bonuses", (rs.getInt("bonuses") + bonus));
				rs.updateRow();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/*
	 * Call everytime a bad word is played or not I don't think we actually use this
	 * column
	 */
	public void updateDirtWordCount(String tname) {
		String query = "SELECT * FROM TEAM_TABLE WHERE team_name=?";

		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement preparedStmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStmt.setString(1, tname);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				rs.updateInt("dirty_word", (rs.getInt("dirty_word") + 1));
				rs.updateRow();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/*
	 * The test does not assume that the underlying popuation variances are equal
	 * and it uses approximated degrees of freedom computed from the sample data to
	 * compute the p-value.
	 */
	public Double tTestResults() {
		String green = "SELECT green_team_score FROM GAME_TABLE";
		String gold = "SELECT gold_team_score FROM GAME_TABLE";
		try (Connection con = DriverManager.getConnection(dbAddress, dbUser, dbPass)) {
			PreparedStatement greenPS = con.prepareStatement(green);
			PreparedStatement goldPS = con.prepareStatement(gold);
			ResultSet greenRS = greenPS.executeQuery();
			ResultSet goldRS = goldPS.executeQuery();
			ArrayList<Double> greenAR = new ArrayList<>();
			ArrayList<Double> goldAR = new ArrayList<>();
			while (greenRS.next()) {
				greenAR.add(greenRS.getDouble(1));
			}
			while (goldRS.next()) {
				goldAR.add(goldRS.getDouble(1));
			}

			double[] greenARR = greenAR.stream().mapToDouble(Double::doubleValue).toArray();
			double[] goldARR = goldAR.stream().mapToDouble(Double::doubleValue).toArray();

			TTest tt = new TTest();
			double pval = tt.tTest(greenARR, goldARR);
			return 1.0 - pval;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
