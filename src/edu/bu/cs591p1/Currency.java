package edu.bu.cs591p1;

public abstract class Currency {
	
	private double _count;
	private double _conversionRate; //to USD, to simplify conversions. * from USD, / to USD
	
	public Currency(double start, double conv) {
		_count = start;
		_conversionRate = conv;
	}
	
	public double getCount() {
		return _count;
	}
	
	//converts c to currency of this, which should usually be USD
	public void addCount(Currency c) { 
		double funds = c.convert(this);
		_count += funds;
	}
	
	//same as above
	public void subtractCount(Currency c) {
		double funds = c.convert(this);
		_count -= funds;
	}
	
	public double getConversionRate() {
		return _conversionRate;
	}
	
	public double convert() { //to USD
		return _count / getConversionRate();
	}
	
	public double convert(Currency c) { //first to USD, then to c
		return convert() * c.getConversionRate();
	}

}
