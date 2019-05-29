package Systemfiles;
import java.awt.Point;

public abstract class ScreenElement extends ATMElement{


	java.awt.Point pos;
	public ScreenElement(String name, java.awt.Point pos) {
		super(name);
		this.pos = pos;
	}
	
	public abstract void setContainer(java.awt.Container container);
	
}
