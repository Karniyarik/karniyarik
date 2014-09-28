package com.karniyarik.brands.supervisor.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.karniyarik.common.util.StreamUtil;

public class KelkooBrandLister
{
	public KelkooBrandLister() throws Exception
	{
		File aFile = StreamUtil.getFile("kelkoo.brands.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(aFile);
		doc.getDocumentElement().normalize();
		NodeList nodeLst = doc.getElementsByTagName("brand");
		
		List<String> aBrandList = new ArrayList<String>();
		
		for(int anIndex = 0; anIndex<nodeLst.getLength(); anIndex++)
		{
			aBrandList.add(nodeLst.item(anIndex).getAttributes().getNamedItem("name").getNodeValue());
		}
		
		FileUtils.writeLines(StreamUtil.getFile("kelkoo.brands.txt"), "UTF-8", aBrandList);
	}

	public static void main(String[] args) throws Exception
	{
		new KelkooBrandLister();
	}
}
