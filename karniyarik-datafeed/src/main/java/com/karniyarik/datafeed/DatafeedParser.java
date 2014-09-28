package com.karniyarik.datafeed;

import java.util.List;

import com.karniyarik.parser.pojo.Product;

public interface DatafeedParser
{
	List<Product> parse(String content);
}
