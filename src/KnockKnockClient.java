import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KnockKnockClient {
    public static void main(String[] args) {
        String svrHostname = HOSTNAME;
        try {
            svrHostname = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(KnockKnockClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        int port = SERVER_PORT;
        new KnockKnockClient(svrHostname, port);
    }
    
    public KnockKnockClient(String hostName, int serverPort) {
        establishServerConnection(hostName, serverPort);
        interactWithServer();
        cleanUp();
    }
    
    private void establishServerConnection(String hostName, int serverPort) {
        System.out.println("Establishing connection to host \"" + hostName + "\" at port " + serverPort + ".");
        try {            
            kkSocket = new Socket(hostName, serverPort);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostName);
            System.exit(1);
        }
        System.out.println("Connected to host \"" + hostName + "\" at port " + serverPort + ".");
    }
    
    private void interactWithServer() {
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer, fromUser;
        while (true) {
            try {
                fromServer = in.readLine();
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye.") || fromServer.equals("")) break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            } catch (IOException ex) {
                Logger.getLogger(KnockKnockClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void cleanUp() {
        out.close();
        try {
            in.close();
            stdIn.close();
            kkSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(KnockKnockClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* Instance variables */
    private Socket kkSocket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;
    
    /* Constants */
    private static final int SERVER_PORT = 4444;
    private static final String HOSTNAME = "BB-REY.blackbird.bbi";
}