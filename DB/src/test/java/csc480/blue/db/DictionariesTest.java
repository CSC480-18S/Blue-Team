package csc480.blue.db;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class DictionariesTest {
	Dictionaries dict = null;
	String path = "./testing_assets/";

	@AfterEach
	void afterEach() {
		dict = null;
		path = "./testing_assets/";
	}

	@Test
	void t00() {
		path += "empty.txt";
		dict = new Dictionaries(path, path, path);
		assertTrue(dict.getEnglishWords().isEmpty());
		assertTrue(dict.getSpecialWords().isEmpty());
		assertTrue(dict.getBadWords().isEmpty());
	}

	@Test
	void t01() {
		path += "singleton.txt";
		dict = new Dictionaries(path, path, path);
		assertTrue(dict.getEnglishWords().contains("SINGLEWORD"));
		assertTrue(dict.getSpecialWords().contains("SINGLEWORD"));
		assertTrue(dict.getBadWords().contains("SINGLEWORD"));
	}

	@Test
	void t02() {
		path += "two_entries.txt";
		dict = new Dictionaries(path, path, path);
		assertTrue(dict.getEnglishWords().contains("WORD1"));
		assertTrue(dict.getSpecialWords().contains("WORD1"));
		assertTrue(dict.getBadWords().contains("WORD1"));
		assertTrue(dict.getEnglishWords().contains("WORD2"));
		assertTrue(dict.getSpecialWords().contains("WORD2"));
		assertTrue(dict.getBadWords().contains("WORD2"));
	}
}
