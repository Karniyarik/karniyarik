/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.template;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.renderer.MarkupWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class TemplateMarkupWriter implements MarkupWriter {

	private TemplateProcessor templateProcessor;

	private TemplateProvider templateProvider;

	private static final Logger logger = LoggerFactory
			.getLogger(TemplateMarkupWriter.class);

	public TemplateMarkupWriter() {
		// empty
	}

	public TemplateMarkupWriter(TemplateProvider templateProvider, TemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
		this.templateProvider = templateProvider;
	}

	public void setTemplateProcessor(TemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
	}

	public void setTemplateProvider(TemplateProvider templateProvider) {
		this.templateProvider = templateProvider;
	}

	public String writeMarkup(Component component, Map/*String,Object*/ arguments) {
	
		logger.trace("Writing markup for component: {} using arguments: {}", component, arguments);
		
		Map/* String,Object */localArguments = new HashMap();
		if(arguments!=null){
			localArguments.putAll(arguments);
		}
		localArguments.put("component", component);

		Template template = templateProvider.get(new ComponentRequest(component));
		String markup = templateProcessor.process(template, localArguments);
		
		return markup;
	}

}
