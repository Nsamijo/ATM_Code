package Systemfiles;

import java.io.IOException;
import java.net.Socket;

public class serverCommunication {

	Socket master;
	Socket client;
	
	void server(){
		try {
			master = new Socket("185.224.91.138", 80);
			client = new Socket("185.224.91.138", 80);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void checkConnection() throws IOException {
		System.out.println("Master: " + master.isConnected());
		System.out.println("Client: " + client.isConnected());
	}
	
	
	public static void main(String[]args) {
		
	}
}
