/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.template.st;

import net.sourceforge.wurfl.wng.renderer.template.Template;

import org.antlr.stringtemplate.StringTemplate;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class STTemplate implements Template {
	
	private final StringTemplate delegate;
	
	public STTemplate(StringTemplate delegate) {
		this.delegate = delegate;
	}

	public StringTemplate getDelegate() {
		return delegate;
	}

}
