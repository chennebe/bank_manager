package edu.bu.cs591p1;

public abstract class Transaction {
	
	private final String _transactionType;
	private final long _initiatorID;
	private final long _accountID; //-1 if not needed

	public Transaction(String t, long custid, long accid) {
		_transactionType = t;
		_initiatorID = custid;
		_accountID = accid;
	}
	
	public String getTransactionType() {
		return _transactionType;
	}
	
	public long getInitiatorID() {
		return _initiatorID;
	}
	
	public long getAccountID() {
		return _accountID;
	}
}
