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

/* temporarily made method public just to test */
public class updateUsernameInPlayerTeamTest {
    QueryClass qc = new QueryClass();
    boolean actual = false;

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test // fail after first run, because tenbergen44 would exist at that point,
          // PASSES first time run prior to tenbergen44 existed in table
    public void t0() {
        try {
            actual = qc.updateUsernameInPlayerTeam("tenbergen", "tenbergen44");
            assertEquals(true, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t1() {
        try {
            actual = qc.updateUsernameInPlayerTeam("北", "tenbergen45");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t2() {
        try {
            actual = qc.updateUsernameInPlayerTeam("", "tenbergen46");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t3() {
        try {
            actual = qc.updateUsernameInPlayerTeam("a", "tenbergen47");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // t4 doesn't compile, passes  

    @Test // passes, shouldn't work due to varchar(15) limit and doesn't
    public void t5() {
        try {
            actual = qc.updateUsernameInPlayerTeam("JosephusJosephusJosephus", "radnmo3");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t6() {
        try {
            actual = qc.updateUsernameInPlayerTeam(null, "tenbergen48");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t7() {
        try {
            actual = qc.updateUsernameInPlayerTeam("tenberge", "tenbergen50");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t8() {
        try {
            actual = qc.updateUsernameInPlayerTeam("tenbergenn", "tenbergen51");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t9() {
        try {
            actual = qc.updateUsernameInPlayerTeam("典", "tenbergen52");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t10() {
        try {
            actual = qc.updateUsernameInPlayerTeam("书", "tenbergen53");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t11() {
        try {
            actual = qc.updateUsernameInPlayerTeam("aa", "tenbergen54");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    // t12-13 don't compile, pass

    @Test // passes
    public void t14() {
        try {
            actual = qc.updateUsernameInPlayerTeam("JosephusJosephusJosephu", "tenbergen55");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // over varchar(15) limit, passses
    public void t15() {
        try {
            actual = qc.updateUsernameInPlayerTeam("JosephusJosephusJosephu", "tenbergen56");
            assertEquals(false, actual);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

   // Don't need to test new_uid's boundaries, same equiv. classes and same code used
   // on old_uid as new_uid can predict behavior accurately
}