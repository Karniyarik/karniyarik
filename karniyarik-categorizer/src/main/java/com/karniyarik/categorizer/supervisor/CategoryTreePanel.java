package com.karniyarik.categorizer.supervisor;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Trailing;

import com.karniyarik.categorizer.xml.category.CategoryType;
import com.karniyarik.categorizer.xml.category.RootType;

//VS4E -- DO NOT REMOVE THIS LINE!
public class CategoryTreePanel extends JPanel
{

	private static final long	serialVersionUID	= 1L;
	private JTextField txtSearch;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane0;
	private JTextArea txtCategoryKeywords;
	private JTree treeCategory;
	
	private RootType root = null;

	public CategoryTreePanel()
	{
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJTextField0(), new Constraints(new Bilateral(12, 12, 4), new Trailing(12, 251, 329)));
		add(getJScrollPane1(), new Constraints(new Bilateral(12, 12, 22), new Trailing(38, 100, 46, 223)));
		add(getJScrollPane0(), new Constraints(new Bilateral(12, 12, 22), new Bilateral(12, 144, 22)));
		setSize(320, 472);
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setBorder(BorderFactory.createTitledBorder(null, "Category Tree", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Dialog",
					Font.BOLD, 12), new Color(51, 51, 51)));
			jScrollPane0.setViewportView(getTreeCategory());
		}
		return jScrollPane0;
	}

	private JTree getTreeCategory() {
		if (treeCategory == null) {
			treeCategory = new JTree();
			DefaultTreeModel treeModel = null;
			{
				DefaultMutableTreeNode node0 = new DefaultMutableTreeNode("Not Loaded");
				treeModel = new DefaultTreeModel(node0);
			}
			treeCategory.setModel(treeModel);
		}
		return treeCategory;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBorder(BorderFactory.createTitledBorder(null, "Keywords", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Dialog",
					Font.BOLD, 12), new Color(51, 51, 51)));
			jScrollPane1.setViewportView(getJTextArea0());
		}
		return jScrollPane1;
	}

	private JTextArea getJTextArea0() {
		if (txtCategoryKeywords == null) {
			txtCategoryKeywords = new JTextArea();
			
		}
		return txtCategoryKeywords;
	}

	private JTextField getJTextField0() {
		if (txtSearch == null) {
			txtSearch = new JTextField();
			
			txtSearch.setBorder(new LineBorder(Color.black, 1, false));
		}
		return txtSearch;
	}
	
	public void loadCategory(RootType root)
	{
		this.root = root;
		loadCategory(root.getCategory());
	}

	private void loadCategory(CategoryType category)
	{
		CategoryTreeNode node = new CategoryTreeNode(category);
		CategoryTreeModel model = new CategoryTreeModel(node);
		getTreeCategory().setModel(model);
		repaint();
	}
	
	public CategoryTreeNode getSelectedNode(){
		if(getTreeCategory().getSelectionPath() != null)
		{
			return (CategoryTreeNode) getTreeCategory().getSelectionPath().getLastPathComponent();
		}
		return null;
	}
}
