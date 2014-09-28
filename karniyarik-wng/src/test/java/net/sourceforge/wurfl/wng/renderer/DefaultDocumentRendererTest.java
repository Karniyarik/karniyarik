/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import junit.framework.Assert;
import net.sourceforge.wurfl.core.MarkUp;
import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.Document;

import org.easymock.classextension.EasyMock;

/**
 * 
 * @author fanta
 * 
 * @testng.test groups = 'func'
 */
public class DefaultDocumentRendererTest {

	private DocumentRenderer documentRenderer;

	/**
	 * @testng.before-class
	 */
	protected void createDocumentRenderer() {
		RendererGroupResolver rendererGroupResolver = new DefaultRendererGroupResolver();
		this.documentRenderer = new DefaultDocumentRenderer(
				rendererGroupResolver);
	}

	public void shouldRenderTheDocument() throws RenderingException {
		Document document = new Document();
		Assert.assertNotNull(document.getHead());
		WNGDevice device = (WNGDevice) EasyMock.createNiceMock(WNGDevice.class);

		// EasyMock.expect(resourceFinder.find(id)).andReturn(templateResource);
		EasyMock.expect(device.getMarkUp()).andReturn(MarkUp.XHTML_ADVANCED);
		EasyMock.expect(device.getPreferredMimeType()).andReturn("");
		EasyMock.replay(new Object[] { device });

		documentRenderer.renderDocument(document, device);
	}

}
