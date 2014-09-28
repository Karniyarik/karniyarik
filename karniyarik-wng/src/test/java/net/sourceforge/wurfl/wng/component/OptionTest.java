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
public class OptionTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		return new Object[][] { 
				{ new Option(), new Option() },
				{new Option("label", "value", "selected"), new Option("label", "value", "selected") }
		};
	}


	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] { 
				{ new Option("label1", "value1", "selected1"), new Option("label2", "value2", "selected2 ") },
				{ new Option("label2", "value2", "selected2"), new Option("label", "value", "selected") }
		};
	}
}
