package com.karniyarik.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.notifier.MailNotifier;
import com.karniyarik.common.util.JerseyUtil;
import com.karniyarik.web.remote.SiteRegistrationRequestVO;

public class AddSiteServlet extends HttpServlet
{
	private static final long serialVersionUID = -5493045153566273797L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		//Captcha captcha = (Captcha) req.getSession().getAttribute(Captcha.NAME);
	    //req.setCharacterEncoding("UTF-8"); // Do this so we can capture non-Latin chars
	    //String answer = req.getParameter("answer");
		String returnAddress = req.getParameter("return");
		ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6LeYPrsSAAAAAMgfFTWdbmJ0G_1P5q087H6bnuNu", "6LeYPrsSAAAAAFixxc-DgpeWHJn2oezrhIso6Af7", true);
	    ReCaptchaResponse captchaResponse = captcha.checkAnswer(req.getRemoteAddr(), req.getParameter("recaptcha_challenge_field"), 
	    		req.getParameter("recaptcha_response_field"));

		String name = getRequestParam(req, "name");
		String email = getRequestParam(req, "email");
		String phone = getRequestParam(req, "phone");
		String sitename = getRequestParam(req, "sitename");
		String url = getRequestParam(req, "url");
		String description = getRequestParam(req, "message");
		String dataFeed = getRequestParam(req, "datafeed");

	    if (captchaResponse.isValid()) {
	    //if (req.getParameter("soru").equals("6")) {
	    	
	    	SiteRegistrationRequestVO request = new SiteRegistrationRequestVO();
	    	request.setRegistrarName(name);
	    	request.setDescription(description);
	    	request.setEmail(email);
	    	request.setFeedURL(dataFeed);
	    	request.setPhone(phone);
	    	request.setSiteName(sitename);
	    	request.setUrl(url);

	    	DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
	    	
	    	String enterpriseDeploymentURL = deploymentConfig.getEnterpriseDeploymentURL();
	
	    	JerseyUtil.sendJSONPut(enterpriseDeploymentURL + "/api/siteregistration/add", request, false, 20000);
	    	
			StringBuffer message = new StringBuffer();
			
			message.append("\nName: ");
			message.append(name);
			message.append("\nEmail: ");
			message.append(email);
			message.append("\nPhone: ");
			message.append(phone);
			message.append("\nSite Name: ");
			message.append(sitename);
			message.append("\nURL: ");
			message.append(url);
			message.append("\nFeed: ");
			message.append(dataFeed);
			message.append("\nDescription: \n");			
			message.append(description);
			new MailNotifier().sendTextMail("Site Registration Request: " + sitename, message.toString());
			resp.sendRedirect(req.getContextPath() + "/" + returnAddress + "?status=ok");
	    }
	    else
	    {
			resp.sendRedirect(req.getContextPath() + "/" + returnAddress + "?status=captchaerr" + 
					"&name="+ name + 
					"&email="+email + 
					"&sitename="+sitename +
					"&datafeed="+dataFeed +
					"&url="+url+
					"&phone="+phone + 
					"&message=" + description);
	    }
	}
	
	
	public static String getRequestParam(HttpServletRequest req, String name)
	{
		String result = req.getParameter(name);
		result = (result == null ? "" : result);
		return result;
	}
	@Override
	protected void doGet(HttpServletRequest aReq, HttpServletResponse aResp) throws ServletException, IOException
	{
	}
}
