package com.karniyarik.categorizer.supervisor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.TreeNode;
import javax.xml.bind.JAXBElement;

import com.karniyarik.categorizer.xml.category.CategoryType;

public class CategoryTreeNode implements TreeNode
{
	private CategoryType categoryType = null;
	private TreeNode parent = null;
	private List<CategoryTreeNode> children = new ArrayList<CategoryTreeNode>();
	
	public CategoryTreeNode(CategoryType categoryType)
	{
		this.categoryType = categoryType;
		
		for(Serializable content: categoryType.getContent())
		{
			if(content instanceof JAXBElement<?>)
			{
				CategoryType child = (CategoryType)((JAXBElement) content).getValue();
				CategoryTreeNode childNode = new CategoryTreeNode(child);
				childNode.setParent(this);
				children.add(childNode);
			}
		}
	}
	
	@Override
	public Enumeration children()
	{
		return new Vector<CategoryTreeNode>(children).elements();
	}
	
	@Override
	public boolean getAllowsChildren()
	{
		return false;
	}
	
	@Override
	public TreeNode getChildAt(int childIndex)
	{
		return children.get(childIndex);
	}
	
	@Override
	public int getChildCount()
	{
		return children.size();
	}
	
	@Override
	public int getIndex(TreeNode node)
	{
		return children.indexOf(node);
	}
	
	@Override
	public TreeNode getParent()
	{
		return parent;
	}
	
	@Override
	public boolean isLeaf()
	{
		return children.size() == 0;
	}
	
	public void setParent(TreeNode node)
	{
		this.parent = node;
	}
	
	@Override
	public String toString()
	{
		return categoryType.getName();
	}

	public CategoryType getCategoryType()
	{
		return categoryType;
	}

	public String getId()
	{
		return categoryType.getId();
	}
}
