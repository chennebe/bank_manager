package edu.bu.cs591p1;

import java.util.ArrayList;

public class Customer {
	
	private Name _name;
	private final long _id;
	private ArrayList<BankAccount> _accounts; //considered separating loans, decided Bank would handle that
	
	public Customer(Name n, long id, ArrayList<BankAccount> accs) {
		_name = n;
		_id = id;
		_accounts = accs;
	}
	
	public Customer(Name n, long id) {
		this(n, id, new ArrayList<BankAccount>());
	}
	
	public Name getName() {
		return _name;
	}
	
	public void updateName(Name n) {
		_name = n;
		for (BankAccount ba : _accounts) {
			ba.updateName(this);
		}
	}
	
	public long getID() {
		return _id;
	}
	
	public ArrayList<BankAccount> getAllAccounts(){
		return _accounts;
	}
	
	public ArrayList<BankAccount> getAccountByType(BankAccount.AccountType t) {
		ArrayList<BankAccount> ret = new ArrayList<BankAccount>();
		for (BankAccount ba : _accounts) {
			if (ba.getAccountType() == t) {
				ret.add(ba);
			}
		}
		return ret;
	}
	
	//note: index is relative to the current view of the customer, and not its id or any permanent identification
	public BankAccount getAccountByIndex(int index) { 
		return _accounts.get(index);
	}
	
	//same as above
	//this should only be called as part of the process of closing an account in Bank
	public void removeAccount(int index) {
		_accounts.remove(index);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (this == o) return true;
		if (!(o instanceof Customer)) return false;
		
		Customer c = (Customer) o;
		
		return this.getID() == c.getID();
	}
	
}
