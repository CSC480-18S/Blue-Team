package Session;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Enumeration;

public class Start {
    public static File qrFile;
    public static void main(String[] args) {
        // Comment out below line to run in Windows
        //System.setProperty("java.awt.headless", "true");

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            String ip = "10.42.0.1";
            /*Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements()){
                NetworkInterface net = e.nextElement();
                System.out.println(net.getDisplayName());
                    //ip = net.getInetAddresses().nextElement().getLocalHost().getHostAddress();
                    byte[] mac = net.getHardwareAddress();
                    if(mac != null) {
                        StringBuilder sb = new StringBuilder(18);
                        for (byte b : mac) {
                            if (sb.length() > 0)
                                sb.append(':');
                            sb.append(String.format("%02x", b));
                        }
                        String strMAC = sb.toString();
                        System.out.println("MAC: " + strMAC);
                        if (strMAC.equals("74:da:38:83:de:28")) {
                            ip = net.getInetAddresses().nextElement().getLocalHost().toString();
                        }
                    }
            }*/
            System.out.println("IP: " + ip);
            String link = "http://" + ip + ":8080/blueteam";
            qrFile = new File("qr.png");
            generateQRCodeImage(link, 150, 150, qrFile.getPath());
        }catch (Exception e){
            e.printStackTrace();
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
}
