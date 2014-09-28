package com.karniyarik.brands.supervisor.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class BrandsTreeKeyListener implements KeyListener
{

	@Override
	public void keyPressed(KeyEvent aE)
	{
		JTree aTree = (JTree) aE.getComponent();
		
		if(aE.getKeyCode() == KeyEvent.VK_DELETE)
		{
			if(aTree.getSelectionPaths() != null)
			{
				for(TreePath aPath: aTree.getSelectionPaths())
				{
					DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) aPath.getLastPathComponent();
					
					if(!aNode.isRoot())
					{
						aNode.removeFromParent();
					}					
				}
				
				((DefaultTreeModel)aTree.getModel()).reload();
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
	}

}
