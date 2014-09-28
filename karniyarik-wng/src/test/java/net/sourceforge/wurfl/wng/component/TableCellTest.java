/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.testng.Assert;

/**
 * @author Filippo De Luca
 * @testng.test groups ="unit"
 */
public class TableCellTest {
	
	public void createShouldReturnNotNull() {
		
		TableCell cell = new TableCell();
		
		Assert.assertNotNull(cell);
	}
	
	public void equalsShouldReturnTrue() {
		
		TableCell cell1 = null;
		TableCell cell2 = null;
		
		cell1 = new TableCell();
		cell2 = new TableCell();
		
		Assert.assertEquals(cell1, cell2);
		
		cell1 = new TableCell();
		cell2 = new TableCell();
		cell1.setColspan(2);
		cell2.setColspan(2);
		
		Assert.assertEquals(cell1, cell2);
		
		cell1 = new TableCell();
		cell2 = new TableCell();
		cell1.setColspan(2);
		cell2.setColspan(2);
		cell1.setText("test");
		cell2.setText("test");
		
		Assert.assertEquals(cell1, cell2);

		cell1 = new TableCell();
		cell2 = new TableCell();
		cell1.setColspan(2);
		cell2.setColspan(2);
		cell1.setText("test");
		cell2.setText("test");
		cell1.setShortText("test");
		cell2.setShortText("test");
		
		Assert.assertEquals(cell1, cell2);
	}
	
	public void hashcodeShouldReturnEquals() {
		
		TableCell cell1 = null;
		TableCell cell2 = null;
		
		cell1 = new TableCell();
		cell2 = new TableCell();
		
		Assert.assertEquals(cell1.hashCode(), cell2.hashCode());
		
		cell1 = new TableCell();
		cell2 = new TableCell();
		cell1.setColspan(2);
		cell2.setColspan(2);
		
		Assert.assertEquals(cell1.hashCode(), cell2.hashCode());
		
		cell1 = new TableCell();
		cell2 = new TableCell();
		cell1.setColspan(2);
		cell2.setColspan(2);
		cell1.setText("test");
		cell2.setText("test");
		
		Assert.assertEquals(cell1.hashCode(), cell2.hashCode());

		cell1 = new TableCell();
		cell2 = new TableCell();
		cell1.setColspan(2);
		cell2.setColspan(2);
		cell1.setText("test");
		cell2.setText("test");
		cell1.setShortText("test");
		cell2.setShortText("test");
		
		Assert.assertEquals(cell1.hashCode(), cell2.hashCode());
	}

}
