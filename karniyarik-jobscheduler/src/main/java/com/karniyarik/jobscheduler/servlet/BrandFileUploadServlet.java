package com.karniyarik.jobscheduler.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.jobscheduler.SchedulerGroupServer;

public class BrandFileUploadServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String tempDir = StreamUtil.getTempDir();
		File file = new File(tempDir + "/uploads");
		if(!file.exists())
		{
			file.mkdir();
		}
		
		DiskFileItemFactory factory = newDiskFileItemFactory(getServletContext(), file);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items;
		try
		{
			items = upload.parseRequest(req);
		}
		catch (FileUploadException e)
		{
			throw new RuntimeException(e);
		}
		

		String fileStr = "";
		boolean result = false;
		if(items.size()>0)
		{
			FileItem item = items.get(0);
			fileStr = item.getName();
			String content = item.getString(StringUtil.DEFAULT_ENCODING);

			result = SchedulerGroupServer.getInstance().sendNewBrandFileContent(content);
		}
		
		resp.sendRedirect(req.getContextPath() + "/uploadbrand.jsp?result=" + (result ? "success" : "fail") + "&files=" + fileStr);
	}
	
	 public static DiskFileItemFactory newDiskFileItemFactory(ServletContext context, File repository) {
		 FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(context);
		 DiskFileItemFactory factory = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository);
		 factory.setFileCleaningTracker(fileCleaningTracker);
		 return factory;
	 }
}

