package com.karniyarik.statistics.fraud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.common.notifier.ExceptionNotifier;
import com.karniyarik.common.statistics.vo.ProductClickLog;

public class FraudDictionary
{
	private final int						MAX_SIZE	= 1000;
	private final Map<String, FraudItem>	map;

	public FraudDictionary()
	{
		map = new HashMap<String, FraudItem>();
	}

	public boolean isFraudClick(ProductClickLog log)
	{
		boolean fraud = false;

		FraudItem newFraudItem = new FraudItem(log.getIp(), log.getUrl(), log.getDate());
		FraudItem oldFraudItem = map.get(newFraudItem.getUniqueKey());

		// if item is not null
		// same IP clicked same product
		if (oldFraudItem != null)
		{
			// if a new fraud click occurred with the existing click series
			// add the click to the existing fraud item
			// if there is enough time between clicks
			// log the old fraud item if there was a fraud
			// and put the new fraud item to the map
			if (oldFraudItem.addClickDate(log.getDate()))
			{
				if (oldFraudItem.fraudDetected())
				{
					fraud = true;
				}
			}
			else
			{
				// time between last two clicks is enough
				// to break the fraud case
				// so put a new clean item to the map
				map.put(newFraudItem.getUniqueKey(), newFraudItem);

				// Even though fraud case is broken with this
				// new clean click, there might be previous fraud session
				// Log the old item before it is replaced with the new one
				// and got lost
				if (oldFraudItem.fraudDetected())
				{
					logFraudClick(oldFraudItem);
				}
			}
		}
		else
		{
			map.put(newFraudItem.getUniqueKey(), newFraudItem);
		}

		if (map.size() == MAX_SIZE)
		{
			cleanDictionary();
		}

		return fraud;
	}

	private void cleanDictionary()
	{
		long time = System.currentTimeMillis();
		List<String> deleteList = new ArrayList<String>();

		for (FraudItem item : map.values())
		{
			if (!item.tooClose(time))
			{
				deleteList.add(item.getUniqueKey());

				if (item.fraudDetected())
				{
					// log fraud clicks before they are lost
					logFraudClick(item);
				}
			}
		}

		for (String key : deleteList)
		{
			map.remove(key);
		}

		deleteList.clear();
		deleteList = null;
	}

	private void logFraudClick(FraudItem fraudItem)
	{
		// TODO: we can log oldFraudItem instead of mailing
		ExceptionNotifier.sendException(fraudItem.getUniqueKey(), "Fraud Detected", fraudItem.toString());
	}
}
