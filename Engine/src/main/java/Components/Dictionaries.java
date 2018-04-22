package Components;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;


/**
 * This class is used to store all (3) of the game's dictionaries. The
 * dictionaries can be separately loaded by reading text files. Once created,
 * the dictionaries can serialized and deserialized as needed (or you can just
 * keep reading the .txt files, it doesn't really make a difference as far as I
 * can tell)
 *
 * @author Christian Van Cleef
 *
 */
// Singleton class
public class Dictionaries implements Serializable {

    private static Dictionaries dictionaries;
    private static final long serialVersionUID = 6470362571456272072L;
    private HashSet<String> englishWords;
    private HashSet<String> specialWords; // Words like "snow" and "oswego"
    private HashSet<String> badWords;
    private HashSet<String> aiWords;

    /**
     * Load dictionary from .txt files
     *
     * @param eng Path to English text file
     * @param spec Path to special word text file
     * @param bad Path to bad words text file
     * @throws FileNotFoundException
     */
    public Dictionaries(String eng, String spec, String bad, String ai) throws FileNotFoundException {
        englishWords = new HashSet<String>();
        specialWords = new HashSet<String>();
        badWords = new HashSet<String>();
        aiWords = new HashSet<String>();
        Scanner sc = null;
        ClassLoader classloader;
        InputStream file;

        // Load English words
        classloader = Thread.currentThread().getContextClassLoader();

        file = classloader.getResourceAsStream(eng);

        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            englishWords.add(sc.nextLine());
        }
        sc.close();

        // Load special words
        //file = new File(spec);
        classloader = Thread.currentThread().getContextClassLoader();
        file = classloader.getResourceAsStream(spec);

        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            specialWords.add(sc.nextLine());
        }
        sc.close();

        // Load bad words
        //file = new File(bad);
        classloader = Thread.currentThread().getContextClassLoader();
        file = classloader.getResourceAsStream(bad);

        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            badWords.add(sc.nextLine());
        }
        sc.close();

        // Load ai words
        //file = new File(bad);
        classloader = Thread.currentThread().getContextClassLoader();
        file = classloader.getResourceAsStream(ai);

        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            aiWords.add(sc.nextLine());
        }
        sc.close();
    }

    public static Dictionaries getDictionaries() {
        // Load dictionaries
        if (dictionaries == null) {
            try {
                dictionaries = new Dictionaries(
                        "dictionary_short.txt",
                        "bonus.txt",
                        "profanity.txt",
                        "aiDict.txt");
            } catch (Exception e) {
                System.out.println("Dictionaries::Error loading dict\n");
                System.out.println(System.getProperty("user.dir"));
                e.printStackTrace();
                //LogWarning("" + e.getMessage() + "\n" + e.getStackTrace());
            }
        }
        return dictionaries;

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

    public HashSet<String> getaiWords(){return aiWords;}
}
