package edu.metrostate.ics372.gp2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Store class is used for calling the primary business functions of the
 * application. It keeps track of all customers, back orders, and washers in the
 * inventory.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher
 * 
 */
public class Store implements Serializable {
	private static final long serialVersionUID = 1L;
	private Inventory inventory;
	private CustomerList customerList;
	private WasherList washerList;
	private DishwasherList dishwasherList;
	private DryerList dryerList;
	private FridgeList fridgeList;
	private FurnaceList furnaceList;
	private RangeList rangeList;
	private BackOrderList backOrderList;
	private static Store store;
	private double totalSales = 0.0;
	private static final int TYPE_ALL = 0;
	private static final int TYPE_DISHWASHER = 1;
	private static final int TYPE_DRYER = 2;
	private static final int TYPE_FRIDGE = 3;
	private static final int TYPE_FURNACE = 4;
	private static final int TYPE_RANGE = 5;
	private static final int TYPE_WASHER = 6;
	private Appliance appliance;


	/**
	 * Private constructor using the singleton pattern. Creates the inventory,
	 * customer, washer, and back order collection objects.
	 */
	private Store() {
		inventory = Inventory.instance();
		customerList = CustomerList.instance();
		washerList = WasherList.instance();
		dishwasherList = DishwasherList.instance();
		dryerList = DryerList.instance();
		fridgeList = FridgeList.instance();
		furnaceList = FurnaceList.instance();
		rangeList = RangeList.instance();
		backOrderList = BackOrderList.instance();
		totalSales = 0.0;
	}

	/**
	 * Supports the singleton pattern.
	 * 
	 * @return the singleton object
	 */
	public static Store instance() {
		if (store == null) {
			CustomerIdServer.instance(); // instantiate all singletons
			return (store = new Store());
		} else {
			return store;
		}
	}
	
	/**
	 * Displays the print screen.
	 * 
	 */
	public void displayApplianceChoices() {
		System.out.println("Enter a number between 0 and 6 as explained below: \n");
		System.out.println("[" + TYPE_ALL + "] List all appliances.");
		System.out.println("[" + TYPE_DISHWASHER + "] List all dishwashers.");
		System.out.println("[" + TYPE_DRYER + "] List all dryers.");
		System.out.println("[" + TYPE_FRIDGE + "] List all refridgerators.");
		System.out.println("[" + TYPE_FURNACE + "] List all furnaces.");
		System.out.println("[" + TYPE_RANGE + "] List all kitchen ranges.");
		System.out.println("[" + TYPE_WASHER + "] List all washers.");
	}

	/**
	 * Organizes the operations for adding a customer.
	 * 
	 * @param name
	 *            customer name
	 * @param address
	 *            customer address
	 * @param phone
	 *            customer phone
	 * @return the Customer object created
	 */
	public Customer addCustomer(String name, String phoneNumber) {
		Customer customer = new Customer(name, phoneNumber);
		if (customerList.insertCustomer(customer)) {
			return (customer);
		}
		return null;
	}

	/**
	 * Organizes the operations for adding a washer.
	 * 
	 * @param brand
	 *            washer brand
	 * @param model
	 *            washer model
	 * @param price
	 *            washer price
	 * @return true if the washer model and brand could be added
	 */
	public Appliance addAppliance(int type, String brand, String model, double price) {		
		switch (type) {
			case TYPE_WASHER:				
				Washer washer = new Washer(brand, model, price);
				if (washerList.add(washer)) {
					appliance = washer;
				}
				break;
			case TYPE_DRYER:
				Dryer dryer = new Dryer(brand, model, price);
				if (dryerList.add(dryer)) {
					appliance = dryer;
				}
				break;
			case TYPE_RANGE:
				Range range = new Range(brand, model, price);
				if (rangeList.add(range)) {
					appliance = range;
				}
				break;
			case TYPE_DISHWASHER:
				Dishwasher dishwasher = new Dishwasher(brand, model, price);
				if (dishwasherList.add(dishwasher)) {
					appliance = dishwasher;
				}
				break;
			case TYPE_FRIDGE:
				Fridge fridge = new Fridge(brand, model, price);
				if (fridgeList.add(fridge)) {
					appliance = fridge;
				}
				break;
			case TYPE_FURNACE:
				Furnace furnace = new Furnace(brand, model, price);
				if (furnaceList.add(furnace)) {
					appliance = furnace;
				}
				break;
		}
		return appliance;
	}

