package edu.bu.cs591p1;

public class TransferTransaction extends Transaction {
	
	private final long _toAccount;
	private final double _transferAmount;
	
	public TransferTransaction(long id, long from, long to, double amount) {
		super("Transfer", id, from);
		_toAccount = to;
		_transferAmount = amount;
	}
	
	public long getToAccountId() {
		return _toAccount;
	}
	
	public double getTransferAmount() {
		return _transferAmount;
	}

}
