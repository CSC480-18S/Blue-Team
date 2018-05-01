package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;

import java.lang.Exception;
import java.lang.NullPointerException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class getTotalGameScoreAverageForTeamTest {
    QueryClass qc = new QueryClass();
    Double actual = 0.0;
    Double expected = 0.0;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test 
    public void t0() {
        actual = qc.getTotalGameScoreAverageForTeam("gold");
        expected = 68.25;
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        actual = qc.getTotalGameScoreAverageForTeam("green");
        expected = 127.75;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        actual = qc.getTotalGameScoreAverageForTeam("goLd");
        expected = 68.25;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        actual = qc.getTotalGameScoreAverageForTeam("GreEn");
        expected = 127.75;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        actual = qc.getTotalGameScoreAverageForTeam("GOLD");
        expected = 68.25;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        actual = qc.getTotalGameScoreAverageForTeam("GREEN");
        expected = 127.75;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        actual = qc.getTotalGameScoreAverageForTeam("gol");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        actual = qc.getTotalGameScoreAverageForTeam("goL");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        actual = qc.getTotalGameScoreAverageForTeam("GreE");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        actual = qc.getTotalGameScoreAverageForTeam("GREENN");
        expected = null;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    @Test 
    public void t19() {
        assertThrows(NullPointerException.class, () -> {
            actual = qc.getTotalGameScoreAverageForTeam(null);
            expected = null;
            assertEquals(actual, expected);
        });
    }

    @Test 
    public void t20() {
        actual = qc.getTotalGameScoreAverageForTeam("");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        actual = qc.getTotalGameScoreAverageForTeam("c");
        expected = null;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}