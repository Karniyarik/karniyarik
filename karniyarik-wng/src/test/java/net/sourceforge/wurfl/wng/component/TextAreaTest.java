/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.testng.Assert;

/**
 * 
 * @author fanta
 * @testng.test groups = 'unit'
 */
public class TextAreaTest extends BaseTest {

	private Component createTextArea() {
		TextArea textArea = new TextArea();
		textArea.setId("id");
		textArea.setCols(10);
		textArea.setName("name");
		textArea.setRows(10);
		textArea.setTitle("title");
		textArea.setValue("value");
		return textArea;
	}

	public void shoudCreateATextArea() {
		TextArea textArea = new TextArea();
		Assert.assertNotNull(textArea);
	}

	public void shoudCreateATextAreaWithNameColsAndRows() {
		String name = "textArea";
		int rows = 10;
		int cols = 10;
		TextArea textArea = new TextArea(name, rows, cols);
		Assert.assertEquals(textArea.getName(), "textArea");
		Assert.assertEquals(textArea.getRows(), 10);
		Assert.assertEquals(textArea.getCols(), 10);
	}

	public void shoudAddAllTheOptionalProperties() {
		TextArea textArea = new TextArea();
		textArea.setName("coolTextArea");
		textArea.setValue("even cooler content");
		textArea.setTitle("title");

		Assert.assertEquals("coolTextArea", textArea.getName());
		Assert.assertEquals("even cooler content", textArea.getValue());
		Assert.assertEquals("title", textArea.getTitle());
	}

	public Object[][] componentsProvider() throws Exception {

		return new Object[][] { { new TextArea(), new TextArea() },
				{ createTextArea(), createTextArea() } };

	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] {
				{new TextArea("one", 10, 10), new TextArea()}
		};
	}

}
