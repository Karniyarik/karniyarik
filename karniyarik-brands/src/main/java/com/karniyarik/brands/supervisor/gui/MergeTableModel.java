package com.karniyarik.brands.supervisor.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import com.karniyarik.brands.BrandHolder;

@SuppressWarnings({"serial", "unchecked"})
public class MergeTableModel extends AbstractTableModel
{
	private String[] mColumns = new String[]{"Result", "New Brand", "Parent", "Accept", "Add as new", "Add under"};
	
	private Class[] mClasses = new Class[]{String.class, BrandHolder.class, BrandHolder.class, Boolean.class, Boolean.class, JButton.class};
	
	private List<Object[]> mData = new ArrayList<Object[]>();
	
	@Override
	public Class<?> getColumnClass(int aColumnIndex)
	{
		return mClasses[aColumnIndex];
	}
	
	@Override
	public int getColumnCount()
	{
		return mColumns.length;
	}
	
	@Override
	public String getColumnName(int aColumnIndex)
	{
		return mColumns[aColumnIndex];
	}
	
	@Override
	public int getRowCount()
	{		
		return mData.size();
	}
	
	@Override
	public Object getValueAt(int aRowIndex, int aColumnIndex)
	{		
		return mData.get(aRowIndex)[aColumnIndex];
	}
	
	@Override
	public boolean isCellEditable(int aRowIndex, int aColumnIndex)
	{
		if(aColumnIndex != 0 && aColumnIndex != 2)
			return true;
		
		return false;
	}
	
	@Override
	public void setValueAt(Object aValue, int aRowIndex, int aColumnIndex)
	{		
		if(aColumnIndex == 1)
		{
			((BrandHolder)mData.get(aRowIndex)[aColumnIndex]).setActualBrand((String)aValue);
		}
		else
		{
			mData.get(aRowIndex)[aColumnIndex] = aValue;	
		}		
	}
	
	public void setData(List<Object[]> aData)
	{
		mData = aData;
	}
	
	public void removeData(int anIndex)
	{
		mData.remove(anIndex);
	}
	
	public List<Object[]> getData()
	{
		return mData;
	}
}
