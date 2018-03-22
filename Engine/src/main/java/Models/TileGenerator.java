package Models;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Bohdan Yevdokymov
 */
public class TileGenerator {
    private static TileGenerator generator;
    private int totalPoints;
    ArrayList<Entry> entries;

    private TileGenerator(){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("tiles.txt");
        Scanner sc = new Scanner(file);
        entries = new ArrayList<>();
        totalPoints = 0;

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            //skip comments
            if(line.contains("//")){
                continue;
            }
            //remove spaces
            line = line.replace(" ", "");
            //skip empty lines
            if(line.length() == 0){
                continue;
            }
            char letter = line.charAt(0);
            int numOfTiles = Integer.parseInt(line.substring(line.indexOf(':') + 1, line.indexOf(',')));
            int points = Integer.parseInt(line.substring(line.indexOf(',') + 1, line.indexOf(';')));
            totalPoints += points;
            entries.add(new Entry(letter, numOfTiles, points));
        }
        sc.close();

        int range = 0;
        for(Entry entry : entries){
            entry.startRange = range;
            entry.endRange = range + entry.numOfTiles - 1;
            range += entry.numOfTiles;
        }

    }

    public Tile getRandTile(){
        Tile tile = null;
        Random rand = new Random();
        int num = rand.nextInt(totalPoints + 1);
        for(Entry entry : entries){
            if(entry.startRange <= num && entry.endRange >= num){
                tile = new Tile(entry.letter, entry.points);
                break;
            }
        }


        return tile;
    }


    public static TileGenerator getInstance(){
        if(generator == null) {
            try {
                generator = new TileGenerator();
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Error loading tiles.txt");
            }
        }
        return generator;
    }

    private class Entry{
        char letter;
        int numOfTiles;
        int points;
        int startRange;
        int endRange;

        private Entry(char letter, int numOfTiles, int points){
            this.letter = letter;
            this.numOfTiles = numOfTiles;
            this.points = points;
            startRange = 0;
            endRange = 0;
        }
    }
}
