package com.karniyarik.web.servlet;

import java.io.InputStream;

import javax.servlet.http.HttpServlet;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.util.StringUtil;

@SuppressWarnings("serial")
public class MailChimpRESTProxy extends HttpServlet
{
	public static void sendToMailChimp(String email, String city, String category)
	{
		String result = "";
		try
		{
			HttpClient client = new DefaultHttpClient();
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 30000);
			HttpConnectionParams.setSoTimeout(params, 30000);
			//params.setParameter("User-agent", botName);
			params.setParameter("Cache-Control", "max-age=0,no-cache");
			params.setParameter("Pragma", "no-cache");
			
			
			CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();			
			int categoryType = categorizerConfig.getCategoryType(category);

			String id =null;
			String source = "";
			switch (categoryType) {
				case CategorizerConfig.CAR_TYPE:id="ba02ca49c3";source="car";break;
				case CategorizerConfig.PRODUCT_TYPE:id="ba02ca49c3";source="product";break;
				case CategorizerConfig.ESTATE_TYPE:id="ba02ca49c3";source="estate";break;
				case CategorizerConfig.CITY_DEAL_TYPE:id="ba02ca49c3";source="citydeal";break;
				default:id="ba02ca49c3";break;
			}
			
			StringBuffer url = new StringBuffer();
			url.append("http://us2.api.mailchimp.com/1.2/?method=listSubscribe&apikey=98f2d57bdc10bdb5150cd39f139bfb31-us2&id="+id+"&output=json");
			url.append("&email_address=");
			url.append(email);
			url.append("&merge_vars[CITY]=");
			url.append(city);
			if(StringUtils.isNotBlank(source))
			{
				url.append("&merge_vars[SOURCE]=");
				url.append(source);				
			}
			
			HttpGet get = new HttpGet(url.toString());
			
			HttpResponse execute = client.execute(get);
			InputStream content = execute.getEntity().getContent();
			result = IOUtils.toString(content, StringUtil.DEFAULT_ENCODING);
			execute.getEntity().consumeContent();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
}
