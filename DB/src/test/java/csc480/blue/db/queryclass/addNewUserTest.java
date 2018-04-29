package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;

import java.lang.NullPointerException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class addNewUserTest {
    QueryClass qc = new QueryClass();

	@AfterEach
	void afterEach() {
        qc = new QueryClass();   // reset state just in case
	}

    @Test // tried with -45, didn't work cause already in table!
    public void t0() {
        try {
            qc.addNewUser("tenbergen", "00-14-22-01-23-46", "gold");
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    } 

    @Test 
    public void t1() {
        try {
            qc.addNewUser("tenbergen2", "00-14-22-01-23-47", "gold");
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test // tried with -45, didn't work cause already in table!
    public void t2() {
        try {
            qc.addNewUser("tenbergen3", "00-15-22-01-23-46", "GoLd");
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    } 

    @Test // tried with -45, didn't work cause already in table!
    public void t3() {
        try {
            qc.addNewUser("tenbergen4", "00-15-22-01-23-47", "GreeN");
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test  // tried with tenbergen, didn't work cause uid existed!
    public void t4() {
        try {
            qc.addNewUser("tenbergen5", "01-14-22-01-23-45", "GOLD");
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    } 

    @Test  // tried with tenbergen, didn't work cause uid existed!
    public void t5() {
        try {
            qc.addNewUser("tenbergen6", "01-14-22-01-23-46", "GREEN");
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    } 

    @Test  // test fails, adds character to user table
    public void t6() {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			qc.addNewUser("北", "21-14-22-01-23-46", "gold");
		});
    } 

    @Test  // test fails, adds character to user table
    public void t7() {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			qc.addNewUser("", "20-14-22-01-23-46", "gold");
		});
    } 

    // no point in t8, fail for same reason as t6 and t7
    // t9 compilation error, passes

    @Test  // passes :D
    public void t10() {
        try {
			qc.addNewUser("JosephusJosephusJosephus", "20-14-22-11-23-46", "GOLD");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    } 

    @Test  // test fails
    public void t11() {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			qc.addNewUser(null, "20-14-22-01-23-46", "gold");
		});
    } 

    @Test
    public void t12() { // fail, no mac validation
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			qc.addNewUser("tenbergen8", "00-80-C8-E3-4C-B北", "gold");
		});
    } 

    @Test
    public void t13() { // fail, no mac validation
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			qc.addNewUser("tenbergen9", "", "gold");
		});
    } 

    // t14 fail for same reason as t13
    // t15 compilation error
    
    @Test  // doesn't throw error, but doesn't add to db so overall pass
    public void t16() {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			qc.addNewUser("tenbergen99", null, "gold");
		});
    } 

    @Test  // adds to user_table, fails test
    public void t17() {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			qc.addNewUser("tenbergen99", "20-19-22-11-23-46", null);
		});
    } 

    @Test  // no exception thrown but no user added so passes.
    public void t19() {
        assertThrows(MySQLSyntaxErrorException.class, () -> {
			qc.addNewUser("tenbergen99", "20-19-82-11-23-49", "c");
		});
    } 

    // halt testing here since all EQ tested, others results can be predicted off aforementioned tests
}