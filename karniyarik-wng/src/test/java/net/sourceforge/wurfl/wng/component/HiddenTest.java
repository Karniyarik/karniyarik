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
public class HiddenTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		return new Object[][] { 
				{ new Hidden(), new Hidden() },
				{ new Hidden("name", "value"), new Hidden("name", "value")}
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] { 
				{ new Hidden(), new Hidden("name", "value") },
				{ new Hidden("nameOne", "valueOne"), new Hidden("nameTwo", "valueTwo")},
				{ new Hidden("nameOne", "value"), new Hidden("nameTwo", "value")},
				{ new Hidden("name", "valueOne"), new Hidden("name", "valueTwo")}
		};
	}
}
