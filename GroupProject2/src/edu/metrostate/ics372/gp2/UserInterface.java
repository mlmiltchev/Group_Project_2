package edu.metrostate.ics372.gp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * The UserInterface class contains the main entry point, and provides end-users
 * a means of interfacing with the application through the command-line.
 * 
 * ICS372-01 - Group Project #2
 * 
 * @author Shannon Fisher, Mihail Miltchev
 * 
 */
public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Store store;
	private static final int EXIT = 0;
	private static final int ADD_CUSTOMER = 1;
	private static final int ADD_APPLIANCE = 2;
	private static final int ADD_TO_INVENTORY = 3;
	private static final int PURCHASE = 4;
	private static final int LIST_CUSTOMERS = 5;
	private static final int LIST_APPLIANCES = 6;
	private static final int DISPLAY_TOTAL = 7;
	private static final int ENROLL_REPAIR_PLAN = 8;
	private static final int WITHDRAW_REPAIR_PLAN = 9;
	private static final int BILL_REPAIR_PLAN = 10;
	private static final int LIST_REPAIR_PLAN_CUSTOMERS = 11;
	private static final int LIST_BACKORDERS = 12;
	private static final int SAVE = 13;
	private static final int HELP = 14;

	/**
	 * Made private for the singleton pattern. Conditionally looks for any saved
	 * data. Otherwise, it gets a singleton Store object.
	 * 
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and use it?")) {
			retrieve();
		} else {
			store = Store.instance();
		}
	}

	/**
	 * Supports the singleton pattern.
	 * 
	 * @return the singleton object
	 * 
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}
	
	/**
	 * Orchestrates the whole process. Calls the appropriate method for the
	 * different functionalities.
	 * 
	 */
	public void process() {
		displayMenu();
		int command = getCommand();
		while (command != EXIT) {
			switch (command) {
			case ADD_CUSTOMER:
				addCustomer();
				break;
			case ADD_APPLIANCE:
				addAppliance();
				break;
			case ADD_TO_INVENTORY:
				addToInventory();
				break;
			case PURCHASE:
				purchase();
				break;
			case LIST_CUSTOMERS:
				listCustomers();
				break;
			case LIST_APPLIANCES:
				listAppliances();
				break;
			case DISPLAY_TOTAL:
				displayTotal();
				break;
			case SAVE:
				save();
				break;
			case HELP:
				displayMenu();
				break;
			case ENROLL_REPAIR_PLAN:
				enrollInRepairPlan();
				break;
			case WITHDRAW_REPAIR_PLAN:
				withdrawFromRepairPlan();
				break;
			case BILL_REPAIR_PLAN:
				billRepairPlanCustomers();
				break;
			case LIST_REPAIR_PLAN_CUSTOMERS:
				listRepairPlanCustomers();
				break;
			case LIST_BACKORDERS:
				listBackorders();
				break;
			}
			displayMenu();
			command = getCommand();
		}
		System.out.println("Goodbye.");
	}

	/**
	 * Gets a token after prompting
	 * 
	 * @param prompt
	 *            - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 * 
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Queries for a yes or no and returns true for yes and false for no.
	 * 
	 * @param prompt
	 *            The string to be prepended to the yes/no prompt.
	 * @return true for yes and false for no
	 * 
	 */
	public boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * Converts the String to an integer.
	 * 
	 * @param prompt
	 *            the String for prompting
	 * @return the integer corresponding to the String.
	 * 
	 */
	public int getInteger(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				if(number < 0) {
					throw new IndexOutOfBoundsException();
				}
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number.");
			}catch(IndexOutOfBoundsException oob) {
				System.out.println("Not a valid selection.");
			}
		} while (true);
	}

	/**
	 * Converts the String to a double.
	 * 
	 * @param prompt
	 *            the String for prompting
	 * @return the double corresponding to the String.
	 * 
	 */
	public double getDouble(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Double number = Double.valueOf(item);
				return number.doubleValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number.");
			}
		} while (true);
	}

	/**
	 * Prompts for a command from the keyboard
	 * 
	 * @return a valid command
	 * 
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken(""));
				if (value >= EXIT && value <= HELP) {
					return value;
				} else {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException nfe) {
				System.out.print("Invalid entry please try again.");
			}
		} while (true);
	}

	/**
	 * Displays the menu screen.
	 * 
	 */
	public void displayMenu() {
		System.out.println("Enter a number between 0 and 14 as explained below: \n");
		System.out.println("[" + ADD_CUSTOMER + "] Add a customer.");
		System.out.println("[" + ADD_APPLIANCE + "] Add an appliance.");
		System.out.println("[" + ADD_TO_INVENTORY + "] Add a appliance to inventory.");
		System.out.println("[" + PURCHASE + "] Purchase an appliance.");
		System.out.println("[" + LIST_CUSTOMERS + "] Display all customers.");
		System.out.println("[" + LIST_APPLIANCES + "] Display all appliances.");
		System.out.println("[" + DISPLAY_TOTAL + "] Display total sales.");
		System.out.println("[" + ENROLL_REPAIR_PLAN + "] Enroll customer in repair plan.");
		System.out.println("[" + WITHDRAW_REPAIR_PLAN + "] Withdraw customer from repair plan.");
		System.out.println("[" + BILL_REPAIR_PLAN + "] Bill repair plan customers.");
		System.out.println("[" + LIST_REPAIR_PLAN_CUSTOMERS + "] Display repair plan customers.");
		System.out.println("[" + LIST_BACKORDERS + "] Display backorders.");
		System.out.println("[" + SAVE + "] Save data.");
		System.out.println("[" + HELP + "] Display list of commands.");
		System.out.println("[" + EXIT + "] Exit Application.");
	}
	
	public String displayApplianceChoices() {
		StringBuilder displayApplianceString = new StringBuilder();		
		displayApplianceString.append("\n[" + Constants.TYPE_DISHWASHER + "] Dishwasher.");									
		displayApplianceString.append("\n[" + Constants.TYPE_DRYER + "] Dryer.");
		displayApplianceString.append("\n[" + Constants.TYPE_FRIDGE + "] Refrigerator.");
		displayApplianceString.append("\n[" + Constants.TYPE_FURNACE + "] Furnace.");
		displayApplianceString.append("\n[" + Constants.TYPE_RANGE + "] Kitchen range.");
		displayApplianceString.append("\n[" + Constants.TYPE_WASHER + "] Washer.");
		return displayApplianceString.toString();
	}
	

	/**
	 * Method to be called for adding a customer. Prompts the user for the
	 * appropriate values and uses the appropriate Store method for adding the
	 * customer.
	 * 
	 */
	public void addCustomer() {
		do {
			String name = getToken("Enter the customer's name: ");
			String phoneNumber = getToken("Enter the phone number: ");
			Customer customer = store.addCustomer(name, phoneNumber);
			if (customer == null) {
				System.out.println("Could not add customer.");
			}
			System.out.println(customer);
		} while (yesOrNo("Would you like to add another customer?"));
	}

	/**
	 * Method to be called for adding a appliance. Prompts the user for the
	 * appropriate values and uses the appropriate Store method for adding the
	 * appliance.
	 * 
	 */
	public void addAppliance() {
		do {			
			int type = getInteger("Enter appliance type: "+ displayApplianceChoices());
			String brand = getToken("Enter appliance brand: ");
			String model = getToken("Enter appliance model: ");
			double price = getDouble("Enter appliance price: ");
			Appliance appliance = store.addAppliance(type,brand, model, price);
			if (appliance != null) {
				System.out.println(appliance);
			} else {
				System.out.println("Appliance could not be added.");
			}
		} while (yesOrNo("Would you like to add another Appliance?"));
	}

	/**
	 * Method to be called for adding a appliance to the inventory. Prompts the
	 * user for the appropriate values and uses the appropriate Inventory method
	 * for adding the appliance.
	 * 
	 */
	public void addToInventory() {
		do {
			int quantity = 0;
			String brand = getToken("Enter appliance brand: ");
			String model = getToken("Enter appliance model: ");
			Appliance item = store.searchAppliances(brand + model);
			if (item == null) {
				System.out.println("No such appliance exists.");
				return;
			}
			quantity = getInteger("Enter quantity to add: ");
			boolean result = store.addApplianceToInventory(brand, model, quantity);
			if(result) {
				System.out.println("Added " + quantity + " of " + item);
			}else {
				System.out.println("Appliance could not be added to inventory.");
			}
			
		} while (yesOrNo("Add more appliances to the inventory?"));
	}

	/**
	 * Method to be called for purchasing a appliance. Prompts the user for the
	 * appropriate values and uses the appropriate Store method for executing
	 * the sale.
	 * 
	 */
	/*
	 * Purchase: The actor identifies the appliance by its brand and model names
	 * and the customer by the customer id. The actor enters the quantity as
	 * well. If there is enough on stock, the purchase is immediate. Otherwise,
	 * this goes on back order.
	 */
	public void purchase() {
		do {
			String id = getToken("Enter customer id: ");
			String brand = getToken("Enter appliance brand: ");
			String model = getToken("Enter appliance model: ");
			int quantity = getInteger("Enter quantity to purchase: ");
			boolean purchased = store.purchaseAppliance(id, brand, model, quantity);
			if (purchased) {
				System.out.println("Purchased " + quantity + " of " + brand + " " + model + " for customer " + id);
			} else {
				System.out.println("Purchase unsuccessful.");
			}
		} while (yesOrNo("Make another purchase?"));
	}

	/**
	 * Method to be called for displaying a list of all customers in the system.
	 * 
	 */
	public void listCustomers() {
		System.out.println(store.listCustomers());
	}

	/**
	 * Method to be called for displaying a list of all appliances in the
	 * inventory.
	 * 
	 */
	public void listAppliances() {
		try {
			System.out.println("Enter a number between 0 and 6 as explained below: \n");
			System.out.print("[" + Constants.TYPE_ALL + "] List all appliances.");
			System.out.println(displayApplianceChoices());
			int value = getInteger("");
			if (value >= Constants.TYPE_ALL && value <= Constants.TYPE_WASHER) {
				System.out.println(store.listAppliances(value));
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			System.out.println("Invalid entry please try again.");
		}
	}

	/**
	 * Method to be called for saving the Store object. Uses the appropriate
	 * Store method for saving.
	 * 
	 */
	private void save() {
		if (Store.save()) {
			System.out.println(" > The store has been successfully saved in the file StoreData.\n");
		} else {
			System.out.println(" > An error occurred during saving.\n");
		}
	}

	/**
	 * Method to be called for displaying the total appliance sales.
	 * 
	 */
	public void displayTotal() {
		System.out.println("Total sales: " + String.format("$%.2f.%n", (float) store.getTotalSales()));
	}

	/**
	 * Method to be called for retrieving saved data. Uses the appropriate Store
	 * method for retrieval.
	 * 
	 */
	
	public void enrollInRepairPlan() {
		System.out.println("Entered enrollInRepairPlan() method.");
	}
	
	public void withdrawFromRepairPlan() {
		System.out.println("Entered withdrawFromRepairPlan() method.");
	}
	
	public void billRepairPlanCustomers() {
		System.out.println("Entered billRepairPlanCustomers() method.");
	}
	
	public void listRepairPlanCustomers() {
		System.out.println("Entered listRepairPlanCustomers() method.");
	}
	
	public void listBackorders() {
		System.out.println(store.listBackOrders());
	}
	
	private void retrieve() {
		try {
			if (store == null) {
				store = Store.retrieve();
				if (store != null) {
					System.out.println(" The store has been successfully retrieved from the file StoreData. \n");
				} else {
					store = Store.instance();
				}
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	

	/**
	 * The method to start the application. Simply calls process().
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}
