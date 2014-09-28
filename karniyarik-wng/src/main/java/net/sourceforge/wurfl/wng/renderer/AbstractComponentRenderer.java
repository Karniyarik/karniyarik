/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Body;
import net.sourceforge.wurfl.wng.component.Component;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.Validate;


/**
 * @author Filippo De Luca
 * @version $Id$
 *
 */
public abstract class AbstractComponentRenderer implements ComponentRenderer {
	
	private MarkupWriter markupWriter;
	
	public AbstractComponentRenderer() {
		// Empty
	}

	public AbstractComponentRenderer(MarkupWriter markupWriter) {
		this.markupWriter = markupWriter;
	}

	public void setMarkupWriter(MarkupWriter markupWriter) {
		this.markupWriter = markupWriter;
	}
	
	public MarkupWriter getMarkupWriter() {
		return markupWriter;
	}
	
	protected String generateMarkup(Component component, Map/*String,Object*/ arguments) {
		
		Map localArguments = new HashMap();
		if(arguments != null) {
			localArguments.putAll(arguments);
		}
		
		boolean insideBody = component.getParent() instanceof Body;
		if (insideBody) {
			arguments.put("insideBody", BooleanUtils.toBooleanObject(insideBody));
		}

		String markup = getMarkupWriter().writeMarkup(component, arguments);
		
		return markup;
	}
	
	protected ComponentRenderer getComponentRenderer(Component component, RenderingContext context) {

		Validate.notNull(component, "The component must be not null");
		Validate.notNull(context, "The context must be not null");
		
		
		return context.getRenderer(component);
	}

}
