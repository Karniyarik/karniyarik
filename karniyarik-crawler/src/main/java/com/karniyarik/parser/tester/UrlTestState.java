package com.karniyarik.parser.tester;

public enum UrlTestState
{
	SUCCESS, FAIL, COULD_NOT_FETCH_CONTENT, SITE_NOT_SUPPORTED;

	@Override
	public String toString()
	{
		String str = null;
		switch (this)
		{
		case SUCCESS:
			str = "Success";
			break;
		case FAIL:
			str = "Failed";
			break;
		case COULD_NOT_FETCH_CONTENT:
			str = "Test content could not be fetched";
			break;
		case SITE_NOT_SUPPORTED:
			str = "Source site of test url is not supported";
			break;
		default:
			str = "Invalid State";
			break;
		}

		return str;
	}

}
