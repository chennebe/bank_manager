package edu.bu.cs591p1;

public class LoanInterestTransaction extends Transaction {
	
	private final double _interestAmount;
	
	public LoanInterestTransaction(long id, long account, double amount) {
		super("LoanInterest", id, account);
		_interestAmount = amount;
	}
	
	public double getInterestAmount() {
		return _interestAmount;
	}

}
