package simpleclientserver;


import java.net.*;
import java.io.*;

public class SimpleClient {
	public static void main(String args[]) {
		boolean großbuchstaben = false;
		//String s="Das Pferd frisst Gurkensalat\n";
		String s="ENDE\n";
		try {
			Socket talkSocket = new Socket("localhost", 4711);
			
			BufferedReader fromServer = new BufferedReader(
					new InputStreamReader(
							talkSocket.getInputStream(), "Cp1252"));
			
			OutputStreamWriter toServer =
					new OutputStreamWriter(
							talkSocket.getOutputStream(), "Cp1252");
			
			if (großbuchstaben)
				s = "1#" + s;
			else
				s = "0#" + s;
			
			System.out.println("Send: "+ s);
			toServer.write(s);
			toServer.flush(); // force message to be sent
			
			String message = fromServer.readLine(); // blocking read
			
			String[] result = message.split("#", 2);
			if (result[0].length() > 0)
				System.out.println("Receive: " + result[1] + "\nPrice: " + result[0]);
			else
				System.out.println("Receive: " + result[1]);
			
			
			toServer.close(); // close writer
			fromServer.close(); // close reader
			talkSocket.close(); // close socket(necessary??)

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
