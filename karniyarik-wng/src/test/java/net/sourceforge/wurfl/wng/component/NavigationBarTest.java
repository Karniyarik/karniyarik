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
public class NavigationBarTest extends BaseTest {

	
	protected Object[][] componentsProvider() throws Exception {
		return new Object[][] { 
				{ new NavigationBar(), new NavigationBar() },
				{ new NavigationBar(), new NavigationBar(" | ") },
				{ new NavigationBar(" | "), new NavigationBar(" | ") }
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] { 
				{ new NavigationBar(), new NavigationBar("||") },
				{ new NavigationBar(" || "), new NavigationBar(" | ") }
		};
	}
}
