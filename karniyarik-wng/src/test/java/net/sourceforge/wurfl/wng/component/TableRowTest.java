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
public class TableRowTest {
	
	public void createShouldReturnNotNull() {
		
		TableRow row = new TableRow();
		
		Assert.assertNotNull(row);
	}
	
	public void equalsShouldReturnTrue() {
		
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();

		Assert.assertEquals(row1, row2);
		
		TableCell cell1 = new TableCell();
		TableCell cell2 = new TableCell();
		row1.addCell(cell1);
		row2.addCell(cell2);

		Assert.assertEquals(row1, row2);

		cell1.setColspan(2);
		cell2.setColspan(2);
		
		Assert.assertEquals(row1, row2);
		
		cell1.setText("test");
		cell2.setText("test");
		
		Assert.assertEquals(row1, row2);

		cell1.setShortText("test");
		cell2.setShortText("test");
		
		Assert.assertEquals(row1, row2);
	}
	
	public void equalsShouldReturnFalse() {
		
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		row1.addCell(new TableCell("test"));

		Assert.assertFalse(row1.equals(row2));

		row1 = new TableRow();
		row2 = new TableRow();
		row1.addCell(new TableCell("test"));
		row2.addCell(new TableCell("false"));
		
		Assert.assertFalse(row1.equals(row2));

		row1 = new TableRow();
		row2 = new TableRow();
		row1.addCell(new TableCell("test", 2));
		row2.addCell(new TableCell("test", 1));
		
		Assert.assertFalse(row1.equals(row2));

		row1 = new TableRow();
		row2 = new TableRow();
		row1.addCell(new TableCell("test"));
		row2.addCell(new TableCell("test"));
		row2.addCell(new TableCell("test"));
		
		Assert.assertFalse(row1.equals(row2));

	}
	
	public void hashCodeShouldBeEquals() {
		
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();

		Assert.assertEquals(row1.hashCode(), row2.hashCode());
		
		TableCell cell1 = new TableCell();
		TableCell cell2 = new TableCell();
		row1.addCell(cell1);
		row2.addCell(cell2);
		
		Assert.assertEquals(row1.hashCode(), row2.hashCode());
		
		cell1.setColspan(2);
		cell2.setColspan(2);
		
		Assert.assertEquals(row1.hashCode(), row2.hashCode());
		
		cell1.setText("test");
		cell2.setText("test");
		
		Assert.assertEquals(row1.hashCode(), row2.hashCode());

		cell1.setShortText("test");
		cell2.setShortText("test");
		
		Assert.assertEquals(row1.hashCode(), row2.hashCode());
	}

	public void hashCodeShouldBeNotEquals() {
		
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		row1.addCell(new TableCell("test"));

		Assert.assertTrue(row1.hashCode() != row2.hashCode());

		row1 = new TableRow();
		row2 = new TableRow();
		row1.addCell(new TableCell("test"));
		row2.addCell(new TableCell("false"));
		
		Assert.assertTrue(row1.hashCode() != row2.hashCode());

		row1 = new TableRow();
		row2 = new TableRow();
		row1.addCell(new TableCell("test", 2));
		row2.addCell(new TableCell("test", 1));
		
		Assert.assertTrue(row1.hashCode() != row2.hashCode());

		row1 = new TableRow();
		row2 = new TableRow();
		row1.addCell(new TableCell("test"));
		row2.addCell(new TableCell("test"));
		row2.addCell(new TableCell("test"));
		
		Assert.assertTrue(row1.hashCode() != row2.hashCode());

	}
	
	public void hasChildrenShouldReturnFalse() {
		TableRow row = new TableRow();
		
		row.addCell(new TableCell("test"));
		
		Assert.assertFalse(row.hasChildren());
	}
	
	public void getChildrenShouldReturnEmpty() {
		TableRow row = new TableRow();
		
		row.addCell(new TableCell("test"));
		
		Assert.assertTrue(row.getChildren().size()==0);
	}
	
	public void addCellShouldAddCell() {
		
		TableCell cell = new TableCell("test");
		TableRow row = new TableRow();
		row.addCell(cell);
		
		Assert.assertTrue(row.getCells().size() == 1);
		Assert.assertTrue(row.getCells().contains(cell));
	}
	
