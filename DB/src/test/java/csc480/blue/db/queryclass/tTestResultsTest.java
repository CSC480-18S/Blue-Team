package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class tTestResultsTest {
    QueryClass qc = new QueryClass();

    // positive test
    @Test 
    public void t0() {
        Double actual = qc.tTestResults();
        Double expected = 1-0.1008 ;
        assertEquals(actual, expected); 
    }
}