package csc480.blue.db;

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

	@Test
	void t00() throws FileNotFoundException {
		path += "empty.txt";
		dict = new Dictionaries(path, path, path);
		assertTrue(dict.getEnglishWords().isEmpty());
		assertTrue(dict.getSpecialWords().isEmpty());
		assertTrue(dict.getBadWords().isEmpty());
	}

	@Test
	void t01() throws FileNotFoundException {
		path += "singleton.txt";
		dict = new Dictionaries(path, path, path);
		assertTrue(dict.getEnglishWords().contains("SINGLEWORD"));
		assertTrue(dict.getSpecialWords().contains("SINGLEWORD"));
		assertTrue(dict.getBadWords().contains("SINGLEWORD"));
	}

	@Test
	void t02() throws FileNotFoundException {
		path += "two_entries.txt";
		dict = new Dictionaries(path, path, path);
		assertTrue(dict.getEnglishWords().contains("WORD1"));
		assertTrue(dict.getSpecialWords().contains("WORD1"));
		assertTrue(dict.getBadWords().contains("WORD1"));
		assertTrue(dict.getEnglishWords().contains("WORD2"));
		assertTrue(dict.getSpecialWords().contains("WORD2"));
		assertTrue(dict.getBadWords().contains("WORD2"));
	}

	// Negative test
	@Test
	void t03() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> {
			path += "does_not_exist.txt";
		    dict = new Dictionaries(path, path, path);
			System.out.println("Crashes when it's supposed to");
		});
	}
}
