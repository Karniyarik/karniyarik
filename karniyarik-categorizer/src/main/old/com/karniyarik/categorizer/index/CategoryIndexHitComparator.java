package com.karniyarik.categorizer.index;

import java.util.Comparator;

public class CategoryIndexHitComparator implements Comparator<CategoryIndexHit>
{
	@Override
	public int compare(CategoryIndexHit aCategoryHit1, CategoryIndexHit aCategoryHit2)
	{
		return Float.valueOf(aCategoryHit2.getScore()).compareTo(aCategoryHit1.getScore());
	}
}
