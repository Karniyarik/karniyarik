package com.karniyarik.web.category;

import java.util.List;
import java.util.Map;

import com.karniyarik.ir.filter.KarniyarikFilterr;

public interface ICategoryHandler
{
	public List<KarniyarikFilterr> getFilters(Map requestParameters);
}
