/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Body;
import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Document;
import net.sourceforge.wurfl.wng.component.Head;

import org.apache.commons.lang.Validate;

public class DocumentComponentRenderer extends AbstractComponentRenderer {


	public DocumentComponentRenderer() {
		// Empty
	}

	public DocumentComponentRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}

	public RenderedComponent renderComponent(Component component,
			RenderingContext renderingContext) {

		Validate.isTrue(component instanceof Document,
				"component must be a Documet");
		Document document = (Document) component;

		List/* RenderedComponent */children = renderDocumentHeadAndBody(
				document, renderingContext);

		Map/* String,Object */arguments = new HashMap();
		arguments.put("children", children);

		String markup = getMarkupWriter().writeMarkup(component, arguments);
		return new DefaultRenderedComponent(markup);
	}

	private List renderDocumentHeadAndBody(Document document,
			RenderingContext renderingContext) {
		List children = new ArrayList();
		RenderedComponent head = renderHead(document.getHead(),
				renderingContext);
		RenderedComponent body = renderBody(document.getBody(),
				renderingContext);

		children.add(head);
		children.add(body);

		return children;
	}

	private RenderedComponent renderHead(Head head, RenderingContext context) {

		ComponentRenderer renderer = getComponentRenderer(head, context);
		return renderer.renderComponent(head, context);
	}

	private RenderedComponent renderBody(Body body, RenderingContext context) {
		ComponentRenderer renderer = getComponentRenderer(body, context);
		return renderer.renderComponent(body, context);
	}

}
