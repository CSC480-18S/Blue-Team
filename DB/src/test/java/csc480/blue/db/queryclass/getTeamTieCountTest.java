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

public class getTeamTieCountTest {
    QueryClass qc = new QueryClass();
    int actual = 0;
    int expected = 0;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
        actual = 0;
        expected = 0;
	}

    @Test 
    public void t0() {
        actual = qc.getTeamTieCount("gold");
        expected = 1;
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        actual = qc.getTeamTieCount("green");
        expected = 1;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        actual = qc.getTeamTieCount("goLd");
        expected = 1;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        actual = qc.getTeamTieCount("GreEn");
        expected = 1;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        actual = qc.getTeamTieCount("GOLD");
        expected = 1;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        actual = qc.getTeamTieCount("GREEN");
        expected = 1;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        actual = qc.getTeamTieCount("gol");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        actual = qc.getTeamTieCount("goL");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        actual = qc.getTeamTieCount("GreE");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        actual = qc.getTeamTieCount("GREENN");
        expected = 0;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    // t19 won't compile, passes

    @Test 
    public void t20() {
        actual = qc.getTeamTieCount("");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        actual = qc.getTeamTieCount("c");
        expected = 0;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}