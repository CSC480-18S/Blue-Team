package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;

import java.lang.Exception;
import java.lang.NullPointerException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class getMacAddressByUserNameTest {
    QueryClass qc = new QueryClass();
    String actual = "";
    String expected = "";

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
        actual = null;
        expected = null;
	}

    @Test 
    public void t0() {
        actual = qc.getMacAddressByUserName("christian");
        expected = "00-11-19-00-20-43";
        assertEquals(actual, expected);
    }

    @Test 
    public void t1() {
        actual = qc.getMacAddressByUserName("北");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t2() {
        actual = qc.getMacAddressByUserName("");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t3() {
        actual = qc.getMacAddressByUserName("a");
        expected = null;
        assertEquals(actual, expected);
    }

    // t4 doesn't compile, passes  

    @Test 
    public void t5() {
        actual = qc.getMacAddressByUserName("JosephusJosephusJosephus");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t6() {
        actual = qc.getMacAddressByUserName(null);
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t7() {
        actual = qc.getMacAddressByUserName("christia");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t8() {
        actual = qc.getMacAddressByUserName("christiann");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t9() {
        actual = qc.getMacAddressByUserName("典");
        expected = null;
        assertEquals(actual, expected);
    }
   

    @Test 
    public void t10() {
        actual = qc.getMacAddressByUserName("书");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t11() {
        actual = qc.getMacAddressByUserName("aa");
        expected = null;
        assertEquals(actual, expected);
    }

    // t12-13 don't compile, pass

    public void t14() {
        actual = qc.getMacAddressByUserName("JosephusJosephusJosephu");
        expected = null;
        assertEquals(actual, expected);
    }

    @Test 
    public void t15() {
        actual = qc.getMacAddressByUserName("JosephusJosephusJosephuss");
        expected = null;
        assertEquals(actual, expected);
    }
}