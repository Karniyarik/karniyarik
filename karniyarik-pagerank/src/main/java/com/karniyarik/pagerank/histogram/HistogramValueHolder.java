package com.karniyarik.pagerank.histogram;

public class HistogramValueHolder implements Comparable {

	private int value;
	private int count;

	public HistogramValueHolder(int value, int count)
	{
		this.value = value;
		this.count = count;
	}

	public int getValue()
	{
		return value;
	}

	public int getCount()
	{
		return count;
	}

	public void setValue(int aValue)
	{
		value = aValue;
	}

	public void setCount(int aCount)
	{
		count = aCount;
	}

	
	@Override
	public int compareTo(Object obj)
	{
		Integer otherValue = ((HistogramValueHolder) obj).getValue();

		if (this.value > otherValue) {
			return 1;
		} else if (this.value < otherValue) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object aObj)
	{
		if (!(aObj instanceof HistogramValueHolder)) {
			return false;
		} else {
			HistogramValueHolder other = (HistogramValueHolder) aObj;
			return (this.value == other.getValue());
		}
	}

	@Override
	public String toString()
	{
		return (Integer.toString(value) + "," + Integer.toString(count));
	}
}