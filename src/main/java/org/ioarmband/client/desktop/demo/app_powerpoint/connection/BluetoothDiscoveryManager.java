package org.ioarmband.client.desktop.demo.app_powerpoint.connection;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;

import org.ioarmband.net.connection.manager.DiscoveryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BluetoothDiscoveryManager extends DiscoveryManager{

	//public static UUID CLIENT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
	private static final Logger logger = LoggerFactory.getLogger(BluetoothDiscoveryManager.class);
     
	public void startdiscoveryDevice() 
	{
		MyDiscoveryListener listener = new MyDiscoveryListener();
		
		 try{
			 	LocalDevice localDevice = LocalDevice.getLocalDevice();
	            DiscoveryAgent agent = localDevice.getDiscoveryAgent();
	            /*  agent.startInquiry(DiscoveryAgent.GIAC,listener );
	           
	            try {
	                synchronized(listener.getLock()){
	                	listener.getLock().wait();
	                }
	            }
	            catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            logger.info("Device Inquiry Completed. ");
	            */
	            RemoteDevice[] devices = agent.retrieveDevices(DiscoveryAgent.PREKNOWN);
	           
	            
	            
	            
	            
	        	UUID[] uuidSet = new UUID[1];
				uuidSet[0] = new UUID(0x1101); // OBEX Object Push service

				int[] attrIDs = new int[] { 0x1101 // Service name
				};
				
		           for (RemoteDevice device : devices) {
		            	
		            	logger.info("Start searchServices in device : "+device.getFriendlyName(false));
		                agent.searchServices(attrIDs,uuidSet,device,listener);
		                
		                
		                try {
		                    synchronized(listener.getLock()){
		                    	listener.getLock().wait();
		                    }
		                }
		                catch (InterruptedException e) {
		                    e.printStackTrace();
		                    return;
		                }
		                
		                
		                logger.info("Service search finished.");
		            }
			/*
			  logger.info("Start SelectService whith UUID = "+uuidSet[0]);
			String url = agent.selectService(uuidSet[0],
                    ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
			
			if(url != null)
			{
				ConnectThread connectThread = new ConnectThread(url);
	        	connectThread.start();
			}else{
				  logger.info("Service not found");
			}
			
			*/
			
		  }
	        catch (Exception e) {
	            e.printStackTrace();
	      }
	}	
}