	/**
	 * Organizes the operations for adding a washer to the inventory.
	 * 
	 * @param washer
	 *            the washer to add to the inventory
	 * @param quantity
	 *            the number of washers to add
	 * @return true if the washer could be added to the inventory
	 */
	public boolean addWasherToInventory(String brand, String model, int quantity) {
		Washer washer = searchWashers(brand + model);
		boolean result = inventory.insertWasher(washer, quantity);
		if (result) {
			processBackOrders();
		}
		return result;
	}

	public boolean purchaseWasher(String id, String brand, String model, int quantity) {
		Washer washer = store.searchWashers(brand + model);
		Customer customer = null;
		boolean purchase = customerList.findUser(id, customerList);
		Iterator<Customer> customers = customerList.iterator();
		
		if (purchase) {
			if (washer == null) {
				System.out.println("No such washer exists.");
				return false;
			} else {
				purchase = inventory.findWasher(brand, model, quantity);
			}
			while (customers.hasNext()) {
				Customer nextCustomer = customers.next();
				if (nextCustomer.matches(id)) {
					customer = nextCustomer;
				}
			}
			if (purchase) {
				int count = quantity;
				while (count != 0) {
					Iterator<Washer> washers = washerList.iterator();
					while (washers.hasNext()) {
						Washer nextWasher = washers.next();
						if (nextWasher.matches(washer.getBrand() + washer.getModel())) {
							customer.purchase(nextWasher);
							totalSales += nextWasher.getPrice();
						}
					}
					count--;
				}	
				inventory.updateQuantity(washer.getBrand(), washer.getModel(), quantity);
			} else {
				if (addToBackOrder(customer, washer, quantity)) {
					System.out.println("Not enough of " + brand + " " + model + " in stock. Back order placed for " + quantity + " units.");
				} else {
					System.out.println("The back order could not be placed.");
				}

			}
		} else {
			System.out.println("Invalid Customer ID.");
		}

		return purchase;
	}

	// Private helper method to quickly add a back order.
	private boolean addToBackOrder(Customer customer, Washer washer, int quantity) {
		return backOrderList.insertBackOrder(new BackOrder(customer, washer, quantity));
	}

	/**
	 * Organizes the operations for displaying all customers in the system.
	 * 
	 * @return a list of all customers in the system
	 */
	public String listCustomers() {
		return customerList.toString();
	}

