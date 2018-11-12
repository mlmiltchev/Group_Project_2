package edu.metrostate.ics372.gp2;

/**
 * The DryerList class is used to maintain a collection of Dryers.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Andrew Siegfried
 * 
 */
public class DryerList extends ItemList<Appliance, String> {
	private static final long serialVersionUID = 1L;
	private static DryerList dryerList;

	// Private constructor for singleton pattern.
	private DryerList() {
	}

	/**
	 * Supports the singleton pattern.
	 * 
	 * @return the singleton object
	 */
	public static DryerList instance() {
		if (dryerList == null) {
			return (dryerList = new DryerList());
		} else {
			return dryerList;
		}
	}
}
