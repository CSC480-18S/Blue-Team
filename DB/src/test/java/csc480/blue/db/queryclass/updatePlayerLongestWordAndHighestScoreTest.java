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

public class updatePlayerLongestWordAndHighestScoreTest {
    QueryClass qc = new QueryClass();

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test // works, both were blank by end "cat" and 19 were  set
    public void t0() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("tenbergen", "cat", 19);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // fails, functionally correct, spec deviation tho
    public void t1() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("北", "cat", 19);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails, functionally correct, spec deviation tho
    public void t2() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("", "cat", 19);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails, functionally correct, spec deviation tho
    public void t3() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("a", "cat", 19);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // t4 compilation error, passes

    @Test // fails, functionally correct, spec deviation tho
    public void t5() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("JosephusJosephusJosephus", "cat", 19);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails, functionally correct, spec deviation tho
    public void t6() {
        try {
            qc.updatePlayerLongestWordAndHighestScore(null, "cat", 19);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // passes, updates from cat to DOG
    public void t7() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("tenbergen", "DOG", 19);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // fails
    public void t8() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("tenbergen", "典Josephus", 19);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // fails
    public void t9() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("tenbergen", "", 19);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // fails
    public void t10() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("tenbergen", "a", 19);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // t11 compilation error, passes

    @Test // fails, functionally correct, spec deviation
    public void t12() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("tenbergen", "JosephusJosephus", 19);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails
    public void t13() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("tenbergen", null, 19);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, doesn't updates fields cause they're smaller
    public void t14() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("tenbergen", "cat", 0);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, updates 19 -> 200
    public void t16() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("tenbergen", "cat", 200);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // t17 compilation error, passes

    @Test // fails
    public void t18() {
        try {
            qc.updatePlayerLongestWordAndHighestScore("tenbergen", "cat", -5);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // t19 compilation error, passes
    // t20 no point in testing similar to t18
    // t21-29 no point in testing, proven to not crash during invalid user
    // t30-40 no point in testing, proven to not have word validation
    // rest of cases are unrealistic or already proven to not behave accordingly
    // for negative input, proven to update when larger and not when smaller
}