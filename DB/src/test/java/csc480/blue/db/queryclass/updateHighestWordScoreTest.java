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

public class updateHighestWordScoreTest {
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
    public void t0() { // 34 > 1
        prior = qc.getHighestWordScore("gold");
        qc.updateHighestWordScore("gold", 1);
        expected = prior; 
        actual = qc.getHighestWordScore("gold");
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() { // 33 == 33
        prior = qc.getHighestWordScore("green");
        qc.updateHighestWordScore("green", 33);
        actual = qc.getHighestWordScore("green");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() { // 34 > 2
        prior = qc.getHighestWordScore("GoLd");
        qc.updateHighestWordScore("GoLd", 2);
        actual = qc.getHighestWordScore("GoLd");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() { // 33 > 32
        prior = qc.getHighestWordScore("GreEn");
        qc.updateHighestWordScore("GreEn", 32);
        actual = qc.getHighestWordScore("GreEn");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() { // 34 == 34
        prior = qc.getHighestWordScore("GOLD");
        qc.updateHighestWordScore("GOLD", 34);
        actual = qc.getHighestWordScore("GOLD");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() { // 33 > 10
        prior = qc.getHighestWordScore("GREEN");
        qc.updateHighestWordScore("GREEN", 10);
        actual = qc.getHighestWordScore("GREEN");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        prior = qc.getHighestWordScore(null);
        qc.updateHighestWordScore(null, 10);
        actual = qc.getHighestWordScore(null);
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t7() {
        prior = qc.getHighestWordScore("");
        qc.updateHighestWordScore("", 10);
        actual = qc.getHighestWordScore("");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t8() {
        prior = qc.getHighestWordScore("c");
        qc.updateHighestWordScore("c", 10);
        expected = prior;
        actual = qc.getHighestWordScore("c");
        assertEquals(actual, expected);
    }

    // t9 doesn't compile, passes
    // t10 unreasonably high input, no point intesting

    @Test // 
    public void t11() {
        prior = qc.getHighestWordScore("green");
        qc.updateHighestWordScore("green", 0);
        expected = prior + 0;
        actual = qc.getHighestWordScore("green");
        assertEquals(actual, expected);
    }

    // t12 doesn't compile, passes

    @Test // fails
    public void t13() {
        prior = qc.getHighestWordScore("green");
        qc.updateHighestWordScore("green", -33);
        expected = prior;
        actual = qc.getHighestWordScore("green");
        assertEquals(actual, expected);
    }

    @Test // fails
    public void t14() {
        prior = qc.getHighestWordScore("green");
        qc.updateHighestWordScore("green", -1);
        expected = prior;
        actual = qc.getHighestWordScore("green");
        assertEquals(actual, expected);
    }

    // t15 unreasonably high input, no point in testing

    @Test // passes because word doesn't exist
    public void t16() {
        prior = qc.getHighestWordScore("gol");
        qc.updateHighestWordScore("gol", 10);
        expected = prior;
        actual = qc.getHighestWordScore("gol");
        assertEquals(actual, expected);
    }

    @Test // passes because word doesn't exist
    public void t19() {
        prior = qc.getHighestWordScore("greenn");
        qc.updateHighestWordScore("greenn", 10);
        expected = prior;
        actual = qc.getHighestWordScore("greenn");
        assertEquals(actual, expected);
    }

    // t17-19, extraneous, point proven about non-utf8 characters and non-existent words
    // t20-21 dont compile
    
    @Test 
    public void t22() {
        prior = qc.getHighestWordScore("GreE");
        qc.updateHighestWordScore("GreE", 10);
        expected = prior;
        actual = qc.getHighestWordScore("GreE");
        assertEquals(actual, expected);
    }

    @Test 
    public void t25() {
        prior = qc.getHighestWordScore("GOLDD");
        qc.updateHighestWordScore("GOLDD", 10);
        expected = prior;
        actual = qc.getHighestWordScore("GOLDD");
        assertEquals(actual, expected);
    }

    // no point in testing string boundaries as they're just non-existent team
    // and we already estab. how the system reacts to them from prev. cases

    // t29-30 don't compile, pass
    // t31-32 unreasonably high input no point in testing

    @Test 
    public void t33() {
        prior = qc.getHighestWordScore("green");
        qc.updateHighestWordScore("green", -34);
        expected = prior;
        actual = qc.getHighestWordScore("green");
        assertEquals(actual, expected);
    }

    @Test 
    public void t34() {
        prior = qc.getHighestWordScore("green");
        qc.updateHighestWordScore("green", -32);
        expected = prior;
        actual = qc.getHighestWordScore("green");
        assertEquals(actual, expected);
    }

    @Test 
    public void t35() {
        prior = qc.getHighestWordScore("green");
        qc.updateHighestWordScore("green", -2);
        expected = prior;
        actual = qc.getHighestWordScore("green");
        assertEquals(actual, expected);
    }

    // t36-37 don't compile, pass

    // test that will actually check changing of value
    @Test 
    public void t36() { // 72 > 71
        prior = qc.getHighestWordScore("GreEn");
        qc.updateHighestWordScore("GreEn", 72);
        actual = qc.getHighestWordScore("GreEn"); 
        expected = 72; // prior + 1
        assertEquals(actual, expected);
    }
}