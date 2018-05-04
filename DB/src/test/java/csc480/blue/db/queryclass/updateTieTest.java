package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class updateTieTest {
    QueryClass qc = new QueryClass();

    @Test
    public void t0() {
        int priorGreen = qc.getTeamTieCount("green");
        int priorGold = qc.getTeamTieCount("gold");
        qc.updateTie();
        int postGreen = qc.getTeamTieCount("green");
        int postGold = qc.getTeamTieCount("gold");
        assertEquals(++priorGold, postGold);
        assertEquals(++priorGreen, postGreen);
    }
}