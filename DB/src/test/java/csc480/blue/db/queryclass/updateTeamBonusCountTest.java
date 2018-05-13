package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;
import java.util.Arrays;

import java.lang.Exception;
import java.lang.NullPointerException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class updateTeamBonusCountTest {
    QueryClass qc = new QueryClass();
    int actual = 0;
    int prior = 0;
    int expected = 0;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
        prior = 0;
        actual = 0;
        expected = 0;
	}

    @Test  
    public void t0() {
        prior = qc.getTeamBonuses("gold");
        qc.updateTeamBonusCount("gold", 1);
        expected = prior + 1;
        actual = qc.getTeamBonuses("gold");
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        prior = qc.getTeamBonuses("green");
        qc.updateTeamBonusCount("green", 33);
        actual = qc.getTeamBonuses("green");
        expected = prior + 33;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        prior = qc.getTeamBonuses("GoLd");
        qc.updateTeamBonusCount("GoLd", 2);
        actual = qc.getTeamBonuses("GoLd");
        expected = prior + 2;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        prior = qc.getTeamBonuses("GreEn");
        qc.updateTeamBonusCount("GreEn", 32);
        actual = qc.getTeamBonuses("GreEn");
        expected = prior + 32;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        prior = qc.getTeamBonuses("GOLD");
        qc.updateTeamBonusCount("GOLD", 34);
        actual = qc.getTeamBonuses("GOLD");
        expected = prior + 34;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        prior = qc.getTeamBonuses("GREEN");
        qc.updateTeamBonusCount("GREEN", 10);
        actual = qc.getTeamBonuses("GREEN");
        expected = prior + 10;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        prior = qc.getTeamBonuses(null);
        qc.updateTeamBonusCount(null, 10);
        actual = qc.getTeamBonuses(null);
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t7() {
        prior = qc.getTeamBonuses("");
        qc.updateTeamBonusCount("", 10);
        actual = qc.getTeamBonuses("");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t8() {
        prior = qc.getTeamBonuses("c");
        qc.updateTeamBonusCount("c", 10);
        expected = prior;
        actual = qc.getTeamBonuses("c");
        assertEquals(actual, expected);
    }

    // t9 doesn't compile, passes
    // t10 unreasonably high input, no point intesting

    @Test // 
    public void t11() {
        prior = qc.getTeamBonuses("green");
        qc.updateTeamBonusCount("green", 0);
        expected = prior + 0;
        actual = qc.getTeamBonuses("green");
        assertEquals(actual, expected);
    }

    // t12 doesn't compile, passes

    @Test // fails
    public void t13() {
        prior = qc.getTeamBonuses("green");
        qc.updateTeamBonusCount("green", -33);
        expected = prior + -33;
        actual = qc.getTeamBonuses("green");
        assertEquals(actual, expected);
    }

    @Test // fails
    public void t14() {
        prior = qc.getTeamBonuses("green");
        qc.updateTeamBonusCount("green", -1);
        expected = prior + -1;
        actual = qc.getTeamBonuses("green");
        assertEquals(actual, expected);
    }

    // t15 unreasonably high input, no point in testing

    @Test // passes because word doesn't exist
    public void t16() {
        prior = qc.getTeamBonuses("gol");
        qc.updateTeamBonusCount("gol", 10);
        expected = prior;
        actual = qc.getTeamBonuses("gol");
        assertEquals(actual, expected);
    }

    @Test // passes because word doesn't exist
    public void t19() {
        prior = qc.getTeamBonuses("greenn");
        qc.updateTeamBonusCount("greenn", 10);
        expected = prior;
        actual = qc.getTeamBonuses("greenn");
        assertEquals(actual, expected);
    }

    // t17-19, extraneous, point proven about non-utf8 characters and non-existent words
    // t20-21 dont compile
    
    @Test 
    public void t22() {
        prior = qc.getTeamBonuses("GreE");
        qc.updateTeamBonusCount("GreE", 10);
        expected = prior;
        actual = qc.getTeamBonuses("GreE");
        assertEquals(actual, expected);
    }

    @Test 
    public void t25() {
        prior = qc.getTeamBonuses("GOLDD");
        qc.updateTeamBonusCount("GOLDD", 10);
        expected = prior;
        actual = qc.getTeamBonuses("GOLDD");
        assertEquals(actual, expected);
    }

    // no point in testing string boundaries as they're just non-existent team
    // and we already estab. how the system reacts to them from prev. cases

    // t29-30 don't compile, pass
    // t31-32 unreasonably high input no point in testing

    @Test 
    public void t33() {
        prior = qc.getTeamBonuses("green");
        qc.updateTeamBonusCount("green", -34);
        expected = prior + -34;
        actual = qc.getTeamBonuses("green");
        assertEquals(actual, expected);
    }

    @Test 
    public void t34() {
        prior = qc.getTeamBonuses("green");
        qc.updateTeamBonusCount("green", -32);
        expected = prior + -32;
        actual = qc.getTeamBonuses("green");
        assertEquals(actual, expected);
    }

    @Test 
    public void t35() {
        prior = qc.getTeamBonuses("green");
        qc.updateTeamBonusCount("green", -2);
        expected = prior + -2;
        actual = qc.getTeamBonuses("green");
        assertEquals(actual, expected);
    }

    // t36-37 don't compile, pass
}