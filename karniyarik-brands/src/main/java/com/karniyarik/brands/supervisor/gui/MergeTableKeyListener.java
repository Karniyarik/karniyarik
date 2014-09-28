package com.karniyarik.brands.supervisor.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTable;

public class MergeTableKeyListener implements KeyListener
{
	@Override
	public void keyPressed(KeyEvent aE)
	{
		JTable aTable = (JTable) aE.getComponent();
		
		if(aE.getKeyCode() == KeyEvent.VK_DELETE)
		{
			int aRow = aTable.getSelectedRow();
			
			if(aRow >= 0)
			{
				((MergeTableModel)aTable.getModel()).removeData(aRow);
				((MergeTableModel)aTable.getModel()).fireTableDataChanged();
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent aE)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent aE)
	{
		// TODO Auto-generated method stub
		
	}
	
}
