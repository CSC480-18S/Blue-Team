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

public class updateLoseTest {
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
        prior = qc.getTeamLoseCount("gold");
        qc.updateLose("gold");
        actual = qc.getTeamLoseCount("gold");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        prior = qc.getTeamLoseCount("green");
        qc.updateLose("green");
        actual = qc.getTeamLoseCount("green");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        prior = qc.getTeamLoseCount("goLd");
        qc.updateLose("goLd");
        actual = qc.getTeamLoseCount("goLd");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        prior = qc.getTeamLoseCount("GreEn");
        qc.updateLose("GreEn");
        actual = qc.getTeamLoseCount("GreEn");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        prior = qc.getTeamLoseCount("GOLD");
        qc.updateLose("GOLD");
        actual = qc.getTeamLoseCount("GOLD");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        prior = qc.getTeamLoseCount("GREEN");
        qc.updateLose("GREEN");
        actual = qc.getTeamLoseCount("GREEN");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        prior = qc.getTeamLoseCount("gol");
        qc.updateLose("gol");
        actual = qc.getTeamLoseCount("gol");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        prior = qc.getTeamLoseCount("goL");
        qc.updateLose("goL");
        actual = qc.getTeamLoseCount("goL");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        prior = qc.getTeamLoseCount("GreE");
        qc.updateLose("GreE");
        actual = qc.getTeamLoseCount("GreE");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        prior = qc.getTeamLoseCount("GREENN");
        qc.updateLose("GREENN");
        actual = qc.getTeamLoseCount("GREENN");
        expected = prior;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    @Test 
    public void t19() {
        prior = qc.getTeamLoseCount(null);
        qc.updateLose(null);
        actual = qc.getTeamLoseCount(null);
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t20() {
        prior = qc.getTeamLoseCount("");
        qc.updateLose("");
        actual = qc.getTeamLoseCount("");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        prior = qc.getTeamLoseCount("c");
        qc.updateLose("c");
        actual = qc.getTeamLoseCount("c");
        expected = prior;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}