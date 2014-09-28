/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

/**
 * 
 * @author fanta
 * 
 * @testng.test groups = "unit";
 */
public class IllustratedItemNoWrapTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		IllustratedItemNoWrap one = new IllustratedItemNoWrap();
		one.setLeftPart(new LeftPart());
		one.setRightPart(new RightPart());

		IllustratedItemNoWrap two = new IllustratedItemNoWrap();
		two.setLeftPart(new LeftPart());
		two.setRightPart(new RightPart());
		return new Object[][] {
				{ new IllustratedItemNoWrap(), new IllustratedItemNoWrap() },
				{ one, two } };
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		IllustratedItemNoWrap one = new IllustratedItemNoWrap();
		one.setLeftPart(new LeftPart());
		one.setRightPart(new RightPart());

		IllustratedItemNoWrap two = new IllustratedItemNoWrap();
		two.setLeftPart(new LeftPart());
		
		return new Object[][] {
				{ new IllustratedItemNoWrap(), two },
				{ one, new IllustratedItemNoWrap() },
				{ one, two}
				};

	}
}
