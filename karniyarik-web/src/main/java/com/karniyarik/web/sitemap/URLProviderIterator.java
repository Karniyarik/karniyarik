package com.karniyarik.web.sitemap;

import com.karniyarik.web.sitemap.vo.urlset.TUrl;

public interface URLProviderIterator 
{
	public boolean hasNext();
	public TUrl next();
	public String getFilename();
	public int getFileStartIndex();
}
