package com.karniyarik.parser.logger;

public class ProductStatisticsLogger extends AbstractLoggerProvider
{

	private final String PRODUCT_MISS = "product_miss";
	private final String IMAGE_MISS = "image_miss";
	
	private static final String APPENDER_NAME = "ParserProductStatisticsAppender";
	private static final String APPENDER_PACKAGE = "parser.productstatistics.";
	
	public ProductStatisticsLogger(String siteName)
	{
		super(siteName, APPENDER_PACKAGE, APPENDER_NAME);
	}

	@Override
	protected String[] getOperationList()
	{
		String [] operationList = {PRODUCT_MISS, IMAGE_MISS};
		return operationList;
	}
	
	public final void logProductMiss(String url) {
		getLogger(PRODUCT_MISS).error(url);
	}
	
	public final void logImageMiss(String url) {
		getLogger(IMAGE_MISS).error(url);
	}

}
