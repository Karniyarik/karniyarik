package com.karniyarik.controller.scheduler;

import java.util.Comparator;

import com.karniyarik.controller.SiteControllerState;

//NOT USED FOR NOW
public class SiteScheduleInfoComparator implements Comparator<SiteScheduleInfo>
{
	private boolean	mTestState	= true;

	public SiteScheduleInfoComparator(boolean testState)
	{
		mTestState = testState;
	}

	@Override
	public int compare(SiteScheduleInfo aO1, SiteScheduleInfo aO2)
	{
		int aState1 = getStateValue(aO1);
		int aState2 = getStateValue(aO2);

		if (!mTestState || aState1 == aState2)
		{
			return Long.valueOf(aO1.getNextExecutionDate().getTime())
					.compareTo(
							Long.valueOf(aO2.getNextExecutionDate().getTime()));
		}
		else
		{
			return Integer.valueOf(aState1).compareTo(Integer.valueOf(aState2));
		}
	}

	private int getStateValue(SiteScheduleInfo anInfo)
	{
		int aState = 0;

		if (anInfo.getState() == null
				|| anInfo.getState() == SiteControllerState.IDLE
				|| anInfo.getState() == SiteControllerState.FAILED)
		{
			aState = SiteControllerState.ENDED.ordinal();
		}
		else
		{
			aState = anInfo.getState().ordinal();
		}

		return aState;
	}
}
