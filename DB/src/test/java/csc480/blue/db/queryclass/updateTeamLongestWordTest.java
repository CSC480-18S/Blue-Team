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

public class updateTeamLongestWordTest {
    QueryClass qc = new QueryClass();
    String actual = null;
    String prior = null;
    String expected = null;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
        prior = null;
        actual = null;
        expected = null;
	}

    @Test  
    public void t0() { // mavrick > cat
        prior = qc.getTeamLongestWord("gold");
        qc.updateTeamLongestWord("gold", "cat");
        expected = prior;
        actual = qc.getTeamLongestWord("gold");
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() { // computers >  dog
        prior = qc.getTeamLongestWord("green");
        qc.updateTeamLongestWord("green", "DOG");
        actual = qc.getTeamLongestWord("green");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() { // mavrick > do
        prior = qc.getTeamLongestWord("GoLd");
        qc.updateTeamLongestWord("GoLd", "DO");
        actual = qc.getTeamLongestWord("GoLd");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() { // computers >  dog
        prior = qc.getTeamLongestWord("GreeN");
        qc.updateTeamLongestWord("GreEn", "DOG");
        actual = qc.getTeamLongestWord("GreEn");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t4() { // mavrick > dog
        prior = qc.getTeamLongestWord("GOLD");
        qc.updateTeamLongestWord("GOLD", "DOG");
        actual = qc.getTeamLongestWord("GOLD");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test 
    public void t5() { // computers >  dog
        prior = qc.getTeamLongestWord("GREEN");
        qc.updateTeamLongestWord("GREEN", "DOG");
        actual = qc.getTeamLongestWord("GREEN");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test // FnF
    public void t6() {
        prior = qc.getTeamLongestWord(null);
        qc.updateTeamLongestWord(null, "cat");
        actual = qc.getTeamLongestWord(null);
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test // FnF
    public void t7() {
        prior = qc.getTeamLongestWord("");
        qc.updateTeamLongestWord("", "cat");
        actual = qc.getTeamLongestWord("");
        expected = prior;
        assertEquals(actual, expected);
    }

    @Test // FnF
    public void t8() {
        prior = qc.getTeamLongestWord("c");
        qc.updateTeamLongestWord("c", "cat");
        expected = prior;
        actual = qc.getTeamLongestWord("c");
        assertEquals(actual, expected);
    }

    // t9 doesn't compile, passes

    // @Test // FnF, chinese throws rest of cases off so commented out
    // public void t10() {
    //     prior = qc.getTeamLongestWord("GOLD");
    //     qc.updateTeamLongestWord("GOLD", "典Josephus");
    //     expected = "典Josephus";
    //     actual = qc.getTeamLongestWord("GOLD");
    //     assertEquals(actual, expected);
    // }

    @Test // Fnf
    public void t11() {
        prior = qc.getTeamLongestWord("GOLD");
        qc.updateTeamLongestWord("GOLD", "");
        expected = prior;
        actual = qc.getTeamLongestWord("GOLD");
        assertEquals(actual, expected);
    }


    @Test // FnF
    public void t12() {
        prior = qc.getTeamLongestWord("GOLD");
        qc.updateTeamLongestWord("GOLD", "a");
        expected = prior;
        actual = qc.getTeamLongestWord("GOLD");
        assertEquals(actual, expected);
    }

    // t13 doesn't compile

    @Test // passes, varchar(15) limit!
    public void t14() {
        prior = qc.getTeamLongestWord("GOLD");
        qc.updateTeamLongestWord("GOLD", "JosephusJosephus");
        expected = prior;
        actual = qc.getTeamLongestWord("GOLD");
        assertEquals(actual, expected);
    }

    @Test // passes
    public void t15() {
        assertThrows(NullPointerException.class, () -> {
            prior = qc.getTeamLongestWord("GOLD");
            qc.updateTeamLongestWord("GOLD", null);
            expected = prior;
            actual = qc.getTeamLongestWord("GOLD");
            assertEquals(actual, expected);
        });
    }

    @Test // passes because word doesn't exist
    public void t16() {
        prior = qc.getTeamLongestWord("gol");
        qc.updateTeamLongestWord("gol", "cat");
        expected = prior;
        actual = qc.getTeamLongestWord("gol");
        assertEquals(actual, expected);
    }

    @Test // passes because word doesn't exist
    public void t19() {
        prior = qc.getTeamLongestWord("greenn");
        qc.updateTeamLongestWord("greenn", "cat");
        expected = prior;
        actual = qc.getTeamLongestWord("greenn");
        assertEquals(actual, expected);
    }

    
    @Test 
    public void t22() {
        prior = qc.getTeamLongestWord("GreE");
        qc.updateTeamLongestWord("GreE", "cat");
        expected = prior;
        actual = qc.getTeamLongestWord("GreE");
        assertEquals(actual, expected);
    }

    @Test 
    public void t25() {
        prior = qc.getTeamLongestWord("GOLDD");
        qc.updateTeamLongestWord("GOLDD", "cat");
        expected = prior;
        actual = qc.getTeamLongestWord("GOLDD");
        assertEquals(actual, expected);
    }

    // no point in testing string boundaries as they're just non-existent team
    // and we already estab. how the system reacts to them from prev. cases

    // t29-30 don't compile, pass
    // t31-32 unreasonably high input no point in testing

    @Test 
    public void t33() {
        prior = qc.getTeamLongestWord("GOLD");
        qc.updateTeamLongestWord("GOLD", "DO");
        expected = prior;
        actual = qc.getTeamLongestWord("GOLD");
        assertEquals(actual, expected);
    }

    @Test 
    public void t34() {
        prior = qc.getTeamLongestWord("GOLD");
        qc.updateTeamLongestWord("GOLD", "DOGG");
        expected = prior;
        actual = qc.getTeamLongestWord("GOLD");
        assertEquals(actual, expected);
    }

    // @Test // FnF, chinese throws rest of cases off so commented out
    // public void t35() {
    //     prior = qc.getTeamLongestWord("GOLD");
    //     qc.updateTeamLongestWord("GOLD", "典Josephu");
    //     expected = prior;
    //     actual = qc.getTeamLongestWord("GOLD");
    //     assertEquals(actual, expected);
    // }

    // t36-37 redundant, point proven
    // t38-39 don't compile
    // t40 redundant

    // passes, varchar(15) limit
    public void t41() {
        prior = qc.getTeamLongestWord("GOLD");
        qc.updateTeamLongestWord("GOLD", "JosephusJosephuss");
        expected = prior;
        actual = qc.getTeamLongestWord("GOLD");
        assertEquals(actual, expected);
    }
}