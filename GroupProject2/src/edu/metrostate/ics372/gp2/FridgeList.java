package edu.metrostate.ics372.gp2;

/**
 * The FridgeList class is used to maintain a collection of Refridgerators.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Andrew Siegfried
 * 
 */
public class FridgeList extends ItemList<Appliance, String> {
	private static final long serialVersionUID = 1L;
	private static FridgeList fridgeList;

	// Private constructor for singleton pattern.
	private FridgeList() {
	}

	/**
	 * Supports the singleton pattern.
	 * 
	 * @return the singleton object
	 */
	public static FridgeList instance() {
		if (fridgeList == null) {
			return (fridgeList = new FridgeList());
		} else {
			return fridgeList;
		}
	}
}
