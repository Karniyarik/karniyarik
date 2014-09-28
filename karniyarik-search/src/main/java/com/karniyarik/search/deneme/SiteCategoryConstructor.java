package com.karniyarik.search.deneme;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.io.FileUtils;

import com.karniyarik.categorizer.xml.category.CategoryType;
import com.karniyarik.categorizer.xml.category.ObjectFactory;
import com.karniyarik.categorizer.xml.category.RootType;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.vo.Product;
import com.karniyarik.indexer.dao.IndexerDAO;
import com.karniyarik.indexer.dao.IndexerDAOFactory;

public class SiteCategoryConstructor
{
	public SiteCategoryConstructor()
	{
	}

	public void construct(String productFilePath, String sitename, String delimiter)
	{
		SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(sitename);
		IndexerDAO dao = new IndexerDAOFactory().create(siteConfig, productFilePath);
		
		CategoryType categoryRoot = new CategoryType();
		categoryRoot.setId("1");

		RootType rootType = new RootType();
		rootType.setCategory(categoryRoot);
		ObjectFactory objectFactory = new ObjectFactory();
		System.out.println(dao.getProductCount());
		System.exit(0);
		for (int index=0; index < dao.getProductCount(); index++)
		{
			Product product = dao.getNextProduct();
			
			if(product.getBreadcrumb() == null)
			{
				continue;
			}
			 
			String[] path = null;
			if(delimiter != null)
			{
				path = product.getBreadcrumb().split(delimiter);	
			}
			else
			{
				path =new String[]{product.getBreadcrumb()};
			}
			
			CategoryType parentCategory = categoryRoot;
			CategoryType childCategory = null;
			StringBuffer currentPath = new StringBuffer();

			for (String pathElement : path)
			{
				pathElement = pathElement.trim();
				currentPath.append(pathElement);
				currentPath.append(" ## ");
				childCategory = getChildCategory(parentCategory, pathElement);
				if (childCategory == null)
				{
					childCategory = new CategoryType();
					childCategory.setName(pathElement);
					childCategory.setId(parentCategory.getId() + "." + (parentCategory.getContent().size() + 1));
					childCategory.setRealPath(currentPath.toString().trim());
					parentCategory.getContent().add(objectFactory.createCategoryTypeCategory(childCategory));
					childCategory.setInstanceCount(1);
					childCategory.setTotalPrice(product.getPrice());
				}
				else
				{
					childCategory.setInstanceCount(childCategory.getInstanceCount()+1);
					childCategory.setTotalPrice(childCategory.getTotalPrice() +  product.getPrice());
				}

				parentCategory = childCategory;
			}
		}

		write(rootType, sitename, productFilePath);
	}
	

	private void write(RootType rootType, String sitename, String filePath) {
		StringBuffer result = new StringBuffer();
		append(rootType.getCategory(), result,"");
		File productFile = new File(filePath);
		String parentPath = productFile.getParent();
		
		File file = new File(parentPath + "/"+sitename+".tsv");
		
		try {
			FileUtils.writeStringToFile(file, result.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void append(CategoryType category, StringBuffer result, String delimiter) {	
		result.append(delimiter);
		result.append(category.getName());
		result.append("\t");
		result.append(category.getInstanceCount());
		result.append("\t");
		if(category.getInstanceCount() != 0)
		{
			result.append(category.getTotalPrice()/category.getInstanceCount());	
		}
		else
		{
			result.append("0");
		}		
		result.append("\n");
		List<Serializable> content = category.getContent();
		for(Serializable item: content)
		{
			JAXBElement<CategoryType> elem = (JAXBElement<CategoryType> )item;
			CategoryType cat = elem.getValue();
			append(cat, result, delimiter+"\t");
		}
	}

	private CategoryType getChildCategory(CategoryType parent, String name)
	{
		for (Serializable content : parent.getContent())
		{
			JAXBElement<CategoryType> childCatType = (JAXBElement<CategoryType>) content;

			if (childCatType.getValue().getName().equals(name))
			{
				return childCatType.getValue();
			}
		}

		return null;
	}

	public static void main(String[] args)
	{
		String path = "C:/work/karniyarik/files/products1/hepsiburada.txt";
		String sitename = "hepsiburada";
		
		if(args.length > 1)
		{
			path = args[0];
			sitename = args[1];
		}
		else
		{
			System.out.println("Usage: java com.karniyarik.search.deneme.SiteCategoryConstructor <filepath> <sitename>");
		}
		 
		new SiteCategoryConstructor().construct(path, sitename, null);
	}
}
