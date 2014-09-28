package com.karniyarik.common.site;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.karniyarik.common.util.StringUtil;

public class SiteInfoFactory 
{
	public static List<SiteInfo> create(String jsonStr)
	{
		List<SiteInfo> resultList = new ArrayList<SiteInfo>();
		
		if(StringUtils.isNotBlank(jsonStr))
		{
			try {
				JSONArray siteJSONList = new JSONArray(jsonStr);
				
				for(int index = 0; index < siteJSONList.length(); index++)
				{
					JSONObject jsonObj = siteJSONList.getJSONObject(index);
					SiteInfo info = create(jsonObj);
					resultList.add(info);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		return resultList;
	}
	
	public static SiteInfo create(JSONObject jsonObject)
	{
		SiteInfo vo = new SiteInfo();
		vo.setDescription(StringUtil.optJSONString(jsonObject, "description"));
		vo.setDisplayName(StringUtil.optJSONString(jsonObject, "displayName"));
		vo.setFeatured(jsonObject.optBoolean("featured"));
		vo.setFeedURL(StringUtil.optJSONString(jsonObject, "feedURL"));
		vo.setLogourl(StringUtil.optJSONString(jsonObject, "logourl"));
		vo.setName(StringUtil.optJSONString(jsonObject, "name"));
		vo.setUrl(StringUtil.optJSONString(jsonObject, "url"));
		vo.setSponsored(jsonObject.optBoolean("sponsored"));
		
		return vo;
	}
}