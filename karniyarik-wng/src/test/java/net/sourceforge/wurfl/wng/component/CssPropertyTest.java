/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.testng.Assert;

/**
 * 
 * @author fanta
 * @testng.test groups = "unit"
 */
public class CssPropertyTest {

	public void shouldReturnThePropertyFormWithIsConstructed() {
		CssProperty cssProperty = new CssProperty("name", "value");
		Assert.assertEquals("value", cssProperty.getValue());
	}
	
	
	public void shouldBeEqual() {
		CssProperty one = new CssProperty("one", "one");
		CssProperty two = new CssProperty("one", "one");
		
		Assert.assertEquals(one, two);
		Assert.assertEquals(one.hashCode(), two.hashCode());
		
	}
}
