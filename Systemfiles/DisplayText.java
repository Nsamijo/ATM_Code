package Systemfiles;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;

public class DisplayText extends ScreenElement implements OutputDevice {

	JLabel label;

	DisplayText(String name, Point pos) {
		super(name, pos);
		label = new JLabel("Centered", JLabel.CENTER);
		label.setForeground(Color.white);
		label.setFont(new Font("SansSerif", Font.BOLD, 30));
		label.setBounds(pos.x, pos.y, 550, 35);
	}

	public void giveOutput(String outPut) {
		label.setText(outPut);
	}

	public void setContainer(Container container) {
		container.add(label);
	}

}
