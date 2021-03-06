package edu.metrostate.ics372.gp2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The ApplianceList class is used to maintain a collection of appliances.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher
 * 
 */
public class ApplianceList extends ItemList<Appliance, String> {
	private static final long serialVersionUID = 1L;
	private static ApplianceList applianceList;

	// Private constructor for singleton pattern.
	private ApplianceList() {
	}

	/**
	 * Supports the singleton pattern.
	 * 
	 * @return the singleton object
	 */
	public static ApplianceList instance() {
		if (applianceList == null) {
			return (applianceList = new ApplianceList());
		} else {
			return applianceList;
		}
	}
	
	public Iterator<Appliance> getIterator(int type) {
		Iterator<Appliance> appliances = this.iterator();
		if(type == 0) {
			return appliances;
		}
		ArrayList<Appliance> list = new ArrayList<Appliance>(); 
		while (appliances.hasNext()) {
			Appliance item = appliances.next();
			if(item.getType() == type) {
				list.add(item);
			}
		}
		return list.iterator();
	}
}
