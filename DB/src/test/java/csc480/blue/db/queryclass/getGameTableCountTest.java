package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class getGameTableCountTest {
    QueryClass qc = new QueryClass();

    @Test
    public void t0() {
        assertEquals(4, qc.getGameTableCount());
    }
}