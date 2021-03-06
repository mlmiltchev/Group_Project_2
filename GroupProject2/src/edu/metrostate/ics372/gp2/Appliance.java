package edu.metrostate.ics372.gp2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * The Appliance class is used to manage all Appliances.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher, Andrew Siegfried
 * 
 */
public class Appliance implements Serializable, IMatchable<String> {
	private static final long serialVersionUID = 1L;
	private String brand;
	private String model;
	private String id;
	private double price;
	private int type;
	private List<BackOrder> backOrders = new LinkedList<BackOrder>();

	

	/**
	 * Will represent an appliance.
	 * 
	 * @param brand
	 *            the appliance's brand
	 * @param model
	 *            the appliance's model
	 * @param price
	 *            the appliance's price
	 */
	public Appliance(String brand, String model, double price, int type) {
		this.brand = brand;
		this.model = model;
		this.id = brand + model;
		this.price = price;
		this.type = type;
	}

	/**
	 * Getter for the appliance's brand.
	 * 
	 * @return appliance brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * Getter for the appliance's type.
	 * 
	 * @return appliance type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Getter for the appliance's model.
	 * 
	 * @return appliance model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Getter for the appliance's ID.
	 * 
	 * @return appliance ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Getter for the appliance's price.
	 * 
	 * @return appliance price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Getter for the appliance's price.
	 * 
	 * @return appliance price
	 */
	private String getTypeID() {
		Map<Integer, String> typeIDs = new LinkedHashMap<Integer, String>();
		typeIDs.put(Constants.TYPE_DISHWASHER, "Dishwasher");
		typeIDs.put(Constants.TYPE_DRYER, "Dryer");
		typeIDs.put(Constants.TYPE_FRIDGE, "Refrigerator");
		typeIDs.put(Constants.TYPE_FURNACE, "Furnace");
		typeIDs.put(Constants.TYPE_RANGE, "Kitchen Range");
		typeIDs.put(Constants.TYPE_WASHER, "Washer");
		return typeIDs.get(getType());
	}

	/**
	 * Adds one back order to this appliance.
	 * 
	 * @param backOrder
	 *            the back order for this appliance
	 */
	public void placeBackOrder(BackOrder backOrder) {
		backOrders.add(backOrder);
	}

	/**
	 * Removes a back order for a specific customer.
	 * 
	 * @param customerId
	 *            customer whose back order is to be removed
	 * @return true if the back order could be removed
	 */
	public boolean removeBackOrder(String customerId) {
		for (ListIterator<BackOrder> iterator = backOrders.listIterator(); iterator.hasNext();) {
			BackOrder backOrder = iterator.next();
			String id = backOrder.getCustomer().getId();
			if (id.equals(customerId)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets attribute from user
	 */
	public String getAttribute(String inputText) {
		BufferedReader readIn = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(inputText);
		String attribute = "";
		try {
			attribute = readIn.readLine();
			if (attribute.length() < 1) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid Input. Try Again.");
			getAttribute(inputText);
		}
		return attribute;
	}

	/**
	 * Returns a valid back order.
	 * 
	 * @return the next valid back order
	 */
	public BackOrder getNextBackOrder() {
		for (ListIterator<BackOrder> iterator = backOrders.listIterator(); iterator.hasNext();) {
			BackOrder backOrder = iterator.next();
			iterator.remove();
			return backOrder;
		}
		return null;
	}

	/**
	 * Checks whether there is a back order on this washer.
	 * 
	 * @return true if there is a back order
	 */
	public boolean hasBackOrder() {
		ListIterator<BackOrder> iterator = backOrders.listIterator();
		if (iterator.hasNext()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns an iterator for the back orders.
	 * 
	 * @return iterator for the back orders on this washer.
	 */
	public Iterator<BackOrder> getBackOrders() {
		return backOrders.iterator();
	}

	@Override
	public boolean matches(String key) {
		if (this.id.equals(key)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * String form of the appliance.
	 * 
	 */
	@Override
	public String toString() {
		return String.format("Type: %s, Brand: \'%s\', Model: \'%s\', Price: $%.2f", getTypeID().toUpperCase(),
				getBrand(), getModel(), getPrice());
	}
}
