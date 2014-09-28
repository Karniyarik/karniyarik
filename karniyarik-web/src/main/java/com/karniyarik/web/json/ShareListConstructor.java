package com.karniyarik.web.json;

import java.util.HashMap;
import java.util.Map;

public class ShareListConstructor
{
	private Map<String, String> shareList = null;
	
	public ShareListConstructor()
	{
		shareList = new HashMap<String, String>();
		shareList.put("blinklist.gif", "http://blinklist.com/index.php?Action=Blink/addblink.php&Url=");
		shareList.put("delicious.gif", "http://del.icio.us/post?url=");
		shareList.put("digg.gif", "http://digg.com/submit?phase=2&url=");
		shareList.put("facebook.gif", "http://www.facebook.com/share.php?u=");
		shareList.put("furl.gif", "http://furl.net/storeIt.jsp?u=");
		shareList.put("google.gif", "http://www.google.com/bookmarks/mark?op=edit&bkmk=");
		shareList.put("reddit.gif", "http://en.reddit.com/submit?url=");
		shareList.put("stumbleupon.gif", "http://www.stumbleupon.com/submit?url=");
		shareList.put("technorati.gif", "http://www.technorati.com/faves?add=");
		shareList.put("windowslive.gif", "https://favorites.live.com/quickadd.aspx?marklet=1&mkt=en-us&url=");
		shareList.put("yahoo.gif", "http://myweb2.search.yahoo.com/myresults/bookmarklet?u=");
	}
	
//	public void updateProduct(ProductResult productResult, RequestWrapper requestWrapper)
//	{
//		for(String name: shareList.keySet())
//		{
//			LinkedLabel label = new LinkedLabel("images/share/" + name, shareList.get(name) + requestWrapper.getRequest().getRequestURL() + "?" + RequestWrapper.PRODUCT_ID + "=" + productResult.getId());
//			productResult.getShareList().add(label);
//		}
//	}
}