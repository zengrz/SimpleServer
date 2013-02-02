import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KKMultiServer {
    public static void main(String[] args) {
        new KKMultiServer();
    }
    
    public KKMultiServer() {
        setup();
        handleRequest();
    }
    
    private void setup() {
        System.out.println("Attempting to establish server.");
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + SERVER_PORT);
            System.exit(-1);
        }
        System.out.println("Server online.");
    }
    
    private void handleRequest() {
        listening = true;
        while (listening) {
            try {
                System.out.println("Listening.");
                Socket newConnection = serverSocket.accept();
                KKMultiServerThread newThread = new KKMultiServerThread(newConnection);
                newThread.start();
                System.out.println("One new thread spawned.");
            } catch (IOException ex) {
                Logger.getLogger(KKMultiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(KKMultiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* Instance variables */
    private ServerSocket serverSocket;   
    private boolean listening;
    
    /* Constants */
    private static final int SERVER_PORT = 4444;
}
