package com.karniyarik.web;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.wurfl.core.Device;
import net.sourceforge.wurfl.core.WURFLHolder;
import net.sourceforge.wurfl.core.WURFLManager;
import net.sourceforge.wurfl.wng.WNGDevice;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.RobotChecker;
import com.karniyarik.web.category.AbstractCategoryHandler;
import com.karniyarik.web.mobile.MobileUtil;
import com.karniyarik.web.util.RequestWrapper;

import cz.mallat.uasparser.UserAgentInfo;

public class UserAgentFilter implements Filter
{
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest aRequest, ServletResponse aResponse, FilterChain aChain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) aRequest;
		HttpServletResponse response = (HttpServletResponse) aResponse;
		
		boolean filterForMobile = true;
		String userAgentType = "";
		String pMobile = null;
		boolean isAPICall = false;
		boolean isServletCall = false;
		String servletPath = request.getServletPath();

		//mobile check
		String[] nonFilteredResources = new String[] {".css",".js",".png",".jpg",".gif",".pdf",".jpeg",".rdf",".txt",".ico",".zip"}; 
		for(String nonFilteredResource : nonFilteredResources)
		{
			if(servletPath.endsWith(nonFilteredResource))
			{
				filterForMobile = false;
				break;
			}
		}
		
		String[] nonFilteredPages = new String[] {"slider.jsp","widget_x.jsp","widget_y.jsp"}; 
		for(String nonFilteredPage : nonFilteredPages)
		{
			if(servletPath.contains(nonFilteredPage))
			{
				filterForMobile = false;
				break;
			}
		}
		
		//is it a servlet call?
		if(servletPath.startsWith("/system") || 
				servletPath.startsWith("/rest") || 
				servletPath.startsWith("/site/") || 
				servletPath.startsWith("/sehirfirsatlari/rss") ||
				servletPath.startsWith("/sehir-firsati/rss") || 
				servletPath.startsWith("/img/") ||
				servletPath.startsWith("/imgrsz/")) 
		{
			isServletCall = true;
			filterForMobile = false;
		}

		if(!filterForMobile)
		{
			aChain.doFilter(aRequest, aResponse);
			return;
		}

		//is it an api call?
		String apikey = AbstractCategoryHandler.getParameterValue(RequestWrapper.API_KEY, request.getParameterMap());
		if(StringUtils.isNotBlank(apikey))
		{
			filterForMobile = false;
		}

		//check user agent		
		String userAgent = request.getHeader("user-agent");
		
		if(userAgent == null)
		{
			userAgent = "";
		}
		UserAgentInfo userAgentInfo = RobotChecker.getInstance().getUserAgentInfo(userAgent);
		if(userAgentInfo != null){
			userAgentType = userAgentInfo.getTyp();
			if(userAgentType == null)
			{
				userAgentType = "";
			}
		}
		
		userAgentType = userAgentType.toLowerCase(Locale.ENGLISH);
		userAgent = userAgent.toLowerCase(Locale.ENGLISH);
		
		//robot check
		if(userAgentType.equals("robot"))
		{
			request.setAttribute(RequestWrapper.ISROBOT, true);
		}
		
		
		if(filterForMobile == true)
		{			
			boolean redirectToMobile = false;
			Device device = null;
			boolean contextHasMobile = false;
			
			boolean paramMobile = false;
			boolean deviceIsMobile = false;
			
			contextHasMobile = servletPath.startsWith("/" + RequestWrapper.MOBILECONTEXT+"/") || 
				servletPath.startsWith("/" + RequestWrapper.IMOBILECONTEXT+"/");
			
			//does the request contains parameter about mobile
			//I will use it mainly for direction from mobile to standard interface
			//i.e m=false
			pMobile = request.getParameter(RequestWrapper.ISMOBILE);
			if(StringUtils.isNotBlank(pMobile)){
				try{
					paramMobile = Boolean.valueOf(pMobile); 
				}catch(Exception e){};
			}

			//try to identify mobile device (if it is)
			WURFLHolder wurflHolder = (WURFLHolder) filterConfig.getServletContext().getAttribute("net.sourceforge.wurfl.core.WURFLHolder");
			WURFLManager wurfl = wurflHolder.getWURFLManager();
			device = wurfl.getDeviceForRequest(request);
			
			String deviceisMobileStr = "";
			if(device != null)
			{
				deviceisMobileStr = device.getCapability("is_wireless_device");
			}
			 
			if(StringUtils.isNotBlank(deviceisMobileStr))
			{
				deviceIsMobile = Boolean.valueOf(deviceisMobileStr);
			}
			else if(userAgentType.equals("mobile browser") || 
					userAgentType.equals("wap browser") || 
					MobileUtil.isAndroid(userAgentType) || 
					MobileUtil.isIPodOrDerivative(userAgentType))
			{
				deviceIsMobile = true;
			}
			
			//if context already includes mobile context or
			//if the mobile parameter exists and equal to false
			if(contextHasMobile || (StringUtils.isNotBlank(pMobile) && paramMobile==false))
			{
				redirectToMobile = false;
			} 
			//if mobile parameter exists and is true
			else if(paramMobile == true)
			{
					redirectToMobile = true;
			}
			else if(deviceIsMobile)
			{
					redirectToMobile = true;
			}
			
			if(redirectToMobile)
			{
				setMobileContextAttr(request, userAgent, device);
				String newURL = getNewURL(request);
				request.getRequestDispatcher(newURL).forward(request, response);
				return;
			}
			else if(contextHasMobile)
			{
				setMobileContextAttr(request, userAgent, device);
			}
			else
			{
				request.setAttribute(RequestWrapper.ISMOBILE, false);	
			}
		}
		
		
		aChain.doFilter(aRequest, aResponse);
	}

	private void setMobileContextAttr(HttpServletRequest request,
			String userAgent, Device device) {
		
		String deviceID = "";
		String deviceBrand = "";
		String deviceOS = ""; 
		Boolean touchscreen = false;
		String pointer = "";
		if(device!=null)
		{
			String brandName = device.getCapability("brand_name");
			String osName = device.getCapability("device_os");
			deviceID = brandName+" " +osName;
			
			pointer = device.getCapability("pointing_method");
		}
		else
		{
			deviceID = userAgent;
		}
		
		deviceID = deviceID.toLowerCase(Locale.ENGLISH);

		if(pointer.contains("touchscreen") || MobileUtil.isIPodOrDerivative(deviceID)){
			touchscreen = true;	
		}

		request.setAttribute(MobileUtil.TOUCHSCREEN, touchscreen);
		request.setAttribute(MobileUtil.DEVICEID, deviceID);
		request.setAttribute(MobileUtil.DEVICEBRAND, deviceBrand);
		request.setAttribute(MobileUtil.DEVICEOS, deviceOS);
		request.setAttribute(RequestWrapper.ISMOBILE, true);

		//ignore for now
//		if(touchscreen || MobileUtil.isAndroid(deviceID) || MobileUtil.isIPodOrDerivative(deviceID))
//		{
//			request.setAttribute(RequestWrapper.MOBILECONTEXT_ATTR, "i");
//		}
//		else
//		{
//			request.setAttribute(RequestWrapper.MOBILECONTEXT_ATTR, "m");
//		}

		request.setAttribute(RequestWrapper.MOBILECONTEXT_ATTR, "m");
	}

	private String getNewURL(HttpServletRequest request)
	{
		StringBuffer newUrl = new StringBuffer();
		
		if(StringUtils.isNotBlank(request.getContextPath()))
		{
			//newUrl.append(request.getContextPath());
		}
		newUrl.append("/");
		newUrl.append(request.getAttribute(RequestWrapper.MOBILECONTEXT_ATTR));
		newUrl.append(request.getServletPath());
		
		if(StringUtils.isNotBlank(request.getQueryString()))
		{
			newUrl.append("?");
			newUrl.append(request.getQueryString());
		}
		
		return newUrl.toString();
	}

	@Override
	public void destroy()
	{

	}
}
