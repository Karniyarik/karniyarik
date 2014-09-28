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
public class TableTest {
	
	public void createShouldReturnNotNull() {
		
		Table table = new Table();
		
		Assert.assertNotNull(table);
	}
	
	public void equalsShouldReturnTrue() {
		
		Table table1 = null; 
		Table table2 = null;
		
		table1 = new Table();
		table2 = new Table();
		
		Assert.assertEquals(table2, table1);
		
		TableHeader header1 = new TableHeader();
		TableHeader header2 = new TableHeader();
		table1.setHeader(header1);
		table2.setHeader(header2);

		Assert.assertEquals(table2, table1);

		header1.addColumn(new TableColumn("test"));
		header2.addColumn(new TableColumn("test"));
		
		Assert.assertEquals(table2, table1);
		
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		table1.addRow(row1);
		table1.addRow(row2);
		table2.addRow(row1);
		table2.addRow(row2);
		
		Assert.assertEquals(table2, table1);
		
		row1.addCell(new TableCell());
		row2.addCell(new TableCell());

		Assert.assertEquals(table2, table1);
	}
	
	public void equalsShouldReturnFalse() {
		
		Table table1 = null; 
		Table table2 = null;
		
		table1 = new Table();
		table2 = new Table();
		TableHeader header1 = new TableHeader();
		TableHeader header2 = new TableHeader();
		table1.setHeader(header1);

		Assert.assertFalse(table2.equals(table1));
		table2.setHeader(header2);
		
		table1 = new Table();
		table2 = new Table();
		header1 = new TableHeader();
		header2 = new TableHeader();
		header1.addColumn(new TableColumn("test"));
		header2.addColumn(new TableColumn("false"));
		table1.setHeader(header1);
		table2.setHeader(header2);
		
		Assert.assertFalse(table2.equals(table1));
		
		table1 = new Table();
		table2 = new Table();
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		table1.addRow(row1);
		
		Assert.assertFalse(table2.equals(table1));
		
		table2.addRow(row2);
		row1.addCell(new TableCell("test"));
		row2.addCell(new TableCell("false"));

		Assert.assertFalse(table2.equals(table1));
	}

	public void hashcodeShouldBeEquals() {
		
		Table table1 = null; 
		Table table2 = null;
		
		table1 = new Table();
		table2 = new Table();
		
		Assert.assertEquals(table2.hashCode(), table1.hashCode());
		
		TableHeader header1 = new TableHeader();
		TableHeader header2 = new TableHeader();
		table1.setHeader(header1);
		table2.setHeader(header2);

		Assert.assertEquals(table2.hashCode(), table1.hashCode());

		header1.addColumn(new TableColumn("test"));
		header2.addColumn(new TableColumn("test"));
		
		Assert.assertEquals(table2.hashCode(), table1.hashCode());
		
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		table1.addRow(row1);
		table1.addRow(row2);
		table2.addRow(row1);
		table2.addRow(row2);
		
		Assert.assertEquals(table2.hashCode(), table1.hashCode());
		
		row1.addCell(new TableCell());
		row2.addCell(new TableCell());

		Assert.assertEquals(table2.hashCode(), table1.hashCode());
	}
	
	public void hashCodeShouldBeDifferent() {
		
		Table table1 = null; 
		Table table2 = null;
		
		table1 = new Table();
		table2 = new Table();
		TableHeader header1 = new TableHeader();
		TableHeader header2 = new TableHeader();
		table1.setHeader(header1);

		Assert.assertFalse(table2.hashCode() == table1.hashCode());
		table2.setHeader(header2);
		
		table1 = new Table();
		table2 = new Table();
		header1 = new TableHeader();
		header2 = new TableHeader();
		header1.addColumn(new TableColumn("test"));
		header2.addColumn(new TableColumn("false"));
		table1.setHeader(header1);
		table2.setHeader(header2);
		
		Assert.assertFalse(table2.hashCode() == table1.hashCode());
		
		table1 = new Table();
		table2 = new Table();
		header1 = new TableHeader();
		header2 = new TableHeader();
		header1.addColumn(new TableColumn("test"));
		header2.addColumn(new TableColumn("false"));
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		table1.addRow(row1);
		
		Assert.assertFalse(table2.hashCode() == table1.hashCode());
		
		table2.addRow(row2);
		row1.addCell(new TableCell("test"));
		row2.addCell(new TableCell("false"));

		Assert.assertFalse(table2.hashCode() == table1.hashCode());
	}
	
