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

public class updateHighestGameSessionScoreTest {
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
    public void t0() { // 91 > 1
        prior = qc.getHighestGameSessionScore("gold");
        qc.updateHighestGameSessionScore("gold", 1);
        expected = prior; 
        actual = qc.getHighestGameSessionScore("gold");
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() { // 200 > 33
        prior = qc.getHighestGameSessionScore("green");
        qc.updateHighestGameSessionScore("green", 33);
        actual = qc.getHighestGameSessionScore("green");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() { // 91 > 2
        prior = qc.getHighestGameSessionScore("GoLd");
        qc.updateHighestGameSessionScore("GoLd", 2);
        actual = qc.getHighestGameSessionScore("GoLd");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() { // 200 >32
        prior = qc.getHighestGameSessionScore("GreEn");
        qc.updateHighestGameSessionScore("GreEn", 32);
        actual = qc.getHighestGameSessionScore("GreEn");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() { // 91 == 34
        prior = qc.getHighestGameSessionScore("GOLD");
        qc.updateHighestGameSessionScore("GOLD", 34);
        actual = qc.getHighestGameSessionScore("GOLD");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() { // 200 >10
        prior = qc.getHighestGameSessionScore("GREEN");
        qc.updateHighestGameSessionScore("GREEN", 10);
        actual = qc.getHighestGameSessionScore("GREEN");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        prior = qc.getHighestGameSessionScore(null);
        qc.updateHighestGameSessionScore(null, 10);
        actual = qc.getHighestGameSessionScore(null);
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t7() {
        prior = qc.getHighestGameSessionScore("");
        qc.updateHighestGameSessionScore("", 10);
        actual = qc.getHighestGameSessionScore("");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t8() {
        prior = qc.getHighestGameSessionScore("c");
        qc.updateHighestGameSessionScore("c", 10);
        expected = prior;
        actual = qc.getHighestGameSessionScore("c");
        assertEquals(actual, expected);
    }

    // t9 doesn't compile, passes
    // t10 unreasonably high input, no point intesting

    @Test // 
    public void t11() {
        prior = qc.getHighestGameSessionScore("green");
        qc.updateHighestGameSessionScore("green", 0);
        expected = prior + 0;
        actual = qc.getHighestGameSessionScore("green");
        assertEquals(actual, expected);
    }

    // t12 doesn't compile, passes

    @Test // fails
    public void t13() {
        prior = qc.getHighestGameSessionScore("green");
        qc.updateHighestGameSessionScore("green", -33);
        expected = prior;
        actual = qc.getHighestGameSessionScore("green");
        assertEquals(actual, expected);
    }

    @Test // fails
    public void t14() {
        prior = qc.getHighestGameSessionScore("green");
        qc.updateHighestGameSessionScore("green", -1);
        expected = prior;
        actual = qc.getHighestGameSessionScore("green");
        assertEquals(actual, expected);
    }

    // t15 unreasonably high input, no point in testing

    @Test // passes because GameSession doesn't exist
    public void t16() {
        prior = qc.getHighestGameSessionScore("gol");
        qc.updateHighestGameSessionScore("gol", 10);
        expected = prior;
        actual = qc.getHighestGameSessionScore("gol");
        assertEquals(actual, expected);
    }

    @Test // passes because team doesn't exist
    public void t19() {
        prior = qc.getHighestGameSessionScore("greenn");
        qc.updateHighestGameSessionScore("greenn", 10);
        expected = prior;
        actual = qc.getHighestGameSessionScore("greenn");
        assertEquals(actual, expected);
    }

    // t17-19, extraneous, point proven about non-utf8 characters and non-existent team
    // t20-21 dont compile
    
    @Test 
    public void t22() {
        prior = qc.getHighestGameSessionScore("GreE");
        qc.updateHighestGameSessionScore("GreE", 10);
        expected = prior;
        actual = qc.getHighestGameSessionScore("GreE");
        assertEquals(actual, expected);
    }

    @Test 
    public void t25() {
        prior = qc.getHighestGameSessionScore("GOLDD");
        qc.updateHighestGameSessionScore("GOLDD", 10);
        expected = prior;
        actual = qc.getHighestGameSessionScore("GOLDD");
        assertEquals(actual, expected);
    }

    // no point in testing string boundaries as they're just non-existent team
    // and we already estab. how the system reacts to them from prev. cases

    // t29-30 don't compile, pass
    // t31-32 unreasonably high input no point in testing

    @Test 
    public void t33() {
        prior = qc.getHighestGameSessionScore("green");
        qc.updateHighestGameSessionScore("green", -34);
        expected = prior;
        actual = qc.getHighestGameSessionScore("green");
        assertEquals(actual, expected);
    }

    @Test 
    public void t34() {
        prior = qc.getHighestGameSessionScore("green");
        qc.updateHighestGameSessionScore("green", -32);
        expected = prior;
        actual = qc.getHighestGameSessionScore("green");
        assertEquals(actual, expected);
    }

    @Test 
    public void t35() {
        prior = qc.getHighestGameSessionScore("green");
        qc.updateHighestGameSessionScore("green", -2);
        expected = prior;
        actual = qc.getHighestGameSessionScore("green");
        assertEquals(actual, expected);
    }

    // t36-37 don't compile, pass

    // test that will actually check changing of value
    @Test 
    public void t36() { // 201 > 200
        prior = qc.getHighestGameSessionScore("GreEn");
        qc.updateHighestGameSessionScore("GreEn", 201);
        actual = qc.getHighestGameSessionScore("GreEn"); 
        expected = 201; // prior + 1
        assertEquals(actual, expected);
    }
}