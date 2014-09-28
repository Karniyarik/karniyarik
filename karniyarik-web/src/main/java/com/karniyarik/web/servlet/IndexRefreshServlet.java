package com.karniyarik.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.karniyarik.web.util.WebApplicationDataHolder;

public class IndexRefreshServlet extends HttpServlet
{
	private static final long	serialVersionUID	= 7293518796613951471L;

	@Override
	protected void doGet(HttpServletRequest aReq, HttpServletResponse aResp) throws ServletException, IOException
	{
		try
		{
			WebApplicationDataHolder.getInstance().refreshSiteData();
		}
		catch (Throwable e)
		{
			getLogger().error("Can not refresh index on web side", e);
		}
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}
}
