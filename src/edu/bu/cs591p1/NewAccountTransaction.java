package edu.bu.cs591p1;

public class NewAccountTransaction extends Transaction {

	private final BankAccount.AccountType _accountType;
	private final Currency _initialDeposit;
	
	public NewAccountTransaction(long cust, long id, BankAccount.AccountType t, Currency c) {
		super("NewAccount", cust, id);
		_accountType = t;
		_initialDeposit = c;
	}
	
	public BankAccount.AccountType getAccountType(){
		return _accountType;
	}
	
	public Currency getInitialDeposit() {
		return _initialDeposit;
	}
}
