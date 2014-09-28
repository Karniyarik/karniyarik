package com.karniyarik.parser.tester;


public class ParserTestAdmin
{
	private static ParserTestAdmin		INSTANCE	= null;
	private ParserTester				tester;

	
	private ParserTestAdmin()
	{
		tester = new ParserTester();
	}

	public static ParserTestAdmin getInstance()
	{
		if (INSTANCE == null)
		{
			synchronized (ParserTestAdmin.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = new ParserTestAdmin();
				}
			}
		}

		return INSTANCE;
	}

	public UrlTestResult testUrl(String url)
	{
		return tester.testUrl(url);
	}

	public static void main(String[] args)
	{
		ParserTestAdmin.getInstance().testUrl("http://www.teknosa.com/Cultures/tr-TR/Products/urundetay.htm?CS_Catalog=_TELEKOM&CS_ProductID=145010926&CS_Category=_IPHONE1014");
	}
}
