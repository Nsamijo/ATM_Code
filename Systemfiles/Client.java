package Systemfiles;


public class Client {
//private fields zodat deze niet zomaar in andere classesn veranderd kunnen worden
	private String name; // voor de naam van de user/client
	private String pin; // voor de beveiliging zodat alleen de user toegang heeft tot zijn gegevens
	private int balance; // de balans van de user

//bij het initialiseren van een client moet de er een naam, pin en balans worden meegegeven
	Client(String name, String pin, int balance) {
		this.name = name;
		this.pin = pin;
		this.balance = balance;
	}

//geeft de naam van de user terug
	public String getName() {
		return name;
	}

//de meegegeven argument moet gelijk zijn aan de pin van de user als deze daadwerkelijk de user us en toegang wilt tot zijn gegevens
	boolean checkPin(String pin) {
		return this.pin.equals(pin);
	}

//om zijn balans te willen bekijken moet de user een goede pin hebben ingevuld, anders krijg deze de kleinste integer mogelijk en wordt de balans van de user niet getoond
	public int getBalance(String pinInput) {
		if (this.checkPin(pinInput)) {
			return balance;
		} else {
			return Integer.MIN_VALUE;
		}
	}

	// de user kan geld in zijn rekening zetten
	public void deposit(int addBalance) {
		this.balance += addBalance;
	}

	public int pinLength() {
		return pin.length();
	}

	public boolean withdraw(int takeAmount, String pin) {
		boolean temp = (this.checkPin(pin) && this.balance >= takeAmount);

		if (temp) {
			this.balance -= takeAmount;
			return temp;
		} else {
			return temp;
		}
	}
}