package Systemfiles;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Bank {

	java.util.Map<String, Client> accounts;
	private Socket sock;
	private InetAddress addr;
	
//er wordt een map gebruikt om alle users in te zetten zodat de users altijd te vinden zijn
	public Bank()	{
		accounts = new java.util.HashMap<String, Client>();
		this.updateDatabase("SU38MYBK21450", "Vladimir Putin", "0020", 20000000);
	}
	
//eigen methode waarbij het aanroepen deze een nieuwe client initialiseerd, en kan dit volledig automatisch
	public void updateDatabase(String account, String name, String pin, int balance)	{
		this.accounts.put(account, new Client(name, pin, balance));
	}

	//de methode hier zorgt ervoor dat er door de hele hashmap gezocht of de opgegeven client wel bestaat
	public Client get(String account)	{
		try {
			if(this.accounts.containsKey(account))	{
				return this.accounts.get(account);
			}else {
				return (Client) null;
			}
		} catch (Exception e) {
			return (Client) null;
		}

	}
	
	public boolean connect() {
		try {
			sock = new Socket("145.24.222.106", 8080);
			addr = sock.getInetAddress();
			System.out.println("Connected to " + addr);
			return true;
		}catch (java.io.IOException e) {
			System.out.println("Connection failed");
	        return false;
	      }
				
	}
	
	public boolean disconnect() {
		try {
			sock.close();
			return false;
		} catch (IOException e) {
			return true;
		}
	}
	
}