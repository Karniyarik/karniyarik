/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import java.util.Arrays;

import org.testng.Assert;

/**
 * @author Filippo De Luca
 * @testng.test groups ="unit"
 */
public class TableHeaderTest {
	
	public void createShouldReturnNotNull() {
		TableHeader header = new TableHeader();
		
		Assert.assertNotNull(header);
	}
	
	public void equalsShouldReturnTrue() {
		TableHeader header1 = new TableHeader();
		TableHeader header2 = new TableHeader();
		
		header1.addColumn(new TableColumn("test"));
		header2.addColumn(new TableColumn("test"));
		
		Assert.assertTrue(header1.equals(header2));
	}
	
	public void equalsShouldReturnFalse() {
		TableHeader header1 = new TableHeader();
		TableHeader header2 = new TableHeader();
		
		header1.addColumn(new TableColumn("test"));
		header2.addColumn(new TableColumn("false"));
		
		Assert.assertFalse(header1.equals(header2));
	}
	
	public void hashCodeShouldReturnTrue() {
		TableHeader header1 = new TableHeader();
		TableHeader header2 = new TableHeader();
		
		header1.addColumn(new TableColumn("test"));
		header2.addColumn(new TableColumn("test"));
		
		Assert.assertTrue(header1.hashCode() == header2.hashCode());
	}
	
	public void hashCodeShouldReturnFalse() {
		TableHeader header1 = new TableHeader();
		TableHeader header2 = new TableHeader();
		
		header1.addColumn(new TableColumn("test"));
		header2.addColumn(new TableColumn("false"));
		
		Assert.assertFalse(header1.hashCode() == header2.hashCode());
	}
	
	public void hasChildrenShouldReturnFalse() {
		TableHeader header = new TableHeader();
		
		header.addColumn(new TableColumn("test"));
		header.addColumn(new TableColumn("test"));
		
		Assert.assertFalse(header.hasChildren());
	}
	
	public void getChildrenShouldReturnEmpty() {
		TableHeader header = new TableHeader();
		
		header.addColumn(new TableColumn("test"));
		header.addColumn(new TableColumn("test"));
		
		Assert.assertTrue(header.getChildren().size()==0);
	}
	
	public void addColumnShouldSetParent() {
		TableHeader header = new TableHeader();
		
		TableColumn column = new TableColumn("test");
		header.addColumn(column);
		
		Assert.assertSame(column.getHeader(), header);
	}

	public void addColumnShouldAddColumn() {
		TableHeader header = new TableHeader();
		
		TableColumn column = new TableColumn("test");
		header.addColumn(column);
		
		Assert.assertEquals(header.getColumnsSize(), 1);
		Assert.assertTrue(header.getColumns().contains(column));
	}
	
	public void removeColumnShouldUnsetParent() {
		TableHeader header = new TableHeader();
		
		TableColumn column = new TableColumn("test");
		header.addColumn(column);
		
		header.removeColumn(column);
		
		Assert.assertSame(column.getHeader(), null);
	}

	public void removeColumnShouldRemoveColumn() {
		TableHeader header = new TableHeader();
		
		TableColumn column = new TableColumn("test");
		header.addColumn(column);
		header.removeColumn(column);
		
		Assert.assertEquals(header.getColumns().size(), 0);
		Assert.assertFalse(header.getColumns().contains(column));
	}
	
	public void setColumnsShouldReplaceColumns() {
		TableHeader header = new TableHeader();
		
		header.addColumn(new TableColumn("to replace"));
		
		TableColumn column = new TableColumn("test");
		header.setColumns(Arrays.asList(new TableColumn[]{column}));
		
		Assert.assertEquals(header.getColumns().size(), 1);
		Assert.assertTrue(header.getColumns().contains(column));
	}

	public void setColumnsShouldAddColumns() {
		TableHeader header = new TableHeader();
		
		TableColumn column = new TableColumn("test");
		header.setColumns(Arrays.asList(new TableColumn[]{column}));
		
		Assert.assertEquals(header.getColumns().size(), 1);
		Assert.assertTrue(header.getColumns().contains(column));
	}
	
	public void setColumnsShouldSetParent() {
		TableHeader header = new TableHeader();
		
		TableColumn column = new TableColumn("test");
		header.setColumns(Arrays.asList(new TableColumn[]{column}));
		
		Assert.assertSame(column.getHeader(), header);
	}
	
	public void getColumnsSizeShouldReturnColspanSum() {
		Table table = new Table();
		TableHeader header = new TableHeader();
		table.setHeader(header);
		
		TableColumn column1 = new TableColumn("1st");
		TableColumn column2 = new TableColumn("2nd", 2);
		TableColumn column3 = new TableColumn("3th");
		TableColumn column4 = new TableColumn("4th");
		
		header.addColumn(column1);
		header.addColumn(column2);
		header.addColumn(column3);
		header.addColumn(column4);

		
		Assert.assertEquals(header.getColumnsSize(), 5);
	}

	public void getColumnAtIndexShouldReturnBoundingColumn() {
		Table table = new Table();
		TableHeader header = new TableHeader();
		table.setHeader(header);
		
		TableColumn column1 = new TableColumn("1st");
		TableColumn column2 = new TableColumn("2nd", 2);
		TableColumn column3 = new TableColumn("3th");
		TableColumn column4 = new TableColumn("4th");
		
		header.addColumn(column1);
		header.addColumn(column2);
		header.addColumn(column3);
		header.addColumn(column4);

		
		Assert.assertEquals(header.getColumnAtIndex(1), column2);
		Assert.assertEquals(header.getColumnAtIndex(2), column2);
	}
	
	public void getColumnStartingAtIndexShouldReturnStartingColumn() {
		Table table = new Table();
		TableHeader header = new TableHeader();
		table.setHeader(header);
		
		TableColumn column1 = new TableColumn("1st");
		TableColumn column2 = new TableColumn("2nd", 2);
		TableColumn column3 = new TableColumn("3th");
		TableColumn column4 = new TableColumn("4th");
		
		header.addColumn(column1);
		header.addColumn(column2);
		header.addColumn(column3);
		header.addColumn(column4);

		Assert.assertEquals(header.getColumnStartingAtIndex(1), column2);
		Assert.assertEquals(header.getColumnStartingAtIndex(2), null);
		Assert.assertEquals(header.getColumnStartingAtIndex(3), column3);
	}

}
