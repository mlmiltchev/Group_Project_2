package edu.metrostate.ics372.gp2;

public class ClothesAppliance extends Appliance {

	private static final long serialVersionUID = 1L;

	private double monthlyRepairCost;	

	public ClothesAppliance(String brand, String model, double price, int type) {
		super(brand, model, price, type);
		setMonthlyRepairCost();
	}

	public void setMonthlyRepairCost() {	
		try {
			this.monthlyRepairCost = Double.parseDouble(getAttribute("Enter Monthly Repair Cost: \n$"));			
		}catch(Exception e) {
			System.out.println("Invalid input for repair cost. Try Again.");
			setMonthlyRepairCost();
		}
	}

	public double getMonthlyRepairCost() {
		return monthlyRepairCost;
	}
	
	@Override
	public String toString() {
		return String.format("%s, Monthly Repair Cost: $%.2f", super.toString(), getMonthlyRepairCost());
	}

}
