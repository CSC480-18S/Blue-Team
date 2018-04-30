package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;

import java.lang.NullPointerException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;


import java.lang.Exception;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class dirtyWordPlayedTest {
    QueryClass qc = new QueryClass();

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test // passes
    public void t0() {
        try {
            qc.dirtyWordPlayed("cat");
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // passes
    public void t1() {
        try {
            qc.dirtyWordPlayed("DOG");
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // fails
    public void t2() {
        try {
            qc.dirtyWordPlayed("典Josephus");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails
    public void t3() {
        try {
            qc.dirtyWordPlayed("");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails
    public void t4() {
        try {
            qc.dirtyWordPlayed("a");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // t5 is a compilationerror, passes

    @Test // fails
    public void t6() {
        try {
            qc.dirtyWordPlayed("JosephusJosephus");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails
    public void t7() {
        try {
            qc.dirtyWordPlayed(null);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails
    public void t8() {
        try {
            qc.dirtyWordPlayed("ca");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails
    public void t9() {
        try {
            qc.dirtyWordPlayed("catt");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails
    public void t10() {
        try {
            qc.dirtyWordPlayed("DO");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails
    public void t13() {
        try {
            qc.dirtyWordPlayed("典Josephuss");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test // fails
    public void t17() {
        try {
            qc.dirtyWordPlayed("JosephusJosephu");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    // rest of cases were skipped either cause point was already made
    // or would lead to compilation error
}