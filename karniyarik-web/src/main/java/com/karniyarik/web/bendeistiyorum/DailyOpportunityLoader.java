package com.karniyarik.web.bendeistiyorum;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.karniyarik.common.vo.Product;
import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.parser.util.BrandUtil;
import com.karniyarik.parser.util.PriceUtil;
import com.karniyarik.web.json.ProductResult;

public class DailyOpportunityLoader
{
	private List<String> urlList = new ArrayList<String>();
	
	private static DailyOpportunityLoader	instance	= null;
	private ReentrantReadWriteLock		readWriteLock;
	private List<Product>				products;
	private final Logger				logger;

	private DailyOpportunityLoader()
	{
		products = new ArrayList<Product>();
		readWriteLock = new ReentrantReadWriteLock();
		logger = Logger.getLogger(this.getClass());
		
		urlList.add("http://www.indirdik.com/karniyarikXML.php");
		
		//refreshData();
	}

	public synchronized static DailyOpportunityLoader getInstance()
	{
		if (instance == null)
			instance = new DailyOpportunityLoader();
		return instance;
	}

	@SuppressWarnings("unchecked")
	public void refreshData()
	{
		for(String url: urlList)
		{
			try
			{
				SAXReader reader = new SAXReader();
				List<Product> tmp = new ArrayList<Product>();
				Document document = reader.read(new URL(url));
				List<Element> elements = document.getRootElement().elements();
				Product p;
				for (int i = 0, size = elements.size(); i < size; i++)
				{
					p = parseElement(elements.get(i));
					if (p != null)
					{
						tmp.add(p);
					}
				}
				
				readWriteLock.writeLock().lock();
				products = tmp;
				readWriteLock.writeLock().unlock();
			}
			catch (Throwable e)
			{
				logger.error("Can not refresh bende istiyorum products.", e);
			}
		}
	}

	public Product getDailyProduct()
	{
		Product product = null;
		try
		{
			readWriteLock.readLock().lock();
			if (products.size() > 0)
			{
				product = products.get(0);
			}
		}
		finally
		{
			readWriteLock.readLock().unlock();
		}
		return product;
	}

	public List<Product> getWeeklyProducts()
	{
		List<Product> weeklyProds = new ArrayList<Product>();
		try
		{
			readWriteLock.readLock().lock();
			for (int i = 1, size = products.size(); i < size; i++)
			{
				weeklyProds.add(products.get(i));
			}
		}
		finally
		{
			readWriteLock.readLock().unlock();
		}
		return weeklyProds;
	}

	public List<Product> getProducts()
	{
		return products;
	}
	
	public List<ProductResult> getRandomProducts(int size)
	{
		List<ProductResult> result = new ArrayList<ProductResult>();
		
		List<Product> selectedProducts = new ArrayList<Product>();
		
		if(size >= products.size())
		{
			selectedProducts.addAll(products);
		}
		else
		{
			Set<Integer> randomIntSet = new HashSet<Integer>();
			int maxTry = size * 4;
			int currentTryCount = 0;
			
			Random random2 = new Random();
			while(randomIntSet.size() < size && currentTryCount < maxTry)
			{
				int random = random2.nextInt(size);
				if(!randomIntSet.contains(random))
				{
					randomIntSet.add(random);
				}
				
				currentTryCount++;
			}
			
			if(randomIntSet.size()>0)
			{
				for(int index: randomIntSet)
				{
					selectedProducts.add(products.get(index));
				}
			}
		}
		
		for(Product product: selectedProducts)
		{
			ProductResult prodResult = new ProductResult(product, null, null);
			result.add(prodResult);
		}
		
		return result;
	}
	
	private Product parseElement(Element element)
	{
		Product product = new Product();
		product.setSourceName("indirdik");
		product.setSourceURL("http://www.indirdik.com");
		// String url = getValue(element, "urun_url");
		// product.setLink(url);

		product.setLink("http://www.indirdik.com/?merc=5565");

		product.setName(getValue(element, "isim"));
		product.setHighlightedName(product.getName());

		String priceText = getValue(element, "fiyat");
		String currencyText = getValue(element, "birim");
		com.karniyarik.parser.pojo.Product tempProduct = new com.karniyarik.parser.pojo.Product();
		
		PriceUtil.setPrice(priceText, currencyText, tempProduct);
		
		product.setPrice(new Double(tempProduct.getPrice()));
		product.setPriceAlternate(new Double(tempProduct.getPriceAlternate()));
		product.setPriceCurrency(tempProduct.getPriceCurrency());

		product.setPriceAlternate(new Double(-1));

		// product.setPriceAlternate(new
		// Double(PriceUtil.parsePrice(ecommerceConfig.getFeedPriceAlternateElementName())));
		product.setBreadcrumb(getValue(element, "kategori"));
		product.setImageURL(getValue(element, "resim"));

		String brandText = getValue(element, "marka");
		product.setBrand(BrandUtil.getBrand("", brandText, product.getName(), product.getBreadcrumb(), null));

		product.setProperties(new ArrayList<ProductProperty>());

		return product;
	}

	private String getValue(Element element, String propertyXPath)
	{
		String value = "";
		if (StringUtils.isNotBlank(propertyXPath) && element != null)
		{
			value = element.valueOf(propertyXPath).trim();
		}
		return value;
	}
}