package com.karniyarik.categorizer.supervisor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import com.karniyarik.categorizer.io.CategoryIO;
import com.karniyarik.categorizer.io.MappingIO;
import com.karniyarik.categorizer.xml.category.RootType;
import com.karniyarik.categorizer.xml.mapping.MappingType;

//VS4E -- DO NOT REMOVE THIS LINE!
public class InitialCategoryMappingIFrame extends JFrame
{

	private static final long	serialVersionUID	= 1L;
	private JButton btnSave;
	private JPanel jPanel1;
	private CategoryTreePanel categoryTreePanel0;
	private JPanel jPanel0;
	private CategoryTreePanel categoryTreePanel1;
	private JPanel jPanel2;
	
	private JMenu jMenu0;
	private JMenuBar jMenuBar0;
	private JList jList0;
	private JScrollPane jScrollPane0;
	private JButton btnMap;
	private JButton jButton3;
	private JButton jButton2;
	
	private com.karniyarik.categorizer.xml.mapping.RootType mappingRootType = null;
	private String sitename = null;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem0;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public InitialCategoryMappingIFrame()
	{
		mappingRootType = new com.karniyarik.categorizer.xml.mapping.RootType();
		initComponents();
	}

	private void initComponents() {
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJPanel2(), new Constraints(new Bilateral(12, 12, 0), new Bilateral(12, 12, 357)));
		setJMenuBar(getJMenuBar0());
		setSize(934, 474);
	}

	private JMenuItem getJMenuItem0() {
		if (jMenuItem0 == null) {
			jMenuItem0 = new JMenuItem();
			jMenuItem0.setText("Load Mapping");
			jMenuItem0.setOpaque(false);
			jMenuItem0.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jMenuItem0ActionActionPerformed(event);
				}
			});
		}
		return jMenuItem0;
	}

	private JMenuItem getJMenuItem2() {
		if (jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem();
			jMenuItem2.setText("Create Mapping");
			jMenuItem2.setOpaque(false);
			jMenuItem2.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jMenuItem2ActionActionPerformed(event);
				}
			});
		}
		return jMenuItem2;
	}


	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Map");
			jButton2.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					jButton2ActionActionPerformed(event);
				}
			});
		}
		return jButton2;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("Close");
		}
		return jButton3;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJList0());
		}
		return jScrollPane0;
	}

	private JList getJList0() {
		if (jList0 == null) {
			jList0 = new JList();
			DefaultListModel listModel = new DefaultListModel();
			jList0.setModel(listModel);
		}
		return jList0;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJButton0(), new Constraints(new Trailing(12, 12, 12), new Leading(7, 10, 10)));
			jPanel1.add(getJButton3(), new Constraints(new Trailing(80, 12, 12), new Leading(7, 12, 12)));
		}
		return jPanel1;
	}

	private JButton getJButton1() {
		if (btnMap == null) {
			btnMap = new JButton();
			btnMap.setText("Delete");
			btnMap.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					btnMapActionActionPerformed(event);
				}
			});
		}
		return btnMap;
	}

	private JButton getJButton0() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("Save");
			btnSave.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent event) {
					btnSaveActionActionPerformed(event);
				}
			});
		}
		return btnSave;
	}

	private JMenuBar getJMenuBar0() {
		if (jMenuBar0 == null) {
			jMenuBar0 = new JMenuBar();
			jMenuBar0.add(getJMenu0());
		}
		return jMenuBar0;
	}

	private JMenu getJMenu0() {
		if (jMenu0 == null) {
			jMenu0 = new JMenu();
			jMenu0.setText("Load");
			jMenu0.setOpaque(false);			
			jMenu0.add(getJMenuItem2());
			jMenu0.add(getJMenuItem0());
		}
		return jMenu0;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJPanel1(), new Constraints(new Bilateral(12, 12, 0), new Trailing(12, 10, 10)));
			jPanel2.add(getCategoryTreePanel0(), new Constraints(new Leading(12, 286, 10, 10), new Bilateral(12, 64, 281)));
			jPanel2.add(getCategoryTreePanel1(), new Constraints(new Trailing(12, 313, 10, 10), new Bilateral(12, 64, 279)));
			jPanel2.add(getJPanel0(), new Constraints(new Bilateral(304, 331, 273), new Bilateral(12, 64, 109)));
		}
		return jPanel2;
	}

	private CategoryTreePanel getCategoryTreePanel1() {
		if (categoryTreePanel1 == null) {
			categoryTreePanel1 = new CategoryTreePanel();
		}
		return categoryTreePanel1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createTitledBorder(null, "Mapping", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD,
					12), new Color(51, 51, 51)));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJButton2(), new Constraints(new Trailing(12, 145, 145), new Trailing(0, 47, 252)));
			jPanel0.add(getJButton1(), new Constraints(new Trailing(71, 12, 12), new Trailing(0, 47, 252)));
			jPanel0.add(getJScrollPane0(), new Constraints(new Bilateral(12, 12, 23), new Bilateral(12, 29, 23)));
		}
		return jPanel0;
	}

	private CategoryTreePanel getCategoryTreePanel0() {
		if (categoryTreePanel0 == null) {
			categoryTreePanel0 = new CategoryTreePanel();
		}
		return categoryTreePanel0;
	}

	

	public static void main(String[] args)
	{
		InitialCategoryMappingIFrame frame = new InitialCategoryMappingIFrame();
		frame.setVisible(true);
	}

	private void jButton2ActionActionPerformed(ActionEvent event) {
		CategoryTreeNode node1 = getCategoryTreePanel0().getSelectedNode();
		CategoryTreeNode node2 = getCategoryTreePanel1().getSelectedNode();
		
		if(node1 != null && node2 != null)
		{
			MappingType type = new MappingType();
			type.setFromCat(node2.getCategoryType());
			type.setToCat(node1.getCategoryType());
			((DefaultListModel)getJList0().getModel()).addElement(type);
			getJList0().repaint();			
			mappingRootType.getMapping().add(type);
		}
	}

	private void btnMapActionActionPerformed(ActionEvent event) {
		MappingType type = (MappingType) ((DefaultListModel)getJList0().getModel()).get(getJList0().getSelectedIndex());
		mappingRootType.getMapping().remove(type);
		((DefaultListModel)getJList0().getModel()).removeElementAt(getJList0().getSelectedIndex());
		getJList0().repaint();
	}

	private void btnSaveActionActionPerformed(ActionEvent event) {
		new MappingIO().write(mappingRootType, sitename);
	}

	private void jMenuItem2ActionActionPerformed(ActionEvent event) {
		sitename = JOptionPane.showInputDialog(this, "Please enter the sitename");
		RootType siteCatRootType = new CategoryIO().read(sitename);
		RootType karniyarikCatRootType = new CategoryIO().read("karniyarik");
		
		getCategoryTreePanel0().loadCategory(karniyarikCatRootType);
		getCategoryTreePanel1().loadCategory(siteCatRootType);
		
		mappingRootType.setName(sitename);
	}

	private void jMenuItem0ActionActionPerformed(ActionEvent event) {
		sitename = JOptionPane.showInputDialog(this, "Please Enter sitename");
		mappingRootType = new MappingIO().read(sitename);
		
		RootType siteCatRootType = new CategoryIO().read(sitename);
		RootType karniyarikCatRootType = new CategoryIO().read("karniyarik");
		getCategoryTreePanel0().loadCategory(karniyarikCatRootType);
		getCategoryTreePanel1().loadCategory(siteCatRootType);
		
		for(MappingType type: mappingRootType.getMapping())
		{
			((DefaultListModel)getJList0().getModel()).addElement(type);
		}
	}
}
