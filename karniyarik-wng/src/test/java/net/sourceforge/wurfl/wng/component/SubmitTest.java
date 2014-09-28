/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

/**
 * @testng.test groups ="unit"
 * 
 */
public class SubmitTest extends BaseTest {

	public Object[][] componentsProvider() throws Exception {

		return new Object[][] { { new Submit(), new Submit() } };

	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] { { new Submit("name", "value1"), new Submit("name", "value2") } };
	}

}
