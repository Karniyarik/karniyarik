package com.karniyarik.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.notifier.MailNotifier;
import com.karniyarik.common.util.JerseyUtil;
import com.karniyarik.web.remote.ContactUsMessageVO;
import com.karniyarik.web.remote.SiteRegistrationRequestVO;

public class ContactServlet extends HttpServlet
{
	private static final long	serialVersionUID	= 7293518796613951471L;

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		//Captcha captcha = (Captcha) req.getSession().getAttribute(Captcha.NAME);
	    //req.setCharacterEncoding("UTF-8"); // Do this so we can capture non-Latin chars
	    //String answer = req.getParameter("answer");
		String returnAddress = req.getParameter("return");
		
		if (StringUtils.isBlank(returnAddress)) {
			returnAddress = "contact.jsp";
		}
		
		ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6LeYPrsSAAAAAMgfFTWdbmJ0G_1P5q087H6bnuNu", "6LeYPrsSAAAAAFixxc-DgpeWHJn2oezrhIso6Af7", true);
	    ReCaptchaResponse captchaResponse = captcha.checkAnswer(req.getRemoteAddr(), req.getParameter("recaptcha_challenge_field"), 
	    		req.getParameter("recaptcha_response_field"));

	    //if (req.getParameter("soru").equals("6")) {
	    if (captchaResponse.isValid()) {
	    	
	    	ContactUsMessageVO messageVO = new ContactUsMessageVO();
	    	messageVO.setGoal(req.getParameter("amac"));
	    	messageVO.setName(req.getParameter("name"));
	    	messageVO.setEmail(req.getParameter("email"));
	    	messageVO.setMessage(req.getParameter("message"));
	    	
			StringBuffer message = new StringBuffer();
			message.append("\nAmac: ");
			message.append(messageVO.getGoal());
			message.append("\nName: ");
			message.append(messageVO.getName()); 
			message.append("\nMail: ");
			message.append(messageVO.getEmail());
			message.append("\n\nMessage: \n");
			message.append("-------------------------\n");
			message.append(messageVO.getMessage());
			new MailNotifier().sendTextMail("Contact Us Message", message.toString());
			resp.sendRedirect(req.getContextPath() + "/" + returnAddress + "?status=ok");
			
	    	DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
	    	String enterpriseDeploymentURL = deploymentConfig.getEnterpriseDeploymentURL();
	    	JerseyUtil.sendJSONPut(enterpriseDeploymentURL + "/api/contactus/add", messageVO, false, 20000);
	    }
	    else
	    {
			resp.sendRedirect(req.getContextPath() + "/" + returnAddress + "?status=captchaerr&name="+req.getParameter("name")+"&email="+req.getParameter("email")+"&message="+req.getParameter("message")+"&amac="+req.getParameter("amac"));
	    }
	}
	
	@Override
	protected void doGet(HttpServletRequest aReq, HttpServletResponse aResp) throws ServletException, IOException
	{
	}
}
