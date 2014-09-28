package com.karniyarik.web.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.karniyarik.common.db.DBConnectionProvider;

@SuppressWarnings("serial")
public class SaveEmailAndCityServlet extends HttpServlet
{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		String email = getRequestParam(req, "email");
		String city = getRequestParam(req, "city");
		String category = getRequestParam(req, "cat");
		
		if (validateEmail(email)) {
			email = email.trim();
			MailChimpRESTProxy.sendToMailChimp(email,city, category);
			//insertEmailAndCity(email,city);
		}
			
	}
	
	private void insertEmailAndCity(String email, String city) {

		Connection connection = DBConnectionProvider.getWebConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "INSERT INTO CITY_DEAL_USERS(EMAIL, CITY) VALUES(?,?)";

		try
		{
			statement = connection.prepareStatement(query);
				
			statement.setString(1, email);
			statement.setString(2, city);
			
			statement.executeUpdate();
			
			connection.commit();
		}
		catch (SQLException e)
		{
			getLogger().log(Level.ERROR, "::: ERROR while saving email and city. REASON -> " + e.getErrorCode() + " -- " + e.getMessage());
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}
	}

	private boolean validateEmail(String email) {
		return !StringUtils.isEmpty(email);
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

	public static String getRequestParam(HttpServletRequest req, String name)
	{
		String result = req.getParameter(name);
		result = (result == null ? "" : result);
		return result;
	}	
}
