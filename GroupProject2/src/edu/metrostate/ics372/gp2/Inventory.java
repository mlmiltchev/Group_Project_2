package edu.metrostate.ics372.gp2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Inventory class is used to maintain a collection of appliances and their
 * respective quantities currently in the inventory.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher
 * 
 */
public class Inventory extends ItemList<Appliance, String> {

	private static final long serialVersionUID = 1L;
	private static Inventory inventory;

	// Private constructor for the singleton pattern.
	private Inventory() {
	}

	/**
	 * Supports the singleton pattern.
	 * 
	 * @return the singleton object
	 */
	public static Inventory instance() {
		if (inventory == null) {
			return (inventory = new Inventory());
		} else {
			return inventory;
		}
	}

	/**
	 * Inserts a appliance into the collection.
	 * 
	 * @param appliance
	 *            the appliance to be inserted
	 * @return true if the appliance could be inserted
	 */
	public boolean insertAppliance(Appliance item, int quantity) {
		return super.add(item, quantity);
	}

	/**
	 * Updates the quantity of a appliance for a specified quantity.
	 * 
	 * @param brand
	 *            the appliance brand
	 * @param model
	 *            the appliance model
	 * @param quantity
	 *            quantity to remove
	 */
	public void updateQuantity(String brand, String model, int quantity) {
		Iterator<Appliance> appliances = this.iterator();
		while (appliances.hasNext()) {
			Appliance appliance = appliances.next();
			if (appliance.matches(brand + model) && (quantity != 0)) {
				appliances.remove();
				quantity--;
			}
		}
	}

	/**
	 * Finds a appliance from the collection.
	 * 
	 * @param brand
	 *            the appliance brand
	 * @param model
	 *            the appliance model
	 * @param quantity
	 *            quantity desired to purchase
	 * @return true if the appliance was found with quantity met
	 */
	public boolean findAppliance(String brand, String model, int quantity) {
		Iterator<Appliance> appliances = this.iterator();
		boolean found = false;
		int count = 0;
		while (appliances.hasNext()) {
			Appliance item = appliances.next();
			if (item.matches(brand + model)) {
				count++;
			}
		}

		if (count >= quantity) {
			found = true;
		}
		return found;
	}

	/**
	 * Returns all appliances and their quantities currently in the inventory.
	 * 
	 * @return all appliances in the inventory
	 */
	/*public Iterator<appliance> getAllappliances() {
		return this.iterator();
	}*/

	public Iterator<Appliance> getAllAppliances(int type) {
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
