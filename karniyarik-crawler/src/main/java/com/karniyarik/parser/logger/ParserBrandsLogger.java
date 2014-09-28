package com.karniyarik.parser.logger;

public class ParserBrandsLogger extends AbstractLoggerProvider
{
	private final String		NOT_RESOLVED		= "notresolved";
	private final String		RESOLVED			= "resolved";
	private final String		NOT_MATCHED			= "notmatched";
	private final String		MATCHED				= "matched";
	private final String		RESOLVED_BRAND		= "resolvedbrand";
	private final String		NOT_RESOLVED_BRAND	= "notresolvedbrand";

	private static final String	APPENDER_NAME		= "BrandsAppender";
	private static final String	APPENDER_PACKAGE	= "extractor.brands.";

	public ParserBrandsLogger(String aSiteName)
	{
		super(aSiteName, APPENDER_PACKAGE, APPENDER_NAME);
	}

	@Override
	protected String[] getOperationList()
	{
		String[] operationList = { NOT_RESOLVED, RESOLVED, NOT_MATCHED,
				MATCHED, RESOLVED_BRAND, NOT_RESOLVED_BRAND };
		return operationList;
	}

	public final void logMatchedBrand(String candidateBrand, String actualBrand)
	{
		getLogger(MATCHED).info(candidateBrand + "->" + actualBrand);
	}

	public final void logNotMatchedBrand(String candidateBrand)
	{
		getLogger(NOT_MATCHED).info(candidateBrand);
	}

	public final void logResolvedFromBrand(String candidateBrand, String actualBrand)
	{
		getLogger(RESOLVED_BRAND).info(candidateBrand + "->" + actualBrand);
	}

	public final void logNotResolvedFromBrand(String candidateBrand,
			String actualBrand)
	{
		getLogger(NOT_RESOLVED_BRAND).info(candidateBrand + "->" + actualBrand);
	}

	public final void logResolvedFromProductName(String brandString,
			String actualBrand)
	{
		getLogger(RESOLVED).info(brandString + "->" + actualBrand);
	}

	public final void logNotResolvedFromProductName(String brandString)
	{
		getLogger(NOT_RESOLVED).info(brandString);
	}

}