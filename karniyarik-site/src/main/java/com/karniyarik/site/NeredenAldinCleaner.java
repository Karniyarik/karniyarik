package com.karniyarik.site;

import java.io.BufferedReader;
import java.io.StringReader;

public class NeredenAldinCleaner {
	public NeredenAldinCleaner() {
		
	}
	
//	public String clean(String content) throws Exception
//	{
//		StringReader reader = new StringReader(content);
//		StringBuffer buffer = new StringBuffer();
//		
//		BufferedReader breader = new BufferedReader(reader);
//		String line = null;
//		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//		buffer.append("<article>\n");
//		while((line = breader.readLine()) != null)
//		{
//			if(! (line.contains("head>") || line.contains("<meta") || StringUtils.isBlank(line) || line.contains("body>")))
//			{
//				buffer.append(line);
//				if(line.contains("<link>"))
//				{
//					buffer.append("</link>");
//				}
//				buffer.append("\n");
//			}
//		}
//		buffer.append("</article>");
//		return buffer.toString();
//	}
	
	public String clean(String content) throws Exception
	{
		StringReader reader = new StringReader(content);
		StringBuffer buffer = new StringBuffer();
		
		BufferedReader breader = new BufferedReader(reader);
		String line = null;
		
		while((line = breader.readLine()) != null)
		{
			if(!line.contains("DOCTYPE"))
			{
				buffer.append(line);
				buffer.append("\n");
			}
		}
		
		return buffer.toString();
	}

}
