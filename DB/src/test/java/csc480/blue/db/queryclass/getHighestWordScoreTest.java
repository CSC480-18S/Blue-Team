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

public class getHighestWordScoreTest {
    QueryClass qc = new QueryClass();
    int actual = 0;
    int expected = 0;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test 
    public void t0() {
        actual = qc.getHighestWordScore("gold");
        expected = 33;
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        actual = qc.getHighestWordScore("green");
        expected = 30;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        actual = qc.getHighestWordScore("goLd");
        expected = 33;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        actual = qc.getHighestWordScore("GreEn");
        expected = 30;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        actual = qc.getHighestWordScore("GOLD");
        expected = 33;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        actual = qc.getHighestWordScore("GREEN");
        expected = 30;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        actual = qc.getHighestWordScore("gol");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        actual = qc.getHighestWordScore("goL");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        actual = qc.getHighestWordScore("GreE");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        actual = qc.getHighestWordScore("GREENN");
        expected = 0;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    // t19 won't compile, passes

    @Test 
    public void t20() {
        actual = qc.getHighestWordScore("");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        actual = qc.getHighestWordScore("c");
        expected = 0;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}