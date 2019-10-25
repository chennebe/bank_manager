package edu.bu.cs591p1;

public class USD extends Currency {
	
	public USD(double start) {
		super(start, 1.0);
	}
	
	public USD() {
		this(0);
	}
	
	@Override
	public String toString() {
		return getCount() + " USD";
	}

}
