/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.resource;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.SystemUtils;
import org.testng.Assert;

/**
 * @author Filippo De Luca
 * @testng.test groups ="unit"
 */
public class FileSystemResourceLoaderTest {
	
	private File tmpDir;
	private File tmpFile;
	
	/**
	 * @testng.before-class
	 */
	public void init() throws IOException {
		tmpDir = new File(SystemUtils.JAVA_IO_TMPDIR);
		
		Assert.assertNotNull(tmpDir);
		Assert.assertTrue(tmpDir.exists());
		Assert.assertTrue(tmpDir.isDirectory());
		Assert.assertTrue(tmpDir.canWrite());
		
		tmpFile = new File(tmpDir, "wng-exist");
		tmpFile.createNewFile();
	}
	
	public void createShouldReturnNotNull() {
		
		FileSystemResourceLoader loader = new FileSystemResourceLoader(tmpDir);
		
		Assert.assertNotNull(loader);
	}
	
	public void loadResourceShouldReturnNotNull() {
		
		FileSystemResourceLoader loader = new FileSystemResourceLoader(tmpDir);
		Resource resource = loader.loadResource("wng-exist");
		
		Assert.assertNotNull(resource);
	}

	/**
	 * @testng.expected-exceptions value = "net.sourceforge.wurfl.wng.resource.ResourceNotFoundException"
	 */
	public void loadResourceShouldThrowException() {
		
		FileSystemResourceLoader loader = new FileSystemResourceLoader(tmpDir);
		Resource resource = loader.loadResource("wng-dontexist");
		
		Assert.assertNull(resource);
	}
}
