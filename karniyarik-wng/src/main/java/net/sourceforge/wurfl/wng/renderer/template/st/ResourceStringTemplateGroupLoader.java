/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.template.st;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sourceforge.wurfl.wng.resource.Resource;
import net.sourceforge.wurfl.wng.resource.ResourceLoader;

import org.antlr.stringtemplate.PathGroupLoader;
import org.antlr.stringtemplate.StringTemplateErrorListener;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class ResourceStringTemplateGroupLoader extends PathGroupLoader {
	
	private ResourceLoader loader;
	
	private String encoding = "UTF-8";
	
	public ResourceStringTemplateGroupLoader(ResourceLoader loader, StringTemplateErrorListener errorListener) {
		super(errorListener);
		this.loader = loader;
	}

	protected BufferedReader locate(String name) throws IOException {
		
		BufferedReader reader = null;
		
		Resource resource = loader.loadResource(name);
		if(resource != null){
			reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), encoding));
		}

		return reader;
	}



}
