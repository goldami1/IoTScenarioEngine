package web.model;

import java.util.LinkedList;

import db.DBHandler;
import engine.Device;

public class UserDevicesService {
	
	DBHandler db = DBHandler.getInstance();
	
	public LinkedList<Device> getDevices(short id) {
		return db.getDevices(id);
	}

	public boolean addDevice(short attribute) {
		return db.addDevice(null);
	}

}
