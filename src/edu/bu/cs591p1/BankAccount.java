package edu.bu.cs591p1;

public class BankAccount {
	
	public enum AccountType { CHECKING, SAVING, LOAN, CENTRAL} //CENTRAL should only be used by the bank manager
	
	private AccountType _type;
	private Currency _balance;
	private final long _id; //unique ID assigned by Bank
	private Customer _customer;
	
	public BankAccount(Currency start, AccountType t, Customer c, long id) {
		_balance = start;
		_type = t;
		_customer = c;
		_id = id;
	}
	
	public AccountType getAccountType() {
		return _type;
	}
	
	public Currency getBalance() {
		return _balance;
	}
	
	public void deposit(Currency c) {
		_balance.addCount(c);
	}
	
	public void withdraw(Currency c) {
		_balance.subtractCount(c);
	}
	
	public long getID() {
		return _id;
	}
	
	public Customer getCustomer() {
		return _customer;
	}
	
	public void updateName(Customer c) {
		_customer = c;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (this == o) return true;
		if (!(o instanceof BankAccount)) return false;
		
		BankAccount ba = (BankAccount) o;
		
		return this.getID() == ba.getID();
	}
}
