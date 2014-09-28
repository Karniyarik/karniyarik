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
public class LinkTest extends BaseTest{
	

	
	public void shoudSetAccesskeyOnlyIfInteger09OrChars() {
		Link link = new Link("http://fantayeneh");
		link.setAccesskey("1");
		Assert.assertEquals("1", link.getAccesskey());
	}
	
	/**
	 * * @testng.expected-exceptions value = "java.lang.IllegalArgumentException"
	 */
	public void shoudSetAccesskeyWithBadCharacterThrowException() {
		Link link = new Link("http://fantayeneh");
		link.setAccesskey("_");
	}

	protected Object[][] componentsProvider() throws Exception {
		return new Object[][] { 
				{ new Link(), new Link() },
				{ new Link("href"), new Link("href")},
				{ new Link("href", "text"), new Link("href", "text") },
				{ new Link("http://xxxx/xxx", "text", "a"), new Link("http://xxxx/xxx", "text", "a")}
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] { 
				{ new Link(), new Link("href")},
				{ new Link("href"), new Link("href", "text") },
				{ new Link("http://xxxx/xxx", "text", "a"), new Link("href", "text", "k")}
		};
	}
	
}
