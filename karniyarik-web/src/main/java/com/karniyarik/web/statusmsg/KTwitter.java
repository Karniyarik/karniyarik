package com.karniyarik.web.statusmsg;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.WebConfig;
import com.karniyarik.web.util.WebLoggerProvider;

public class KTwitter
{

	/*
	 * Consumer key goFfpsqJTBiquNwfOluMcA Consumer secret
	 * 18eJ7fgzR6R6emU3taFqQRLWfpT4YCGBRBmwjkgNzoQ Request token URL
	 * https://twitter.com/oauth/request_token Access token URL
	 * https://twitter.com/oauth/access_token Authorize URL
	 * https://twitter.com/oauth/authorize
	 */

	private Twitter			twitter		= null;
	private String			token		= "32654394-zL5pSSI73mw9SJS1voWhFEmDBhnlyek50QH5u9bto";
	private String			tokenSecret	= "o91tEO8BNLQbsdwSEPkjzf2qQe3zcx3evXaWXTTefsU";
	private Properties		prop		= null;
	private static KTwitter	instance	= null;

	private KTwitter()
	{
		InputStream stream = null;
		WebConfig webConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig();

		// try {
		// File tmpFile = File.createTempFile("twittertoken", "tmp");
		// String parent = tmpFile.getParent();
		// System.out.println(parent);
		// stream = StreamUtil.getStream(parent + "/twitter.token");
		// if(stream == null)
		// {
		// stream = StreamUtil.getStream("conf/system/twitter.token");
		// }
		// }
		// catch (Exception e) {
		// stream = StreamUtil.getStream("conf/system/twitter.token");
		// }

		try
		{
			// prop = new Properties();
			// prop.load(stream);
			// stream.close();
			//			
			// token = prop.getProperty("token");
			// tokenSecret = prop.getProperty("tokenSecret");

			twitter = new Twitter(webConfig.getTwitterUsername(), webConfig.getTwitterPassword());

			twitter.setOAuthConsumer("goFfpsqJTBiquNwfOluMcA", "18eJ7fgzR6R6emU3taFqQRLWfpT4YCGBRBmwjkgNzoQ");

			twitter.setOAuthAccessToken(token, tokenSecret);

			// System.out.println(token);
			// System.out.println(tokenSecret);

			twitter.test();
		}
		catch (Exception e)
		{
			WebLoggerProvider.logException("Cannot initialize twitter connection", e);
		}
	}

	public static KTwitter getInstance()
	{
		if (instance == null)
		{
			instance = new KTwitter();
		}
		return instance;
	}

	public List<Status> getStatusUpdates(int count)
	{
		List<Status> statusUpdates = new ArrayList<Status>();
		try
		{
			Paging paging = new Paging(1, count);
			statusUpdates = twitter.getUserTimeline(paging);
		}
		catch (TwitterException e)
		{
			WebLoggerProvider.logException("Cannot get twitter messages", e);
		}

		return statusUpdates;
	}

//	public String[] getNewTokenURL()
//	{
//		try
//		{
//			RequestToken oAuthRequestToken = twitter.getOAuthRequestToken();
//
//			String[] str = new String[2];
//			str[0] = oAuthRequestToken.getAuthenticationURL();
//			str[1] = oAuthRequestToken.getAuthorizationURL();
//
//			token = oAuthRequestToken.getToken();
//			tokenSecret = oAuthRequestToken.getTokenSecret();
//
//			prop.setProperty("token", token);
//			prop.setProperty("tokenSecret", tokenSecret);
//
//			File tmpFile = File.createTempFile("twitter", "token");
//			// File file = StreamUtil.getFile("conf/system/twitter.token");
//			FileWriter fw = new FileWriter(tmpFile);
//			prop.store(fw, "");
//			fw.close();
//
//			return str;
//		}
//		catch (Throwable e)
//		{
//			throw new RuntimeException("Cannot reset twitter tokens");
//		}
//	}

	public void updateStatus(String string)
	{
		try
		{
			twitter.updateStatus(string);
		}
		catch (TwitterException e)
		{
			WebLoggerProvider.logException("Cannot update twitter status", e);
		}
	}

	public String getToken()
	{
		return token;
	}

	public String getTokenSecret()
	{
		return tokenSecret;
	}

	public static void main(String[] args)
	{
		KTwitter kTwitter = new KTwitter();
		kTwitter.getStatusUpdates(5);
	}

}
