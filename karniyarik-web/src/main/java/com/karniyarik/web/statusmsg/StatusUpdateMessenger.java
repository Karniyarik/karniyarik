package com.karniyarik.web.statusmsg;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.WebConfig;
import com.karniyarik.common.statistics.vo.TopSearch;
import com.karniyarik.common.statistics.vo.TopSearches;
import com.karniyarik.common.util.StatisticsWebServiceUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.web.util.WebLoggerProvider;

public class StatusUpdateMessenger
{
	Logger log = Logger.getLogger(StatusUpdateMessenger.class);
	
	public void update()
	{
//		log.info("Status update is started");
		WebConfig webConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig();
		//FacebookJsonRestClient client = new FacebookJsonRestClient(webConfig.getFacebookKey(), webConfig.getFacebookSecret());
		updateStatus("ürünlerden ", "#encokarananurun", "urun", CategorizerConfig.PRODUCT_TYPE, webConfig);
		updateStatus("arabalardan ", "#encokarananaraba", "araba", CategorizerConfig.CAR_TYPE, webConfig);
		updateStatus("emlaklardan ", "#encokarananaraba", "emlak", CategorizerConfig.ESTATE_TYPE, webConfig);
		log.info("Status update executed successfully");
	}

	private void updateStatus(String msgType, String tag, String contextPath, int catType, WebConfig webConfig)
	{
		try
		{
			TopSearches topSearches = StatisticsWebServiceUtil.getWeeklySearches(catType, 2, new Date().getTime());
			if (topSearches != null)
			{
				List<TopSearch> queries = topSearches.getTopSearchList();

				if (queries != null && queries.size() > 0)
				{
					StringBuffer msg = new StringBuffer();
					msg.append("Geçen hafta ");
					msg.append(msgType);
					msg.append("en çok ");
					String query = queries.get(0).getQuery();
					query.replaceAll("\\s+", " ");
					if(query.length() > 78)
					{
						query = query.substring(0,75) + "...";
					}
					msg.append(query);
					msg.append("(");
					msg.append(shorten(contextPath, queries.get(0).getQuery(), webConfig));
					msg.append(")");
					msg.append(" arandı #FB");
					//msg.append(tag);
					//msg.append(" #karniyarik");

					KTwitter.getInstance().updateStatus(msg.toString());
				}
			}
			else
			{
				WebLoggerProvider.logException("Cannot update twitter status since top searches returned null", new Exception());
			}
		}
		catch (Throwable e)
		{
			WebLoggerProvider.logException("Cannot update twitter status", e);
		}
	}

	public String shorten(String contextPath, String query, WebConfig webConfig) throws Throwable
	{
		String shortUrl = "";
		String fullUrl = "http://www.karniyarik.com/" + contextPath + "/ne-kadar/" + query;
		String encodedUrl = URLEncoder.encode(fullUrl, StringUtil.DEFAULT_ENCODING);
		HttpClient httpclient = new DefaultHttpClient();

		/**
		 * tinyurl commented out bitly provides tracking options
		 */
		// HttpGet get = new HttpGet("http://tinyurl.com/api-create.php?url=" +
		// encodedUrl);
		// HttpResponse response = httpclient.execute(get);
		// shortUrl = EntityUtils.toString(response.getEntity());

		HttpGet get = new HttpGet("http://api.bit.ly/shorten?version=2.0.1&longUrl=" + encodedUrl + "&login=" + webConfig.getBitlyLogin() + "&apiKey=" + webConfig.getBitlyApiKey());
		HttpResponse response = httpclient.execute(get);

		String responseText = "";
		try
		{
			responseText = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(responseText);
			shortUrl = ((JSONObject) json.getJSONObject("results").get(fullUrl)).getString("shortUrl");
		}
		catch (Exception e)
		{

			StringBuffer sb = new StringBuffer();

			sb.append("Cannot shorten url\n");
			sb.append("contextPath : " + contextPath + "\n");
			sb.append("query : " + query + "\n");
			sb.append("fullUrl : " + fullUrl + "\n");
			sb.append("encodedUrl : " + encodedUrl + "\n");
			sb.append("response : " + responseText);

			WebLoggerProvider.logException(sb.toString(), e);

			throw e;
		}

		return shortUrl;
	}

	public static void main(String[] args) throws Throwable
	{
		StatusUpdateMessenger s = new StatusUpdateMessenger();
		s.update();
	}
}
