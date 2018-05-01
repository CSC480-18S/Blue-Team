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

// public class addNewToGameTableTest {
//     QueryClass qc = new QueryClass();

// 	@AfterEach
// 	void afterEach() {
//         qc = new QueryClass();   // reset state just in case
// 	}

//     @Test // passes, added to table
//     public void t0() {
//         try {
//             qc.addNewToGameTable(1, 1);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }

//     @Test // passes, added to table
//     public void t1() {
//         try {
//             qc.addNewToGameTable(40, 40);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }   

//     // t2 no point in testing unrealistic input


//     @Test // passes, added to table
//     public void t3() {
//         try {
//             qc.addNewToGameTable(0, 0);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }   

//     // t4 compilation error, passes

//     @Test // functionally correct, fails spec
//     public void t5() {
//         try {
//             qc.addNewToGameTable(-40, 0);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }  

//     @Test // functionally correct, fails spec
//     public void t6() {
//         try {
//             qc.addNewToGameTable(-1, 0);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }  

//     // t7 no point in testing, unrealistic input
//     // t8 compilation error, passes
//     // t9-10 no point in testing, same code executed on same Eq. Cl. rep as t5-6
//     // t11 no point in testing, unrealistic input

//     @Test // passes, added to table
//     public void t12() {
//         try {
//             qc.addNewToGameTable(2, 11);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }

//     @Test // passes, added to table
//     public void t13() {
//         try {
//             qc.addNewToGameTable(39, 12);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }

//     @Test // passes, added to table
//     public void t14() {
//         try {
//             qc.addNewToGameTable(41, 11);
//             assertTrue(true);
//         } catch (Exception e) {
//             assertTrue(false);
//         }
//     }

//     // t15-16 unrealistic input

//     @Test // functionally correct, fails spec
//     public void t17() {
//         try {
//             qc.addNewToGameTable(-41, 11);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }  

//     @Test // functionally correct, fails spec
//     public void t18() {
//         try {
//             qc.addNewToGameTable(-39, 12);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }  

//     @Test // functionally correct, fails spec
//     public void t19() {
//         try {
//             qc.addNewToGameTable(-2, 11);
//             assertTrue(false);
//         } catch (Exception e) {
//             assertTrue(true);
//         }
//     }     

//     // rest of test cases are either unrealistic, or same code run on previously tested
//     // representative of the same eq. class, 
// }