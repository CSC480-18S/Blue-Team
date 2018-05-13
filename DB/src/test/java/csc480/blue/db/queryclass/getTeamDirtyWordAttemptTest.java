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

public class getTeamDirtyWordAttemptTest {
    QueryClass qc = new QueryClass();
    int actual = 0;
    int expected = 0;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test 
    public void t0() {
        actual = qc.getTeamDirtyWordAttempt("gold");
        expected = 10;
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        actual = qc.getTeamDirtyWordAttempt("green");
        expected = 5;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        actual = qc.getTeamDirtyWordAttempt("goLd");
        expected = 10;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        actual = qc.getTeamDirtyWordAttempt("GreEn");
        expected = 5;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        actual = qc.getTeamDirtyWordAttempt("GOLD");
        expected = 10;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        actual = qc.getTeamDirtyWordAttempt("GREEN");
        expected = 5;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        actual = qc.getTeamDirtyWordAttempt("gol");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        actual = qc.getTeamDirtyWordAttempt("goL");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        actual = qc.getTeamDirtyWordAttempt("GreE");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        actual = qc.getTeamDirtyWordAttempt("GREENN");
        expected = 0;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    // t19 won't compile, passes

    @Test 
    public void t20() {
        actual = qc.getTeamDirtyWordAttempt("");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        actual = qc.getTeamDirtyWordAttempt("c");
        expected = 0;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}