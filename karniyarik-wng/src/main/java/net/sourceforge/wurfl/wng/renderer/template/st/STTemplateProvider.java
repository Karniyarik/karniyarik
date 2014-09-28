/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.template.st;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;

import net.sourceforge.wurfl.wng.renderer.template.Template;
import net.sourceforge.wurfl.wng.renderer.template.TemplateProvider;
import net.sourceforge.wurfl.wng.renderer.template.TemplateRequest;
import net.sourceforge.wurfl.wng.resource.Resource;
import net.sourceforge.wurfl.wng.resource.ResourceLoader;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class STTemplateProvider implements TemplateProvider {

	private static final Logger logger = LoggerFactory
			.getLogger(STTemplateProvider.class);

	private ResourceLoader templatesLoader;

	private transient StringTemplateGroup commonGroup;

	private String groupName;

	public STTemplateProvider(ResourceLoader templatesLoader, String groupName) {
		this.templatesLoader = templatesLoader;
		this.groupName = groupName;
	}

	public Template get(TemplateRequest request) {

		Template template;

		String templateLocation = resolveTemplateLocation(request);
		Resource templateResource = templatesLoader.loadResource(templateLocation);
		try {
			
			StringTemplate st = new StringTemplate(getCommonGroup(), IOUtils
					.toString(templateResource.getInputStream(), "UTF-8"));
			template = new STTemplate(st);
		} catch (Exception ex) {
			logger.error("Error parsing template: {}", templateLocation);
			throw new RuntimeException(ex);
		}

		return template;
	}

	/**
	 * @param request
	 * @return
	 */
	private String resolveTemplateLocation(TemplateRequest request) {
		StrBuilder templateLocationBuilder = new StrBuilder();
		templateLocationBuilder.append(groupName).append("/")
				.append(request.getTemplateName()).append(".st");
		String templateLocation = templateLocationBuilder.toString()
				.toLowerCase(Locale.ENGLISH);
		
		logger.debug("Resolved template location: {}", templateLocation);
		
		return templateLocation;
	}

	protected StringTemplateGroup getCommonGroup() {
		if (commonGroup == null) {

			StrBuilder templateLocationBuilder = new StrBuilder();
			templateLocationBuilder.append(groupName).append("/")
					.append("common").append(".stg");
			String templateLocation = templateLocationBuilder.toString()
					.toLowerCase();

			Reader commonReader;
			try {
				commonReader = new InputStreamReader(templatesLoader
						.loadResource(templateLocation).getInputStream(),
						"UTF-8");
				commonGroup = new StringTemplateGroup(commonReader,
						DefaultTemplateLexer.class, null);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

		return commonGroup;
	}

}
