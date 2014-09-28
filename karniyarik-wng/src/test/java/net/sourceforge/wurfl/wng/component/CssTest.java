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
 *
 * @testng.test groups = "unit"
 */
public class CssTest {
	
	
	public void shoudBeSame() {
		Css css1 = new Css();
		Css css2 = new Css();
		Assert.assertEquals(css1.hashCode(), css2.hashCode());
	}
	
}
