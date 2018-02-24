package main.java.Session;

public class Start {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");
        Session session = Session.getSession();
    }
}
