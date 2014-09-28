/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.template.st;


/**
 * 
 * @author fanta
 * @testng.test groups = 'func'
 */
public class STTemplateProviderTest {

	/*
	private static final String BASE_CLASS_PATH = "net/sourceforge/wurfl/wng";
	
	public void shoudReturnATemplateForATemplateRequest() {
		ResourceIdBuilder resourceIdBuilder = new StringTemplateIdBuilder();
		ResourceFinder resourceFinder = new ClassPathResourceFinder();
		
		STTemplateProvider stTemplateProvider = new STTemplateProvider(resourceFinder, resourceIdBuilder);
		TemplateRequest templateRequest = new DefaultTemplateRequest("xhtml", "document");
		StringTemplate st = (StringTemplate) stTemplateProvider.get(templateRequest);		
		Assert.assertNotNull(st);
	}
	
	public void shoudAskForResourceFinderToFindTheTemplate() {
		
		//TemplateRequest templateRequest = (TemplateRequest) EasyMock.createNiceMock(TemplateRequest.class);
		TemplateRequest templateRequest = new DefaultTemplateRequest("xhtml", "document");
		Resource templateResource = (Resource) EasyMock.createNiceMock(Resource.class);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("net/sourceforge/wurfl/wng/renderer/st/xhtml/document.st");
		
		System.out.println("is: " + is);
		ResourceFinder resourceFinder = (ResourceFinder) EasyMock.createStrictMock(ResourceFinder.class);
		ResourceIdBuilder resourceIdBuilder = (ResourceIdBuilder) EasyMock.createStrictMock(ResourceIdBuilder.class);
		String id = "xhtml/document.st";
		
		EasyMock.expect(resourceFinder.find(id)).andReturn(templateResource);
		EasyMock.expect(resourceIdBuilder.build(templateRequest)).andReturn("xhtml/document.st");
		EasyMock.expect(templateResource.getInputStream()).andReturn(is);
		EasyMock.replay(new Object[]{resourceFinder, resourceIdBuilder});
		
		STTemplateProvider stProvider = new STTemplateProvider(resourceFinder, resourceIdBuilder);
		stProvider.get(templateRequest);
		
		EasyMock.verify(new Object[]{resourceFinder});
		
	}
	
	*/
}
