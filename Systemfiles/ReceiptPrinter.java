package Systemfiles;

public class ReceiptPrinter extends HardwareElement implements OutputDevice{
//in de constructor wordt doorgegeven aan de superclass zodat deze weet welke harware geinitialiseerd is
	ReceiptPrinter(String name) {
		super(name);
	}
//print de bon in de terminal
	public void giveOutput(String outPut) {
		System.out.println(outPut);		
	}

}