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

public class getTeamLongestWordTest {
    QueryClass qc = new QueryClass();
    String actual = null;
    String expected = null;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test 
    public void t0() {
        actual = qc.getTeamLongestWord("gold");
        expected = "mavrick";
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        actual = qc.getTeamLongestWord("green");
        expected = "computers";
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        actual = qc.getTeamLongestWord("goLd");
        expected = "mavrick";
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        actual = qc.getTeamLongestWord("GreEn");
        expected = "computers";
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        actual = qc.getTeamLongestWord("GOLD");
        expected = "mavrick";
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        actual = qc.getTeamLongestWord("GREEN");
        expected = "computers";
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        actual = qc.getTeamLongestWord("gol");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        actual = qc.getTeamLongestWord("goL");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        actual = qc.getTeamLongestWord("GreE");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        actual = qc.getTeamLongestWord("GREENN");
        expected = null;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    // t19 won't compile, passes

    @Test 
    public void t20() {
        actual = qc.getTeamLongestWord("");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        actual = qc.getTeamLongestWord("c");
        expected = null;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}