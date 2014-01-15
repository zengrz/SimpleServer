/**
 *
 * @author rey
 */

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientStub {
    public static void main(String[] args) {
        String localhostname = HOSTNAME;
        try {
            localhostname = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientStub.class.getName()).log(Level.SEVERE, null, ex);
        }
        int port = SERVER_PORT;
        new ClientStub(localhostname, port);
    }
    
    public ClientStub(String hostName, int serverPort) {
        establishServerConnection(hostName, serverPort);
        interactWithServer();
        cleanUp();
    }
    
    private void establishServerConnection(String hostName, int serverPort) {
        System.out.println("Establishing connection to host \"" + hostName + "\" at port " + serverPort + ".");
        try {            
            sckt = new Socket(hostName, serverPort);
            toSvr = new PrintWriter(sckt.getOutputStream(), true);
            fromSvr = new BufferedReader(new InputStreamReader(sckt.getInputStream()));
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
        input = new BufferedReader(new InputStreamReader(System.in));
        String fromServer, fromUser;
        
        while (true) {
            try {
                fromServer = fromSvr.readLine();
                // Termination condition (based on server message) here, or do something with the message from server.
                
                fromUser = input.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    toSvr.println(fromUser);
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientStub.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void cleanUp() {
        toSvr.close();
        try {
            fromSvr.close();
            input.close();
            sckt.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientStub.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* Instance variables */
    private Socket sckt;
    private PrintWriter toSvr;
    private BufferedReader fromSvr;
    private BufferedReader input;
    
    /* Constants */
    private static final int SERVER_PORT = 4444;
    private static final String HOSTNAME = ""; // Right click "My Computer" to find out...
}