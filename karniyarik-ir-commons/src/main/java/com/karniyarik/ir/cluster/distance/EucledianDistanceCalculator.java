package com.karniyarik.ir.cluster.distance;


public class EucledianDistanceCalculator implements IDistanceCalculator
{

	@Override
	public double calculateDistance(double a, double b)
	{
		return Math.abs(a-b);
	}
}
