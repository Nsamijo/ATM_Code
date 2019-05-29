package Systemfiles;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Keypad extends HardwareElement implements InputDevice {

	BufferedReader keypad;

	public Keypad(String name) {
		super(name);
		keypad = new BufferedReader(new InputStreamReader(System.in));
	}

	public String getInput() {
//		System.out.println("Please enter your pin: ");
		try {
			if (keypad.ready()) {
				String pin = null;
				pin = keypad.readLine();
				return pin;
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

}
