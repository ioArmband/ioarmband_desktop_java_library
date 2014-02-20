package org.ioarmband.client.desktop.demo.app_powerpoint.connection;




import org.ioarmband.net.connection.manager.ConnectThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PCConnectThread extends ConnectThread {
	 
	private String serverURL;
	   
	private static final Logger logger = LoggerFactory.getLogger(PCConnectThread.class);
	
	   public PCConnectThread(String serverURL) {
	    this.serverURL =serverURL;
	     
	   }
	   
	   public void run() {
		   logger.info("ConnectThread run");
		   BluetoothConnectionManager bluetoothConnectionManager = BluetoothConnectionManager.getInstance();
		   bluetoothConnectionManager.newConnection(serverURL);
	   }
	   
	   public void cancel() {
		   logger.info("ConnectThread cancel");
	   }

	}