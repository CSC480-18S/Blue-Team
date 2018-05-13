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

public class getTopPlayersByTeamTest {
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
        actual = qc.getTopPlayersByTeam(1, "green");
        assertArrayEquals(actual[0], expected[0]);
    } 


    @Test   
    public void t1() {
        expected = new String[33][3];
        actual = qc.getTopPlayersByTeam(33, "green");
        expected[0][0] = "kim";
        expected[0][1] = "green";
        expected[0][2] = "200";

        expected[1][0] = "dor";
        expected[1][1] = "green";
        expected[1][2] = "161";

        expected[2][0] = "yingying";
        expected[2][1] = "green";
        expected[2][2] = "150";

        for (int i = 3; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    // t2 not worth testing, will never have that many players

    @Test
    public void t3() {
        actual = qc.getTopPlayersByTeam(0, "green");
        expected = new String[0][3];
        assertArrayEquals(actual, expected);
    } 

    // successfully crashes, program won't compile 
    // @Test
    // public void t4() {
    //     actual = qc.getTopPlayersByTeam(null, "green");
    //     expected = new String[0][3];
    //     assertArrayEquals(actual, expected);
    // } 

    @Test  // fails, doesn't throw right exceptions
    public void t5() throws MySQLSyntaxErrorException {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			actual = qc.getTopPlayersByTeam(-33, "green");
            assertEquals(actual, null);
		});
    } 

    // no point testing t6 & t7 will fail for same reason as t5

    @Test   
    public void t8() {
        expected = new String[33][3];
        actual = qc.getTopPlayersByTeam(33, "gold");
        expected[0][0] = "ken";
        expected[0][1] = "gold";
        expected[0][2] = "150";

        expected[1][0] = "christian";
        expected[1][1] = "gold";
        expected[1][2] = "92";

        expected[2][0] = "john";
        expected[2][1] = "gold";
        expected[2][2] = "31";

        for (int i = 3; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    @Test   
    public void t9() {
        expected = new String[33][3];
        actual = qc.getTopPlayersByTeam(33, "GoLd");
        expected[0][0] = "ken";
        expected[0][1] = "gold";
        expected[0][2] = "150";

        expected[1][0] = "christian";
        expected[1][1] = "gold";
        expected[1][2] = "92";

        expected[2][0] = "john";
        expected[2][1] = "gold";
        expected[2][2] = "31";

        for (int i = 3; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    @Test   
    public void t10() {
        expected = new String[33][3];
        actual = qc.getTopPlayersByTeam(33, "GreeN");
        expected[0][0] = "kim";
        expected[0][1] = "green";
        expected[0][2] = "200";

        expected[1][0] = "dor";
        expected[1][1] = "green";
        expected[1][2] = "161";

        expected[2][0] = "yingying";
        expected[2][1] = "green";
        expected[2][2] = "150";

        for (int i = 3; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    @Test   
    public void t11() {
        expected = new String[33][3];
        actual = qc.getTopPlayersByTeam(33, "GOLD");
        expected[0][0] = "ken";
        expected[0][1] = "gold";
        expected[0][2] = "150";

        expected[1][0] = "christian";
        expected[1][1] = "gold";
        expected[1][2] = "92";

        expected[2][0] = "john";
        expected[2][1] = "gold";
        expected[2][2] = "31";

        for (int i = 3; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    @Test
    public void t12() {
        expected = new String[33][3];
        actual = qc.getTopPlayersByTeam(33, "GREEN");
        expected[0][0] = "kim";
        expected[0][1] = "green";
        expected[0][2] = "200";

        expected[1][0] = "dor";
        expected[1][1] = "green";
        expected[1][2] = "161";

        expected[2][0] = "yingying";
        expected[2][1] = "green";
        expected[2][2] = "150";

        for (int i = 3; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    @Test  // fails, doesn't throw right exceptions
    public void t13() throws MySQLSyntaxErrorException {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			actual = qc.getTopPlayersByTeam(33, null);
            assertEquals(actual, null);
		});
    } 

    @Test  
    public void t14() {
        actual = qc.getTopPlayersByTeam(33, "");
        expected = new String[33][3];
        for (int i = 0; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    @Test  
    public void t15() {
        actual = qc.getTopPlayersByTeam(33, "c");
        expected = new String[33][3];
        for (int i = 0; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    // t16 is a compilation error

    @Test
    public void t17() {
        expected = new String[2][3];
        expected[0][0] = "ken";
        expected[0][1] = "gold";
        expected[0][2] = "150";

        expected[1][0] = "christian";
        expected[1][1] = "gold";
        expected[1][2] = "92";
        actual = qc.getTopPlayersByTeam(2, "gold");
        assertArrayEquals(actual[0], expected[0]);
        assertArrayEquals(actual[1], expected[1]);
    } 

    // t18-19 tested by t1
    // t20-21 unreasonable user #
    // t22-26 fails for same reason as t5 no point in testing

    // used t33 and t36 as representatives for non-tested { t27-t39 \ {t33, t36} }

    @Test  
    public void t33() {
        actual = qc.getTopPlayersByTeam(33, "GreE");
        expected = new String[33][3];
        for (int i = 0; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    @Test  
    public void t36() {
        actual = qc.getTopPlayersByTeam(33, "GOLDD");
        expected = new String[33][3];
        for (int i = 0; i < 33; i++) {
            expected[i][0] = null;
            expected[i][1] = null;
            expected[i][2] = null;
        }

        for (int i = 0; i < 33; i++)
            assertArrayEquals(actual[i], expected[i]);
    } 

    // t39 no point same as t15
    // t40-41 compilation error

}