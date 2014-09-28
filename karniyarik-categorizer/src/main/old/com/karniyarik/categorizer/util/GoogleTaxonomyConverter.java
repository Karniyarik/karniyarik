package com.karniyarik.categorizer.util;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.karniyarik.categorizer.vo.Category;

public class GoogleTaxonomyConverter
{
	public GoogleTaxonomyConverter() throws Exception
	{
		List<String> aLines = FileUtils.readLines(new File("taxonomy.txt"));

		Category aRootCategory = new Category();
		aRootCategory.setName("Root");
		int anID = 0;

		for (String aLine : aLines)
		{
			Category aCategory = new Category();

		}
	}

	public static void main(String[] args) throws Exception
	{
		new GoogleTaxonomyConverter();
	}
}
