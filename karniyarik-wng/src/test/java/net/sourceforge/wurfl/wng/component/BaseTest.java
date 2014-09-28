/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.testng.Assert;

/**
 * @author fanta
 * @testng.test groups = 'unit'
 */
public abstract class BaseTest {
	
	/**
	 * @testng.test dataProvider = "componentsProvider"
	 * @param one
	 * @param two
	 */
	public void shouldBeEqual(Component one, Component two) {
		Assert.assertEquals(one, two);
		Assert.assertEquals(one.hashCode(), two.hashCode());
	}
	
	/**
	 * @testng.test dataProvider = "differentComponentsProvider"
	 * @param one
	 * @param two
	 */
	public void shouldNotBeEqual(Component one, Component two) {
		Assert.assertNotSame(one, two, one.toString() + " vs " + two.toString());
		Assert.assertFalse(one.hashCode()==two.hashCode(), one.hashCode() + " vs " + two.hashCode());
	}
	
	/**
	 * @testng.data-provider name="componentsProvider"
	 * @throws Exception TODO
	 */
	protected abstract Object[][] componentsProvider() throws Exception;
	
	/**
	 * @testng.data-provider name="differentComponentsProvider"
	 * @throws Exception TODO
	 */
	protected abstract Object[][] differentComponentsProvider() throws Exception;
	
}
