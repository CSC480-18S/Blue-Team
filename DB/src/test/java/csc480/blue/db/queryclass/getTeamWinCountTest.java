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

public class getTeamWinCountTest {
    QueryClass qc = new QueryClass();
    int actual = 0;
    int expected = 0;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test 
    public void t0() {
        actual = qc.getTeamWinCount("gold");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        actual = qc.getTeamWinCount("green");
        expected = 3;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        actual = qc.getTeamWinCount("goLd");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        actual = qc.getTeamWinCount("GreEn");
        expected = 3;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        actual = qc.getTeamWinCount("GOLD");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        actual = qc.getTeamWinCount("GREEN");
        expected = 3;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        actual = qc.getTeamWinCount("gol");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        actual = qc.getTeamWinCount("goL");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        actual = qc.getTeamWinCount("GreE");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        actual = qc.getTeamWinCount("GREENN");
        expected = 0;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    // t19 won't compile, passes

    @Test 
    public void t20() {
        actual = qc.getTeamWinCount("");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        actual = qc.getTeamWinCount("c");
        expected = 0;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}