package edu.metrostate.ics372.gp2;

/**
 * The RangeList class is used to maintain a collection of Kitchen Ranges.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Andrew Siegfried
 * 
 */
public class RangeList extends ItemList<Appliance, String> {
	private static final long serialVersionUID = 1L;
	private static RangeList rangeList;

	// Private constructor for singleton pattern.
	private RangeList() {
	}

	/**
	 * Supports the singleton pattern.
	 * 
	 * @return the singleton object
	 */
	public static RangeList instance() {
		if (rangeList == null) {
			return (rangeList = new RangeList());
		} else {
			return rangeList;
		}
	}
}
