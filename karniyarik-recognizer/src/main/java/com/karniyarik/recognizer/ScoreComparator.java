package com.karniyarik.recognizer;

import java.util.Comparator;

public class ScoreComparator implements Comparator<ScoreHit>
{
	@Override
	public int compare(ScoreHit o1, ScoreHit o2)
	{
		return Double.valueOf(o2.getScore()).compareTo(o1.getScore());
	}
}
