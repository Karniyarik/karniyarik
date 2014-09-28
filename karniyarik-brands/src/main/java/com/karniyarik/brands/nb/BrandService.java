package com.karniyarik.brands.nb;


public interface BrandService 
{
	String recognize(String candidateBrand);
	ResolveResult resolve(String longProductName);
	boolean isBrandRecognized(String aBrand);
}
