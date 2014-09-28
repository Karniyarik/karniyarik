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
public class SelectTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		Option option = new Option("label", "value", "selected");
		
		Select one = new Select("one");
		one.addOption(option);
		
		Select two = new Select("one");
		two.addOption(option);
		
		
		return new Object[][] { 
				{ new Select(), new Select() },
				{ new Select("one"), new Select("one")},
				{ one, two}
				
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] { 
				{ new Select("one"), new Select("two") },
				{ new Select("2"), new Select("4")}
		};
	}

}
