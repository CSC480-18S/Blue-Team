package main.java.Components;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/// Class to control logging throughout app
public class Log
{
    public Logger logger;
    private FileHandler handler;
    private String filepath = "c:/temp/sqrabble_log";

    public Log() throws SecurityException, IOException
    {
        // create file if it doesn't exist
        File file = new File(filepath);
        if (!file.exists())
        {
            file.createNewFile();
        }

        // true to append file
        handler = new FileHandler(filepath, true);
        logger = Logger.getLogger("sqrabble");
        logger.addHandler(handler);
        SimpleFormatter format = new SimpleFormatter();
        handler.setFormatter(format);
    }
}
