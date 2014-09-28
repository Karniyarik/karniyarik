package com.karniyarik.web.sitemap;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.karniyarik.web.sitemap.vo.urlset.TUrl;
import com.karniyarik.web.sitemap.vo.urlset.Urlset;

public class Generator {
	public static int MAX_URL_COUNT = 500;

	public int fileIndex = 0;
	private List<String> fileNames = new ArrayList<String>();
	private URLCache cache = null;
	private URLProviderIterator sourceProvider = null;
	private String rootPath = null;
	private Date date = null;

	public Generator(URLCache cache, URLProviderIterator sourceProvider,
			String rootPath) {
		this.cache = cache;
		this.sourceProvider = sourceProvider;
		this.rootPath = rootPath;
		fileIndex = sourceProvider.getFileStartIndex();
		date = Calendar.getInstance().getTime();
	}

	public void generate() {
		Urlset urlSet = SitemapTypeFactory.createUrlSet();

		while (sourceProvider.hasNext()) {
			TUrl url = sourceProvider.next();

			if (!cache.exists(url.getLoc())) {
				url.setLastmod(getUTCDate(date));
				urlSet.getUrl().add(url);
			}
			
			if(urlSet.getUrl().size() > MAX_URL_COUNT)
			{
				writeSitemap(fileIndex, urlSet);
				fileIndex++;
				urlSet = SitemapTypeFactory.createUrlSet();
			}
		}
		
		//for'dan ciktiktan sonra bir daha kontrol et. kalanlari da yaz
		if(urlSet.getUrl().size() > 0)
		{
			writeSitemap(fileIndex, urlSet);
		}
	}
	
	private void writeSitemap(int fileIndex, Urlset urlSet)
	{
		String tmpFileName = sourceProvider.getFilename() + "-" + fileIndex;
		SitemapIO.writeMap(rootPath, tmpFileName, urlSet);
		System.out.println("Sitemap " + tmpFileName + " is written");
		fileNames.add(tmpFileName + ".xml");
	}

	public static String getUTCDate(Date date) {
		return DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(date);
	}

	public List<String> getFileNames() {
		return fileNames;
	}
	
	public static int findMaxIndex(String rootPath, String fileName) {
		int maxFileIndex = 0;
		File file = new File(rootPath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		Collection<File> files = (Collection<File>) FileUtils.listFiles(file, new String[]{"xml"}, false);
		
 		for(File tmpFile: files)
		{
			String name = tmpFile.getName();
			if(name.contains(fileName))
			{
				name=name.replace(fileName, "");
				name=name.replace("-", "");
				name=name.replace(".xml", "");
				int tmpIndex = Integer.valueOf(name);
				if(tmpIndex > maxFileIndex)
				{
					maxFileIndex = tmpIndex;
				}
			}
		}
		
		return maxFileIndex;
	}

}
