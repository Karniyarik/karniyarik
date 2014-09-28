package com.karniyarik.categorizer.index;

import java.util.Comparator;

public class IndexTermComparator implements Comparator<IndexTerm>
{
	@Override
	public int compare(IndexTerm aIndexTerm1, IndexTerm aIndexTerm2)
	{
		return Integer.valueOf(aIndexTerm2.getTotalFreq()).compareTo(aIndexTerm1.getTotalFreq());
	}
}
