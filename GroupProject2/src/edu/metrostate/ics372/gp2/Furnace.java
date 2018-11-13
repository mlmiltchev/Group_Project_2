package edu.metrostate.ics372.gp2;

/**
 * The Furnace class is used to manage a single furnace.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Andrew Siegfried
 * 
 */
public class Furnace extends Appliance {
	private static final long serialVersionUID = 1L;
	private int maxHeatOutput;

	/**
	 * Represents a single washer.
	 * 
	 * @param brand
	 *            the washer's brand
	 * @param model
	 *            the washer's model
	 * @param price
	 *            the washer's price
	 */
	public Furnace(String brand, String model, double price) {
		super(brand, model, price, Constants.TYPE_FURNACE);
		setMaxHeatOutput();
	}

	private void setMaxHeatOutput() {
		try {
			this.maxHeatOutput = Integer.parseInt(getAttribute("Enter Maximum Heating Output in BTU:\n"));
		} catch (Exception e) {
			System.out.println("Invalid input, please enter a Maximum Heat Output in BTU.");
			setMaxHeatOutput();
		}
	}

	public Integer getMaxHeatOutput() {
		return maxHeatOutput;
	}

	@Override
	public String toString() {
		return String.format("%s, BTU: %d", super.toString(), getMaxHeatOutput());
	}

}
