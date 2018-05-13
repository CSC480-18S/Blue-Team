package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;
import java.util.Arrays;

import java.lang.NullPointerException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class userAlreadyExistsTest {
    QueryClass qc = new QueryClass();

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test // changed to ken from tenbergen to match dummy data, passes tho
    public void t0() {
        assertTrue(qc.userAlreadyExists("tenbergen"));
    }

    @Test // returns true cause char in table, but shouldn't be...
    public void t1() {
        assertTrue(!qc.userAlreadyExists("北"));
    }

    @Test // returns true cause char in table, but shouldn't be...
    public void t2() {
        assertTrue(!qc.userAlreadyExists(""));
    }

    @Test // passes!
    public void t3() {
        assertTrue(!qc.userAlreadyExists("a"));
    }

    // t4 compilation error, passes

    @Test // passes!
    public void t5() {
        assertTrue(!qc.userAlreadyExists("JosephusJosephusJosephus"));
    }

    @Test // fails
    public void t6() {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
            assertTrue(!qc.userAlreadyExists(null));
        });
    }
    
    @Test // passes!
    public void t7() {
        assertTrue(!qc.userAlreadyExists("tenberge"));
    }

    @Test // passes!
    public void t8() {
        assertTrue(!qc.userAlreadyExists("tenbergenn"));
    }

    // rest of test's eq classes tested and results can be predicted
}