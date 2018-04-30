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

public class findUserTest {
    QueryClass qc = new QueryClass();
    String[] expected = new String[2];
    String[] actual = new String[2];


	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
        expected = null;
        actual = null;
	}

    @Test 
    public void t0() {
        actual = qc.findUser("00-14-22-01-23-45");
        expected = new String[] {"kim", "green"};
        assertArrayEquals(actual, expected);
    }

    @Test // no error thrown
    public void t1() {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			actual = qc.findUser("00-80-C8-E3-4C-B北");
            expected = null;
            assertEquals(actual, expected);
		});
    }

    @Test // no error thrown, but functionally correct
    public void t2() {
        assertThrows(Exception.class, () -> {
			actual = qc.findUser("");
            expected = null;
            assertEquals(actual, expected);
		});
    }

    @Test // no error thrown, but functionally correct
    public void t3() {
        assertThrows(Exception.class, () -> {
            actual = qc.findUser("b");
            expected = null;
            assertEquals(actual, expected);
        });
    }

    // @Test // compilation error, passes
    // public void t4() {
    //     actual = qc.findUser(21);
    //     expected = null;
    //     assertEquals(actual, expected);
    //    });
    // }

    @Test // no error thrown, but functionally correct
    public void t5() {
        assertThrows(Exception.class, () -> {
            actual = qc.findUser(null);
            expected = null;
            assertEquals(actual, expected);
        });
    }

    @Test // no error thrown, but functionally correct
    public void t6() {
        assertThrows(Exception.class, () -> {
            actual = qc.findUser("00-14-22-01-23-44");
            expected = null;
            assertEquals(actual, expected);
        });
    }

    @Test // no error thrown, but functionally correct
    public void t7() {
        assertThrows(Exception.class, () -> {
            actual = qc.findUser("00-14-22-01-23-46");
            expected = null;
            assertEquals(actual, expected);
        });
    }

    @Test // no error thrown, but functionally correct
    public void t8() {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
            actual = qc.findUser("00-80-C8-E3-4C-B典");
            expected = null;
            assertEquals(actual, expected);
        });
    }

    // t9 is just excessive, point made about non-utf char

    @Test // no error thrown, but functionally correct
    public void t10() {
        assertThrows(Exception.class, () -> {
            actual = qc.findUser("bb");
            expected = null;
            assertEquals(actual, expected);
        });
    }

    // t11 & t12 wouldn't compile, no point in writing
}