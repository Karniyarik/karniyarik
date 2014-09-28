package com.karniyarik.crawler.linkgraph;

import java.util.Comparator;

public class VisitedLinksComparator implements Comparator<Link>
{	
	private long mCurrentTime = 0;
	
	public VisitedLinksComparator()
	{
	}
	
	public long getCurrentTime()
	{
		return mCurrentTime;
	}

	
	public void setCurrentTime(long aCurrentTime)
	{
		mCurrentTime = aCurrentTime;
	}

	@Override
	public int compare(Link aLink1, Link aLink2)
	{	
		long anAge1 = mCurrentTime - aLink1.getLastHitTime();
		long anAge2 = mCurrentTime - aLink2.getLastHitTime();
		
		double aNormHit = (aLink1.getHitCount() - aLink2.getHitCount())*1.0 / Math.max(aLink1.getHitCount(), aLink2.getHitCount());
		double aNormAge = (anAge1 - anAge2)*1.0 / Math.max(anAge1, anAge2);   
		
		double aResult = aNormHit * 0.70 + aNormAge * 0.30;
		
		if (Math.abs(aResult) < 0.005) 
			return 0;
		else if(aResult < 0)
			return -1;
		else
			return 1;
	}
	
	public static void main(String[] args)
	{
		VisitedLinksComparator comparator = new VisitedLinksComparator();
		
		comparator.setCurrentTime(11);
		
		Link link1 = new Link("1");
		Link link2 = new Link("2");
		
		link1.setHitCount(10);
		link1.setLastHitTime(1);
		link2.setHitCount(10);
		link2.setLastHitTime(10);
		System.out.println(comparator.compare(link1, link2));
		
		link1.setHitCount(1);
		link1.setLastHitTime(10);
		link2.setHitCount(10);
		link2.setLastHitTime(10);
		System.out.println(comparator.compare(link1, link2));

		link1.setHitCount(10);
		link1.setLastHitTime(10);
		link2.setHitCount(10);
		link2.setLastHitTime(10);
		System.out.println(comparator.compare(link1, link2));
	}
}
