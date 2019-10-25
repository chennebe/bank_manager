package edu.bu.cs591p1;

public class Name {

	private String _first;
	private String _middle;
	private String _last;
	
	//simple name class with a few different printing styles
	public Name(String f, String m, String l) {
		_first = f;
		_middle = m;
		_last = l;
	}
	
	public String getFirst() {
		return _first;
	}
	
	public void setFirst(String f) {
		_first = f;
	}
	
	public String getMiddle() {
		return _middle;
	}
	
	public String getMiddleInitial() {
		return _middle.toUpperCase().charAt(0) + "";
	}
	
	public void setMiddle(String m) {
		_middle = m;
	}
	
	public String getLast() {
		return _last;
	}
	
	public void setLast(String l) {
		_last = l;
	}
	
	public String getInitials() {
		String fi = _first.toUpperCase().charAt(0) + ".";
		String mi = getMiddleInitial() + ".";
		String li = _first.toUpperCase().charAt(0) + ".";
		return fi + mi + li;
	}
	
	public String getFullName() {
		return _first + " " + _middle + " " + _last;
	}
	
	public String getLastNameFirst() {
		return _last + ", " + _first + " " + getMiddleInitial();
	}
	
	@Override
	public String toString() {
		return _first + " " + getMiddleInitial() + " " + _last;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (this == o) return true;
		if (!(o instanceof Name)) return false;
		
		Name n = (Name) o;
		
		return this.getFullName().equals(n.getFullName());
	}
}