package com.karniyarik.web.sitemap.providers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.web.sitemap.BaseURLProvider;
import com.karniyarik.web.sitemap.Generator;
import com.karniyarik.web.sitemap.SiteMapGenerationConstants;
import com.karniyarik.web.sitemap.vo.urlset.TUrl;

public class InsightProvider extends BaseURLProvider {
	
	int index = 0;
	
	private List<String> labels = new ArrayList<String>();
	
	public InsightProvider(String rootPath)
	{
		this(rootPath, null);
	}
	
	public InsightProvider(String rootPath, String insightfilename) 
	{
		try {
			String filename = "insight.txt";
			if(insightfilename != null)
			{
				filename = insightfilename;
			}
			
			InputStream stream = StreamUtil.getStream(filename);
			List<String> lines = IOUtils.readLines(stream);
			stream.close();
			for (String query : lines) 
			{
				if(StringUtils.isNotBlank(query))
				{
					labels.add(query.trim());	
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int maxFileIndex = Generator.findMaxIndex(rootPath, getFilename());
		setFileStartIndex(maxFileIndex+1); 
	}
	
	@Override
	public String getFilename() {
		return "insight";
	}

	@Override
	public boolean hasNext() {
		return labels.size() > index;
	}

	@Override
	public TUrl next() 
	{
		String label = labels.get(index);
		index++;
		String url = generateURL (label, SiteMapGenerationConstants.PRODUCT_ROOT);		 
		TUrl urlType = createTUrl(url, 
				SiteMapGenerationConstants.INSIGHT_PRIORITY, 
				SiteMapGenerationConstants.INSIGHT_FREQ);		
		return urlType;
	}
}