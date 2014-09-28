package com.karniyarik.ir;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.karniyarik.common.KarniyarikRepository;

public class DoubleField {
	
	private static final ThreadLocal<DecimalFormat>	THREAD_LOCAL_FORMAT	= new ThreadLocal<DecimalFormat>()
	{
		protected synchronized DecimalFormat initialValue()
		{
			DecimalFormatSymbols aDecimalFormatSymbols = new DecimalFormatSymbols();
			aDecimalFormatSymbols.setDecimalSeparator('.');
			aDecimalFormatSymbols.setGroupingSeparator(',');
			String aFormat = KarniyarikRepository.getInstance().getConfig()
					.getConfigurationBundle().getSearchConfig()
					.getDoubleFieldIndexingFormat();
			return new DecimalFormat(aFormat, aDecimalFormatSymbols);
		}
	};

	public static Double getDouble(String aString) {
		try {
			Number aNumber = THREAD_LOCAL_FORMAT.get().parse(aString);
			return aNumber.doubleValue();
		} catch (Throwable e) {
			throw new RuntimeException("Cannot parse string to double: "
					+ aString);
		}
	}

	public static String getString(Double aDouble) {
		return THREAD_LOCAL_FORMAT.get().format(aDouble);
	}

	public static String getString(Number aNumber) {
		return THREAD_LOCAL_FORMAT.get().format(aNumber);
	}
}
