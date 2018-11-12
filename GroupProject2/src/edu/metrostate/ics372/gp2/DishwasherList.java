package edu.metrostate.ics372.gp2;

/**
 * The DishwasherList class is used to maintain a collection of Dishwashers.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Andrew Siegfried
 * 
 */
public class DishwasherList extends ItemList<Appliance, String> {
	private static final long serialVersionUID = 1L;
	private static DishwasherList dishWasherList;

	// Private constructor for singleton pattern.
	private DishwasherList() {
	}

	/**
	 * Supports the singleton pattern.
	 * 
	 * @return the singleton object
	 */
	public static DishwasherList instance() {
		if (dishWasherList == null) {
			return (dishWasherList = new DishwasherList());
		} else {
			return dishWasherList;
		}
	}
}
