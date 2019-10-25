package edu.bu.cs591p1;

public class LoanPaidTransaction extends Transaction {
	
	private final double _payment;
	
	public LoanPaidTransaction(long id, long loan, double payment) {
		super("LoadPaidCash", id, loan);
		_payment = payment;
	}
	
	public double getPayment() {
		return _payment;
	}

}
