package edu.metrostate.ics372.gp2;

/**
 * The WasherList class is used to maintain a collection of washers.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher
 * 
 */
public class WasherList extends ItemList<Appliance, String> {
	private static final long serialVersionUID = 1L;
	private static WasherList washerList;

	// Private constructor for singleton pattern.
	private WasherList() {
	}

	/**
	 * Supports the singleton pattern.
	 * 
	 * @return the singleton object
	 */
	public static WasherList instance() {
		if (washerList == null) {
			return (washerList = new WasherList());
		} else {
			return washerList;
		}
	}
}
