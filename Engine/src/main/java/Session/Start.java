package Session;

public class Start {
    public static void main(String[] args) {
        // Comment out below line to run in Windows
        //System.setProperty("java.awt.headless", "true");
        System.out.println("*********************************" + java.awt.GraphicsEnvironment.isHeadless());
        Session.getSession();
    }
}
