package com.karniyarik.externalrank.alexa;

import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.filters.StringInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.karniyarik.common.util.StringUtil;
import com.karniyarik.externalrank.NamespaceContextMap;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class AlexaWebService {

	private static final String ACTION_NAME = "UrlInfo";
	private static final String RESPONSE_GROUP_NAME = "Rank,Speed,RankByCountry,UsageStats,ContactInfo,LinksInCount,SiteData";
	//private static final String RESPONSE_GROUP_NAME = "Rank";
	private static final String AWS_BASE_URL = "http://awis.amazonaws.com/";
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private static final String DATEFORMAT_AWS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	private static final String ACCESS_KEY = "AKIAIC2DXKKZYVJKLGUA";
	private static final String SECRET = "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";
	
	private static final String title = "aws:UrlInfoResult/aws:Alexa/aws:ContentData/aws:SiteData/aws:Title";
	private static final String description = "aws:UrlInfoResult/aws:Alexa/aws:ContentData/aws:SiteData/aws:Description";
	private static final String onlineSince = "aws:UrlInfoResult/aws:Alexa/aws:ContentData/aws:SiteData/aws:OnlineSince";
	private static final String medianLoadTime = "aws:UrlInfoResult/aws:Alexa/aws:ContentData/aws:Speed/aws:MedianLoadTime";
	private static final String loadPercentage = "aws:UrlInfoResult/aws:Alexa/aws:ContentData/aws:Speed/aws:Percentile";
	private static final String linksInCount = "aws:UrlInfoResult/aws:Alexa/aws:ContentData/aws:LinksInCount";
	private static final String rank = "aws:UrlInfoResult/aws:Alexa/aws:TrafficData/aws:Rank";
	private static final String rankByCountry = "aws:UrlInfoResult/aws:Alexa/aws:TrafficData/aws:RankByCountry/aws:Country[@Code='TR']/aws:Rank";

	public AlexaSiteInfo getSiteInfo(String sitename, String siteUrl)
	{
		AlexaSiteInfo info = null;
		
		try {
			ClientConfig clientConfig = new DefaultClientConfig();
			Client client = Client.create(clientConfig);
			client.setConnectTimeout(20000);
			client.setReadTimeout(20000);
			
			WebResource webResource = client.resource(AWS_BASE_URL);
			
			
			String timestamp = getTimestampFromLocalTime(Calendar.getInstance().getTime());
			String signature = generateSignature("", ACTION_NAME, timestamp, SECRET);
			
			MultivaluedMap<String, String> params = new MultivaluedMapImpl();
			params.add("Action", ACTION_NAME);
			params.add("ResponseGroup", RESPONSE_GROUP_NAME);
			params.add("AWSAccessKeyId", ACCESS_KEY);
			params.add("Signature", URLEncoder.encode(signature, "UTF-8"));
			params.add("Timestamp", URLEncoder.encode(timestamp, "UTF-8"));
			params.add("Url", URLEncoder.encode(siteUrl, "UTF-8"));
			params.add("Version", "2005-07-11");
			
			ClientResponse response = webResource.queryParams(params).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (Status.OK.getStatusCode() == response.getStatus())
			{
				String resultStr = response.getEntity(String.class);
				
				DocumentBuilderFactory xmlFact = DocumentBuilderFactory.newInstance();
		        xmlFact.setNamespaceAware(true);
		        DocumentBuilder builder = xmlFact.newDocumentBuilder();
		        Document document = builder.parse(new StringInputStream(resultStr, StringUtil.DEFAULT_ENCODING));
		        
				//SAXReader reader = new SAXReader();
				//Document document = reader.read(new StringInputStream(resultStr, StringUtil.DEFAULT_ENCODING));
				
				XPath xPath = XPathFactory.newInstance().newXPath();
				NamespaceContextMap map = new NamespaceContextMap("aws", "http://awis.amazonaws.com/doc/2005-07-11");
				xPath.setNamespaceContext(map);
				
				Element root =  document.getDocumentElement();
				
				
				info = new AlexaSiteInfo();
				info.setSitename(sitename);
				
				info.setTitle(getStringValue(title, root.getFirstChild(), xPath));
				info.setDescription(getStringValue(description, root.getFirstChild(), xPath));
				info.setLinksInCount(getLongValue(linksInCount, root.getFirstChild(), xPath));
				info.setLoadPercentage(getLongValue(loadPercentage, root.getFirstChild(), xPath));
				info.setMedianLoadTime(getLongValue(medianLoadTime, root.getFirstChild(), xPath));
				info.setRank(getLongValue(rank, root.getFirstChild(), xPath));
				info.setRankByCountry(getLongValue(rankByCountry, root.getFirstChild(), xPath));
				info.setOnlineSince(getDateValue(onlineSince, root.getFirstChild(), xPath));	
			}
			
		} catch (Throwable t) {
			t.printStackTrace();
		} 
		
		return info;
	}

	private String getStringValue(String xPathStatement, Node document, XPath xPathExecutor) throws Exception {
		try {
			return (String) xPathExecutor.evaluate(xPathStatement, document, XPathConstants.STRING);
		} catch (Exception e) {
		}
		
		return "";
	}

	private Long getLongValue(String xPathStatement, Node document, XPath xPathExecutor) throws Exception {
		try {
			return ((Double) xPathExecutor.evaluate(xPathStatement, document, XPathConstants.NUMBER)).longValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Long.MAX_VALUE;
	}

	private Date getDateValue(String xPathStatement, Node document, XPath xPathExecutor) throws Exception {
		try {
			String onlineSinceStr = (String) xPathExecutor.evaluate(xPathStatement, document, XPathConstants.STRING);
			if(StringUtils.isNotBlank(onlineSinceStr))
			{
				SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
				return format.parse(onlineSinceStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getTimestampFromLocalTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT_AWS);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(date);
	}
	
	public String generateSignature(String service, String operation, String timestamp, String key) throws java.security.SignatureException {
		String result;
		try {
			String data = operation + timestamp;
			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());
			byte[] encodeBase64 = Base64.encodeBase64(rawHmac);
			result = new String(encodeBase64);

		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : "
					+ e.getMessage(), e);
		}
		return result;
	}
	
	public static void main(String[] args) {
		new AlexaWebService().getSiteInfo("karniyarik", "http://www.karniyarik.com/main/index.jsp");
	}
}
