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
import java.util.Map.Entry;
/**
 * The Store class is used for calling the primary business functions of the
 * application. It keeps track of all customers, back orders, and appliances in the
 * inventory.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher, Mihail Miltchev
 * 
 */
public class Store implements Serializable {
	private static final long serialVersionUID = 1L;
	private Inventory inventory;
	private CustomerList customerList;
	private ApplianceList applianceList;
	private BackOrderList backOrderList;
	private RepairPlanLog repairPlanLog;
	private static Store store;
	private double totalSales = 0.0;
	private double totalRepairPlanIncome = 0.0;
	

	/**
	 * Private constructor using the singleton pattern. Creates the inventory,
	 * customer, appliance, and back order collection objects.
	 */
	private Store() {
		inventory = Inventory.instance();
		customerList = CustomerList.instance();
		applianceList = ApplianceList.instance();
		backOrderList = BackOrderList.instance();
		repairPlanLog = RepairPlanLog.instance();
		totalSales = 0.0;
		totalRepairPlanIncome = 0.0;
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
	 * Organizes the operations for adding a appliance.
	 * 
	 * @param brand
	 *            appliance brand
	 * @param model
	 *            appliance model
	 * @param price
	 *            appliance price
	 * @return true if the appliance model and brand could be added
	 */
	public Appliance addAppliance(int type, String brand, String model, double price) {
		Appliance appliance = null;
		switch (type) {
		case Constants.TYPE_WASHER:
			Washer washer = new Washer(brand, model, price);
			if (applianceList.add(washer)) {
				appliance = washer;
			}
			break;
		case Constants.TYPE_DRYER:
			Dryer dryer = new Dryer(brand, model, price);
			if (applianceList.add(dryer)) {
				appliance = dryer;
			}
			break;
		case Constants.TYPE_RANGE:
			Range range = new Range(brand, model, price);
			if (applianceList.add(range)) {
				appliance = range;
			}
			break;
		case Constants.TYPE_DISHWASHER:
			Dishwasher dishwasher = new Dishwasher(brand, model, price);
			if (applianceList.add(dishwasher)) {
				appliance = dishwasher;
			}
			break;
		case Constants.TYPE_FRIDGE:
			Fridge fridge = new Fridge(brand, model, price);
			if (applianceList.add(fridge)) {
				appliance = fridge;
			}
			break;
		case Constants.TYPE_FURNACE:
			Furnace furnace = new Furnace(brand, model, price);
			if (applianceList.add(furnace)) {
				appliance = furnace;
			}
			break;
		}
		return appliance;
	}

	/**
	 * Organizes the operations for adding a appliance to the inventory.
	 * 
	 * @param appliance
	 *            the appliance to add to the inventory
	 * @param quantity
	 *            the number of appliances to add
	 * @return true if the appliance could be added to the inventory
	 */
	public boolean addApplianceToInventory(String brand, String model, int quantity) {
		Appliance item = searchAppliances(brand + model);
		boolean result = inventory.insertAppliance(item, quantity);
		if (result) {
			processBackOrders();
		}
		return result;
	}

	public boolean purchaseAppliance(String id, String brand, String model, int quantity) {
		Appliance appliance = store.searchAppliances(brand + model);
		Customer customer = null;
		boolean purchase = customerList.findUser(id, customerList);
		Iterator<Customer> customers = customerList.iterator();

		if (purchase) {
			if (appliance == null) {
				System.out.println("No such appliance exists.");
				return false;
			} else {
				purchase = inventory.findAppliance(brand, model, quantity);
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
					Iterator<Appliance> appliances = applianceList.iterator();
					while (appliances.hasNext()) {
						Appliance nextAppliance = appliances.next();
						if (nextAppliance.matches(appliance.getBrand() + appliance.getModel())) {
							customer.purchase(nextAppliance);
							totalSales += nextAppliance.getPrice();
							customer.increasePurchaseCharge(nextAppliance.getPrice());
						}
					}
					count--;
				}
				inventory.updateQuantity(appliance.getBrand(), appliance.getModel(), quantity);
			} else {
				if(appliance.getType() != Constants.TYPE_FURNACE) {
					if (addToBackOrder(customer, appliance, quantity)) {
					System.out.println("Not enough of " + brand + " " + model +" in stock. Back order placed for " + quantity + " units.");
					} else { 
						System.out.println("The back order could not be placed."); 
					}
				} else {
					System.out.println("No furnace " + brand + " " + model + " in stock.");
				}
			}
		} else {
			System.out.println("Invalid Customer ID.");
		}

		return purchase;
	}

	// Private helper method to quickly add a back order.
	private boolean addToBackOrder(Customer customer, Appliance appliance, int quantity) {
		return backOrderList.insertBackOrder(new BackOrder(customer, appliance, quantity));
	}

	/**
	 * Organizes the operations for displaying all customers in the system.
	 * 
	 * @return a list of all customers in the system
	 */
	public String listCustomers() {
		//get iterator from customerList
		//get map from repair plan, any duplicates mark yes in plan
		return customerList.toString();
	}
	
	/**
	 * Organizes the operations for displaying all backorders in the system.
	 * 
	 * @return a list of all backorders in the system
	 */
	public void listBackOrders() {
		BackOrderVisitor visitor = new BackOrderVisitor();
		Iterator<BackOrder> backOrders = backOrderList.iterator();
		while (backOrders.hasNext()) {
			backOrders.next().accept(visitor);
        }
	}

