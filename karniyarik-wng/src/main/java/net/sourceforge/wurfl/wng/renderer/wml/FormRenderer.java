/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.wml;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Form;
import net.sourceforge.wurfl.wng.component.Hidden;
import net.sourceforge.wurfl.wng.renderer.CompositeComponentRenderer;
import net.sourceforge.wurfl.wng.renderer.DefaultRenderedComponent;
import net.sourceforge.wurfl.wng.renderer.MarkupWriter;
import net.sourceforge.wurfl.wng.renderer.RenderedComponent;
import net.sourceforge.wurfl.wng.renderer.RenderingContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.Validate;

/**
 * 
 * @author Asres Gizaw Fantayeneh
 * @version $Id$
 */
public class FormRenderer extends CompositeComponentRenderer {

	public FormRenderer() {
		// empty
	}

	public FormRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}

	protected RenderedComponent doRenderComponent(Component component,
			List children, RenderingContext context) {

		Validate.notNull(component);
		Validate.isTrue(component instanceof Form);

		// FIXME Use RenderedComponent in template!!!
		Collection/* String */childrenMarkups = CollectionUtils.collect(children,
				new Transformer() {
					public Object transform(Object input) {
						RenderedComponent renderedChild = (RenderedComponent) input;
						return renderedChild.getMarkup();
					}
				});

		Form form = (Form) component;

		Collection inputComponents = form.getInputComponents();
		Collection hiddenFields = CollectionUtils.select(inputComponents, PredicateUtils.instanceofPredicate(Hidden.class));
		Collection postFields = CollectionUtils.subtract(inputComponents, hiddenFields);
		
		Map/* String,Object */arguments = new HashMap();
		arguments.put("children", childrenMarkups);
		arguments.put("hiddenfields", hiddenFields);
		arguments.put("postfields", postFields);

		String markup = generateMarkup(component, arguments);
		return new DefaultRenderedComponent(markup);
	}

}
