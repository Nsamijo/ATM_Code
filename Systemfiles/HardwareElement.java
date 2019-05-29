package Systemfiles;

public abstract class HardwareElement extends ATMElement {

	boolean power = false;
	public HardwareElement(String name) {
		super(name);
	}
	
	void powerOn()	{
		this.power = true;
	}
	
	void powerOff()	{
		this.power = false;
	}

}
