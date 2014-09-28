package com.karniyarik.brands.supervisor.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.karniyarik.brands.BrandHolder;

@SuppressWarnings("serial")
public class BrandTree extends JTree
{
	public BrandTree(DefaultMutableTreeNode aTreeNode)
	{
		super(aTreeNode);
	}
	
	
	public List<BrandHolder> getBrands()
	{
		DefaultMutableTreeNode aRootNode = (DefaultMutableTreeNode)getModel().getRoot();
		
		List<BrandHolder> aBrandsList = new ArrayList<BrandHolder>();
		
		BrandHolder aHolder = null;
		DefaultMutableTreeNode aNode = null;
		DefaultMutableTreeNode aChildNode = null;
		
		for(int anIndex = 0; anIndex < aRootNode.getChildCount(); anIndex++)
		{
			aNode = (DefaultMutableTreeNode)aRootNode.getChildAt(anIndex);
			
			aHolder = new BrandHolder();
			
			aHolder.setActualBrand(aNode.getUserObject().toString());
			
			aBrandsList.add(aHolder);
			
			for(int aChildIndex = 0; aChildIndex < aNode.getChildCount(); aChildIndex++)
			{
				aChildNode = (DefaultMutableTreeNode) aNode.getChildAt(aChildIndex);
				aHolder.addAlternateBrand(aChildNode.getUserObject().toString()); 
			}	
		}

		return aBrandsList;
	}

	
	public DefaultMutableTreeNode setBrands(List<BrandHolder> aList)
	{
		DefaultMutableTreeNode aRootNode = (DefaultMutableTreeNode)getModel().getRoot();
		
		aRootNode.removeAllChildren();
		
		DefaultMutableTreeNode aMainBrandNode = null;
		DefaultMutableTreeNode aChildNode = null;
		
		for(BrandHolder aHolder: aList)
		{
			aMainBrandNode = new DefaultMutableTreeNode(aHolder.getActualBrand());
			
			aRootNode.add(aMainBrandNode);
			
			for(String aChildBrand: aHolder.getListOfAlternateBrands())
			{
				aChildNode = new DefaultMutableTreeNode(aChildBrand);
				aMainBrandNode.add(aChildNode);
			}
		}
		return aRootNode;
	}
	
	public BrandHolder getSelectedBrand()
	{
		if(getSelectionPath() != null)
		{
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) getSelectionPath().getLastPathComponent();
			BrandHolder aHolder = new BrandHolder();
			aHolder.setActualBrand(aNode.getUserObject().toString());
			
			return aHolder;
		}
		
		return null;
	}
}
