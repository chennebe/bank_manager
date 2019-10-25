package edu.bu.cs591p1;

public class CheckingDepositTransaction extends Transaction {
	
	private final double _depositAmount;
	
	public CheckingDepositTransaction(long id, long account, double deposit) {
		super("CheckingDeposit", id, account);
		_depositAmount = deposit;
	}
	
	public double getDepositAmount() {
		return _depositAmount;
	}
}
