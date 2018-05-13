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

public class wordIDAlreadyExistsInValidWordTableTest {
    QueryClass qc = new QueryClass();

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test // passes
    public void t0() {
        assertTrue(qc.wordIDAlreadyExistsInValidWordTable(1));
    }

    @Test // passes, there arent 33 games in dummy data
    public void t1() {
        assertTrue(!qc.wordIDAlreadyExistsInValidWordTable(33));
    }

    // t2 is unrealistic

    @Test // passes
    public void t3() {
        assertTrue(qc.wordIDAlreadyExistsInValidWordTable(0));
    }

    // t4, compilation error, passes

    @Test // functionality correct, spec dev., fails
    public void t5() {
        assertTrue(qc.wordIDAlreadyExistsInValidWordTable(-33));
    }

    @Test // functionality correct, spec dev., fails
    public void t6() {
        assertTrue(qc.wordIDAlreadyExistsInValidWordTable(-1));
    }

    // t7 is unrealistic

    @Test // passes :D
    public void t8() {
        assertTrue(qc.wordIDAlreadyExistsInValidWordTable(2));
    }

    @Test // passes :D, aren't 32 games, returns expected
    public void t9() {
        assertTrue(!qc.wordIDAlreadyExistsInValidWordTable(32));
    }

    // t10, we get the point... lol
    // t11-12 unrealistic input

    @Test // functionality correct, spec dev., fails
    public void t13() {
        assertTrue(qc.wordIDAlreadyExistsInValidWordTable(-34));
    }

    // t14-16, we get the point... fails for that Eq. Class
    // t17-18 are unrealistic input
}