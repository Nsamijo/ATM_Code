package Systemfiles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Bank {

	private Socket sock;
	private DataInputStream received;
	private DataOutputStream send;

	public Bank()	{
		try{
			this.connect();
			received = new DataInputStream(sock.getInputStream());
			send = new DataOutputStream(sock.getOutputStream());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void blackList() {
		try {
			send.writeUTF("blockCard");
		} catch (IOException e) {
			System.out.println("DEVINE INTERVENTION HAS PREVENTED THE BLOCKING OF THE CARD!!!");
		}
	}
	
	public void connect() {
		try {
			sock = new Socket("145.24.222.106", 8080);
			System.out.println("CONNECTION SUCCESVOL! READY TO SEND AND RECEIVE DATA!");
		} catch (UnknownHostException e) {
			System.out.println("HOST COULD NOT BE RESOLVED! PLEASE TRY AGAIN!");
		} catch (IOException e) {
			System.out.println("AN UNEXPECTED ERROR HAPPENED! PLEASE TRY AGAIN");
		}
	}
	

	
	public boolean checkIbanBlocked(String iban) {
		try {
			send.writeUTF("checkBlocked");
			return received.readBoolean();
		} catch (IOException e) {
			System.out.println("PLEASE TRY AGAIN!");
			return false;
		}
	}
	
	public boolean checkPin(String pin) {
		try {
			this.connect();
			send.writeUTF("pin");
			send.writeInt(Integer.valueOf(pin));
			return received.readBoolean();
		} catch (IOException e) {
			System.out.println("THE GODS HAVE FORSAKEN YOU!!!!");
			return false;
		}
	}
	
	public int getBalance(String iban, String pin) {
		try {
			send.writeUTF("balance");
			send.writeUTF(iban);
			send.writeInt(Integer.valueOf(pin));
			received = new DataInputStream(sock.getInputStream());
			return received.readInt();
		} catch (IOException e) {
			System.out.println("UNABLE TO GET BALANCE");
			return Integer.MIN_VALUE;
		}
	}
	
	public boolean getIban(String iban) {
		try {
			send.writeUTF("iban");
			send.writeUTF(iban);
			return received.readBoolean();
		} catch (IOException e) {
			System.out.println("STREAM ERROR! PLEASE TRY AGAIN");
		}
		return false;
	}
		
	public boolean withdraw(String amount) {
		try {
			send.writeUTF("withdraw");
			send.writeUTF(amount);
			System.out.println(sock.isConnected() + " => CONNECTION STATUS");
			received = new DataInputStream(sock.getInputStream());
			return received.readBoolean();
		} catch (IOException e) {
			System.out.println("UNABLE TO GET MONEY BROKE NIGGA");
			e.printStackTrace();
			return false;
		}
	}
	
}