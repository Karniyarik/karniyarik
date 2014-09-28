package com.karniyarik.brands;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meralan
 *
 */
public class BrandHolder {

	private String actualBrand;
	private List<String> listOfAlternateBrands = new ArrayList<String>();
	
	/**
	 * @return the actualBrand
	 */
	public String getActualBrand() {
		return actualBrand;
	}
	/**
	 * @param actualBrand the actualBrand to set
	 */
	public void setActualBrand(String actualBrand) {
		this.actualBrand = actualBrand;
	}
	/**
	 * @return the listOfAlternateBrands
	 */
	public List<String> getListOfAlternateBrands() {
		return listOfAlternateBrands;
	}
	/**
	 * @param listOfAlternateBrands the listOfAlternateBrands to set
	 */
	public void setListOfAlternateBrands(List<String> listOfAlternateBrands) {
		this.listOfAlternateBrands = listOfAlternateBrands;
	}
	
	public void addAlternateBrand(String aBrandname)
	{
		if(aBrandname!= null && !contains(aBrandname))
		{			
			getListOfAlternateBrands().add(aBrandname);
		}
	}

	public void addAlternateBrand(BrandHolder aHolder)
	{
		addAlternateBrand(aHolder.getActualBrand());
		for(String aStr: aHolder.getListOfAlternateBrands())
		{
			addAlternateBrand(aStr);
		}		
	}

	public boolean contains(String aBrandname)
	{
		if(aBrandname.equalsIgnoreCase(getActualBrand()))
		{
			return true;
		}
		
		for(String anAlternate: getListOfAlternateBrands())
		{
			if(anAlternate.equalsIgnoreCase(aBrandname))
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return getActualBrand();
	}
}
