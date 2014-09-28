package com.karniyarik.web.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import com.karniyarik.common.KarniyarikRepository;

public class Formatter
{
	private static DecimalFormat mDecimalFormat = null;
	private static DecimalFormat mDoubleFormatWithoutComma = null;
	private static DecimalFormat mMoneyFormat = null;
	private static DecimalFormat mMoneyFormatWithoutComma = null;
	private static DecimalFormat mClusterFormat = null;
	private static DecimalFormat mScoreFormat = null;
	private static DateFormat	 mDateFormat = null;
	private static DecimalFormat mRatingFormat = null;
	private static NumberFormat  mIntegerFormat = null;
	
	public static String formatMoney(Double aMoney)
	{
		if(mMoneyFormat == null)
		{
			mMoneyFormat = new DecimalFormat(
					KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig().getMoneyFormat());			
		}
		
		if(aMoney != null)
		{
			return mMoneyFormat.format(aMoney);	
		}
		return "0";		
	}
	
	public static String formatMoneyWithoutComma(Double aMoney)
	{
		if(mMoneyFormatWithoutComma == null)
		{
			mMoneyFormatWithoutComma = new DecimalFormat("###,###,###");
		}
		
		if(aMoney != null)
		{
			return mMoneyFormatWithoutComma.format(aMoney);	
		}
		return "0";		
	}

	public static String formatDoubleWithoutComma(Double number)
	{
		if(mDoubleFormatWithoutComma == null)
		{
			mDoubleFormatWithoutComma = new DecimalFormat("##");
		}
		
		if(number != null)
		{
			return mDoubleFormatWithoutComma.format(number);	
		}
		return "0";		
	}

	public static String formatTime(double aTime)
	{
		if(mDecimalFormat == null)
		{
			mDecimalFormat = new DecimalFormat(
					KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig().getResultTimeFormat());
		}
		
		return mDecimalFormat.format(aTime);
	}
	
	public static String formatCluster(double aValue)
	{
		if(mClusterFormat == null)
		{
			mClusterFormat = new DecimalFormat(
					KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig().getClusterFormat());
		}

		return mClusterFormat.format(aValue);
	}
	
	public static String formatScore(float aValue)
	{
		if(mScoreFormat == null)
		{
			mScoreFormat = new DecimalFormat("##");
		}

		return mScoreFormat.format(aValue);
	}

	public static String formatRating(float aValue)
	{
		if(mRatingFormat == null)
		{
			mRatingFormat = new DecimalFormat("#.#");
			DecimalFormatSymbols dfs = mRatingFormat.getDecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
		}

		return mRatingFormat.format(aValue);
	}
	
	public static String formatInteger(Integer aValue)
	{
		if(mIntegerFormat == null)
		{
			mIntegerFormat = NumberFormat.getInstance(Locale.ENGLISH);
		}

		return mIntegerFormat.format(aValue);
	}

	public static String formatDate(Date aDate)
	{
		if(mDateFormat == null)
		{
			mDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale("tr"));
		}
		if(aDate != null)
		{
			return mDateFormat.format(aDate);	
		}
		else
		{
			return "";
		}
	}
}
