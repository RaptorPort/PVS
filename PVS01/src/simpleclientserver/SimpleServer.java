package simpleclientserver;

import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.io.*;

public class SimpleServer {
	public static void main(String args[]) {
		Socket talkSocket;
		BufferedReader fromClient;
		OutputStreamWriter toClient;
		String stringToConvert;
		
		try {
			ServerSocket listenSocket = new ServerSocket(4711);
			while (true) {
				talkSocket = listenSocket.accept();
				
				fromClient = new BufferedReader(new InputStreamReader(
						talkSocket.getInputStream(), "Cp1252"));
								
				toClient = new OutputStreamWriter(
						talkSocket.getOutputStream(), "Cp1252");
				
				String[] message = fromClient.readLine().split("#", 2);
				
				stringToConvert = message[1];
				
				if (stringToConvert.equals("ENDE")) {
					toClient.write("#Server Shutdown\n");
					toClient.close();
					talkSocket.close();
					break;
				}
				
				String result = null;
				float price = stringToConvert.length() * 1.5f;
				
				if (message[0].equals("1"))
					result = stringToConvert.toUpperCase();
				else if (message[0].equals("0"))
					result = stringToConvert.toLowerCase();
				else {
					result = "Error - Wrong Protocol!";
					price = 0;
				}
				
				toClient.write(price + "Ct#" + result + "\n");
				
				toClient.close();
				fromClient.close();
				talkSocket.close();
			}
			listenSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
	
