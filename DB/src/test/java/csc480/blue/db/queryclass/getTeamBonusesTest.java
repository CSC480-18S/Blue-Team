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

public class getTeamBonusesTest {
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
        actual = qc.getTeamBonuses("gold");
        expected = 13;
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        actual = qc.getTeamBonuses("green");
        expected = 21;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        actual = qc.getTeamBonuses("goLd");
        expected = 13;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        actual = qc.getTeamBonuses("GreEn");
        expected = 21;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        actual = qc.getTeamBonuses("GOLD");
        expected = 13;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        actual = qc.getTeamBonuses("GREEN");
        expected = 21;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        actual = qc.getTeamBonuses("gol");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        actual = qc.getTeamBonuses("goL");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        actual = qc.getTeamBonuses("GreE");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        actual = qc.getTeamBonuses("GREENN");
        expected = 0;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    // t19 won't compile, passes

    @Test 
    public void t20() {
        actual = qc.getTeamBonuses("");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        actual = qc.getTeamBonuses("c");
        expected = 0;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}