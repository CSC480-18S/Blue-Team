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

public class changeUserNameTest {
    QueryClass qc = new QueryClass();
    boolean actual = false;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test // passes, successfully changed to dorqa in DB, wont pass on re-rerun cause
          // dorqa would already exist in db at that point.., failute on rerun not concern
    public void t0() {
        try {
            actual = qc.changeUserName("tenbergen", "00-14-22-01-23-46", "dorqa");
            assertEquals(true, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t1() {
        try {
            actual = qc.changeUserName("北", "21-14-22-01-23-46", "radnmo");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t2() {
        try {
            actual = qc.changeUserName("", "20-14-22-01-23-46", "radnmo2");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t3() {
        try {
            actual = qc.changeUserName("a", "00-14-22-01-23-47", "radnmo2");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // t4 doesn't compile, passes  

    @Test // passes, shouldn't work due to varchar(15) limit and doesn't
    public void t5() {
        try {
            actual = qc.changeUserName("JosephusJosephusJosephus", "01-14-22-01-23-45", "radnmo3");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t6() {
        try {
            actual = qc.changeUserName(null, "00-14-22-01-23-45", "tenbergen2");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t7() {
        try {
            actual = qc.changeUserName("tenbergen", "00-80-C8-E3-4C-B北", "random");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t8() {
        try {
            actual = qc.changeUserName("tenbergen", "", "random");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t9() {
        try {
            actual = qc.changeUserName("tenbergen3", "b", "random3");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t10() {
        try {
            actual = qc.changeUserName("tenbergen3", "b", "random3");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t11() {
        try {
            actual = qc.changeUserName("tenbergen3", null, "北2");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t12() {
        try {
            actual = qc.changeUserName("tenbergen3", null, "北2");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t13() {
        try {
            actual = qc.changeUserName("tenbergen3", null, "");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t14() {
        try {
            actual = qc.changeUserName("tenbergen3", null, "a");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // t15 doesn't compile, passes

    @Test // passes, shouldn't work due to varchar(15) limit and doesn't
    public void t16() {
        try {
            actual = qc.changeUserName("tenbergen4", "81-14-54-01-23-45", "JosephusJosephusJosephus2");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t17() {
        try {
            actual = qc.changeUserName("tenbergen4", "81-14-54-01-23-45", null);
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, uid DNE
    public void t18() {
        try {
            actual = qc.changeUserName("tenberge", "81-14-94-01-23-45", "g0lde");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, uid, DNE
    public void t19() {
        try {
            actual = qc.changeUserName("tenbergenn", "81-14-94-01-23-45", "g0lde");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, uid, DNE
    public void t20() {
        try {
            actual = qc.changeUserName("典", "82-14-94-01-23-45", "g0lde");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, uid, DNE
    public void t21() {
        try {
            actual = qc.changeUserName("书", "89-14-94-01-23-45", "g0lde");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, uid, DNE
    public void t22() {
        try {
            actual = qc.changeUserName("aa", "89-14-44-41-23-45", "g0lde");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // t23-24 don't compile, pass
    // t25-26 excessive, we already estab how system treats long and non-existent uid input

    @Test // passes, mac DNE
    public void t27() {
        try {
            actual = qc.changeUserName("tenbergen", "00-14-22-01-23-44", "g0lde");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, mac DNE for user
    public void t28() {
        try {
            actual = qc.changeUserName("tenbergen", "00-14-22-01-23-47", "g0lde");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, mac DNE
    public void t29() {
        try {
            actual = qc.changeUserName("tenbergen", "00-80-C8-E3-4C-B典", "g0lde");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, mac DNE for user
    public void t30() {
        try {
            actual = qc.changeUserName("tenbergen", "00-80-C8-E3-4C-B书", "g0lde");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes, mac DNE for user
    public void t31() {
        try {
            actual = qc.changeUserName("tenbergen", "bb", "g0lde");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // t32-33 dont compile, pass
    // no point in testing rest of tests, same equivalance classes
    // used for old_uid and same code being executed on them, can predict output

    @Test // extra test to check not changing valid info cause of existing uid
    public void t50() {
        try {
            actual = qc.changeUserName("dorqa", "00-14-22-01-23-46", "radnmo");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}