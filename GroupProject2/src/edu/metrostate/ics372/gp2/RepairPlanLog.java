package edu.metrostate.ics372.gp2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RepairPlanLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private static RepairPlanLog repairPlanLog;
	Map<Customer, ArrayList<ClothesAppliance>> log = new HashMap<>();
	
	private RepairPlanLog() {
	}

	public static RepairPlanLog instance() {
		if (repairPlanLog == null) {
			return (repairPlanLog = new RepairPlanLog());
		} else {
			return repairPlanLog;
		}
	}

	public void insert(Customer customer, ClothesAppliance appliance) {
		if (log.containsKey(customer)) {
			log.get(customer).add(appliance);
		} else {
			log.put(customer, new ArrayList<ClothesAppliance>());
			log.get(customer).add(appliance);
		}
	}

	public void withdraw(Customer customer, ClothesAppliance appliance) {
		if (log.containsKey(customer)) {
			if (log.get(customer).contains(appliance)) {
				log.get(customer).remove(appliance);
				System.out.println("Customer: " + customer + " with appliance: " + appliance + " removed from repair plan.");
				if (log.get(customer).isEmpty()) {
					log.remove(customer);
				}
			} else {
				System.out.println("Customer: " + customer + " does not have a plan for appliance: " + appliance);
			}
		} else {
			System.out.println("Customer: " + customer + " does not have a plan for appliance: " + appliance);
		}
	}

	public void billCustomers() {
		for (Entry<Customer, ArrayList<ClothesAppliance>> entry : log.entrySet()) {
			entry.getKey().bill(entry.getValue());
		}
	}
	
	public Map<Customer, ArrayList<ClothesAppliance>> iterator() {
		return log;
	}
}
