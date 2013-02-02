import java.net.*;
import java.io.*;

public class KKMultiServerThread extends Thread {
    public KKMultiServerThread(Socket socket) {
	super("KKMultiServerThread");
	this.socket = socket;
    }

    @Override
    public void run() {
	try {
	    out = new PrintWriter(socket.getOutputStream(), true);
	    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    String inputLine, outputLine;
            // Replace this protocol with another protocol to handle different incoming msg
	    KnockKnockProtocol kkp = new KnockKnockProtocol();
	    outputLine = kkp.processInput(null);
	    out.println(outputLine);

	    while (true) {
                inputLine = in.readLine();
		outputLine = kkp.processInput(inputLine);
		out.println(outputLine);
		if (outputLine.equals("Bye")) {
                    break;
                }
	    }
	    out.close();
	    in.close();
	    socket.close();
	} catch (IOException e) {
	}
    }
    
    /* Instance variables */
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private static final String[] TERMINAL_SEQUENCE = {null, "", "."};
}