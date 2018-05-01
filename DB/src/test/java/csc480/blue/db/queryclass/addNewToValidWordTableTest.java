// package csc480.blue.db.queryclass;

// import csc480.blue.db.QueryClass;
// import java.util.Arrays;

// import java.lang.Exception;
// import java.lang.NullPointerException;
// import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

// import static org.junit.jupiter.api.Assertions.assertArrayEquals;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.AfterEach;

// public class addNewToValidWordTableTest {
//     QueryClass qc = new QueryClass();

// 	@AfterEach
// 	void afterEach() {
//         qc = new QueryClass();   // reset state just in case
// 	}

//     @Test // passes, cat added to table with all info
//     public void t0() {
//         try {
//             qc.addNewToValidWordTable("cat", 20, 1, true, 0);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }

//     @Test // passes, cat no re-added
//     public void t1() {
//         try {
//             qc.addNewToValidWordTable("DOG", 0, 11, false, 1);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }

//     @Test // passes, cat not re-added
//     public void t2() {
//         try {
//             qc.addNewToValidWordTable("cat", 1, 9, true, 5);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }

//     @Test // passes, cat added to table with all info
//     public void t3() {
//         try {
//             qc.addNewToValidWordTable("cat", 200, 11, false, 1);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }

//     @Test // fails, functionally correct but deviates from spec
//     public void t4() {
//         try {
//             qc.addNewToValidWordTable("典Josephus", 200, 11, false, 1);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     @Test // fails, functionally correct but deviates from spec
//     public void t5() {
//         try {
//             qc.addNewToValidWordTable("", 0, 11, false, 1);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     @Test // fails, functionally correct but deviates from spec
//     public void t6() {
//         try {
//             qc.addNewToValidWordTable("a", 1, 9, true, 5);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     // t7 doesnt compile, passes

//     @Test // fails, functionally correct but deviates from spec
//     public void t8() {
//         try {
//             qc.addNewToValidWordTable("JosephusJosephus", 20, 1, true, 0);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     @Test // fails, functionally correct but deviates from spec
//     public void t9() {
//         try {
//             qc.addNewToValidWordTable(null, 0, 11, false, 1);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     @Test // fails, functionally correct but deviates from spec
//           // puts word cata even though value is invalid
//     public void t10() {
//         try {
//             qc.addNewToValidWordTable("cat", -5, 9, true, 5);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     // t11 doesn't compile, passes

//     @Test // fails, functionally correct but deviates from spec
//     public void t12() {
//         try {
//             qc.addNewToValidWordTable("cat", -1, 1, true, 0);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     // t13 doesn't compile, passes

//     @Test // fails, functionally correct but deviates from spec
//     public void t14() {
//         try {
//             qc.addNewToValidWordTable("cat", 1, 0, true, 5);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     @Test // fails, functionally correct but deviates from spec
//           // puts word DOGo even though length is invalid
//     public void t15() {
//         try {
//             qc.addNewToValidWordTable("DOG", 200, -8, false, 1);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     // t16 doesn't compile, passes

//     @Test  // fails, functionally correct but deviates from spec
//     public void t17() {
//         try {
//             qc.addNewToValidWordTable("DOG", 200, 33, false, 1);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     // t18-22 don't compile, pass

//     @Test  // fails, functionally correct but deviates from spec
//     public void t23() {
//         try {
//             qc.addNewToValidWordTable("DOG", 200, 11, false, -7);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     @Test  // fails, functionally correct but deviates from spec
//     public void t24() {
//         try {
//             qc.addNewToValidWordTable("cat", 20, 1, true, -1);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     // t25 doesn't compile

//     @Test // passes
//     public void t26() {
//         try {
//             qc.addNewToValidWordTable("ca", 1, 9, true, 5);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }

//     // t27-28 are redundant, point proven, limits dont have much significance

//     @Test // passes
//     public void t29() {
//         try {
//             qc.addNewToValidWordTable("DOGG", 0, 11, false, 1);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }

//     @Test  // fails, functionally correct but deviates from spec
//     public void t30() {
//         try {
//             qc.addNewToValidWordTable("典Josephu", 1, 9, true, 5);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     @Test  // fails, functionally correct but deviates from spec
//     public void t31() {
//         try {
//             qc.addNewToValidWordTable("典Josephuss", 200, 11, false, 1);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     // t32-33 point proven
//     // t34-35 won't compile, pass

//     @Test  // fails, functionally correct but deviates from spec
//     public void t36() {
//         try {
//             qc.addNewToValidWordTable("JosephusJosephu", 20, 1, true, 0);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     // fails t36 will obv fail t37

//     @Test  // fails, functionally correct but deviates from spec
//     public void t38() {
//         try {
//             qc.addNewToValidWordTable("cat", 19, 9, true, 5);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     @Test  // fails, functionally correct but deviates from spec
//     public void t39() {
//         try {
//             qc.addNewToValidWordTable("DOG", 21, 11, false, 1);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     @Test  // fails, functionally correct but deviates from spec
//     public void t40() {
//         try {
//             qc.addNewToValidWordTable("cat", 20, 1, true, 0);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     // t41 is unnecessary, point proven

//     @Test  // fails, functionally correct but deviates from spec
//     public void t42() {
//         try {
//             qc.addNewToValidWordTable("cat", -6, 9, true, 5);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }

//     // t43 & t46 are excessive at this point
//     // t44-45 won't compile, pass

//     // same code, same eq. classes, rest are either excessive or won't compile, halt testing
// }