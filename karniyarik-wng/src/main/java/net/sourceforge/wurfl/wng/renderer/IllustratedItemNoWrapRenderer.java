/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.IllustratedItemNoWrap;

import org.apache.commons.lang.Validate;

public class IllustratedItemNoWrapRenderer extends AbstractComponentRenderer {

	public IllustratedItemNoWrapRenderer() {
		// Empty
	}

	public IllustratedItemNoWrapRenderer(MarkupWriter writer) {
		super(writer);
	}

	public RenderedComponent renderComponent(Component component,
			RenderingContext context) {
		
		Validate.isTrue(component instanceof IllustratedItemNoWrap);
		
		IllustratedItemNoWrap illustratedItemNoWrap = (IllustratedItemNoWrap) component;
		
		RenderedComponent renderedLeftPart = renderLeftPart(illustratedItemNoWrap, context);
		RenderedComponent renderedRightPart = renderRightPart(illustratedItemNoWrap, context);
		
		Map/*String, Object*/ arguments = new HashMap();
		arguments.put("leftPart", renderedLeftPart);
		arguments.put("rightPart", renderedRightPart);
		
		String markup = generateMarkup(component, arguments);
		return new DefaultRenderedComponent(markup);

	}

	private RenderedComponent renderRightPart(IllustratedItemNoWrap illustratedItemNoWrap, RenderingContext context) {
		
		ComponentRenderer renderer = getComponentRenderer(illustratedItemNoWrap.getRightPart(), context);
		
		return renderer.renderComponent(illustratedItemNoWrap.getRightPart(), context);
	}

	private RenderedComponent renderLeftPart(IllustratedItemNoWrap illustratedItemNoWrap, RenderingContext context) {
		
		ComponentRenderer renderer = getComponentRenderer(illustratedItemNoWrap.getLeftPart(), context);
		
		return renderer.renderComponent(illustratedItemNoWrap.getLeftPart(), context);
	}

}
