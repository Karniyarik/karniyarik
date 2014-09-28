package com.karniyarik.web.sitemap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.apache.commons.lang.StringEscapeUtils;

import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.web.sitemap.vo.urlset.TChangeFreq;
import com.karniyarik.web.sitemap.vo.urlset.TUrl;
import com.karniyarik.web.util.QueryStringHelper;

public abstract class BaseURLProvider implements URLProviderIterator {
	
	private int fileStartIndex = 0;
	
	private DecimalFormat format = null;
	
	QueryStringHelper helper = new QueryStringHelper();
	
	public BaseURLProvider() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(',');
		format = new DecimalFormat("0.0", symbols);
		helper.setSort(1);
		helper.setShowImages(1);
		helper.setPageSize(SearchConfig.DEFAULT_PAGE_SIZE);
		helper.setContextPath("");
	}
	
	public abstract boolean hasNext();

	public abstract TUrl next();

	public abstract String getFilename();

	protected String encode(String query) {
		try {
			query = URLEncoder.encode(query, "UTF-8");
			//query = StringEscapeUtils.escapeXml(query);
			return query;
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	protected String cleanHTML(String query) {
		query = org.carrot2.util.StringUtils.removeHtmlTags(query);
		query = cleanProblematicCharacters(query);
		//query = StringEscapeUtils.escapeXml(query);
		return query;
	}

	public static String cleanProblematicCharacters(String query) {
		query = query.replaceAll("\\/", " ");
		query = query.replaceAll("\\\\", " ");
		query = query.replaceAll(":", " ");
		query = query.replaceAll("\\s+", " ");
		return query;
	}

	public TUrl createTUrl(String url, double priority, TChangeFreq changeFreq) {
		TUrl urlType = SitemapTypeFactory.createUrl();
		urlType.setLoc(url);
		urlType.setChangefreq(changeFreq);
		urlType.setPriority(format.format(priority));
		return urlType;
	}
	
	public String getRootURL()
	{
		return SiteMapGenerationConstants.rootURL;
	}
	
	public String generateURL(String query, String category)
	{
		helper.setCategoryFilterValue(encode(category));
		query = query.trim();
		query = cleanHTML(query);
		helper.setSearchQuery(query);
		//query = encode(query);
		
		return getRootURL() + helper.getRequestQuery();
	}
	
	public int getFileStartIndex() {
		return fileStartIndex;
	}
	
	public void setFileStartIndex(int fileStartIndex) {
		this.fileStartIndex = fileStartIndex;
	}
	
	public static void main(String[] args) {
		
		BaseURLProvider provider = new BaseURLProvider() {
			
			@Override
			public TUrl next() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public String getFilename() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		//String cleanHTML = provider.cleanHTML("MERCEDES E CLASS- CLASS W124 W124- 85/95/Diğer");
		//System.out.println(cleanHTML);
	}
}
