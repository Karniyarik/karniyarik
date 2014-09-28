package com.karniyarik.brands.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author meralan
 *
 */
public class ProductNameSplitter {

	public static final String space = " ";
	public static final String parenthesesRegExp = "\\(";
	public static final String openParentheses = "(";
	public static final String closedParentheses = ")";
	public static final String d_quote = "\"";
	public static final String comma = ",";
	
	public static List<String> splitProductName(String longProductName) {

		List<String> resultList  = new ArrayList<String>();

		for (String tempString : longProductName.split(space)) {

			if (tempString.contains(openParentheses) && tempString.contains(closedParentheses)) {
				tempString = cleanWordsInParantheses(tempString);
			}
			else if (tempString.startsWith(openParentheses) || tempString.endsWith(closedParentheses)) {
				//case: (Bilgisayar) or (Cep or Telefonu) or ( or )
				//just ignore these. last two are so obvious.
				//other cases mostly occured when there is category information
				//added to the product name, ex: Asus F3SC (Laptop)
				continue;
			}
			else if (tempString.contains(openParentheses)) {
				//case: Asus(Laptop) F3SC
				// actual brand name is sticked to a category information
				// surrounded with parentheses so we should get rid of it
				tempString = cleanWordsInParantheses(tempString);
			}

			if(tempString.contains(d_quote))
			{
				String[] aStrArr = tempString.split(d_quote);
				for(String aStr: aStrArr)
				{
					addToResultList(resultList, aStr);	
				}
				continue;
			}

			if(tempString.contains(comma))
			{
				String[] aStrArr = tempString.split(comma);
				for(String aStr: aStrArr)
				{
					addToResultList(resultList, aStr);	
				}
				continue;
			}

			addToResultList(resultList, tempString);
		}

		return resultList;
	}
	
	private static void addToResultList(List<String> resultList, String aResult)
	{
		if(StringUtils.isNotBlank(aResult))
		{
			resultList.add(aResult);		
		}
	}

	private static String cleanWordsInParantheses(String string) {

		String result = new String();

		result = string.split(parenthesesRegExp)[0];

		return result;
	}
}
