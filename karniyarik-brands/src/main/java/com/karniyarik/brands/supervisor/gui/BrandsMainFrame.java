package com.karniyarik.brands.supervisor.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.karniyarik.brands.BrandHolder;
import com.karniyarik.brands.supervisor.BrandsMainController;

public class BrandsMainFrame extends JFrame
{

	private static final long	serialVersionUID	= 1L;
	private JPanel				jContentPane		= null;
	private BrandsMainController mController = null;
	private JPanel pnlBrandToKBMatch = null;
	private JScrollPane scrollKB = null;
	private JScrollPane scrollBrands = null;
	private JTree treeKB = null;  //  @jve:decl-index=0:visual-constraint="494,238"
	private JTree treeBrands = null;
	private JScrollPane scrollMerge = null;
	private JTable tblMerge = null;
	private JMenuBar menuMain = null;
	private JMenu mnuFile = null;
	private JMenuItem mnuOpenKB = null;
	private JMenuItem mnuOpenBrandLog = null;
	private JMenuItem mnuAnalyzeBrandLog = null;
	private JMenuItem mnuMergeBrandsToKB = null;
	private JMenuItem mnuSaveKBAndBrandsLog = null;
	private JMenuItem mnuFindKBDuplicates = null;
	private JMenuItem mnuSaveKB = null;
	private JMenuItem mnuSaveLog = null;
	private JSplitPane pnlMain = null;
	private JTabbedPane pnlRight = null;
	private JToolBar toolMenu = null;
	private JButton btnLoadKB = null;
	private JButton btnOpenBrand = null;
	private JButton btnAnalyzeBrandLog = null;
	private JButton btnMergeBrand = null;
	private JButton btnSaveKB = null;
	private JButton btnFindKBDuplicates = null;
	private JButton btnSaveBrands = null;
	private JButton btnExit = null;
	private JPanel pnlStatus = null;
	/**
	 * This is the default constructor
	 */
	public BrandsMainFrame(BrandsMainController aController)
	{
		super();
		mController = aController;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(529, 403);
		this.setJMenuBar(getMenuMain());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/karniyarik/brands/supervisor/images/kb.png")));
		this.setContentPane(getJContentPane());
		this.setTitle("Brands Tool");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPnlMain(), BorderLayout.CENTER);
			jContentPane.add(getToolMenu(), BorderLayout.NORTH);
			jContentPane.add(getPnlStatus(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes pnlBrandToKBMatch	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlBrandToKBMatch()
	{
		if (pnlBrandToKBMatch == null)
		{
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.weighty = 1.0;
			gridBagConstraints11.gridwidth = 2;
			gridBagConstraints11.insets = new Insets(4, 4, 4, 4);
			gridBagConstraints11.gridx = 0;
			pnlBrandToKBMatch = new JPanel();
			pnlBrandToKBMatch.setLayout(new GridBagLayout());
			pnlBrandToKBMatch.add(getScrollMerge(), gridBagConstraints11);
		}
		return pnlBrandToKBMatch;
	}

	/**
	 * This method initializes scrollKB	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollKB()
	{
		if (scrollKB == null)
		{
			scrollKB = new JScrollPane();
			scrollKB.setViewportView(getTreeKB());
		}
		return scrollKB;
	}

	/**
	 * This method initializes scrollBrands	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollBrands()
	{
		if (scrollBrands == null)
		{
			scrollBrands = new JScrollPane();
			scrollBrands.setViewportView(getTreeBrands());
		}
		return scrollBrands;
	}

	/**
	 * This method initializes treeKB	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private BrandTree getTreeKB()
	{
		if (treeKB == null)
		{
			DefaultMutableTreeNode aNode = new DefaultMutableTreeNode("KB Brands");
			treeKB = new BrandTree(aNode);
			treeKB.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			treeKB.setShowsRootHandles(true);
		}
		return (BrandTree) treeKB;
	}

	/**
	 * This method initializes treeBrands	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private BrandTree getTreeBrands()
	{
		if (treeBrands == null)
		{
			DefaultMutableTreeNode aNode = new DefaultMutableTreeNode("Brands");
			treeBrands = new BrandTree(aNode);
			treeBrands.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
			treeBrands.setShowsRootHandles(true);
			treeBrands.addKeyListener(new BrandsTreeKeyListener());
			treeBrands.setEditable(true);
		}
		return (BrandTree) treeBrands;
	}

	/**
	 * This method initializes scrollMerge	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollMerge()
	{
		if (scrollMerge == null)
		{
			scrollMerge = new JScrollPane();
			scrollMerge.setViewportView(getTblMerge());
		}
		return scrollMerge;
	}

	
	/**
	 * This method initializes tblMerge	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTblMerge()
	{
		if (tblMerge == null)
		{
			MergeTableModel aModel = new MergeTableModel();
			
			tblMerge = new JTable(aModel);
			tblMerge.addKeyListener(new MergeTableKeyListener());
			tblMerge.getColumnModel().getColumn(5).setCellEditor(new AddUnderEditor(getTreeKB()));
			tblMerge.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
			tblMerge.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));
		}
		return tblMerge;
	}



	/**
	 * This method initializes menuMain	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMenuMain()
	{
		if (menuMain == null)
		{
			menuMain = new JMenuBar();
			menuMain.add(getMnuFile());
		}
		return menuMain;
	}

	/**
	 * This method initializes mnuFile	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMnuFile()
	{
		if (mnuFile == null)
		{
			mnuFile = new JMenu();
			mnuFile.setText("File");
			mnuFile.add(getMnuOpenKB());
			mnuFile.add(getMnuOpenBrandLog());
			mnuFile.add(getMnuAnalyzeBrandLog());
			mnuFile.add(getMnuFindKBDuplicates());
			mnuFile.add(getMnuMergeBrandsToKB());			
			mnuFile.add(getMnuSaveKB());
			mnuFile.add(getMnuSaveLog());
			mnuFile.add(getMnuSaveKBAndBrandsLog());
		}
		return mnuFile;
	}

	/**
	 * This method initializes mnuOpenKB	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMnuOpenKB()
	{
		if (mnuOpenKB == null)
		{
			mnuOpenKB = new JMenuItem();
			mnuOpenKB.setText("Open Brand KB");
			mnuOpenKB.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent aE)
				{				
					loadBrandsKB();
				}
			});
		}
		return mnuOpenKB;
	}

	/**
	 * This method initializes mnuOpenBrandLog	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMnuOpenBrandLog()
	{
		if (mnuOpenBrandLog == null)
		{
			mnuOpenBrandLog = new JMenuItem();
			mnuOpenBrandLog.setText("open Brands Log");
			mnuOpenBrandLog.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent aE)
				{				
					loadBrandsFile();
				}
			});
		}
		return mnuOpenBrandLog;
	}

	/**
	 * This method initializes mnuAnalyzeBrandLog	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMnuAnalyzeBrandLog()
	{
		if (mnuAnalyzeBrandLog == null)
		{
			mnuAnalyzeBrandLog = new JMenuItem();
			mnuAnalyzeBrandLog.setText("Analyze Brand Log");
			mnuAnalyzeBrandLog.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent aE)
				{				
					analyzeBrands();
				}
			});
		}
		return mnuAnalyzeBrandLog;
	}

	/**
	 * This method initializes mnuMergeBrandsToKB	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMnuMergeBrandsToKB()
	{
		if (mnuMergeBrandsToKB == null)
		{
			mnuMergeBrandsToKB = new JMenuItem();
			mnuMergeBrandsToKB.setText("Merge Brands Log and KB");
			mnuMergeBrandsToKB.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent aE)
				{				
					merge();
				}
			});
		}
		return mnuMergeBrandsToKB;
	}

	/**
	 * This method initializes mnuSaveKBAndBrandsLog	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMnuSaveKBAndBrandsLog()
	{
		if (mnuSaveKBAndBrandsLog == null)
		{
			mnuSaveKBAndBrandsLog = new JMenuItem();
			mnuSaveKBAndBrandsLog.setText("Save KB and Brands Log");
			mnuSaveKBAndBrandsLog.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent aE)
				{				
					save();
				}
			});
		}
		return mnuSaveKBAndBrandsLog;
	}

	/**
	 * This method initializes mnuFindKBDuplicates	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMnuFindKBDuplicates()
	{
		if (mnuFindKBDuplicates == null)
		{
			mnuFindKBDuplicates = new JMenuItem();
			mnuFindKBDuplicates.setText("Find KB Duplicates");
			mnuFindKBDuplicates.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent aE)
				{				
					findKBDuplicates();
				}

			});
		}
		return mnuFindKBDuplicates;
	}

	/**
	 * This method initializes mnuSaveKB	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMnuSaveKB()
	{
		if (mnuSaveKB == null)
		{
			mnuSaveKB = new JMenuItem();
			mnuSaveKB.setText("Save KB");
			mnuSaveKB.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent aE)
				{				
					saveKB();
				}


			});
		}
		return mnuSaveKB;
	}

	private JMenuItem getMnuSaveLog()
	{
		if (mnuSaveLog == null)
		{
			mnuSaveLog = new JMenuItem();
			mnuSaveLog.setText("Save Log");
			mnuSaveLog.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent aE)
				{				
					saveLog();
				}


			});
		}
		return mnuSaveLog;
	}
	
	private void save()
	{
		MergeTableModel aModel = (MergeTableModel) getTblMerge().getModel();
		
		List<BrandHolder> aBrands = getTreeBrands().getBrands();
		
		List<Object[]> aData = mController.save(aModel.getData(), aBrands);
		
		aModel.setData(aData);
		
		aModel.fireTableDataChanged();
		
		loadBrandsKBToGUI();
		
		loadBrandsToGUI(aBrands);
	}

	private void findKBDuplicates()
	{
		List<Object[]> aResult = mController.findKBDuplicates();
		
		MergeTableModel aTableModel = (MergeTableModel) getTblMerge().getModel();
		
		aTableModel.setData(aResult);

		aTableModel.fireTableDataChanged();
	}
	
	private void saveKB()
	{
		MergeTableModel aModel = (MergeTableModel) getTblMerge().getModel();
		List<Object[]> aResult = mController.saveKBDuplicates(aModel.getData());
		aModel.setData(aResult);
		aModel.fireTableDataChanged();
		loadBrandsKBToGUI();
	}
	private void analyzeBrands()
	{
		List<BrandHolder> aBrandsList = getTreeBrands().getBrands();
		
		loadBrandsToGUI(mController.analyzeBrands(aBrandsList));		
	}


	private void loadBrandsFile()
	{
		mController.loadBrandsFile();
		
		List<BrandHolder> aList = mController.getBrands();
		
		loadBrandsToGUI(aList);
	}
	
	private void loadBrandsToGUI(List<BrandHolder> aList)
	{
		getTreeBrands().setBrands(aList);
		
		getTreeBrands().repaint();
		
		getTreeBrands().invalidate();
		
		((DefaultTreeModel)getTreeBrands().getModel()).reload();
	}

	private void loadBrandsKB()
	{
		mController.loadBrandsKB();
		
		loadBrandsKBToGUI();
	}

	private void loadBrandsKBToGUI()
	{
		List<BrandHolder> aList = mController.getBrandsKB();

		DefaultMutableTreeNode aRootNode = (DefaultMutableTreeNode) getTreeKB().getModel().getRoot();
		
		getTreeKB().setBrands(aList);
		
		getTreeKB().expandPath(new TreePath(aRootNode.getPath()));
		
		((DefaultTreeModel)getTreeKB().getModel()).reload();
	}

	public void merge()
	{		
		List<BrandHolder> aBrandsList = getTreeBrands().getBrands();
		
		List<Object[]> aResult = mController.merge(aBrandsList);
		
		MergeTableModel aTableModel = (MergeTableModel) getTblMerge().getModel();
		
		aTableModel.setData(aResult);

		aTableModel.fireTableDataChanged();
	}
		
	
	public void saveLog()
	{
		List<BrandHolder> aBrandsList = getTreeBrands().getBrands();
		mController.saveLog(aBrandsList);
	}

	/**
	 * This method initializes pnlMain	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getPnlMain()
	{
		if (pnlMain == null)
		{
			pnlMain = new JSplitPane();
			pnlMain.setDividerLocation(200);
			pnlMain.setLeftComponent(getScrollKB());
			pnlMain.setRightComponent(getPnlRight());
		}
		return pnlMain;
	}

	/**
	 * This method initializes pnlRight	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getPnlRight()
	{
		if (pnlRight == null)
		{
			pnlRight = new JTabbedPane();
			pnlRight.addTab("Match Result", null, getPnlBrandToKBMatch(), null);
			pnlRight.addTab("Brands", null, getScrollBrands(), null);
		}
		return pnlRight;
	}

	/**
	 * This method initializes toolMenu	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getToolMenu()
	{
		if (toolMenu == null)
		{
			toolMenu = new JToolBar();
			toolMenu.add(getBtnLoadKB());
			toolMenu.add(getBtnOpenBrand());
			toolMenu.add(getBtnAnalyzeBrandLog());
			toolMenu.add(getBtnFindKBDuplicates());
			toolMenu.add(getBtnMergeBrand());
			toolMenu.add(getBtnSaveKB());
			toolMenu.add(getBtnSaveBrands());
			toolMenu.add(getBtnExit());
		}
		return toolMenu;
	}

	/**
	 * This method initializes btnLoadKB	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnLoadKB()
	{
		if (btnLoadKB == null)
		{
			btnLoadKB = new JButton();
			btnLoadKB.setIcon(new ImageIcon(getClass().getResource("/com/karniyarik/brands/supervisor/images/Load.png")));
			btnLoadKB.setToolTipText("Open Brand Knowledge Base");
			btnLoadKB.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					loadBrandsKB();
				}
			});
		}
		return btnLoadKB;
	}

	/**
	 * This method initializes btnOpenBrand	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOpenBrand()
	{
		if (btnOpenBrand == null)
		{
			btnOpenBrand = new JButton();
			btnOpenBrand.setToolTipText("Open Brands Log");
			btnOpenBrand.setIcon(new ImageIcon(getClass().getResource("/com/karniyarik/brands/supervisor/images/box_upload_48.png")));
			btnOpenBrand.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					loadBrandsFile();
				}
			});
		}
		return btnOpenBrand;
	}

	/**
	 * This method initializes btnAnalyzeBrandLog	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAnalyzeBrandLog()
	{
		if (btnAnalyzeBrandLog == null)
		{
			btnAnalyzeBrandLog = new JButton();
			btnAnalyzeBrandLog.setToolTipText("Analyze Brand Log");
			btnAnalyzeBrandLog.setIcon(new ImageIcon(getClass().getResource("/com/karniyarik/brands/supervisor/images/Bar Chart.png")));
			btnAnalyzeBrandLog.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					analyzeBrands();
				}
			});
		}
		return btnAnalyzeBrandLog;
	}

	/**
	 * This method initializes btnMergeBrand	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnMergeBrand()
	{
		if (btnMergeBrand == null)
		{
			btnMergeBrand = new JButton();
			btnMergeBrand.setIcon(new ImageIcon(getClass().getResource("/com/karniyarik/brands/supervisor/images/Line Chart.png")));
			btnMergeBrand.setToolTipText("Merge Brands to Knowledge Base");
			btnMergeBrand.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					merge();
				}
			});
		}
		return btnMergeBrand;
	}

	/**
	 * This method initializes btnSaveKB	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSaveKB()
	{
		if (btnSaveKB == null)
		{
			btnSaveKB = new JButton();
			btnSaveKB.setIcon(new ImageIcon(getClass().getResource("/com/karniyarik/brands/supervisor/images/Save.png")));
			btnSaveKB.setToolTipText("Save Knowledge Base");
			btnSaveKB.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					saveKB();
				}
			});
		}
		return btnSaveKB;
	}

	/**
	 * This method initializes btnFindKBDuplicates	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnFindKBDuplicates()
	{
		if (btnFindKBDuplicates == null)
		{
			btnFindKBDuplicates = new JButton();
			btnFindKBDuplicates.setToolTipText("Find Duplicates in Knowledge Base");
			btnFindKBDuplicates.setIcon(new ImageIcon(getClass().getResource("/com/karniyarik/brands/supervisor/images/Comment.png")));
			btnFindKBDuplicates.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					findKBDuplicates();
				}
			});
		}
		return btnFindKBDuplicates;
	}

	/**
	 * This method initializes btnSaveBrands	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSaveBrands()
	{
		if (btnSaveBrands == null)
		{
			btnSaveBrands = new JButton();
			btnSaveBrands.setIcon(new ImageIcon(getClass().getResource("/com/karniyarik/brands/supervisor/images/box_download_48.png")));
			btnSaveBrands.setToolTipText("Save Brands Log");
			btnSaveBrands.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					saveLog();
				}
			});
		}
		return btnSaveBrands;
	}

	/**
	 * This method initializes btnExit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExit()
	{
		if (btnExit == null)
		{
			btnExit = new JButton();
			btnExit.setToolTipText("Exit");
			btnExit.setIcon(new ImageIcon(getClass().getResource("/com/karniyarik/brands/supervisor/images/Exit.png")));
			btnExit.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes pnlStatus	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlStatus()
	{
		if (pnlStatus == null)
		{
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnlStatus = new JPanel();
			pnlStatus.setLayout(flowLayout);
		}
		return pnlStatus;
	}
}  //  @jve:decl-index=0:visual-constraint="-79,-5"
