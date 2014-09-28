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
public class HeadTest extends BaseTest {

	protected Object[][] componentsProvider() throws Exception {
		Head one = new Head();
		one.setId("one");
		one.setTitle("title");
		one.add(Meta.createHttpEquivMeta("x", "x"));

		Head two = new Head();
		two.setId("one");
		two.setTitle("title");
		two.add(Meta.createHttpEquivMeta("x", "x"));

		return new Object[][] { { new Head(), new Head() }, { one, two } };
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		Head one = new Head();
		one.setId("one");
		one.setTitle("title");
		one.add(Meta.createHttpEquivMeta("x", "x"));

		Head two = new Head();
		two.setId("two");
		two.setTitle("title");
		two.add(Meta.createHttpEquivMeta("x", "x"));

		return new Object[][] { { one, two } };
	}
}
