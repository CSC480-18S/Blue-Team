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

public class updateDirtWordCountTest {
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
        prior = qc.getTeamDirtyWordAttempt("gold");
        qc.updateDirtWordCount("gold");
        actual = qc.getTeamDirtyWordAttempt("gold");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        prior = qc.getTeamDirtyWordAttempt("green");
        qc.updateDirtWordCount("green");
        actual = qc.getTeamDirtyWordAttempt("green");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        prior = qc.getTeamDirtyWordAttempt("goLd");
        qc.updateDirtWordCount("goLd");
        actual = qc.getTeamDirtyWordAttempt("goLd");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        prior = qc.getTeamDirtyWordAttempt("GreEn");
        qc.updateDirtWordCount("GreEn");
        actual = qc.getTeamDirtyWordAttempt("GreEn");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        prior = qc.getTeamDirtyWordAttempt("GOLD");
        qc.updateDirtWordCount("GOLD");
        actual = qc.getTeamDirtyWordAttempt("GOLD");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        prior = qc.getTeamDirtyWordAttempt("GREEN");
        qc.updateDirtWordCount("GREEN");
        actual = qc.getTeamDirtyWordAttempt("GREEN");
        expected = ++prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        prior = qc.getTeamDirtyWordAttempt("gol");
        qc.updateDirtWordCount("gol");
        actual = qc.getTeamDirtyWordAttempt("gol");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        prior = qc.getTeamDirtyWordAttempt("goL");
        qc.updateDirtWordCount("goL");
        actual = qc.getTeamDirtyWordAttempt("goL");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        prior = qc.getTeamDirtyWordAttempt("GreE");
        qc.updateDirtWordCount("GreE");
        actual = qc.getTeamDirtyWordAttempt("GreE");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        prior = qc.getTeamDirtyWordAttempt("GREENN");
        qc.updateDirtWordCount("GREENN");
        actual = qc.getTeamDirtyWordAttempt("GREENN");
        expected = prior;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    @Test  // FnF
    public void t19() {
        prior = qc.getTeamDirtyWordAttempt(null);
        qc.updateDirtWordCount(null);
        actual = qc.getTeamDirtyWordAttempt(null);
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t20() {
        prior = qc.getTeamDirtyWordAttempt("");
        qc.updateDirtWordCount("");
        actual = qc.getTeamDirtyWordAttempt("");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        prior = qc.getTeamDirtyWordAttempt("c");
        qc.updateDirtWordCount("c");
        actual = qc.getTeamDirtyWordAttempt("c");
        expected = prior;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}