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
public class ImageTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		return new Object[][] {
				{ new Image(), new Image() },
				{ new Image("images/x.gif"), new Image("images/x.gif") },
				{ new Image("images/x.gif", "alt", "200", "300"), new Image("images/x.gif", "alt", "200", "300") }
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] {
				{ new Image("image/x.gif"), new Image() },
				{ new Image("images/x.gif"), new Image("images/xy.gif") },
				{ new Image("images/x.gif", "alt", "100", "300"), new Image("images/x.gif", "alt", "200", "300") }
		};
	}
}
