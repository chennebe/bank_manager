package edu.bu.cs591p1;

public class CheckingWithdrawalTransaction extends Transaction {

	private final double _withdrawalAmount;

	public CheckingWithdrawalTransaction(long id, long account, double withdrawal) {
		super("CheckingWithdrawal", id, account);
		_withdrawalAmount = withdrawal;
	}

	public double getWithdrawalAmount() {
		return _withdrawalAmount;
	}

}
