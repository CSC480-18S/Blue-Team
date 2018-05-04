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

public class updateTeamCumulativeTest {
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
        prior = qc.getTeamCumulative("gold");
        qc.updateTeamCumulative("gold", 1);
        expected = prior + 1;
        actual = qc.getTeamCumulative("gold");
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        prior = qc.getTeamCumulative("green");
        qc.updateTeamCumulative("green", 33);
        actual = qc.getTeamCumulative("green");
        expected = prior + 33;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        prior = qc.getTeamCumulative("GoLd");
        qc.updateTeamCumulative("GoLd", 2);
        actual = qc.getTeamCumulative("GoLd");
        expected = prior + 2;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        prior = qc.getTeamCumulative("GreEn");
        qc.updateTeamCumulative("GreEn", 32);
        actual = qc.getTeamCumulative("GreEn");
        expected = prior + 32;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() {
        prior = qc.getTeamCumulative("GOLD");
        qc.updateTeamCumulative("GOLD", 34);
        actual = qc.getTeamCumulative("GOLD");
        expected = prior + 34;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() {
        prior = qc.getTeamCumulative("GREEN");
        qc.updateTeamCumulative("GREEN", 10);
        actual = qc.getTeamCumulative("GREEN");
        expected = prior + 10;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        prior = qc.getTeamCumulative(null);
        qc.updateTeamCumulative(null, 10);
        actual = qc.getTeamCumulative(null);
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t7() {
        prior = qc.getTeamCumulative("");
        qc.updateTeamCumulative("", 10);
        actual = qc.getTeamCumulative("");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t8() {
        prior = qc.getTeamCumulative("c");
        qc.updateTeamCumulative("c", 10);
        expected = prior;
        actual = qc.getTeamCumulative("c");
        assertEquals(actual, expected);
    }

    // t9 doesn't compile, passes
    // t10 unreasonably high input, no point intesting

    @Test // 
    public void t11() {
        prior = qc.getTeamCumulative("green");
        qc.updateTeamCumulative("green", 0);
        expected = prior + 0;
        actual = qc.getTeamCumulative("green");
        assertEquals(actual, expected);
    }

    // t12 doesn't compile, passes

    @Test // fails
    public void t13() {
        prior = qc.getTeamCumulative("green");
        qc.updateTeamCumulative("green", -33);
        expected = prior + -33;
        actual = qc.getTeamCumulative("green");
        assertEquals(actual, expected);
    }

    @Test // fails
    public void t14() {
        prior = qc.getTeamCumulative("green");
        qc.updateTeamCumulative("green", -1);
        expected = prior + -1;
        actual = qc.getTeamCumulative("green");
        assertEquals(actual, expected);
    }

    // t15 unreasonably high input, no point in testing

    @Test // passes because word doesn't exist
    public void t16() {
        prior = qc.getTeamCumulative("gol");
        qc.updateTeamCumulative("gol", 10);
        expected = prior;
        actual = qc.getTeamCumulative("gol");
        assertEquals(actual, expected);
    }

    @Test // passes because word doesn't exist
    public void t19() {
        prior = qc.getTeamCumulative("greenn");
        qc.updateTeamCumulative("greenn", 10);
        expected = prior;
        actual = qc.getTeamCumulative("greenn");
        assertEquals(actual, expected);
    }

    // t17-19, extraneous, point proven about non-utf8 characters and non-existent words
    // t20-21 dont compile
    
    @Test 
    public void t22() {
        prior = qc.getTeamCumulative("GreE");
        qc.updateTeamCumulative("GreE", 10);
        expected = prior;
        actual = qc.getTeamCumulative("GreE");
        assertEquals(actual, expected);
    }

    @Test 
    public void t25() {
        prior = qc.getTeamCumulative("GOLDD");
        qc.updateTeamCumulative("GOLDD", 10);
        expected = prior;
        actual = qc.getTeamCumulative("GOLDD");
        assertEquals(actual, expected);
    }

    // no point in testing string boundaries as they're just non-existent team
    // and we already estab. how the system reacts to them from prev. cases

    // t29-30 don't compile, pass
    // t31-32 unreasonably high input no point in testing

    @Test 
    public void t33() {
        prior = qc.getTeamCumulative("green");
        qc.updateTeamCumulative("green", -34);
        expected = prior + -34;
        actual = qc.getTeamCumulative("green");
        assertEquals(actual, expected);
    }

    @Test 
    public void t34() {
        prior = qc.getTeamCumulative("green");
        qc.updateTeamCumulative("green", -32);
        expected = prior + -32;
        actual = qc.getTeamCumulative("green");
        assertEquals(actual, expected);
    }

    @Test 
    public void t35() {
        prior = qc.getTeamCumulative("green");
        qc.updateTeamCumulative("green", -2);
        expected = prior + -2;
        actual = qc.getTeamCumulative("green");
        assertEquals(actual, expected);
    }

    // t36-37 don't compile, pass
}