	/**
	 * Organizes the operations for displaying all appliances in the inventory.
	 * 
	 * @return a list of all appliances in the inventory
	 */
	public String listAppliances(int type) {
		Iterator<Appliance> appliances = inventory.getAllAppliances(type);
		Iterator<Appliance> applianceLog = applianceList.getIterator(type);
		StringBuilder stringBuilder = new StringBuilder();
		Map<Appliance, Integer> applianceCount = new LinkedHashMap<Appliance, Integer>();
		while (applianceLog.hasNext()) {
			applianceCount.put(applianceLog.next(), 0);
		}
		while (appliances.hasNext()) {
			applianceCount.merge(appliances.next(), 1, (x, y) -> x + y);
		}
		for (Map.Entry<Appliance, Integer> entry : applianceCount.entrySet()) {
			stringBuilder.append(entry.getKey() + " inventory count: " + entry.getValue() + "\n");
		}

		return stringBuilder.toString();
	}

	/**
	 * Processes back orders for the store.
	 */
	public void processBackOrders() {
		Iterator<BackOrder> backOrderLog = backOrderList.iterator();

		while (backOrderLog.hasNext()) {
			BackOrder backOrder = backOrderLog.next();
			Customer customer = backOrder.getCustomer();
			Appliance appliance = backOrder.getAppliance();
			int quantity = backOrder.getQuantity();
			boolean purchase = inventory.findAppliance(appliance.getBrand(), appliance.getModel(), quantity);

			if (purchase) {
				double sale = 0.0;
				int count = quantity;
				while (count != 0) {
					Iterator<Appliance> appliances = applianceList.iterator();
					while (appliances.hasNext()) {
						Appliance temp = appliances.next();
						if (temp.matches(appliance.getBrand() + appliance.getModel())) {
							customer.purchase(temp);
							totalSales += temp.getPrice();
							sale += temp.getPrice();
						}
					}
					count--;
				}
				inventory.updateQuantity(appliance.getBrand(), appliance.getModel(), quantity);
				System.out.println("A backorder for " + customer.getId() + " purchasing " + quantity + " of " + appliance
						+ " has been processed for: " + String.format("$%.2f.%n", (float) sale));
			}
		}
	}

	/**
	 * Searches for a given appliance.
	 * 
	 * @param applianceId
	 *            ID of the appliance
	 * @return true if the appliance is in the appliance collection
	 */
	public Appliance searchAppliances(String applianceId) {
		return applianceList.search(applianceId);
	}

	/**
	 * Searches for a given customer.
	 * 
	 * @param customerId ID of the customer
	 * @return Customer otherwise null
	 */
	public Customer searchCustomers(String customerId) {
		return customerList.search(customerId);
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
	 * Getter method to retrieve the total for repair plan incomes.
	 * 
	 * @return the repair plan income
	 */
	public double getRepairPlanIncome() {
		return totalRepairPlanIncome;
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

	public void enrollInRepairPlan(String customerId, String applianceId) {
		Appliance appliance = searchAppliances(applianceId);
		Customer customer = searchCustomers(customerId);
		if(customer != null) {
			if(appliance != null && (appliance.getType() == Constants.TYPE_WASHER || appliance.getType() == Constants.TYPE_DRYER)) {
				repairPlanLog.insertRepairPlan(customer, (ClothesAppliance) appliance);
				System.out.println("Customer: " + customer + " with appliance: " + appliance + " entered for a repair plan.");
			} else {
				System.out.println("Appliance is not eligable for a repair plan.");
			}
		} else {
			System.out.println("Customer does not exist.");
		}
	}

	public void withdrawFromRepairPlan(String customerId, String applianceId) {
		Appliance appliance = searchAppliances(applianceId);
		Customer customer = searchCustomers(customerId);
		if(customer != null) {
			if(appliance != null && (appliance.getType() == Constants.TYPE_WASHER || appliance.getType() == Constants.TYPE_DRYER)) {
				repairPlanLog.withdrawRepairPlan(searchCustomers(customerId), (ClothesAppliance) appliance);
			} else {
				System.out.println("Appliance is not eligable for a repair plan.");
			}
		} else {
			System.out.println("Customer does not exist.");
		}
	}

	public void billRepairPlanCustomers() {
		repairPlanLog.billCustomers();
		double bill = 0;
		Map<Customer, ArrayList<ClothesAppliance>> log = repairPlanLog.iterator();
		for (Entry<Customer, ArrayList<ClothesAppliance>> entry : log.entrySet()) {
			for (ClothesAppliance appliance : entry.getValue()) {
				totalRepairPlanIncome += appliance.getMonthlyRepairCost();
				bill += appliance.getMonthlyRepairCost();
			}
		}
		System.out.println("All customers have been billed for a total of " + String.format("$%.2f.%n", (float) bill));
	}

	public void listRepairPlanCustomers() {
		Map<Customer, ArrayList<ClothesAppliance>> log = repairPlanLog.iterator();
		for (Entry<Customer, ArrayList<ClothesAppliance>> entry : log.entrySet()) {
			for (ClothesAppliance appliance : entry.getValue()) {
				System.out.println("Customer: " + entry.getKey() + " Account balance: " + String.format("$%.2f", (float) entry.getKey().getBalanceDue()) + ". Appliance in repair plan: " + appliance);
			}
		}
	}

}
