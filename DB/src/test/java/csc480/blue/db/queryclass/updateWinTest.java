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

public class updateWinTest {
    QueryClass qc = new QueryClass();
    int actual = 0;
    int expected = 0;
    int prior = 0;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test 
    public void t0() {
        prior = qc.getTeamWinCount("gold");
        qc.updateWin("gold");
        actual = qc.getTeamWinCount("gold");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        prior = qc.getTeamWinCount("green");
        qc.updateWin("green");
        actual = qc.getTeamWinCount("green");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        prior = qc.getTeamWinCount("goLd");
        qc.updateWin("goLd");
        actual = qc.getTeamWinCount("goLd");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        prior = qc.getTeamWinCount("GreEn");
        qc.updateWin("GreEn");
        actual = qc.getTeamWinCount("GreEn");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        prior = qc.getTeamWinCount("GOLD");
        qc.updateWin("GOLD");
        actual = qc.getTeamWinCount("GOLD");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        prior = qc.getTeamWinCount("GREEN");
        qc.updateWin("GREEN");
        actual = qc.getTeamWinCount("GREEN");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        prior = qc.getTeamWinCount("gol");
        qc.updateWin("gol");
        actual = qc.getTeamWinCount("gol");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        prior = qc.getTeamWinCount("goL");
        qc.updateWin("goL");
        actual = qc.getTeamWinCount("goL");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        prior = qc.getTeamWinCount("GreE");
        qc.updateWin("GreE");
        actual = qc.getTeamWinCount("GreE");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        prior = qc.getTeamWinCount("GREENN");
        qc.updateWin("GREENN");
        actual = qc.getTeamWinCount("GREENN");
        expected = prior;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    @Test  // FnF
    public void t19() {
        prior = qc.getTeamWinCount(null);
        qc.updateWin(null);
        actual = qc.getTeamWinCount(null);
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t20() {
        prior = qc.getTeamWinCount("");
        qc.updateWin("");
        actual = qc.getTeamWinCount("");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        prior = qc.getTeamWinCount("c");
        qc.updateWin("c");
        actual = qc.getTeamWinCount("c");
        expected = prior;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}