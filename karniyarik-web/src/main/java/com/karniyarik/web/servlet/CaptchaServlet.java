package com.karniyarik.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;

@SuppressWarnings("serial")
public class CaptchaServlet extends HttpServlet
{
	private int width = 0;
	private int height = 0;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		width = Integer.parseInt(config.getInitParameter("width"));
		height= Integer.parseInt(config.getInitParameter("height"));
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Captcha captcha = new Captcha.Builder(width, height)
		   .addText()
		   .addBackground()
		   .addNoise()
		   .gimp()
		   .addBorder()
		   .build(); 
		
		req.getSession().setAttribute(Captcha.NAME, captcha.getAnswer());
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(captcha.getImage(), "png",baos);

		resp.getOutputStream().write(baos.toByteArray());
	}

}
