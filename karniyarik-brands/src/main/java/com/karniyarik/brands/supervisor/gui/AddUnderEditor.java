package com.karniyarik.brands.supervisor.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.karniyarik.brands.BrandHolder;

@SuppressWarnings("serial")
public class AddUnderEditor extends AbstractCellEditor implements TableCellEditor, ActionListener
{
	JButton mButton = null;
	BrandHolder mHolder = null;
	int mRow = 0;
	BrandTree mKBTree = null;
	JTable mTable = null;
	
	public AddUnderEditor(BrandTree aKBTree)
	{
		mKBTree = aKBTree;
		mButton = new JButton("Add");
		mButton.addActionListener(this);
		mButton.setBorderPainted(false);
	}
	
	@Override
	public Object getCellEditorValue()
	{
		if(mHolder != null)
		{
			return mHolder;
		}
		
		return null;
	}
	
	
	@Override
	public Component getTableCellEditorComponent(JTable aTable, Object aValue,
			boolean aIsSelected, int aRow, int aColumn)
	{
		mTable = aTable;
		mRow = aRow;
		return mButton;
	}
	
	@Override
	public void actionPerformed(ActionEvent aE)
	{
		mHolder = mKBTree.getSelectedBrand();
		mKBTree.setSelectionPath(null);
		((MergeTableModel)mTable.getModel()).setValueAt(mHolder, mRow, 2);
	}
	
}
