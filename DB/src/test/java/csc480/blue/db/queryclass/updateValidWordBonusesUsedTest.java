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

public class updateValidWordBonusesUsedTest {
    QueryClass qc = new QueryClass();

    // ran once during initial test run
    // qc.addNewToValidWordTable("cat", 20, 1, true, 0);
    // qc.addNewToValidWordTable("DOG", 0, 11, false, 1);

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test // works, changed from 0 to 1
    public void t0() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("cat", 1));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // works, changed from 1 to 33
    public void t1() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("DOG", 33));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // fails, correct functionality dev from spec
    public void t2() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("典Josephus", 33));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails, correct functionality dev from spec
    public void t3() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("", 33));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails, correct functionality dev from spec
    public void t4() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("a", 33));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // t5 compilation error, passes

    @Test // fails, correct functionality dev from spec
    public void t6() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("JosephusJosephus", 33));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails, correct functionality dev from spec
    public void t7() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed(null, 33));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // t8 unreasonable input, no point in testing
    // t9 compilation error, passes

    @Test // fails, correct functionality dev from spec
    public void t10() {
        try {
            assertTrue(!qc.updateValidWordBonusesUsed("cat", -33));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // passes
    public void t11() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("cat", 0));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails, correct functionality dev from spec
    public void t12() {
        try {
            assertTrue(!qc.updateValidWordBonusesUsed("cat", -1));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // t13, unreasonable input, no point in testing

    @Test // passes, doesn't update cause DNE
    public void t14() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("ca", 33));
        } catch (Exception e) {
            assertTrue(true);
        }
    }    

    // t15-16 are extraneous, get insight out of t11 and t14, t3, t4, & t6
    
    @Test // passes, doesn't update cause DNE
    public void t17() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("DOGG", 33));
        } catch (Exception e) {
            assertTrue(true);
        }
    } 

    // found out that table & function supports non-utf8 chars, as well
    // as fn behavior for non-existent entries, so t18-20 are unnecessary

    // t21-22 are compilation errors
    
    @Test // fails, correct functionality dev from spec
    public void t23() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("JosephusJosephu", 33));
        } catch (Exception e) {
            assertTrue(true);
        }
    } 

    // t24 will fail cause t23 fails
    // t25-26 are unrealistic inputs

    @Test // fails, correct functionality, dev. from spec
    public void t27() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("JosephusJosephu", -34));
        } catch (Exception e) {
            assertTrue(true);
        }
    } 

    // t28 is extraneous, same problems addressed in t27, t29, and others

    @Test // fails, correct functionality, dev. from spec
    public void t29() {
        try {
            assertTrue(qc.updateValidWordBonusesUsed("JosephusJosephu", -2));
        } catch (Exception e) {
            assertTrue(true);
        }
    } 

    // t30-31 are unreasonable bonus values, no point in testing
}