package com.karniyarik.web.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.web.json.LinkedLabel;
import com.karniyarik.web.json.ProductResult;
import com.karniyarik.web.json.SearchResult;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "results", propOrder = { "totalHits", "page", "pageSize", "query", "didyoumean", "error", "link", "products", "filters" })
public class ResultVO
{
	@XmlElement(name = "product")
	@XmlElementWrapper(name = "products")
	private List<ProductVO>		products	= new ArrayList<ProductVO>();

	@XmlElement(name = "filter")
	@XmlElementWrapper(name = "filters")
	private List<FilterGroupVO>	filters		= new ArrayList<FilterGroupVO>();

	@XmlElement(name = "totalhits")
	private int					totalHits	= 0;

	@XmlElement(name = "page")
	private int					page		= 0;

	@XmlElement(name = "pagesize")
	private int					pageSize	= 0;

	@XmlElement(name = "query")
	private String				query		= null;

	@XmlElement(name = "link")
	private String				link		= null;

	@XmlElement(name = "error")
	private String				error		= null;
	
	@XmlElement(name = "didyoumean")
	private List<String>		didyoumean = new ArrayList<String>();

	public ResultVO()
	{
	}

	public ResultVO(ProductResult product)
	{
		getProducts().add(new ProductVO(product));
	}

	public ResultVO(SearchResult searchResult, String link)
	{
		setQuery(searchResult.getQuery());
		setPage(searchResult.getPageNumber());
		setPageSize(searchResult.getPageSize());
		setTotalHits((int)searchResult.getTotalHits());
		setLink(link);
		
		for(LinkedLabel query: searchResult.getSuggestedWords())
		{
			getDidyoumean().add(query.getLabel());
		}
			
		for (ProductResult product : searchResult.getResults())
		{
			getProducts().add(new ProductVO(product));
		}

		Map<String, List<LinkedLabel>> categoryPropMap = searchResult.getCategoryPropMap();
		for (String key : categoryPropMap.keySet())
		{   //this are the numbered properties, they do not have any values attached
			if (key.equalsIgnoreCase("enginevolume") || key.equalsIgnoreCase("modelyear") || key.equalsIgnoreCase("enginepower") || key.equalsIgnoreCase("km"))
			{
				continue;
			}
			else
			{
				filters.add(new FilterGroupVO(key, categoryPropMap.get(key)));
			}
		}
	}

	public List<ProductVO> getProducts()
	{
		return products;
	}

	public void setProducts(List<ProductVO> products)
	{
		this.products = products;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}

	public int getTotalHits()
	{
		return totalHits;
	}

	public void setTotalHits(int totalHits)
	{
		this.totalHits = totalHits;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public List<FilterGroupVO> getFilters()
	{
		return filters;
	}

	public void setFilters(List<FilterGroupVO> filters)
	{
		this.filters = filters;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public void setLink(String link)
	{
		this.link = link;
	}
	
	public List<String> getDidyoumean() {
		return didyoumean;
	}
	
	public void setDidyoumean(List<String> didyoumean) {
		this.didyoumean = didyoumean;
	}
}