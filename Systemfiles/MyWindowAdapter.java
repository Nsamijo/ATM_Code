package Systemfiles;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class MyWindowAdapter extends WindowAdapter {

	Frame f;
	MyWindowAdapter(Frame f) {
		this.f = f;
		
	}
	
	public void windowClosing(WindowEvent e) {
		f.dispose();
		System.exit(0);

	}

}
