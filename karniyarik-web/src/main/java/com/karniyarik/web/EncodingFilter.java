package com.karniyarik.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.karniyarik.common.util.StringUtil;

public class EncodingFilter implements Filter
{

	@Override
	public void init(FilterConfig aArg0) throws ServletException
	{

	}

	@Override
	public void doFilter(ServletRequest aRequest, ServletResponse aResponse, FilterChain aChain) throws IOException, ServletException
	{
		((HttpServletRequest) aRequest).setCharacterEncoding(StringUtil.DEFAULT_ENCODING);
		aResponse.setCharacterEncoding(StringUtil.DEFAULT_ENCODING);
		aChain.doFilter(aRequest, aResponse);
	}

	@Override
	public void destroy()
	{

	}
}
