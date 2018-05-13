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

public class getTeamCumulativeTest {
    QueryClass qc = new QueryClass();
    int actual = 0;
    int expected = 0;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test 
    public void t0() {
        actual = qc.getTeamCumulative("gold");
        expected = 273;
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        actual = qc.getTeamCumulative("green");
        expected = 511;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        actual = qc.getTeamCumulative("goLd");
        expected = 273;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        actual = qc.getTeamCumulative("GreEn");
        expected = 511;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        actual = qc.getTeamCumulative("GOLD");
        expected = 273;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        actual = qc.getTeamCumulative("GREEN");
        expected = 511;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        actual = qc.getTeamCumulative("gol");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t10() {
        actual = qc.getTeamCumulative("goL");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t12() {
        actual = qc.getTeamCumulative("GreE");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t18() {
        actual = qc.getTeamCumulative("GREENN");
        expected = 0;
        assertEquals(actual, expected);
    }
   
    // cases not listed b/w 6-18 not mentioned cause point made by mentioned cases
    // b/w 6-18
    
    // t19 won't compile, passes

    @Test 
    public void t20() {
        actual = qc.getTeamCumulative("");
        expected = 0;
        assertEquals(actual, expected);
    }

    @Test 
    public void t21() {
        actual = qc.getTeamCumulative("c");
        expected = 0;
        assertEquals(actual, expected);
    }

    // rest of cases won't compile or are redundant
}