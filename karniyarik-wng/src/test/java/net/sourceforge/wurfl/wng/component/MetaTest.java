/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import junit.framework.Assert;

/**
 *
 * @author fanta
 * @testng.test groups ="unit"
 *
 */
public class MetaTest extends BaseTest{

	/**
	 * @testng.expected-exceptions value = "java.lang.IllegalArgumentException"
	 */
	public void shoudThrowExceptionForNullHttpEquiv(){
		Meta.createHttpEquivMeta(null, "value");
	}
	
	/**
	 * @testng.expected-exceptions value = "java.lang.IllegalArgumentException"
	 */
	public void shoudThrowExceptionForEmptyHttpEquiv(){
		Meta.createHttpEquivMeta("", "value");
	}
	
	/**
	 * @testng.test
	 * @testng.expected-exceptions value = "java.lang.IllegalArgumentException"
	 */
	public void shoudThrowExceptionForNullContent(){
		Meta.createHttpEquivMeta("Value", null);
	}
	
	/**
	 * @testng.expected-exceptions value = "java.lang.IllegalArgumentException"
	 */
	public void shoudThrowExceptionForEmptyContent(){
		Meta.createHttpEquivMeta("value", "");
	}
	
	
	

	public void shoudCreateHttpEquivMeta() {
		Meta meta = Meta.createHttpEquivMeta("Cache-Control", "max-age=200");
		Assert.assertEquals(meta.getHttpEquiv(), "Cache-Control");
		Assert.assertEquals(meta.getContent(), "max-age=200");
	}
	
	
	public void shoudCreateNamedMeta() {
		Meta meta = Meta.createNamedMeta("Cache-Control", "max-age=200");
		Assert.assertEquals(meta.getName(), "Cache-Control");
		Assert.assertEquals(meta.getContent(), "max-age=200");
	}

	protected Object[][] componentsProvider() throws Exception {
		return new Object[][] { 
				{ Meta.createHttpEquivMeta("Cache-Control", "max-age=200"), Meta.createHttpEquivMeta("Cache-Control", "max-age=200") },
				{ new Meta(), new Meta() }
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] { 
				{ Meta.createHttpEquivMeta("Cache-Control", "max-age=600"), Meta.createHttpEquivMeta("Cache-Control", "max-age=200") },
				{ Meta.createHttpEquivMeta("Cache-Control----", "max-age=600"), Meta.createHttpEquivMeta("Cache-Control", "max-age=200") }
		};
	}
	
	
}
