package com.karniyarik.categorizer.matcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.categorizer.io.CategoryWriter;
import com.karniyarik.categorizer.vo.Category;
import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.crawler.util.URLAnalyzer;

public class SiteCategoryFetcher
{
	private static final String	QUERY	= "SELECT DISTINCT P.FLD_BREADCRUMB, L.FLD_LINK FROM TBL_PRODUCTS P, TBL_LINKS L WHERE "
												+ "(P.FLD_BREADCRUMB IS NOT NULL) AND (P.FLD_LINK_ID=L.FLD_ID) AND (L.FLD_SITE_NAME=?)";

	private URLAnalyzer mURLAnalyzer = null;
	
	public SiteCategoryFetcher()
	{
		mURLAnalyzer = new URLAnalyzer();
	}

	public void fetch(String aSiteName, String aDelimiter, String[] aCategoryAttrArr)
	{
		Map<String, String> aBreadcrumbMap = getBreacrumbs(aSiteName);
		Map<String, Category> aCategoryMap = new HashMap<String, Category>();
		
		String aCategoryID = null;

		Category aRootCategory = new Category();
		aRootCategory.setName("Root");

		aCategoryMap.put(aRootCategory.getName(), aRootCategory);

		List<String> aCategoryAttrList = new ArrayList<String>();
		
		aCategoryAttrList = Arrays.asList(aCategoryAttrArr);
		
		for (String aBreadCrumb : aBreadcrumbMap.keySet())
		{
			aCategoryID = getCategoryID(aCategoryAttrList, aBreadcrumbMap.get(aBreadCrumb), aBreadCrumb);

			analyzeBreadCrumb(aBreadCrumb, aCategoryMap, aDelimiter, aCategoryID, aRootCategory);
		}

		new CategoryWriter().write(aRootCategory, aSiteName + "-categories.xml");

	}

	public String getCategoryID(List<String> aCategoryAttrArr, String aURL, String aBreadCrumb)
	{
		String aCategoryID = null;

		Map<String, String> aQueryParameters = mURLAnalyzer.getQueryParameters(aURL, true);

		for (String aCategoryAttr : aCategoryAttrArr)
		{
			aCategoryID = aQueryParameters.get(aCategoryAttr);
			if (StringUtils.isNotBlank(aCategoryID))
			{
				break;
			}
		}

		if (aCategoryID == null)
		{
			aCategoryID = aBreadCrumb;
		}

		return aCategoryID;
	}

	private void analyzeBreadCrumb(String aBreadCrumb, Map<String, Category> aCategoryMap, String aDelimiter, String aCategoryID,
			Category aRootCategory)
	{
		String[] aSubCategories = aBreadCrumb.split(aDelimiter);

		Category aCategory = null;

		Category aParentCategory = null;

		for (int anIndex = 0; anIndex < aSubCategories.length; anIndex++)
		{
			String aCategoryStr = aSubCategories[anIndex].trim();

			if (StringUtils.isNotBlank(aCategoryStr))
			{
				if (aCategoryMap.containsKey(aCategoryStr))
				{
					aParentCategory = aCategoryMap.get(aCategoryStr);
				}
				else
				{
					aCategory = new Category();
					aCategory.setName(aCategoryStr);

					if (aParentCategory != null)
					{
						aParentCategory.addChild(aCategory);
					}
					else
					{
						aRootCategory.addChild(aCategory);
					}

					if (anIndex == (aSubCategories.length - 1))
					{
						aCategory.setID(aCategoryID);
					}
					else
					{
						aParentCategory = aCategory;
					}

					aCategoryMap.put(aCategory.getName(), aCategory);
				}
			}
		}
	}

	private static Map<String, String> getBreacrumbs(String aSiteName)
	{
		Map<String, String> aBreadcrumbMap = new HashMap<String, String>();

		Connection connection = DBConnectionProvider.getConnection(true, false, Connection.TRANSACTION_READ_COMMITTED);

		try
		{
			PreparedStatement aStatement = connection.prepareStatement(QUERY);

			aStatement.setString(1, aSiteName);

			ResultSet aResultSet = aStatement.executeQuery();

			String aBreadcrumb = null;

			while (aResultSet.next())
			{
				aBreadcrumb = aResultSet.getString("P.FLD_BREADCRUMB");

				if (!aBreadcrumbMap.containsKey(aBreadcrumb))
				{
					aBreadcrumbMap.put(aBreadcrumb, aResultSet.getString("L.FLD_LINK"));
				}
			}

			aResultSet.close();
			aStatement.close();
			connection.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnectionProvider.closeConnection(connection);
		}

		return aBreadcrumbMap;
	}

	public static void main(String[] args)
	{
		// new SiteCategoryFetcher().fetch("hipernex", ">", new String[]{"cid"});
		// new SiteCategoryFetcher().fetch("ereyon", "\\\\", new String[] {"ctrid", "ctlid" });
		//new SiteCategoryFetcher().fetch("hepsiburada", ">", new String[] { "categoryid", "catid" });
		new SiteCategoryFetcher().fetch("estore", ">", new  String[]{"cid"});
	}

}
