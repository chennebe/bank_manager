package edu.bu.cs591p1;

public class SavingInterestTransaction extends Transaction {
	
	private final double _interestAmount;
	
	public SavingInterestTransaction(long id, long account, double amount) {
		super("SavingInterest", id, account);
		_interestAmount = amount;
	}
	
	public double getInterestAmount() {
		return _interestAmount;
	}

}
