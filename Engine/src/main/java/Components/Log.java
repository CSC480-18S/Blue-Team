package Components;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/// Class to control logging throughout app
public class Log
{
    private static Log logger;
    private File logFile;

    public Log()
    {
        try {
            // create file if it doesn't exist
            logFile = new File("scrabble_log.txt");
            logFile.createNewFile();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Log getLogger(){
        if(logger == null){
            logger = new Log();
        }
        return logger;
    }

    public void logException(Exception e){
        try {
            FileWriter fw = new FileWriter(logFile, true);
            PrintWriter pw = new PrintWriter(fw);

            //getting current date and time using Date class
            DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            Date dateobj = new Date();
            pw.write(df.format(dateobj) + "\n");
            e.printStackTrace(pw);
            pw.write("\n\n\n");

            pw.flush();
            pw.close();
            fw.close();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
