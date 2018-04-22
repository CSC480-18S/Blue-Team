package Session;



import Components.Log;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.*;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class Start {
    public static File qrFile;
    public static void main(String[] args) {
        // Comment out below line to run in Windows
        //System.setProperty("java.awt.headless", "true");

        try {
            String ip = loadIP();
            System.out.println("IP: " + ip);
            String link = "http://" + ip + ":8080/blueteam";
            qrFile = new File("qr.png");
            generateQRCodeImage(link, 150, 150, qrFile.getPath());

        }catch (Exception e){
            e.printStackTrace();
            Log.getLogger().logException(e);

        }
        Session.getSession();
    }

    private static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    private static String loadIP(){
        String ip = "10.42.0.1";
        try{
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream file = classloader.getResourceAsStream("settings.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                line = line.replace(" ", "");
                String [] param = line.split("=");
                if(param[0].toUpperCase().equals("IP")){
                    ip = param[1];
                    break;
                }
            }
            sc.close();
        } catch (Exception e ){
            e.printStackTrace();
            Log.getLogger().logException(e);
            return ip;
        }
        return ip;
    }
}
