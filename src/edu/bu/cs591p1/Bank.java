package edu.bu.cs591p1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Bank {

	private BankAccount _centralAccount; // account information for the bank, all fees/interest payments are
											// added/deducted here
	private ArrayList<BankAccount> _accounts; // all accounts
	private ArrayList<BankAccount> _loanRequests;
	private ArrayList<Customer> _customers;
	private ArrayList<Transaction> _transactions;
	private int _nextTransaction; // next unviewed transaction
	private long _nextAccountID; // ID for the next created account
	private long _nextCustomerID; // ID for the next created customer

	private final double _MIN_SAVING_FOR_INTEREST = 1000; // min USD to generate interest
	private final double _SAVING_NTEREST_RATE = (.06 / 12); // 6% compounded monthly
	private final double _LOAN_INTEREST_RATE = .1 / 12; // 10% compounded monthly
	private final double _ACCOUNT_OPEN_FEE = 15;
	private final double _ACCOUNT_CLOSE_FEE = 25;
	private final double _CHECKING_WITHDRAWAL_FEE = 5.0;
	private final double _CHECKING_DEPOSIT_FEE = 2.0;
	private final double _SAVING_WITHDRAWAL_FEE = 3.0; // transfer fee from checkings to savings = transfer fee from
														// savings to checkings

	public Bank(ArrayList<BankAccount> accs, BankAccount central) { // to recover state, not used yet
		_centralAccount = central;
		_accounts = accs;
		_nextAccountID = _accounts.size() + 1; // 0 is the central account
		initializeCustomers();
		_transactions = new ArrayList<Transaction>();
		_nextTransaction = 0;
		_loanRequests = new ArrayList<BankAccount>();
	}

	public Bank() {
		this(new ArrayList<BankAccount>(), new BankAccount(new USD(), BankAccount.AccountType.CENTRAL,
				new Customer(new Name("Mr", "Bank", "Manager"), 0), 0)); // oof
	}

	public BankAccount getCentralAccount() {
		return _centralAccount;
	}

	private void initializeCustomers() { // initializes customers from some loaded state
		HashMap<Long, Boolean> ids = new HashMap<Long, Boolean>();
		ArrayList<Customer> customers = new ArrayList<Customer>();

		for (BankAccount ba : _accounts) {
			if (ids.containsKey(ba.getID())) {
				customers.add(ba.getCustomer());
			}
			ids.put(ba.getID(), true);
		}

		_nextCustomerID = customers.size() + 1;
		_customers = customers;
	}

	// returns balance of the central account
	public Currency getBankBalance() {
		return _centralAccount.getBalance();
	}

	public ArrayList<BankAccount> getAllAccounts() {
		return _accounts;
	}

	public ArrayList<BankAccount> getAccountsByCustomerID(long id) {
		ArrayList<BankAccount> ret = new ArrayList<BankAccount>();
		for (BankAccount ba : _accounts) {
			if (ba.getCustomer().getID() == id) {
				ret.add(ba);
			}
		}
		return ret;
	}

	public BankAccount getAccountByID(long id) {
		for (BankAccount ba : _accounts) {
			if (ba.getID() == id) {
				return ba;
			}
		}
		return null;
	}

	public ArrayList<Customer> getAllCustomers() {
		return _customers;
	}

	public Customer getCustomerByID(long id) {
		for (Customer c : _customers) {
			if (c.getID() == id) {
				return c;
			}
		}
		return null;
	}

	public void newCustomer(Name n) {
		_customers.add(new Customer(n, _nextCustomerID));
		_transactions.add(new NewCustomerTransaction(_nextCustomerID, n));
		_nextCustomerID++;
	}

	public void newAccount(Customer c, BankAccount.AccountType t, Currency curr) {
		_accounts.add(new BankAccount(curr, t, c, _nextAccountID));
		_centralAccount.deposit(new USD(_ACCOUNT_OPEN_FEE));
		_transactions.add(new NewAccountTransaction(c.getID(), _nextAccountID, t, curr));
		_nextAccountID++;
	}

	// we assume this is done in a way that fee is paid in cash
	public void closeAccount(long accountID) {
		BankAccount ba = getAccountByID(accountID);
		if (ba != null) {
			_accounts.remove(ba);
			_centralAccount.deposit(new USD(_ACCOUNT_CLOSE_FEE));
			_transactions.add(new CloseAccountTransaction(ba.getCustomer().getID(), ba.getID()));
		}
	}

	// assumes we wont deposit so little that you lose money
	public boolean deposit(long accountID, Currency amount) {
		BankAccount ba = getAccountByID(accountID);
		if (ba != null) {
			double deposit = amount.convert();
			if (ba.getAccountType() == BankAccount.AccountType.CHECKING) {
				if (deposit > _CHECKING_DEPOSIT_FEE) {
					ba.deposit(new USD(deposit - _CHECKING_DEPOSIT_FEE));
					_centralAccount.deposit(new USD(_CHECKING_DEPOSIT_FEE));
					_transactions.add(new CheckingDepositTransaction(ba.getCustomer().getID(), ba.getID(),
							deposit - _CHECKING_DEPOSIT_FEE));
					return true;
				}
			} else { // is saving account
				ba.deposit(new USD(deposit));
				_transactions.add(new SavingDepositTransaction(ba.getCustomer().getID(), ba.getID(), deposit));
				return true;
			}

		}
		return false;
	}

	public boolean withdraw(long accountID, Currency amount) {
		BankAccount ba = getAccountByID(accountID);
		if (ba != null) {
			double withdrawal = amount.convert();
			double fee;
			if (ba.getAccountType() == BankAccount.AccountType.CHECKING) { // check which fee to use
				fee = _CHECKING_WITHDRAWAL_FEE;
			} else {
				fee = _SAVING_WITHDRAWAL_FEE;
			}

			if (ba.getBalance().convert() > withdrawal + fee) {
				ba.withdraw(new USD(withdrawal + fee));
				_centralAccount.deposit(new USD(fee));
				if (ba.getAccountType() == BankAccount.AccountType.CHECKING) {
					_transactions
							.add(new CheckingWithdrawalTransaction(ba.getCustomer().getID(), ba.getID(), withdrawal));
				} else {
					_transactions
							.add(new SavingWithdrawalTransaction(ba.getCustomer().getID(), ba.getID(), withdrawal));
				}
				return true;
			}
		}
		return false;
	}

	// could probably change withdraw and deposit to validation functions so this
	// function could just call them instead of repeating their checks
	public boolean transfer(long fromID, long toID, Currency amount) {
		BankAccount fromba = getAccountByID(fromID);
		BankAccount toba = getAccountByID(toID);
		if (fromba != null && toba != null) {
			double transfer = amount.convert();
			double fromBalance = fromba.getBalance().convert();
			double toBalance = toba.getBalance().convert();
			double fromfee;
			double tofee;

			if (fromba.getAccountType() == BankAccount.AccountType.CHECKING) {
				fromfee = _CHECKING_WITHDRAWAL_FEE;
			} else {
				fromfee = _SAVING_WITHDRAWAL_FEE;
			}

			if (fromBalance < transfer + fromfee) {
				return false;
			}

			if (toba.getAccountType() == BankAccount.AccountType.CHECKING) {
				tofee = _CHECKING_DEPOSIT_FEE;
			} else {
				tofee = 0;
			}

			if (toBalance < tofee) {
				return false;
			}

			fromba.withdraw(new USD(transfer + fromfee));
			toba.withdraw(new USD(tofee));
			toba.deposit(new USD(transfer));
			_centralAccount.deposit(new USD(fromfee + tofee));
			_transactions
					.add(new TransferTransaction(fromba.getCustomer().getID(), fromba.getID(), toba.getID(), transfer));
			return true;
		}
		return false;
	}

	public ArrayList<BankAccount> getLoanRequests() {
		return _loanRequests;
	}

	public BankAccount getLoanRequestById(long id) {
		for (BankAccount ba : _loanRequests) {
			if (ba.getID() == id) {
				return ba;
			}
		}
		return null;
	}

	public void requestLoan(Customer cust, Currency c) {
		BankAccount loan = new BankAccount(c, BankAccount.AccountType.LOAN, cust, _nextAccountID);
		_loanRequests.add(loan);
		_nextAccountID++;
	}

	public void approveLoan(long loanID) {
		BankAccount loan = getLoanRequestById(loanID);
		if (loan != null) {
			_loanRequests.remove(loan);
			_accounts.add(loan);
			_centralAccount.withdraw(loan.getBalance()); // subtract loan amount from central account, make it back from
															// payments
			_transactions.add(new NewAccountTransaction(loan.getCustomer().getID(), loanID,
					BankAccount.AccountType.LOAN, loan.getBalance()));
		}
	}

	//provides a way to remove requests without approving them
	public void rejectLoan(long loanID) { 
		BankAccount loan = getLoanRequestById(loanID);
		_loanRequests.remove(loan);
	}

	//dont know if this is strictly necessary, but illustrates the more validation centric approach mentioned by transfer()
	public void payLoanInCash(long loanID, Currency c) {
		BankAccount loan = getAccountByID(loanID);
		if (loan != null) {
			loan.withdraw(c);
			_centralAccount.deposit(c);
			_transactions.add(new LoanPaidTransaction(loan.getCustomer().getID(), loanID, c.convert()));
		}
	}

	public boolean payLoanFromAccount(long loanID, long accountID, Currency c) {
		boolean success = withdraw(accountID, c);
		if (success) {
			payLoanInCash(loanID, c);
			return true;
		}
		return false;
	}

	// standin because no time progression
	public void applyMonth() {
		for (BankAccount ba : _accounts) {
			if (ba.getAccountType() == BankAccount.AccountType.SAVING) {
				double balance = ba.getBalance().convert();
				if (balance >= _MIN_SAVING_FOR_INTEREST) {
					balance *= _SAVING_NTEREST_RATE;
					ba.deposit(new USD(balance));
					_transactions.add(new SavingInterestTransaction(ba.getCustomer().getID(), ba.getID(), balance));
				}
			} else if (ba.getAccountType() == BankAccount.AccountType.LOAN) {
				double balance = ba.getBalance().convert();
				balance *= _LOAN_INTEREST_RATE;
				ba.deposit(new USD(balance));
				_transactions.add(new LoanInterestTransaction(ba.getCustomer().getID(), ba.getID(), balance));
			}
		}
	}

	public ArrayList<Transaction> getAllTransactions() {
		return _transactions;
	}

	public ArrayList<Transaction> getAllNewTransactions() {
		ArrayList<Transaction> ts = new ArrayList<Transaction>();
		for (int i = _nextTransaction; i < _transactions.size(); ++i) {
			ts.add(_transactions.get(i));
		}
		_nextTransaction = _transactions.size();
		return ts;
	}

	public ArrayList<Transaction> getTransactionsByCustomerId(long id) {
		ArrayList<Transaction> ts = new ArrayList<Transaction>();
		for (Transaction t : _transactions) {
			if (t.getInitiatorID() == id) {
				ts.add(t);
			}
		}
		return ts;
	}

	public ArrayList<Transaction> getTransactionsByAccountId(long id) {
		ArrayList<Transaction> ts = new ArrayList<Transaction>();
		for (Transaction t : _transactions) {
			if (t.getAccountID() == id) {
				ts.add(t);
			}
		}
		return ts;
	}

}

//comparators for sorting based on a few different values
class IDComparator implements Comparator<BankAccount> {

	@Override
	public int compare(BankAccount arg0, BankAccount arg1) {
		return (int) (arg1.getID() - arg0.getID());
	}
}

class BalanceComparator implements Comparator<BankAccount> {

	@Override
	public int compare(BankAccount arg0, BankAccount arg1) {
		return (int) (arg1.getBalance().convert() - arg0.getBalance().convert());
	}
}

class NameComparator implements Comparator<BankAccount> {

	@Override
	public int compare(BankAccount arg0, BankAccount arg1) {
		if (arg0.getCustomer().getName().equals(arg1.getCustomer().getName())) {
			return (int) (arg1.getID() - arg0.getID());
		}
		return arg0.getCustomer().getName().getFullName().compareTo(arg1.getCustomer().getName().getFullName());
	}
}

