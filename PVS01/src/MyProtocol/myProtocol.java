package MyProtocol;


import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.io.*;

public class myProtocol {
	
	
	public myProtocol() {
		
	}
	
	
	public static void mytestmain() {
		Socket talkSocket;
		BufferedReader fromClient;
		OutputStreamWriter toClient;
		String stringToConvert;
		
		try {
			ServerSocket listenSocket = new ServerSocket(4711);
			while (true) {
				talkSocket = listenSocket.accept();
				
				// read incoming message
				
				
				DataInputStream in = new DataInputStream(talkSocket.getInputStream());

				int bytesToRead = -1;
				
				// bytesToRead = length.getInt();

				
				 System.out.println("Reading " + bytesToRead + " bytes...");
				 byte data[] = new byte[bytesToRead];
				 in.readFully(data, 0, bytesToRead);
				 
			    

				System.out.println("Bytes:");
				// System.out.println(targetArray.length);
				// System.out.println(new String(targetArray, "Cp1252"));
				
								
				toClient = new OutputStreamWriter(
						talkSocket.getOutputStream(), "Cp1252");
				
				stringToConvert = "random";
				
				if (stringToConvert.equals("ENDE")) {
					toClient.write("Server Shutdown\n");
					toClient.close();
					talkSocket.close();
					break;
				}
				
				toClient.write(stringToConvert.toUpperCase() + "\n");
				
				toClient.close();
				
				talkSocket.close();
			}
			listenSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int readHeader(DataInputStream inStream) throws IOException {
		byte[] header = new byte[4];
		// read first byte ( Protocol ID Byte )
		header[0] = inStream.readByte();
		
		byte myProtocolID = new Byte("10101010");
		if (Byte.compare(header[0], myProtocolID) != 0) {
			return -1;
		}
		
		// get length of message ( Bytes 1-3 )
		for (int i = 1; i < 4; i++)
			header[i] = inStream.readByte();
		
		ByteBuffer length = ByteBuffer.wrap(header, 1, 3);
		return length.getInt();
	}
	
	private static boolean checkProtocol(Byte firstByte) {
		byte myProtocolID = new Byte("10101010");
		if (Byte.compare(firstByte.byteValue(), myProtocolID) == 0) {
			return true;
		} else {
			// not our protocol
			return false;
		}
		
		/*
		if (!checkProtocol(header[0])) {
			// different protocol detected -> drop connection
			System.out.println("Incoming message did not match myProtocol -> Dropping connection");
			in.close();
			talkSocket.close();
			continue;
		}
		*/
	}
}
