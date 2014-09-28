package com.karniyarik.statistics;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.IOUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.util.TableNameUtil;

public class SiteClickStatDB {

	public void run() {
		String tmp = "INSERT INTO TBL_SITE_CLICK_STAT "
				+ "(FLD_SITE_NAME, FLD_LISTING_VIEW, FLD_LISTING_HIT, "
				+ "FLD_SPONSOR_VIEW, FLD_SPONSOR_HIT, FLD_DATE) "
				+ "SELECT \"sitename\", SUM(FLD_LISTING_VIEW) AS FLD_LISTING_VIEW, "
				+ "SUM(FLD_LISTING_HIT) AS FLD_LISTING_HIT, SUM(FLD_SPONSOR_VIEW) AS FLD_SPONSOR_VIEW, "
				+ "SUM(FLD_SPONSOR_HIT) AS FLD_SPONSOR_HIT, MIN(FLD_DATE) AS FLD_DATE "
				+ "FROM tablename WHERE TO_DAYS(FLD_DATE) IN "
				+ "(SELECT DISTINCT TO_DAYS(FLD_DATE) FROM tablename) GROUP BY TO_DAYS(FLD_DATE);";

		ArrayList<String> list = new ArrayList<String>();
		Collection<SiteConfig> siteConfigList = KarniyarikRepository
				.getInstance().getConfig().getConfigurationBundle()
				.getSitesConfig().getSiteConfigList();
		for (SiteConfig siteConfig : siteConfigList) {
			list.add(siteConfig.getSiteName());
		}
		list.addAll(KarniyarikRepository.getInstance().getConfig()
				.getConfigurationBundle().getApiKeyConfig().getAPIKeys());

		StringBuffer buff = new StringBuffer();
		
		String q;
		for (String siteName : list) {
			q = tmp.replaceAll("tablename", TableNameUtil
					.createProductStatTableName(siteName));
			q = q.replaceAll("sitename", siteName);
			
			buff.append(q);
			buff.append("\n");
			
		}
		
		try
		{
			IOUtils.write(buff, new FileOutputStream("D:\\all.sql"));
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String q;
//		Connection connection;
//		PreparedStatement statement;
//		int processed = 0;
//		for (String siteName : list) {
//			q = tmp.replaceAll("tablename", TableNameUtil
//					.createProductStatTableName(siteName));
//			q = q.replaceAll("sitename", siteName);
//
//			connection = DBConnectionProvider.getConnection(Boolean.FALSE,
//					Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
//			statement = null;
//			processed++;
//			try {
//				statement = connection.prepareStatement(q);
//				statement.executeUpdate();
//				connection.commit();
//				System.out.println("Success: " + siteName + "\t processed: "
//						+ processed + "\t remaining: "
//						+ (list.size() - processed));
//			} catch (Throwable e) {
//				System.out.println("Fail: " + siteName + "\t processed: "
//						+ processed + "\t remaining: "
//						+ (list.size() - processed));
//			} finally {
//				DBConnectionProvider
//						.closeResources(connection, statement, null);
//			}
//		}
	}

	public static void main(String[] args) {
		new SiteClickStatDB().run(); 
	}

}
