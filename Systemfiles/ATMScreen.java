package Systemfiles;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ATMScreen extends java.awt.Container {

	//in de constructor wordt de setlayout op null gezet en dit laat ons toe om zelf de layout te zetten
	ATMScreen()	{
		this.setLayout(null);	
		
	}
	
	void add(ScreenElement element)	{
		element.setContainer(this);
	}
	
	void clear() {
		removeAll();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.RED);
		g.fillRoundRect(1247, 730, 35, 35, 10, 10);
		g.fillRect(1277, 760, 5, 5); g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.BOLD, 20));
		g.drawString("HR", 1250, 750);
		g.setFont(new Font("SansSerif", Font.PLAIN, 12));
		g.drawString("bank", 1251, 760);
		
	}
}
