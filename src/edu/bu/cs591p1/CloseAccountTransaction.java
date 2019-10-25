package edu.bu.cs591p1;

public class CloseAccountTransaction extends Transaction {
	
	public CloseAccountTransaction(long id, long account) {
		super("CloseAccount", id, account);
	}
}
