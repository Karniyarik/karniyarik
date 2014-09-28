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
public class StyleContainerTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		Css css = new Css();
		css.setSelector("selector");
		css.addProperty("x", "y");
		
		StyleContainer one = new StyleContainer();
		one.setId("id");
		one.addStyle(css);
		
		StyleContainer two = new StyleContainer();
		two.setId("id");
		two.addStyle(css);
		
		return new Object[][] { 
				{ new StyleContainer(), new StyleContainer() },
				{ one, two}};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		
		Css css = new Css();
		css.setSelector("selector");
		css.addProperty("x", "y");
		
		StyleContainer one = new StyleContainer();
		one.setId("id");
		one.addStyle(css);
		
		return new Object[][] { { one, new StyleContainer() } };
	}

	
}
