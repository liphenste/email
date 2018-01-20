import java.net.*;
import java.io.*;

public class NetIO {
    public static String myUserName() {
	return System.getProperty("user.name");
    }
    
    public static String myIPAddress() {
	String ipAddress = "";
	try {
	    InetAddress me = InetAddress.getLocalHost();
	    ipAddress = me.getHostAddress();
	}
	catch (IOException e) {
	    System.out.println("error getting ip-address");
	}
	return ipAddress;
    }
    
    public static int sendRequest(String message, String destinationIPAddress) {
	int errorCode = -1;
	int attempts = 0;
	do {
	    try {
		Socket me = new Socket();
		me.connect(new InetSocketAddress(destinationIPAddress, 5000), 10000);
		me.setSoTimeout(10000);
	    
		DataOutputStream output = new DataOutputStream(me.getOutputStream());
		output.writeUTF(message);
	    
		DataInputStream input = new DataInputStream(me.getInputStream());
		String confirmation = input.readUTF();
	    
		if (isANumber(confirmation))
		    errorCode = Integer.parseInt(confirmation);
		
		me.close();
	    }
	    catch (IOException e) {
		System.out.println("Exception thrown in sendRequest");
	    }
	    attempts++;
	} while (errorCode != Globals.NET_OK && attempts < Globals.SENDING_ATTEMPTS_LIMIT);
	return errorCode;
    }
    
    public static String receiveRequest() {
	String message = "";
	try {
	    ServerSocket server = new ServerSocket(5000, 100);
	    Socket me = server.accept();
	    me.setSoTimeout(10000);

	    DataInputStream input = new DataInputStream(me.getInputStream());
	    message = input.readUTF();
	    
	    DataOutputStream output = new DataOutputStream(me.getOutputStream());
	    output.writeUTF("0");
	    
	    me.close();
	    server.close();
	}
	catch (IOException e) {
	    System.out.println("error receiving message");
	}
	return message;
    }
    
    private static boolean isANumber(String s) {
	boolean result = true;
	for (int i = 0; i < s.length() && result == true; i++)
	    result = Character.isDigit(s.charAt(i));
	return result;
    }
}
