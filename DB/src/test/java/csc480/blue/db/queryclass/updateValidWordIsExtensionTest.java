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

public class updateValidWordIsExtensionTest {
    QueryClass qc = new QueryClass();

    // ran once during initial test run
    // qc.addNewToValidWordTable("cat", 20, 1, false, 0);
    // qc.addNewToValidWordTable("DOG", 0, 11, true, 1);

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test // works, changed from 0 to 1
    public void t0() {
        try {
            assertTrue(qc.updateValidWordIsExtension("cat", true));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // works, changed from 1 to 33
    public void t1() {
        try {
            assertTrue(qc.updateValidWordIsExtension("DOG", false));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // fails, correct functionality dev from spec
    public void t2() {
        try {
            assertTrue(qc.updateValidWordIsExtension("典Josephus", true));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails, correct functionality dev from spec
    public void t3() {
        try {
            assertTrue(qc.updateValidWordIsExtension("", true));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails, correct functionality dev from spec
    public void t4() {
        try {
            assertTrue(qc.updateValidWordIsExtension("a", true));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // t5 compilation error, passes

    @Test // fails, correct functionality dev from spec
    public void t6() {
        try {
            assertTrue(qc.updateValidWordIsExtension("JosephusJosephus", true));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails, correct functionality dev from spec
    public void t7() {
        try {
            assertTrue(qc.updateValidWordIsExtension(null, true));
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // t8-12 dont compile

    // @Test // fails, correct functionality dev from spec
    // public void t9() {
    //     try {
    //         assertTrue(!qc.updateValidWordIsExtension("DOG", "true"));
    //     } catch (Exception e) {
    //         assertTrue(true);
    //     }
    // }


    // @Test // passes
    // public void t11() {
    //     try {
    //         assertTrue(qc.updateValidWordIsExtension("DOG", 1));
    //     } catch (Exception e) {
    //         assertTrue(true);
    //     }
    // }

    @Test // passes because word doesn't exist
    public void t13() {
        try {
            assertTrue(!qc.updateValidWordIsExtension("ca", true));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes because word doesn't exist
    public void t14() {
        try {
            assertTrue(!qc.updateValidWordIsExtension("catt", true));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes because word doesn't exist
    public void t15() {
        try {
            assertTrue(!qc.updateValidWordIsExtension("DO", true));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes because word doesn't exist
    public void t16() {
        try {
            assertTrue(!qc.updateValidWordIsExtension("DOGG", true));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // t17-19, extraneous, point proven about non-utf8 characters and non-existent words
    // t20-21 dont compile
    
    @Test // fails, right functionality, but spec deviation
    public void t22() {
        try {
            assertTrue(qc.updateValidWordIsExtension("JosephusJosephu", true));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // since t22 fails, obvs. t23 fails
    // t24-29 don't compile
}