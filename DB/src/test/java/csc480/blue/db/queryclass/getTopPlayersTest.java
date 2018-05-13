package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;
import java.util.Arrays;

import java.lang.NullPointerException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class getTopPlayersTest {
    QueryClass qc = new QueryClass();
    String[][] actual = null;
    String[][] expected = null;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
		actual = null;
		expected = null;
	}

    @Test
    public void t0() {
        expected = new String[1][3];
        expected[0][0] = "kim";
        expected[0][1] = "green";
        expected[0][2] = "200";
        actual = qc.getTopPlayers(1);
        assertArrayEquals(actual[0], expected[0]);
    } 


    @Test   
    public void t1() {
        expected = new String[33][3];
        actual = qc.getTopPlayers(33);
        expected[0][0] = "kim";
        expected[0][1] = "green";
        expected[0][2] = "200";

        expected[1][0] = "dor";
        expected[1][1] = "green";
        expected[1][2] = "161";

        expected[2][0] = "ken";
        expected[2][1] = "gold";
        expected[2][2] = "150";

        expected[3][0] = "yingying";
        expected[3][1] = "green";
        expected[3][2] = "150";

        expected[4][0] = "christian";
        expected[4][1] = "gold";
        expected[4][2] = "92";

        expected[5][0] = "john";
        expected[5][1] = "gold";
        expected[5][2] = "31";

        for (int i = 6; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    //  t2 not worth testing, will never have that many players

    @Test
    public void t3() {
        actual = qc.getTopPlayers(0);
        expected = new String[0][3];
        assertArrayEquals(actual, expected);
    } 

    // successfully crashes, program won't compile 
    // @Test
    // public void t4() {
    //     actual = qc.getTopPlayers(null);
    //     expected = new String[0][3];
    //     assertArrayEquals(actual, expected);
    // } 

    @Test  // fails, doesn't throw right exceptions
    public void t5() throws MySQLSyntaxErrorException {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			actual = qc.getTopPlayers(-33);
            assertEquals(actual, null);
		});
    } 

    // no point testing t6 & t7 will fail for same reason as t5

    @Test
    public void t8() {
        expected = new String[2][3];
        expected[0][0] = "kim";
        expected[0][1] = "green";
        expected[0][2] = "200";

        expected[1][0] = "dor";
        expected[1][1] = "green";
        expected[1][2] = "161";
        actual = qc.getTopPlayers(2);
        assertArrayEquals(actual[0], expected[0]);
        assertArrayEquals(actual[1], expected[1]);
    } 

    // t9-10 tested by t1
    // t11-12 unreasonable user #
    // t13-17 fails for same reason as t5 no point in testing
}