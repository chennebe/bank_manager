package edu.bu.cs591p1;

public class EUR extends Currency {
	
	public EUR(double start) {
		super(start, 1.11); //as of October 14, 2019
	}
	
	public EUR() {
		this(0);
	}

	@Override
	public String toString() {
		return getCount() + " EUR";
	}
}
