package edu.bu.cs591p1;

public class SavingWithdrawalTransaction extends Transaction {
	
	private final double _withdrawalAmount;

	public SavingWithdrawalTransaction(long id, long account, double withdrawal) {
		super("SavingWithdrawal", id, account);
		_withdrawalAmount = withdrawal;
	}

	public double getWithdrawalAmount() {
		return _withdrawalAmount;
	}

}
