package edu.metrostate.ics372.gp2;

/**
 * The FurnaceList class is used to maintain a collection of Furnaces.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Andrew Siegfried
 * 
 */
public class FurnaceList extends ItemList<Appliance, String> {
	private static final long serialVersionUID = 1L;
	private static FurnaceList furnaceList;

	// Private constructor for singleton pattern.
	private FurnaceList() {
	}

	/**
	 * Supports the singleton pattern.
	 * 
	 * @return the singleton object
	 */
	public static FurnaceList instance() {
		if (furnaceList == null) {
			return (furnaceList = new FurnaceList());
		} else {
			return furnaceList;
		}
	}
}
