/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.resource;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.testng.Assert;

/**
 * @author Filippo De Luca
 * @testng.test groups ="unit"
 */
public class ResourceLoaderChainTest {
	
	public void createShouldReturnNotNull() {
		
		ResourceLoaderChain chain = new ResourceLoaderChain();
		
		Assert.assertNotNull(chain);
	}
	
	public void createByArrayShouldReturnNotNull() {
		
		ResourceLoaderChain chain = new ResourceLoaderChain(new ResourceLoader[]{new ClassPathResourceLoader("")});
		
		Assert.assertNotNull(chain);
	}
	
	/**
	 * @testng.expected-exceptions value = "java.lang.IllegalArgumentException"
	 */
	public void createByNullArrayShouldThrowException() {
			
		ResourceLoaderChain chain = new ResourceLoaderChain((ResourceLoader[])null);
		
		Assert.assertNotNull(chain);
	}
	
	public void createByListShouldReturnNotNull() {
		
		ResourceLoaderChain chain = new ResourceLoaderChain(Arrays.asList(new ResourceLoader[]{new ClassPathResourceLoader("")}));
		
		Assert.assertNotNull(chain);
	}
	
	/**
	 * @testng.expected-exceptions value = "java.lang.IllegalArgumentException"
	 */
	public void createByNullListShouldThrowException() {
			
		ResourceLoaderChain chain = new ResourceLoaderChain((List)null);
		
		Assert.assertNotNull(chain);
	}

	/**
	 * @testng.expected-exceptions value = "java.lang.IllegalArgumentException"
	 */
	public void createByNullElementListShouldThrowException() {
			
		ResourceLoaderChain chain = new ResourceLoaderChain(Arrays.asList(new ResourceLoader[]{null, new ClassPathResourceLoader("")}));
		
		Assert.assertNotNull(chain);
	}
	
	/**
	 * @testng.expected-exceptions value = "java.lang.IllegalArgumentException"
	 */
	public void createByBadElementListShouldThrowException() {
			
		ResourceLoaderChain chain = new ResourceLoaderChain(Arrays.asList(new Object[]{new String("babla"), new ClassPathResourceLoader("")}));
		
		Assert.assertNotNull(chain);
	}
	
	public void loadResourceShouldReturnNotNull() {
		
		Resource expected = new Resource() {
			
			public InputStream getInputStream() {
				return null;
			}
			
			public String getInfo() {
				return null;
			}
			
			public void close() {
			}
		};
		
		ResourceLoader loader = (ResourceLoader)EasyMock.createNiceMock(ResourceLoader.class);
		EasyMock.expect(loader.loadResource("id")).andReturn(expected).anyTimes();
		EasyMock.replay(new Object[]{loader});
		
		ResourceLoaderChain chain = new ResourceLoaderChain();
		chain.addResourceLoader(loader);
		
		Resource testValue = chain.loadResource("id");
		Assert.assertEquals(testValue, expected);
	}
	
	public void loadResourceShouldReturnFoundResource() {
		
		Resource expected = new Resource() {
			
			public InputStream getInputStream() {
				return null;
			}
			
			public String getInfo() {
				return null;
			}
			
			public void close() {
			}
		};
		
		ResourceLoader notFoundLoader = (ResourceLoader)EasyMock.createNiceMock(ResourceLoader.class);
		EasyMock.expect(notFoundLoader.loadResource("id")).andThrow(new ResourceNotFoundException("id"));

		ResourceLoader foundLoader = (ResourceLoader)EasyMock.createNiceMock(ResourceLoader.class);
		EasyMock.expect(foundLoader.loadResource("id")).andReturn(expected).anyTimes();

		EasyMock.replay(new Object[]{foundLoader, notFoundLoader});
		
		ResourceLoaderChain chain = new ResourceLoaderChain();
		chain.addResourceLoader(notFoundLoader);
		chain.addResourceLoader(foundLoader);
		
		Resource testValue = chain.loadResource("id");
		Assert.assertEquals(testValue, expected);
	}
	
	public void loadResourceShouldReturnFirstResource() {
		
		Resource firstResource = new Resource() {
			
			public InputStream getInputStream() {
				return null;
			}
			
			public String getInfo() {
				return null;
			}
			
			public void close() {
			}
		};
		
		Resource secondResource = new Resource() {
			
			public InputStream getInputStream() {
				return null;
			}
			
			public String getInfo() {
				return null;
			}
			
			public void close() {
			}
		};
		
		ResourceLoader firstLoader = (ResourceLoader)EasyMock.createNiceMock(ResourceLoader.class);
		EasyMock.expect(firstLoader.loadResource("id")).andReturn(firstResource).anyTimes();

		ResourceLoader secondLoader = (ResourceLoader)EasyMock.createNiceMock(ResourceLoader.class);
		EasyMock.expect(secondLoader.loadResource("id")).andReturn(secondResource).anyTimes();

		EasyMock.replay(new Object[]{firstLoader, secondLoader});
		
		ResourceLoaderChain chain = new ResourceLoaderChain();
		chain.addResourceLoader(firstLoader);
		chain.addResourceLoader(secondLoader);
		
		Resource testValue = chain.loadResource("id");
		Assert.assertEquals(testValue, firstResource);
	}

}