	public void hasChildrenShouldReturnFalse() {
		TableRow row = new TableRow();
		Table table = new Table();
		table.addRow(row);
		
		Assert.assertFalse(table.hasChildren());
	}
	
	public void getChildrenShouldReturnFalse() {
		TableRow row = new TableRow();
		Table table = new Table();
		table.addRow(row);

		Assert.assertTrue(table.getChildren().isEmpty());
	}
	
	public void addRowShouldAddRow() {
		
		TableRow row = new TableRow();
		Table table = new Table();
		table.addRow(row);
		
		Assert.assertEquals(table.getRows().size(), 1);
		Assert.assertTrue(table.getRows().contains(row));
	}
	
	public void addRowShouldSetParent() {
		
		TableRow row = new TableRow();
		Table table = new Table();
		table.addRow(row);
		
		Assert.assertSame(row.getTable(), table);
	}
	
	public void addRowsShouldAddRows() {
		
		TableRow row = new TableRow();
		Table table = new Table();
		table.addRows(Arrays.asList(new TableRow[]{row}));
		
		Assert.assertEquals(table.getRows().size(), 1);
		Assert.assertTrue(table.getRows().contains(row));
	}
	
	public void addRowsShouldSetParent() {
		
		TableRow row = new TableRow();
		Table table = new Table();
		table.addRows(Arrays.asList(new TableRow[]{row}));
		
		Assert.assertSame(row.getTable(), table);
	}
	
	public void removeRowShouldRemoveRow() {
		
		TableRow row = new TableRow();
		row.addCell(new TableCell("foo"));
		Table table = new Table();
		table.addRow(row);
		table.removeRow(row);
		
		Assert.assertEquals(table.getRows().size(), 0);
		Assert.assertFalse(table.getRows().contains(row));
	}
	
	public void removeRowShouldUnsetParent() {
		
		TableRow row = new TableRow();
		row.addCell(new TableCell("foo"));
		Table table = new Table();
		table.addRow(row);
		table.removeRow(row);
		
		Assert.assertNotSame(row.getTable(), table);
	}
	
	public void removeRowsShouldRemoveRows() {
		
		TableRow row = new TableRow();
		row.addCell(new TableCell("foo"));
		Table table = new Table();
		table.addRow(row);
		table.removeRows(Arrays.asList(new TableRow[]{row}));
		
		Assert.assertEquals(table.getRows().size(), 0);
		Assert.assertFalse(table.getRows().contains(row));
	}
	
	public void removeRowsShouldUnsetParent() {
		
		TableRow row = new TableRow();
		row.addCell(new TableCell("foo"));
		Table table = new Table();
		table.addRow(row);
		table.removeRows(Arrays.asList(new TableRow[]{row}));
		
		Assert.assertNotSame(row.getTable(), table);
	}
	
	public void isLastRowShouldReturnFalse() {
		
		Table table = new Table();
		
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		TableRow row3 = new TableRow();
		TableRow row4 = new TableRow();
		
		table.addRow(row1);
		table.addRow(row2);
		table.addRow(row3);
		table.addRow(row4);
		
		Assert.assertFalse(table.isLastRow(row1));
	}
	
	public void isLastRowShouldReturnTrue() {
		
		Table table = new Table();
		
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		TableRow row3 = new TableRow();
		TableRow row4 = new TableRow();
		
		table.addRow(row1);
		table.addRow(row2);
		table.addRow(row3);
		table.addRow(row4);
		
		Assert.assertFalse(table.isLastRow(row4));
	}
}
