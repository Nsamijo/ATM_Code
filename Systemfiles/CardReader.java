package Systemfiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CardReader extends HardwareElement implements InputDevice {

	BufferedReader cardReader;
	CardReader(String name) {
		super(name);
		cardReader= new BufferedReader(new InputStreamReader(System.in));
	}

	public String getInput() {
		System.out.println("To simulate inserting card, enter card number: ");
		try {
			String account = cardReader.readLine();
			return account;
		} catch (IOException e) {
			return null;
		}
	}

}
