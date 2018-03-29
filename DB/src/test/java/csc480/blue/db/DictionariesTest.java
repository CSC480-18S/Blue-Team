package csc480.blue.db;

import java.lang.NullPointerException;
import java.io.FileNotFoundException;
import java.lang.ClassLoader;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class DictionariesTest {
	Dictionaries dict = null;
	ClassLoader classLoader = getClass().getClassLoader();
	String path = classLoader.getResource("testing_assets/").getPath();

	@AfterEach
	void afterEach() {
		dict = null;
		path = classLoader.getResource("testing_assets/").getPath();
	}

	// Used in boundary interior testing too
	@Test
	void t00() throws FileNotFoundException {
		path += "two_entries.txt";
		dict = new Dictionaries(path, path, path);
		assertTrue(dict.getEnglishWords().contains("WORD1"));
		assertTrue(dict.getSpecialWords().contains("WORD1"));
		assertTrue(dict.getBadWords().contains("WORD1"));
		assertTrue(dict.getEnglishWords().contains("WORD2"));
		assertTrue(dict.getSpecialWords().contains("WORD2"));
		assertTrue(dict.getBadWords().contains("WORD2"));
	}

	@Test
	void t01() throws FileNotFoundException {
		path += "two_entries.txt";
		assertThrows(NullPointerException.class, () -> {
			dict = new Dictionaries(null, path, path);
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
		});
	}

	@Test
	void t03() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries("", path, path);
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t04() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries("a", path, path);
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t05() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries("tenbergen", path, path);
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t06() throws FileNotFoundException {
		assertThrows(NullPointerException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries(path, null, path);
			assertTrue(dict.getEnglishWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getEnglishWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t07() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries(path, "", path);
			assertTrue(dict.getEnglishWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getEnglishWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t08() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries(path, "b", path);
			assertTrue(dict.getEnglishWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getEnglishWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t09() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries(path, "tenbegen", path);
			assertTrue(dict.getEnglishWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getEnglishWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t10() throws FileNotFoundException {
		assertThrows(NullPointerException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries(path, path, null);
			assertTrue(dict.getEnglishWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getEnglishWords().contains("WORD2"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t11() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries(path, path, "");
			assertTrue(dict.getEnglishWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getEnglishWords().contains("WORD2"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t12() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries(path, path, "b");
			assertTrue(dict.getEnglishWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getEnglishWords().contains("WORD2"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t13() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries(path, path, "tenbergen");
			assertTrue(dict.getEnglishWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getEnglishWords().contains("WORD2"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t14() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries("aa", path, path);
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t15() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries("tenberge", path, path);
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}

	@Test
	void t16() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "two_entries.txt";
		    dict = new Dictionaries("tenbergenn", path, path);
			assertTrue(dict.getSpecialWords().contains("WORD1"));
			assertTrue(dict.getBadWords().contains("WORD1"));
			assertTrue(dict.getSpecialWords().contains("WORD2"));
			assertTrue(dict.getBadWords().contains("WORD2"));
			System.out.println("Crashes when it's supposed to");
		});
	}
	
	@Test // 0 loops
	void t17() throws FileNotFoundException {
		path += "empty.txt";
		dict = new Dictionaries(path, path, path);
		assertTrue(dict.getEnglishWords().isEmpty());
		assertTrue(dict.getSpecialWords().isEmpty());
		assertTrue(dict.getBadWords().isEmpty());
	}

	@Test  // 1 loop
	void t18() throws FileNotFoundException {
		path += "singleton.txt";
		dict = new Dictionaries(path, path, path);
		assertTrue(dict.getEnglishWords().contains("SINGLEWORD"));
		assertTrue(dict.getSpecialWords().contains("SINGLEWORD"));
		assertTrue(dict.getBadWords().contains("SINGLEWORD"));
	}
}