	public void addCellShouldSetParent() {
		
		TableCell cell = new TableCell("test");
		TableRow row = new TableRow();
		row.addCell(cell);
		
		Assert.assertSame(cell.getRow(), row);
	}
	
	public void addCellsShouldAddCell() {
		
		TableCell cell = new TableCell("test");
		TableRow row = new TableRow();
		row.addCells(Arrays.asList(new TableCell[]{cell}));
		
		Assert.assertTrue(row.getCells().size() == 1);
		Assert.assertTrue(row.getCells().contains(cell));
	}
	
	public void addCellsShouldSetParent() {
		
		TableCell cell = new TableCell("test");
		TableRow row = new TableRow();
		row.addCells(Arrays.asList(new TableCell[]{cell}));
		
		Assert.assertSame(cell.getRow(), row);
	}

	public void removeCellShouldAddCell() {
		
		TableCell cell = new TableCell("test");
		TableRow row = new TableRow();
		row.addCell(cell);
		row.removeCell(cell);
		
		Assert.assertTrue(row.getCells().size() == 0);
		Assert.assertFalse(row.getCells().contains(cell));
	}
	
	public void removeCellShouldUnsetParent() {
		
		TableCell cell = new TableCell("test");
		TableRow row = new TableRow();
		row.addCell(cell);
		row.removeCell(cell);
		
		Assert.assertNull(cell.getRow(), null);
	}
	
	public void removeCellsShouldAddCells() {
		
		TableCell cell = new TableCell("test");
		TableRow row = new TableRow();
		row.addCell(cell);
		row.removeCells(Arrays.asList(new TableCell[]{cell}));
		
		Assert.assertTrue(row.getCells().size() == 0);
		Assert.assertFalse(row.getCells().contains(cell));
	}
	
	public void removeCellsShouldUnsetParent() {
		
		TableCell cell = new TableCell("test");
		TableRow row = new TableRow();
		row.addCell(cell);
		row.removeCells(Arrays.asList(new TableCell[]{cell}));
		
		Assert.assertNull(cell.getRow(), null);
	}
	
	public void isLastCellShouldReturnTrue() {
		
		TableRow row = new TableRow();
		
		TableCell cell1 = new TableCell("1st");
		TableCell cell2 = new TableCell("2nd");
		TableCell cell3 = new TableCell("3th", 2);
		TableCell cell4 = new TableCell("4th");

		row.addCell(cell1);
		row.addCell(cell2);
		row.addCell(cell3);
		row.addCell(cell4);
		
		Assert.assertTrue(row.isLastCell(cell4));
	}
	
	public void isLastCellShouldReturnFalse() {
		
		TableRow row = new TableRow();
		
		TableCell cell1 = new TableCell("1st");
		TableCell cell2 = new TableCell("2nd");
		TableCell cell3 = new TableCell("3th", 2);
		TableCell cell4 = new TableCell("4th");

		row.addCell(cell1);
		row.addCell(cell2);
		row.addCell(cell3);
		row.addCell(cell4);
		
		Assert.assertFalse(row.isLastCell(cell2));
	}
	
	public void isLastShouldReturnTrue() {
		
		Table table = new Table();
		
		TableRow row1 = new TableRow();
		row1.addCell(new TableCell("1st"));

		TableRow row2 = new TableRow();
		row2.addCell(new TableCell("2nd"));

		TableRow row3 = new TableRow();
		row3.addCell(new TableCell("3th"));

		TableRow row4 = new TableRow();
		row4.addCell(new TableCell("4th"));
		
		table.addRow(row1);
		table.addRow(row2);
		table.addRow(row3);
		table.addRow(row4);
		
		Assert.assertTrue(row4.isLast());
	}
	
	public void isLastShouldReturnFalse() {
		
		Table table = new Table();
		
		TableRow row1 = new TableRow();
		row1.addCell(new TableCell("1st"));

		TableRow row2 = new TableRow();
		row2.addCell(new TableCell("2nd"));

		TableRow row3 = new TableRow();
		row3.addCell(new TableCell("3th"));

		TableRow row4 = new TableRow();
		row4.addCell(new TableCell("4th"));
		
		table.addRow(row1);
		table.addRow(row2);
		table.addRow(row3);
		table.addRow(row4);
		
		Assert.assertFalse(row2.isLast());
	}

}
