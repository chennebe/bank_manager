package edu.bu.cs591p1;

public class CAD extends Currency {

	public CAD(double start) {
		super(start, .77); //as of October 14, 2019
	}
	
	public CAD() {
		this(0);
	}
	
	@Override
	public String toString() {
		return getCount() + " CAD";
	}
}
