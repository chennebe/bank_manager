package edu.bu.cs591p1;

public class NewCustomerTransaction extends Transaction {
	
	private final Name _name;
	
	public NewCustomerTransaction(long id, Name n) {
		super("NewCustomer", id, -1);
		_name = n;
	}
	
	public Name getName() {
		return _name;
	}

}
