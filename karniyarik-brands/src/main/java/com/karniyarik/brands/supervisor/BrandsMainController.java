package com.karniyarik.brands.supervisor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.karniyarik.brands.BrandHolder;
import com.karniyarik.brands.BrandStore;
import com.karniyarik.brands.supervisor.gui.BrandsMainFrame;

public class BrandsMainController
{
	private BrandsMainFrame	mMainFrame		= null;
	private BrandsKB		mBrandsKB		= null;
	private BrandsReader	mReader			= null;
	private BrandsAnalyzer	mBrandAnalyzer	= null;

	public BrandsMainController()
	{
		mReader = new BrandsReader();
		mBrandsKB = new BrandsKB();
		mBrandAnalyzer = new BrandsAnalyzer();

		mMainFrame = new BrandsMainFrame(this);
		mMainFrame.setLocationByPlatform(true);
		mMainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mMainFrame.setVisible(true);
	}

	public void loadBrandsKB()
	{
		mBrandsKB.load(BrandStore.BRANDS_TXT_FILE);
	}

	public void loadBrandsFile()
	{
		JFileChooser aFileChooser = new JFileChooser(System
				.getProperty("user.dir"));
		if (aFileChooser.showOpenDialog(mMainFrame) == JFileChooser.APPROVE_OPTION)
		{
			mReader.readBrands(aFileChooser.getSelectedFile());
		}
	}

	public List<BrandHolder> getBrandsKB()
	{
		return mBrandsKB.getBrands();
	}

	public List<BrandHolder> getBrands()
	{
		return mReader.getBrands();
	}

	public static void main(String[] args)
	{
		new BrandsMainController();
	}

	public List<BrandHolder> analyzeBrands(List<BrandHolder> aBrandsList)
	{
		return mBrandAnalyzer.analyzeBrands(aBrandsList);
	}

	public List<Object[]> merge(List<BrandHolder> aBrandsList)
	{
		BrandHolder aMaxSimilarityTarget = null;

		List<Object[]> aResult = new ArrayList<Object[]>();

		Object[] aRow = null;

		for (BrandHolder aHolder : aBrandsList)
		{
			aRow = new Object[6];

			aMaxSimilarityTarget = mBrandAnalyzer.getMaximumSimilarityBrand(
					aHolder, mBrandsKB.getBrands());

			aRow[1] = aHolder;

			if (aMaxSimilarityTarget != null)
			{
				aRow[0] = "Matched";
				aRow[2] = aMaxSimilarityTarget;
			}
			else
			{
				aRow[0] = "NOT Matched";
				aRow[2] = null;
			}

			aRow[3] = false;
			aRow[4] = false;
			aRow[5] = false;

			aResult.add(aRow);
		}

		return aResult;
	}

	private void addToParent(BrandHolder aParent, BrandHolder aHolder)
	{
		aParent.addAlternateBrand(aHolder.getActualBrand());

		if (aHolder.getListOfAlternateBrands().size() > 0)
		{
			for (String anAlternate : aHolder.getListOfAlternateBrands())
			{
				aParent.addAlternateBrand(anAlternate);
			}
		}
	}

	public List<Object[]> save(List<Object[]> aData, List<BrandHolder> aBrands)
	{
		BrandHolder aParent = null;

		List<Object[]> aResult = new ArrayList<Object[]>();

		for (Object[] aDataObject : aData)
		{
			if (((Boolean) aDataObject[3]) == true)
			{
				if (aDataObject[2] == null)
				{
					aResult.add(aDataObject);
				}
				else
				{
					aParent = mBrandsKB.getBrand(((BrandHolder) aDataObject[2])
							.getActualBrand());
					addToParent(aParent, (BrandHolder) aDataObject[1]);
					removeFromBrandsList(((BrandHolder) aDataObject[1])
							.getActualBrand(), aBrands);
				}
			}
			else if (((Boolean) aDataObject[4]) == true)
			{
				mBrandsKB.getBrands().add((BrandHolder) aDataObject[1]);
				removeFromBrandsList(((BrandHolder) aDataObject[1])
						.getActualBrand(), aBrands);
			}
			else
			{
				aResult.add(aDataObject);
			}
		}
		mReader.writeBrands(aBrands, "brands_service.log");
		mBrandsKB.writeDB(BrandStore.BRANDS_TXT_FILE);

		return aResult;
	}

	private void removeFromBrandsList(String aBrandName,
			List<BrandHolder> aBrands)
	{
		BrandHolder aHolderToBeRemoved = null;

		for (BrandHolder aHolder : aBrands)
		{
			if (aBrandName.equalsIgnoreCase(aHolder.getActualBrand()))
			{
				aHolderToBeRemoved = aHolder;
				break;
			}
		}

		if (aHolderToBeRemoved != null)
		{
			aBrands.remove(aHolderToBeRemoved);
		}
	}

	public List<Object[]> findKBDuplicates()
	{
		BrandHolder aMaxSimilarityTarget = null;

		List<Object[]> aResult = new ArrayList<Object[]>();

		Object[] aRow = null;

		List<BrandHolder> aDuplicateList = new ArrayList<BrandHolder>();

		for (BrandHolder aHolder : mBrandsKB.getBrands())
		{
			if (!aDuplicateList.contains(aHolder))
			{
				aRow = new Object[6];

				aMaxSimilarityTarget = mBrandAnalyzer
						.getMaximumSimilarityBrand(aHolder, mBrandsKB
								.getBrands());

				aRow[1] = aHolder;

				if (aMaxSimilarityTarget != null)
				{
					aRow[0] = "Duplicate";
					aRow[2] = aMaxSimilarityTarget;
					aRow[3] = false;
					aRow[4] = false;
					aRow[5] = false;

					aDuplicateList.add(aMaxSimilarityTarget);
					aResult.add(aRow);
				}
			}
		}

		return aResult;
	}

	public List<Object[]> saveKBDuplicates(List<Object[]> aData)
	{
		BrandHolder aParent = null;
		BrandHolder aChild = null;

		List<Object[]> aResult = new ArrayList<Object[]>();

		for (Object[] aDataObject : aData)
		{
			if (((Boolean) aDataObject[3]) == true)
			{
				aParent = (BrandHolder) aDataObject[2];
				aChild = (BrandHolder) aDataObject[1];
				mBrandsKB.getBrands().remove(aChild);

				aParent.addAlternateBrand(aChild);
			}
			else
			{
				aResult.add(aDataObject);
			}
		}

		mBrandsKB.writeDB(BrandStore.BRANDS_TXT_FILE);

		return aResult;

	}

	public void saveLog(List<BrandHolder> aBrandsList)
	{
		mReader.writeBrands(aBrandsList, "brands_service.log");
	}
}
