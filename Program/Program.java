package Program;

import Systemfiles.*;

public class Program {
	
	public static void main(String[] args) throws InterruptedException	{
		Bank bank = new Bank();
		ATM atm = new ATM(bank);
	}

}
