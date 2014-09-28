/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Component;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Transformer;

/**
 * This {@link ComponentRenderer} implementation will render first all of the
 * component children and then the component using the rendered children.
 * 
 * @author Filippo De Luca
 * @version $Id$
 */
public class CompositeComponentRenderer extends AbstractComponentRenderer {

	public CompositeComponentRenderer() {
		// Empty
	}

	public CompositeComponentRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}

	public RenderedComponent renderComponent(Component component,
			RenderingContext renderingContext) {

		List/* String */renderedChildren = renderChildren(component,
				renderingContext);
		RenderedComponent renderedComponent = doRenderComponent(component,
				renderedChildren, renderingContext);

		return renderedComponent;
	}

	protected List/* RenderedComponent */renderChildren(Component component,
			RenderingContext context) {
		List/* RenderedComponent */renderedChildren = new LinkedList();

		Iterator childrenIterator = IteratorUtils.getIterator(component
				.getChildren());
		while (childrenIterator.hasNext()) {
			Component child = (Component) childrenIterator.next();
			ComponentRenderer childRenderer = getComponentRenderer(child, context);
			RenderedComponent renderedChild = childRenderer.renderComponent(
					child, context);
			renderedChildren.add(renderedChild);
		}

		return renderedChildren;
	}

	protected RenderedComponent doRenderComponent(Component component,
			List/* RenderedComponent */children,
			RenderingContext renderingContext) {

		// FIXME Use RenderedComponent in template!!!
		Collection/* String */childrenMarkups = CollectionUtils.collect(children,
				new Transformer() {
					public Object transform(Object input) {
						RenderedComponent renderedChild = (RenderedComponent) input;
						return renderedChild.getMarkup();
					}
				});

		Map/* String,Object */arguments = new HashMap();
		arguments.put("children", childrenMarkups);

		String markup = generateMarkup(component, arguments);
		return new DefaultRenderedComponent(markup);
	}

}
