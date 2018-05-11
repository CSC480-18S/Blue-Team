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
    private static ArrayList<Tile> bag;
    private static ArrayList<Tile> sampleBag;


    private TileGenerator(){
        fillBag();
    }

    private void fillBag(){
        bag = new ArrayList<>();
        sampleBag = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("tiles.txt");
        Scanner sc = new Scanner(file);
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
            for(int i =0; i < numOfTiles; i ++){
                bag.add(new Tile(letter, points));
            }
            if(!sampleBag.contains(new Tile (letter, points))){
                sampleBag.add(new Tile(letter, points));
            }
        }
        sc.close();
    }

    public Tile getRandTile(){
        Tile tile = null;
        Random rand = new Random();
        int num = rand.nextInt(bag.size());
        tile = bag.get(num);
        bag.remove(num);

        //check if bag is empty
        if (bag.size() == 0){
            fillBag();
        }

        return tile;
    }

    public Tile exchangeTile(Tile tile){
        bag.add(tile);
        Tile newTile = null;
        Random rand = new Random();
        int num = rand.nextInt(bag.size());
        newTile = bag.get(num);
        bag.remove(num);

        //check if bag is empty
        if (bag.size() == 0){
            fillBag();
        }

        return newTile;
    }

    public void putInBag(Tile[] tiles){
        for(int i=0; i < tiles.length; i++){
            bag.add(tiles[i]);
        }
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

    //error - needs fix
    public Tile getTile(char letter) {
        for (Tile tile : sampleBag) {
            if (tile.getLetter() == letter) {
                return tile;
            }
        }
        return null;
    }

}
