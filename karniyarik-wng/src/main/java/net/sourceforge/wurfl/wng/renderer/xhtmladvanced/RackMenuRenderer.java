/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.xhtmladvanced;

import java.util.List;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.renderer.CompositeComponentRenderer;
import net.sourceforge.wurfl.wng.renderer.MarkupWriter;
import net.sourceforge.wurfl.wng.renderer.RenderedComponent;
import net.sourceforge.wurfl.wng.renderer.RenderingContext;

/**
 * 
 * @author Asres Gizaw Fantayeneh
 * @version $Id$
 */
public class RackMenuRenderer extends CompositeComponentRenderer {


	public RackMenuRenderer() {
		// Empty
	}

	public RackMenuRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}

	public RenderedComponent doRenderComponent(Component component,
			List children, RenderingContext renderingContext) {

		if (!isEvenChildrenNumber(children)) {
			children.add(RenderedComponent.EMPTY);
		}

		return super.doRenderComponent(component, children, renderingContext);
	}

	private boolean isEvenChildrenNumber(List children) {
		return (children.size() % 2 == 0);
	}

}
