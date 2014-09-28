/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.Document;

import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class DefaultDocumentRenderer implements DocumentRenderer {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultDocumentRenderer.class);
		
	private RendererGroupResolver rendererGroupResolver;

	public DefaultDocumentRenderer() {
		// Empty
	}
	
	public DefaultDocumentRenderer(RendererGroupResolver rendererGroupResolver) {
		this.rendererGroupResolver = rendererGroupResolver;
	}

	public void setDelegate(RendererGroupResolver rendererGroupResolver) {
		this.rendererGroupResolver = rendererGroupResolver;
	}

	public RenderedDocument renderDocument(Document document, WNGDevice device) throws RenderingException {
		// Add RendererGroup
		RendererGroup rendererGroup = rendererGroupResolver.resolveRendererGroup(device);
		
		RenderingContext context = new RenderingContext(device, rendererGroup);				

		ComponentRenderer documentRenderer = rendererGroup.getRenderer(document);
		
		String contentType = resolveContentType(document, context);
		String markup = documentRenderer.renderComponent(document, context).getMarkup();
		
		RenderedDocument renderedDocument = new RenderedDocument(contentType, markup);
		logger.debug("Rendered Document: \n{}", renderedDocument);

		return renderedDocument;
	}

	protected String resolveContentType(Document document, RenderingContext context) {
		
		// Build contentType
		StrBuilder contentTypeBuilder = new StrBuilder();
		contentTypeBuilder.append(context.getDevice().getPreferredMimeType());
		contentTypeBuilder.append("; charset=").append(document.getEncoding());
		String contentType = contentTypeBuilder.toString();

		return contentType;
	}
}
