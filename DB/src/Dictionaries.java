import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This class is used to store all (3) of the game's dictionaries.
 * The dictionaries can be separately loaded by reading text files.
 * Once created, the dictionaries can serialized and deserialized
 * as needed (or you can just keep reading the .txt files,
 * it doesn't really make a difference as far as I can tell)
 * 
 * @author Christian Van Cleef
 *
 */

public class Dictionaries implements Serializable {
	private static final long serialVersionUID = 6470362571456272072L;
	private HashSet<String> englishWords;
	private HashSet<String> specialWords; // Words like "snow" and "oswego"
	private HashSet<String> badWords;

	/**
	 * Load dictionary from .txt files
	 * 
	 * @param eng
	 *            Path to English text file
	 * @param spec
	 *            Path to special word text file
	 * @param bad
	 *            Path to bad words text file
	 * @throws FileNotFoundException
	 */
	public Dictionaries(String eng, String spec, String bad) throws FileNotFoundException {
		englishWords = new HashSet<String>();
		specialWords = new HashSet<String>();
		badWords = new HashSet<String>();
		Scanner sc = null;
		File file = null;
		
		// Load English words
		file = new File(eng);
		sc = new Scanner(file);
		while (sc.hasNextLine()) {
			englishWords.add(sc.nextLine());
		}
		sc.close();
		
		// Load special words
		file = new File(spec);
		sc = new Scanner(file);
		while (sc.hasNextLine()) {
			specialWords.add(sc.nextLine());
		}
		sc.close();
		
		// Load bad words
		file = new File(bad);
		sc = new Scanner(file);
		while (sc.hasNextLine()) {
			badWords.add(sc.nextLine());
		}
		sc.close();
	}
	
	public HashSet<String> getEnglishWords() {
		return englishWords;
	}

	public HashSet<String> getSpecialWords() {
		return specialWords;
	}

	public HashSet<String> getBadWords() {
		return badWords;
	}
}