	/**
	 * Organizes the operations for displaying all washers in the inventory.
	 * @return a list of all washers in the inventory
	 */
	public String listAppliances() {
		String output = "";
		try {
			displayApplianceChoices();
			Scanner input = new Scanner(System.in);
			int value = input.nextInt();
			input.close();
			if (value >= TYPE_ALL && value <= TYPE_FURNACE) {
				output = handlePrinting(value);
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			return "Invalid entry please try again.";
		}
		return output;
	}
	
	public String handlePrinting(int type) {
		Iterator<Appliance> appliances = inventory.getAllAppliances(type);
		Iterator<Appliance> applianceLog = getListIterator(type);
		StringBuilder stringBuilder = new StringBuilder();
		Map<Appliance,Integer> applianceCount = new LinkedHashMap<Appliance,Integer>();
		while (applianceLog.hasNext()) {
			applianceCount.put(applianceLog.next(), 0);
		}
		while (appliances.hasNext()) {
			applianceCount.merge(appliances.next(), 1, (x, y) -> x + y);
		}
		for (Map.Entry<Appliance,Integer> entry : applianceCount.entrySet()) {
			stringBuilder.append(entry.getKey() + " inventory count: " + entry.getValue() +"\n");
		}
		
		return stringBuilder.toString();
	}

	private Iterator<Appliance> getListIterator(int type) {
		Iterator<Appliance> appliances = null;
		switch (type) {
			case TYPE_ALL:
				appliances = combineIterators();
				break;
			case TYPE_WASHER:
				appliances = washerList.iterator();
				break;
			case TYPE_DRYER:
				appliances = dryerList.iterator();
				break;
			case TYPE_RANGE:
				appliances = rangeList.iterator();
				break;
			case TYPE_DISHWASHER:
				appliances = dishwasherList.iterator();
				break;
			case TYPE_FRIDGE:
				appliances = fridgeList.iterator();
				break;
			case TYPE_FURNACE:
				appliances = furnaceList.iterator();
				break;
		}
		return appliances;
	}

	private Iterator<Appliance> combineIterators() {
		ArrayList<Appliance> list = new ArrayList<Appliance>(); 
		while (washerList.iterator().hasNext()) {
			list.add(washerList.iterator().next());
		}
		while (dryerList.iterator().hasNext()) {
			list.add(washerList.iterator().next());
		}
		while (rangeList.iterator().hasNext()) {
			list.add(washerList.iterator().next());
		}
		while (dishwasherList.iterator().hasNext()) {
			list.add(washerList.iterator().next());
		}
		while (fridgeList.iterator().hasNext()) {
			list.add(washerList.iterator().next());
		}
		while (fridgeList.iterator().hasNext()) {
			list.add(furnaceList.iterator().next());
		}
		return list.iterator();
	}

	/**
	 * Processes back orders for the store.
	 */
	public void processBackOrders() {
		Iterator<BackOrder> backOrderLog = backOrderList.iterator();
		
		while (backOrderLog.hasNext()) {
			BackOrder backOrder = backOrderLog.next();
			Customer customer = backOrder.getCustomer();
			Washer washer = backOrder.getWasher();
			int quantity = backOrder.getQuantity();
			boolean purchase = inventory.findWasher(washer.getBrand(), washer.getModel(), quantity);
			
			if (purchase) {
				double sale = 0.0;
				int count = quantity;
				while (count != 0) {
					Iterator<Washer> washers = washerList.iterator();
					while (washers.hasNext()) {
						Washer temp = washers.next();
						if (temp.matches(washer.getBrand() + washer.getModel())) {
							customer.purchase(temp);
							totalSales += temp.getPrice();
							sale += temp.getPrice();
						}
					}
					count--;
				}	
				inventory.updateQuantity(washer.getBrand(), washer.getModel(), quantity);
				System.out.println("A backorder for " + customer.getId() + " purchasing " + quantity + " of " + washer+" has been processed for: " + String.format("$%.2f.%n", (float)sale));
			}
		}
	}

	/**
	 * Searches for a given washer.
	 * 
	 * @param washerId
	 *            ID of the washer
	 * @return true if the washer is in the washer collection
	 */
	public Washer searchWashers(String washerId) {
		return washerList.search(washerId);
	}

	/**
	 * Getter method to retrieve the total sales.
	 * 
	 * @return the total sales
	 */
	public double getTotalSales() {
		return totalSales;
	}

	/**
	 * Retrieves a deserialized version of the Store from disk.
	 * 
	 * @return a Store object
	 */
	public static Store retrieve() {
		try {
			FileInputStream file = new FileInputStream("StoreData");
			ObjectInputStream input = new ObjectInputStream(file);
			store = (Store) input.readObject();
			CustomerIdServer.retrieve(input);
			return store;
		} catch (IOException ioe) {
			// ioe.printStackTrace();
			System.out.println("File doesnt exist; creating new store...");
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}

	/**
	 * Serializes the Store object.
	 * 
	 * @return true if the data could be saved
	 */
	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("StoreData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(store);
			output.writeObject(CustomerIdServer.instance());
			file.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	/**
	 * String form of the Store.
	 * 
	 */
	@Override
	public String toString() {
		return inventory + "\n" + customerList;
	}
}
