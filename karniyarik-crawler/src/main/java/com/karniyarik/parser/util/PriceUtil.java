package com.karniyarik.parser.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.vo.CurrencyType;
import com.karniyarik.parser.pojo.Product;

public class PriceUtil
{

	private static final Logger						logger							= Logger.getLogger(PriceUtil.class.getName());

	public static final float						DEFAULT_PRICE					= -1f;
	private static final Pattern					pricePatternPriceFirst			= Pattern
																							.compile(
																									"([\\d,\\.]+)\\s*(YTL|TL|EUR|USD|AED|AUD|BHD|BRL|CAD|CHF|DKK|FJD|GBP|HKD|JOD|JPY|KWD|MYR|NOK|NZD|OMR|PLN|QAR|RUB|SAR|SEK|SGD|THB|TWD|ZAR|EURO|DOLAR|\\$|€).*(\\b|$)",
																									Pattern.CASE_INSENSITIVE);
	private static final Pattern					pricePatternCurrencyFirst		= Pattern.compile("([$€]{1})\\s*([\\d,\\.]+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern					priceWithoutCurPattern			= Pattern.compile("([\\d,\\.]+)");

	private static final ThreadLocal<DecimalFormat>	COMMA_LAST_FORMAT_THREAD_LOCAL	= new ThreadLocal<DecimalFormat>()
																					{
																						protected synchronized DecimalFormat initialValue()
																						{
																							DecimalFormatSymbols aSymbols = new DecimalFormatSymbols();
																							aSymbols.setDecimalSeparator(',');
																							aSymbols.setGroupingSeparator('.');
																							return new DecimalFormat("###,###.##", aSymbols);
																						}
																					};

	private static final ThreadLocal<DecimalFormat>	DOT_LAST_FORMAT_THREAD_LOCAL	= new ThreadLocal<DecimalFormat>()
																					{
																						protected synchronized DecimalFormat initialValue()
																						{
																							DecimalFormatSymbols aSymbols = new DecimalFormatSymbols();
																							aSymbols.setDecimalSeparator('.');
																							aSymbols.setGroupingSeparator(',');
																							return new DecimalFormat("###,###.##", aSymbols);
																						}
																					};

	private PriceUtil()
	{

	}

	public static void setPrice(String rawPriceString, Product product)
	{
		// pass price string as currency string so that
		// currency is extracted from it
		setPrice(rawPriceString, null, product);
	}

	public static void setPrice(String rawPriceString, String currencyText, Product product)
	{
		// datafeed parsers use this since they parse currencyText serparetly
		setPrice(rawPriceString, currencyText, product, null,0);
	}
	
	public static void setPrice(String rawPriceString, String currencyText, Product product, DecimalFormat formatter,int kdvValue)
	{
		float price = parsePrice(rawPriceString, formatter);

		if (StringUtils.isBlank(currencyText))
		{
			currencyText = rawPriceString;
		}
		else
		{
			currencyText = rawPriceString + " " + currencyText;
		}

		CurrencyType currency = determineCurrency(currencyText);

		if (currency != CurrencyType.TL)
		{
			if(kdvValue == 0)
			{
				product.setPriceAlternate(price);
				product.setPriceCurrency(currency.getSymbol());
				product.setPrice(price * CurrencyManager.getInstance().getExchangeRate(currency));
		
			}
			else
			{
				product.setPriceAlternate(price + (price * kdvValue) / 100 );
				product.setPriceCurrency(currency.getSymbol());
				product.setPrice((price * CurrencyManager.getInstance().getExchangeRate(currency) * (100+kdvValue))/100);
			}
		}
		else
		{
			if(kdvValue == 0)
			{
				product.setPrice(price);
			}
			else
			{
				product.setPrice(price + (price * kdvValue) / 100);
			}
		}
	}

	public static float parsePrice(String rawPriceString, DecimalFormat formatter)
	{
		float price = DEFAULT_PRICE;
		String trimmedPrice;
		if (StringUtils.isNotBlank(rawPriceString))
		{
			try
			{
				trimmedPrice = trimToPrice(rawPriceString);
				if (StringUtils.isNotBlank(trimmedPrice))
				{
					DecimalFormat priceFormat = null;
					if (formatter == null)
					{
						trimmedPrice = reconstructPrice(trimmedPrice);
						priceFormat = chooseFormatter(trimmedPrice);
					}
					else
					{
						priceFormat = formatter;
					}

					price = priceFormat.parse(trimmedPrice).floatValue();
				}
			}
			catch (Exception e)
			{
				logger.error("Can not parse price string " + rawPriceString + " to a float.", e);
			}
		}

		return price;
	}

	private static String reconstructPrice(String rawPriceString)
	{
		String result = rawPriceString;
		String[] split = rawPriceString.split("[\\.\\,]");
		
		int commaIndex = result.indexOf(",");
		int dotIndex = result.indexOf(".");
		
		boolean bothCommaAndDotExists = commaIndex != -1 && dotIndex != -1;
		
		if(split != null && split.length > 1)
		{
			StringBuffer buff = new StringBuffer();
			int index = 0;
			
			if(split.length == 2 && split[0].length() > 3)
			{
				//no grouping only decimals
				buff.append(split[0]);
				buff.append(".");
				if(split[1].length() > 2)
				{
					buff.append(split[1].substring(0,2));
				}
				else
				{
					buff.append(split[1]);
				}
			}
			else
			{
				for(;index < split.length-1; index++)
				{
					buff.append(split[index]);
					if(index < split.length-2)
					{
						buff.append(",");	
					}
				}
				
				if(bothCommaAndDotExists || split[index].length() != 3)
				{
					buff.append(".");
					buff.append(split[index]);
				}
				else
				{
					buff.append(",");
					buff.append(split[index]);
				}	
			}
			
			result = buff.toString();
		}
		
		return result;
	}

	private static DecimalFormat chooseFormatter(String trimmedPriceText)
	{
		//return DOT_LAST_FORMAT_THREAD_LOCAL.get();
		
		DecimalFormat format = null;

		int lastCommaIndex = trimmedPriceText.lastIndexOf(",");
		int lastDotIndex = trimmedPriceText.lastIndexOf(".");

		// only dot character is used
		if (lastCommaIndex == -1 && lastDotIndex != -1)
		{
			if ((trimmedPriceText.length() - lastDotIndex - 1) == 3)
			{
				// 178.000 = 178K TL
				format = COMMA_LAST_FORMAT_THREAD_LOCAL.get();
			}
			else
			{
				// 178.00 = 178 TL
				// 178.0000 = 178 TL
				// 178.9900 = 178.99 TL
				format = DOT_LAST_FORMAT_THREAD_LOCAL.get();
			}
		}
		// only comma charater is used
		else if (lastCommaIndex != -1 && lastDotIndex == -1)
		{
			if ((trimmedPriceText.length() - lastCommaIndex - 1) == 3)
			{
				// 178,000 = 178K TL
				format = DOT_LAST_FORMAT_THREAD_LOCAL.get();
			}
			else
			{
				// 178,00 = 178 TL
				// 178,0000 = 178 TL
				// 178,9900 = 178.99 TL
				format = COMMA_LAST_FORMAT_THREAD_LOCAL.get();
			}
		}
		else if (lastDotIndex > lastCommaIndex)
		{
			format = DOT_LAST_FORMAT_THREAD_LOCAL.get();
		}
		else
		{
			format = COMMA_LAST_FORMAT_THREAD_LOCAL.get();
		}

		return format;
	}

	/**
	 * Extracts price text from a string
	 * 
	 * @param rawTxt
	 *            String input price string
	 * @return String containing only digits
	 */
	private static String trimToPrice(String rawTxt)
	{
		String result = trimToPrice(rawTxt, pricePatternPriceFirst, 1);

		if (StringUtils.isBlank(result))
		{
			result = trimToPrice(rawTxt, pricePatternCurrencyFirst, 2);
		}

		if (StringUtils.isBlank(result))
		{
			result = trimToPrice(rawTxt, priceWithoutCurPattern, 1);
		}

		return result;
		// rawTxt.replaceAll("[^\\d]*([\\d.,]*).*", "$1");
	}

	private static String trimToPrice(String rawTxt, Pattern pattern, int group)
	{
		String result = null;

		Matcher matcher = pattern.matcher(rawTxt);

		while (matcher.find())
		{
			result = matcher.group(group);
			if (StringUtils.isNotBlank(result))
			{
				break;
			}
		}

		return result;
	}

	public static CurrencyType determineCurrency(String priceString)
	{
		// default
		CurrencyType result = CurrencyType.TL;

		if (StringUtils.isNotBlank(priceString))
		{
			priceString = priceString.replaceAll("\\s+", " ");
			Matcher matcher = pricePatternPriceFirst.matcher(priceString);

			int group = -1;
			if (matcher.find())
			{
				group = 2;
			}
			else
			{
				matcher = pricePatternCurrencyFirst.matcher(priceString);
				if (matcher.find())
				{
					group = 1;
				}
			}

			if (group != -1)
			{
				String currencyStr = matcher.group(group);

				for (CurrencyType currencyType : CurrencyType.values())
				{
					if ((StringUtils.isNotBlank(currencyType.getSymbol()) && currencyStr.contains(currencyType.getSymbol()))
							|| (StringUtils.isNotBlank(currencyType.getCode()) && StringUtils.containsIgnoreCase(currencyStr, currencyType.getCode()))
							|| (StringUtils.isNotBlank(currencyType.getName()) && StringUtils.containsIgnoreCase(currencyStr, currencyType.getName())))
					{
						result = currencyType;
						break;
					}
				}
			}
		}

		return result;
	}

	public static void main(String[] args)
	{
//		for (CurrencyType type : CurrencyType.values())
//		{
//			System.out.print(type.getCode() + "|");
//		}
		
		DecimalFormatSymbols aSymbols = new DecimalFormatSymbols();
		aSymbols.setDecimalSeparator('.');
		aSymbols.setGroupingSeparator('.');
		DecimalFormat format = new DecimalFormat("###,###.##", aSymbols);
		
		String[] teststrs= new String[]{"1,234.345", "1212.55", "1.00", "1,0", "1200", "12.00", "12,00", "120.00", "120,00", "1212,00", "1212,555","1.345.66",
				"178,10", "178,1010","178.10","178.1010","178,000","178.000","1,234,345","1,234,34","1.234.345","1.234.34","1.234.3456"};
		
		for(String test: teststrs)
		{
			float parsePrice = PriceUtil.parsePrice(test, null);
			System.out.println(test + " - " + parsePrice);	
		}
		
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DAY_OF_YEAR, -1);
		System.out.println(instance.getTime().getTime());
	}

}
