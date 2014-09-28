package com.karniyarik.indexer.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.LineIterator;

import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.vo.Product;
import com.karniyarik.common.vo.ProductProperty;

public class SiteIndexerFileDAO implements IndexerDAO
{

	private final String		siteName;
	private final String		siteUrl;
	private final LineIterator	iterator;
	private final int			count;

	public SiteIndexerFileDAO(String siteName, String siteUrl, LineIterator iterator, int count)
	{
		this.siteName = siteName;
		this.siteUrl = siteUrl;
		this.iterator = iterator;
		this.count = count;
	}

	@Override
	public Product getNextProduct()
	{
		return convertProduct(JSONUtil.parseJSON(iterator.nextLine(), com.karniyarik.parser.pojo.Product.class));
	}

	private Product convertProduct(com.karniyarik.parser.pojo.Product parserProduct)
	{
		Product product = new Product();
		String breadcrumb = null;
		String priceCurrency = null;
		String brand = null;
		String imageUrl = null;

		product.setSourceName(siteName);
		product.setSourceURL(siteUrl);

		product.setLink(parserProduct.getUrl());
		product.setName(parserProduct.getName());

		breadcrumb = parserProduct.getBreadcrumb();
		breadcrumb = breadcrumb == null ? "" : breadcrumb;
		product.setBreadcrumb(breadcrumb);

		product.setPrice(new Double(parserProduct.getPrice()));
		product.setPriceAlternate(new Double(parserProduct.getPriceAlternate()));

		priceCurrency = parserProduct.getPriceCurrency();
		priceCurrency = priceCurrency == null ? "" : priceCurrency;
		product.setPriceCurrency(priceCurrency);

		brand = parserProduct.getBrand();
		brand = brand == null ? "" : brand;
		product.setBrand(brand);

		imageUrl = parserProduct.getImageUrl();
		imageUrl = imageUrl == null ? "" : imageUrl;
		product.setImageURL(imageUrl);

		//product.setTags(parserProduct.getTags());
		
		// RANK VALUE IS SET TO HIT VARIABLE
		// SINCE THERE IS NOTHING CALLED RANK
		// INSIDE A PRODUCT
		product.setHit(parserProduct.getRank());

		List<ProductProperty> properties = new ArrayList<ProductProperty>();

		if (parserProduct.getProperties() != null)
		{
			try
			{
				for (ProductProperty property : parserProduct.getProperties())
				{
					properties.add(((ProductProperty) property.clone()));
				}
			}
			catch (Throwable e)
			{
				// clone not supported exceptıon wıll not happen
			}
		}

		product.setProperties(properties);

		product.setLastFetchDate(parserProduct.getFetchDate());

		return product;
	}

	@Override
	public int getProductCount()
	{
		return count;
	}

	@Override
	public boolean hasNext()
	{
		return iterator.hasNext();
	}

	@Override
	public void close()
	{
		LineIterator.closeQuietly(iterator);
	}

}
