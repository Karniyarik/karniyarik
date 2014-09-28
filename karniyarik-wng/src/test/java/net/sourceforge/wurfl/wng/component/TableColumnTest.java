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
public class TableColumnTest {

	public void createShouldReturnNotNull() {
		TableColumn column = new TableColumn();

		Assert.assertNotNull(column);
	}

	public void createWithLabelShouldSetLabel() {
		String expected = "label";
		TableColumn column = new TableColumn(expected);

		Assert.assertEquals(column.getLabel(), expected);
	}

	public void createWithLabelColspanShouldSetLabel() {
		String expected = "label";
		TableColumn column = new TableColumn(expected, 2);

		Assert.assertEquals(column.getLabel(), expected);
	}

	public void createWithLabelColspanShortLabelShouldSetLabel() {
		String expected = "label";
		TableColumn column = new TableColumn(expected, 2, "short_label");

		Assert.assertEquals(column.getLabel(), expected);
	}

	public void createWithLabelColspanShouldSetColspan() {
		int expected = 2;
		TableColumn column = new TableColumn("label", expected);

		Assert.assertEquals(column.getColspan(), expected);
	}

	public void createWithLabelColspanShortLabelShouldSetColspan() {
		int expected = 2;
		TableColumn column = new TableColumn("label", expected, "short_label");

		Assert.assertEquals(column.getColspan(), expected);
	}

	public void createWithLabelColspanShortLabelShouldSetShortLabel() {
		String expected = "slabel";
		TableColumn column = new TableColumn("label", 2, expected);

		Assert.assertEquals(column.getShortLabel(), expected);
	}

	public void getChildrenShouldReturnEmpty() {
		TableColumn column = new TableColumn();

		Assert.assertEquals(column.getChildren().size(), 0);
	}

	public void hasChildrenShouldReturnFalse() {
		TableColumn column = new TableColumn();

		Assert.assertEquals(column.hasChildren(), false);
	}

	public void equalsShouldReturnTrue() {
		TableColumn column1 = new TableColumn("test");
		TableColumn column2 = new TableColumn("test");

		Assert.assertTrue(column1.equals(column2));

		column1.setColspan(2);
		column2.setColspan(2);

		Assert.assertTrue(column1.equals(column2));

		column1.setShortLabel("sl");
		column2.setShortLabel("sl");

		Assert.assertTrue(column1.equals(column2));
	}

	public void equalsShouldReturnFalse() {
		TableColumn column1 = new TableColumn("test");
		TableColumn column2 = new TableColumn("notest");

		Assert.assertFalse(column1.equals(column2));

		column1 = new TableColumn("test", 1);
		column2 = new TableColumn("test", 2);

		Assert.assertFalse(column1.equals(column2));

		column1 = new TableColumn("test", 1, "sl");
		column2 = new TableColumn("test", 1, "err");

		Assert.assertFalse(column1.equals(column2));
	}

	public void hashCodeShouldReturnTrue() {
		TableColumn column1 = new TableColumn("test");
		TableColumn column2 = new TableColumn("test");

		Assert.assertTrue(column1.hashCode() == column2.hashCode());

		column1.setColspan(2);
		column2.setColspan(2);

		Assert.assertTrue(column1.hashCode() == column2.hashCode());

		column1.setShortLabel("sl");
		column2.setShortLabel("sl");

		Assert.assertTrue(column1.hashCode() == column2.hashCode());
	}

	public void hashCodeShouldReturnFalse() {
		TableColumn column1 = new TableColumn("test");
		TableColumn column2 = new TableColumn("false");

		Assert.assertFalse(column1.hashCode() == column2.hashCode());

		column1 = new TableColumn("test", 1, "sl");
		column2 = new TableColumn("test", 1, "err");

		Assert.assertFalse(column1.hashCode() == column2.hashCode());

		column1 = new TableColumn("test", 1, "sl");
		column2 = new TableColumn("test", 1, "err");

		Assert.assertFalse(column1.hashCode() == column2.hashCode());
	}

	public void getIndexShouldReturnIndex() {
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
		
		Assert.assertEquals(column2.getIndex(), 1);
		Assert.assertEquals(column3.getIndex(), 3);
	}
	
	/**
	 * @testng.expected-exceptions value = "java.lang.IllegalStateException"
	 */
	public void getTableShouldThrowISE() {
		TableColumn column = new TableColumn("Test");
		
		column.getTable();
	}
}
