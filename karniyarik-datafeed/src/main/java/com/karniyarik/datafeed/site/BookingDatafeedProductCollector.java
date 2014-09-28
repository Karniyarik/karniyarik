package com.karniyarik.datafeed.site;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.parser.pojo.Product;
import com.karniyarik.parser.util.PriceUtil;


public class BookingDatafeedProductCollector
{

	private final Integer	NAME_INDEX				= 1;
	private final Integer	PRICE_INDEX				= 9;
	private final Integer	CURRENCY_INDEX			= 8;
	private final Integer	URL_INDEX				= 16;
	private final Integer	IMAGE_URL_INDEX			= 17;
	private final Integer	CITY_INDEX				= 29;
	private final Integer	LONGTITUDE_INDEX		= 14;
	private final Integer	LATITUDE_INDEX			= 15;
	private final Integer	ZIP_INDEX				= 3;

	private String			fileName				= "booking.tsv";
	private String[]		datafeedList			= { "http://feeds.booking.com/partner/bfklAvPBx4nkxfbGCN81NQ.tsv", "http://feeds.booking.com/partner/o49KBIj1dnpbNxJvpjynbQ.tsv",
			"http://feeds.booking.com/partner/uEvnGUwpFz8tdyEDjx5pA.tsv" };

	public List<Product> collectProducts()
	{
		List<Product> productList = new ArrayList<Product>();
		Date fetchDate = new Date();
		String content = getFileContents();
		String[] lines = content.split("\n");
		Product product;
		String[] tokens;
		String line;
		List<ProductProperty> properties;
		for (String feedUrl : datafeedList)
		{
			
		}
		for (int i = 1; i < lines.length; i++)
		{
			line = lines[i];
			tokens = line.split("\t");
			product = new Product();

			product.setName(tokens[NAME_INDEX]);
			product.setUrl(tokens[URL_INDEX]);
			PriceUtil.setPrice(tokens[PRICE_INDEX], tokens[CURRENCY_INDEX], product);

			if (StringUtils.isNotBlank(product.getName()) && StringUtils.isNotBlank(product.getUrl()) && product.getPrice() > PriceUtil.DEFAULT_PRICE)
			{
				product.setImageUrl(tokens[IMAGE_URL_INDEX]);
				product.setFetchDate(fetchDate);
				properties = new ArrayList<ProductProperty>();
				properties.add(new ProductProperty("city", tokens[CITY_INDEX]));
				properties.add(new ProductProperty("longtitude", tokens[LONGTITUDE_INDEX]));
				properties.add(new ProductProperty("latitude", tokens[LATITUDE_INDEX]));
				properties.add(new ProductProperty("zip", tokens[ZIP_INDEX]));
				product.setProperties(properties);
				productList.add(product);
			}
		}

		return productList;
	}

	@SuppressWarnings("unchecked")
	private String getFileContents()
	{
		File file = new File(fileName);
		String content = "";
		try
		{
			content = FileUtils.readFileToString(file, "UTF-8");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return content;
	}

	private List<String> getFeedContents()
	{
		List<String> lines = new ArrayList<String>();

		return lines;
	}

	public static void main(String[] args)
	{
		BookingDatafeedProductCollector col = new BookingDatafeedProductCollector();
		col.collectProducts();
	}

}
