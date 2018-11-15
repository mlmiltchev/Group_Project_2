package edu.metrostate.ics372.gp2;

public class BackOrderVisitor implements Visitor {
	@Override
	public void visit(BackOrder backOrder) {
		System.out.println("Backorder for: " + backOrder.getCustomer() + " Ordering: " + backOrder.getAppliance() + " Amount: " + backOrder.getQuantity() + ".");
	}
}
