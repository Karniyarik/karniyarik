package com.karniyarik.common.site;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.karniyarik.common.util.StringUtil;

public class FeaturedMerchantFactory 
{
	public static FeaturedMerchantVO create(String jsonStr)
	{
		try 
		{
			return create(new JSONObject(jsonStr));
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<FeaturedMerchantVO> createList(String jsonStr)
	{
		List<FeaturedMerchantVO> resultList = new ArrayList<FeaturedMerchantVO>();
		
		if(StringUtils.isNotBlank(jsonStr))
		{
			try {
				JSONArray siteJSONList = new JSONArray(jsonStr);
				
				for(int index = 0; index < siteJSONList.length(); index++)
				{
					JSONObject jsonObj = siteJSONList.getJSONObject(index);
					FeaturedMerchantVO info = create(jsonObj);
					resultList.add(info);
				}
			} catch (Exception e) {
				//e.printStackTrace();
				
			}			
		}
		
		return resultList;
	}
	
	public static FeaturedMerchantVO create(JSONObject rootObject)
	{
		FeaturedMerchantVO vo = new FeaturedMerchantVO();
		
		try 
		{
			String rootUrl =  StringUtil.optJSONString(rootObject,"urlroot");
			
			vo.setSitename(StringUtil.optJSONString(rootObject, "sitename"));
			
			JSONObject jsonObject = rootObject.optJSONObject("merchant");
			if(jsonObject != null)
			{
				vo.setFacebook(StringUtil.optJSONString(jsonObject,"facebook"));
				vo.setLogo(StringUtil.optJSONString(rootObject,"logo"));
				vo.setTwitter(StringUtil.optJSONString(jsonObject, "twitter"));
				vo.setAboutus(StringUtil.optJSONString(jsonObject,"aboutus"));
				vo.getContact().setContactus(StringUtil.optJSONString(jsonObject,"contactus"));
				vo.getContact().setUrl(StringUtil.optJSONString(jsonObject, "url"));
				vo.setName(jsonObject.optString("name"));
				//vo.getContact().set(jsonObject.optString("sitename"));
				vo.getContact().setCompany(StringUtil.optJSONString(jsonObject, "company"));
				vo.getContact().setAddress(StringUtil.optJSONString(jsonObject, "address"));
				vo.getContact().setEmail(StringUtil.optJSONString(jsonObject, "email"));
				vo.getContact().setPhone(StringUtil.optJSONString(jsonObject, "phone"));
				vo.getContact().setFax(StringUtil.optJSONString(jsonObject, "fax"));
				vo.getContact().setUrl(StringUtil.optJSONString(jsonObject, "url"));
				vo.getContact().setNotes(StringUtil.optJSONString(jsonObject, "notes"));	
				
				JSONArray jsonArray = jsonObject.optJSONArray("certificates");
				for(int index=0; index< jsonArray.length(); index++)
				{
					JSONObject tmpObj = jsonArray.getJSONObject(index);
					vo.getCertificates().add(new Certificate(StringUtil.optJSONString(tmpObj, "name"), rootUrl + "/" + StringUtil.optJSONString(tmpObj,"image")));
				}
				
				jsonArray = jsonObject.optJSONArray("shipmentTypes");
				for(int index=0; index< jsonArray.length(); index++)
				{
					JSONObject tmpObj = jsonArray.getJSONObject(index);
					vo.getShipment().add(new Shipment(StringUtil.optJSONString(tmpObj,"name"), rootUrl + "/" + StringUtil.optJSONString(tmpObj, "image")));
				}

				jsonArray = jsonObject.optJSONArray("paymentMethods");
				for(int index=0; index< jsonArray.length(); index++)
				{
					JSONObject tmpObj = jsonArray.getJSONObject(index);
					vo.getPayment().add(new Payment(StringUtil.optJSONString(tmpObj, "name"), rootUrl + "/" + StringUtil.optJSONString(tmpObj, "image")));
				}
				

				jsonArray = jsonObject.optJSONArray("links");
				for(int index=0; index< jsonArray.length(); index++)
				{
					JSONObject tmpObj = jsonArray.getJSONObject(index);
					vo.getLinks().put(StringUtil.optJSONString(tmpObj, "name"), StringUtil.optJSONString(tmpObj,"url"));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}
}