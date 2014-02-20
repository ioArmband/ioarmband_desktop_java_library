package org.ioarmband.client.desktop.demo.app_powerpoint.connection;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import org.ioarmband.net.connection.StreamedConnection;
import org.ioarmband.net.connection.manager.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BluetoothConnectionManager extends ConnectionManager{


	private static final Logger logger = LoggerFactory.getLogger(BluetoothConnectionManager.class);
	
	private static BluetoothConnectionManager instance = null;

	private String serverURL;
	private StreamConnection clientSession;
	
	private BluetoothConnectionManager()
	{
		super();
		streamConnection = null;
	}
	
	

	
	public static synchronized BluetoothConnectionManager getInstance() {
		if(instance == null)
		{
			instance = new BluetoothConnectionManager();
		}
		return instance;
	}
	
	@Override
	public void newConnectionComplementary(Object serverURL) {

		this.serverURL = (String) serverURL;
		
		  try {
			  logger.info("Connecting to " + this.serverURL);
			  
			try {
				clientSession = (StreamConnection) Connector.open(this.serverURL);
				
			
			} catch (RuntimeException e) {
				logger.error("Failed to connect whith RuntimeException");
				logger.error(e.toString());
				return; 

			} catch (Exception e) {
				logger.error("Failed to connect whith Exception");
				logger.error(e.toString());
				return;
			}
			logger.info("strat streamConnection");
			streamConnection = new StreamedConnection(clientSession.openInputStream(), clientSession.openOutputStream());
			streamConnection.addConnectionListener(connection);
			logger.info("strat addConnectionListener done");
			
			
		  } catch (IOException e) {
			e.printStackTrace();
		}
		
	}



	@Override
	public void LauchDiscovery() {
		BluetoothDiscoveryManager bluetoothDiscoveryManager = new BluetoothDiscoveryManager();
		bluetoothDiscoveryManager.startdiscoveryDevice();
	}



	@Override
	public void closeConnectionComplementary() {
			try {
				clientSession.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	} 
}
