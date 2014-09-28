package com.karniyarik.categorizer;

import java.util.List;

public interface IClassifier
{
	public List<CategoryResult> resolve(String name, String brand, String breadcrumb);
}
