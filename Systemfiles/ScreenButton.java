package Systemfiles;
import java.awt.Button;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenButton extends ScreenElement implements InputDevice , java.awt.event.ActionListener {

	java.awt.Button button;
	private boolean inputAvailable = false;//variabel om te kijken of er inputbeschikbaar is
	//in de constructor wordt een naam en positie meegegeven
	ScreenButton(String name, Point pos) {
		super(name, pos);
		button = new Button(name);
		button.setBounds(pos.x, pos.y, 50 + 15 * 20, 25);
		button.addActionListener((ActionListener) this);
	}
//deze methode geeft de naam van de button terug en kan gezien worden als input
	public String getInput() {
		if(this.inputAvailable)	{
			this.inputAvailable = false;//wordt weer op false gezet zodat er weer gekeken kan worden of deze nog een keer is ingedrukt
			return name;
		}else
			return null;
	}

	public void setContainer(Container container) {
		container.add(this.button);
		
	}
//kijken of de button is ingedrukt en zetten deze de variabel op true
	public void actionPerformed(ActionEvent e) {
		this.inputAvailable = true;	
	}



}
