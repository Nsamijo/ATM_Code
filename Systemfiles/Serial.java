package Systemfiles;

import com.fazecast.jSerialComm.*;

public class Serial {
	private static String data;
	private static String serialData;

	public static void listenSerial() {
		SerialPort comPort = SerialPort.getCommPort("COM3");
		
		//set the baud rate to 9600 (same as the Arduino)
		comPort.setBaudRate(500000);
		
		//open the port
		comPort.openPort();
		
		//create a listener and start listening
		comPort.addDataListener(new SerialPortDataListener() {
			@Override
			public int getListeningEvents() { 
				return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; 
			}
			@Override
			public void serialEvent(SerialPortEvent event)
			{
				data = null;
				serialData = null;
				if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
				return; //wait until we receive data
			
				byte[] newData = new byte[comPort.bytesAvailable()]; //receive incoming bytes
				comPort.readBytes(newData, newData.length); //read incoming bytes
				serialData = new String(newData); //convert bytes to string
			}
		});
	}

	public String getData() {
		data = serialData;
		return data;
	}


}