/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.resource;

import org.testng.Assert;

/**
 * @author Filippo De Luca
 * @testng.test groups ="unit"
 */
public class ClassPathResourceLoaderTest {
	
	public void createShouldReturnNotNull() {
		
		ClassPathResourceLoader loader = new ClassPathResourceLoader("/");
		
		Assert.assertNotNull(loader);
	}
	
	public void createWithNullClassPathShouldNotThrowError() {
		
		ClassPathResourceLoader loader = new ClassPathResourceLoader(null);
		
		Assert.assertNotNull(loader);
	}

	public void loadResourceShouldReturnResource() {
		
		ClassPathResourceLoader loader = new ClassPathResourceLoader("net/sourceforge/wurfl/wng/resource");
		Resource resource = loader.loadResource("exist");
		
		Assert.assertNotNull(resource);
		
	}
	
	public void loadResourceWithSlashTrailingClassPathShouldReturnResource() {
		
		ClassPathResourceLoader loader = new ClassPathResourceLoader("net/sourceforge/wurfl/wng/resource/");
		Resource resource = loader.loadResource("exist");
		
		Assert.assertNotNull(resource);
	}
	
	public void loadResourceWithSlashStartingClassPathShouldReturnResource() {
		
		ClassPathResourceLoader loader = new ClassPathResourceLoader("/net/sourceforge/wurfl/wng/resource");
		Resource resource = loader.loadResource("exist");
		
		Assert.assertNotNull(resource);
	}
	
	public void loadResourceWithSlashStartingAndTrailingClassPathShouldReturnResource() {
		
		ClassPathResourceLoader loader = new ClassPathResourceLoader("/net/sourceforge/wurfl/wng/resource/");
		Resource resource = loader.loadResource("exist");
		
		Assert.assertNotNull(resource);
	}
	
	/**
	 * @testng.expected-exceptions value = "net.sourceforge.wurfl.wng.resource.ResourceNotFoundException"
	 */
	public void loadResourceShouldThrowException() {
		
		ClassPathResourceLoader loader = new ClassPathResourceLoader("/net/sourceforge/wurfl/wng/resource");
		Resource resource = loader.loadResource("dontexist");
		
		Assert.assertNull(resource);
		
	}
}
