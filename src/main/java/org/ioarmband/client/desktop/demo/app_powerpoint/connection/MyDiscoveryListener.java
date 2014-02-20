package org.ioarmband.client.desktop.demo.app_powerpoint.connection;

import java.util.ArrayList;

import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyDiscoveryListener implements DiscoveryListener {

	public ArrayList<RemoteDevice> devices;
	private static Object lock=new Object();
	
	
	public static Object getLock() {
		return lock;
	}

	public static void setLock(Object lock) {
		MyDiscoveryListener.lock = lock;
	}

	public MyDiscoveryListener() {
		super();
		devices = new ArrayList<RemoteDevice>();
	}

	private static final Logger logger = LoggerFactory.getLogger(MyDiscoveryListener.class);
    
	@Override
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass arg1) {
		 String name;
	        try {
	            name = btDevice.getFriendlyName(false);
	        } catch (Exception e) {
	            name = btDevice.getBluetoothAddress();
	        }
	        devices.add(btDevice);
	        logger.info("device found: " + name);
	}

	@Override
	public void inquiryCompleted(int arg0) {
		 synchronized(lock){
	            lock.notify();
	        }
	}

	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		 synchronized (lock) {
	            lock.notify();
	        }
	}

	@Override
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		 logger.info("servicesDiscovered");
		  for (int i = 0; i < servRecord.length; i++) {
	            String url = servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
	            logger.info("url found = "+ url);
	            if (url == null) {
	                continue;
	            }
	            logger.info("get serviceName");
	            DataElement serviceName = servRecord[i].getAttributeValue(0x1101);
	            logger.info("serviceName = " +serviceName);
	           // if (serviceName != null) {
	            	//logger.info("service " + serviceName.getValue() + " found " + url);
	                
	            /*
	            
	            	ConnectThread connectThread = new ConnectThread(url);
	            	connectThread.start();
	            	*/
	            	
	            BluetoothConnectionManager bluetoothConnectionManager = BluetoothConnectionManager.getInstance();
	      		  bluetoothConnectionManager.newConnection(url);

	            /*} else {
	                System.out.println("service found " + url);
	            }*/
	        }
	}
}
