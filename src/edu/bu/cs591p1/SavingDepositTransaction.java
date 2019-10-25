package edu.bu.cs591p1;

public class SavingDepositTransaction extends Transaction {

	private final double _depositAmount;

	public SavingDepositTransaction(long id, long account, double deposit) {
		super("SavingDeposit", id, account);
		_depositAmount = deposit;
	}

	public double getDepositAmount() {
		return _depositAmount;
	}

}
