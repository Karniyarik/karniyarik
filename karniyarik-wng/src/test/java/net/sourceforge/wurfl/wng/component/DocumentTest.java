/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
/**
 * 
 */
package net.sourceforge.wurfl.wng.component;

import org.easymock.classextension.EasyMock;
import org.testng.Assert;

/**
 * @author fanta
 * @testng.test groups ="unit"
 *  
 */
public class DocumentTest extends BaseTest {
	
	private Document document;
	
	/**
	 * @testng.before-class
	 */
	public void createDocument() {
		this.document = new Document();
	}
	
	
	public void shouldBePossibleToSetTheHead() {
		Head head = (Head) EasyMock.createMock(Head.class);
		this.document.setHead(head);
		Assert.assertEquals(head, document.getHead());
	}
	
	
	public void shouldBePossibleToSetTheBody() {
		Body body = (Body) EasyMock.createMock(Body.class);
		this.document.setBody(body);
		Assert.assertEquals(body, document.getBody());
	}
	
	public void shoudBePossibleToCreateDocumentWithEncoding() {
		Document document = new Document("UTF-8");
		Assert.assertEquals("UTF-8", document.getEncoding());
	}
	
	public void shoudHaveUTF8AsDefaultEncoding() {
		Assert.assertEquals("UTF-8", document.getEncoding());
	}
		
	/**
	 * @testng.expected-exceptions value = "net.sourceforge.wurfl.wng.component.InvalidContainmentException"
	 */
	public void shouldLaounchExceptionIfAddingBodyToHead() throws Exception {
		this.document.addToHead(new Body());
	}
	
	public void shoudBePossibleToAddAJavascriptHook() {
		String src = "script.js";
		String type = "text/javascript";
		this.document.addScript(src, type);
	}


	protected Object[][] componentsProvider() throws Exception {
		
		
		Document d5 = new Document();
		d5.setId("DoQment");
		Document d6 = new Document();
		d6.setId("DoQment");
		
		
		return new Object[][]{{new Document(),new Document()},{new Document(Document.DEFAULT_ENCODING),new Document(Document.DEFAULT_ENCODING)},{d5,d6}};
	}


	protected Object[][] differentComponentsProvider() throws Exception {
		
		Document d3 = new Document();
		d3.setId("DoQment");
		Document d4 = new Document();
		d4.setId("MsWord");
		return new Object[][]{{new Document(Document.DEFAULT_ENCODING),new Document("UNICODE")},{d3,d4}};
	}
	
}
