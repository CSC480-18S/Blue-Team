package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;
import java.util.Arrays;

import java.lang.NullPointerException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class updatePlayerCumulativeTest {
    QueryClass qc = new QueryClass();

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test  // was 0 before test and 1 after, passes
    public void t0() {
        try {
            qc.updatePlayerCumulative("tenbergen", 1);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test  // fails
    public void t1() {
        try {
            qc.updatePlayerCumulative("北", 2);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test  // fails
    public void t2() {
        try {
            qc.updatePlayerCumulative("", 3);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test  // fails
    public void t3() {
        try {
            qc.updatePlayerCumulative("a", 2);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // t4 comp error , passes

    @Test  // fails
    public void t5() {
        try {
            qc.updatePlayerCumulative("JosephusJosephusJosephus", 10);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test  // fails, doesn't crash
    public void t6() {
        try {
            qc.updatePlayerCumulative(null, 10);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test  // 5 before, 38 after, passes
    public void t7() {
        try {
            qc.updatePlayerCumulative("tenbergen", 33);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // t8 unrealistic, no point in testing

    @Test  // passes
    public void t9() {
        try {
            qc.updatePlayerCumulative("tenbergen", 0);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }   

    // t10 doesn't compile, passes

    @Test  // functionally correct was 38 ended at 5, fails
    public void t11() {
        try {
            qc.updatePlayerCumulative("tenbergen", -33);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }   

    @Test  // functionally correct was 5 ended at 4, fails
    public void t12() {
        try {
            qc.updatePlayerCumulative("tenbergen", -1);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    } 

    // t13 unrealistic #, no point in testing

    @Test  // passes
    public void t14() {
        try {
            qc.updatePlayerCumulative("tenberge", 10);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    } 

    @Test  // fails
    public void t16() {
        try {
            qc.updatePlayerCumulative("典", 10);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    } 


    @Test  // passes was 4 ended 36
    public void t23() {
        try {
            qc.updatePlayerCumulative("tenbergen", 32);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    } 

    @Test  // fails, functionally correct but spec deviation
    public void t28() {
        try {
            qc.updatePlayerCumulative("tenbergen", -32);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    } 

    // rest of cases were skipped cause point was made, unrealistic, or compilation error
}