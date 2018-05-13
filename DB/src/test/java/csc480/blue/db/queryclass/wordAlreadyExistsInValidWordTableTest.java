package csc480.blue.db.queryclass;

import csc480.blue.db.QueryClass;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class wordAlreadyExistsInValidWordTableTest {
    QueryClass qc = new QueryClass();

    @Test
    public void t0() {
        assertTrue(qc.wordAlreadyExistsInValidWordTable("cat"));
    }

    @Test
    public void t1() {
        assertTrue(qc.wordAlreadyExistsInValidWordTable("DOG"));
    }

    @Test
    public void t2() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("典Josephus"));
    }

    @Test
    public void t3() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable(""));
    }

    @Test
    public void t4() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("a"));
    }

    // t5 doesn't compile, it passes

    @Test
    public void t6() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("JosephusJosephus"));
    }

    @Test
    public void t7() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable(null));
    }

    @Test
    public void t8() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("ca"));
    }

    @Test
    public void t9() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("catt"));
    }

    @Test
    public void t10() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("DO"));
    }

    @Test
    public void t11() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("DOGG"));
    }

    @Test
    public void t12() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("典Josephu"));
    }

    @Test
    public void t13() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("典Josephuss"));
    }

    @Test
    public void t14() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("a"));
    }

    @Test
    public void t15() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("aa"));
    }

    // t16-17 doesn't compile, they pass

    @Test
    public void t18() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("JosephusJosephu"));
    }

    @Test
    public void t19() {
        assertTrue(!qc.wordAlreadyExistsInValidWordTable("JosephusJosephuss"));
    }
}