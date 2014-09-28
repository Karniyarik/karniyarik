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
 * 
 * @testng.test groups = "unit";
 */
public class IllustratedItemTest extends BaseTest {

	private IllustratedItem illustratedItem;

	/**
	 * @testng.before-class
	 * 
	 */
	protected void createIllustratedItem() {
		this.illustratedItem = new IllustratedItem();
	}

	public void shouldAddAnImage() {
		Image image = new Image();
		this.illustratedItem.setImage(image);
		Assert.assertEquals(image, this.illustratedItem.getImage());
	}

	protected Object[][] componentsProvider() throws Exception {
		IllustratedItem one = new IllustratedItem();
		one.setImage(new Image());

		IllustratedItem two = new IllustratedItem();
		two.setImage(new Image());

		return new Object[][] {
				{ new IllustratedItem(), new IllustratedItem() }, { one, two } };
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		IllustratedItem one = new IllustratedItem();
		one.setImage(new Image());

		IllustratedItem two = new IllustratedItem();

		return new Object[][] { { one, new IllustratedItem() }, { one, two } };
	}

}
