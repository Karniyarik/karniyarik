/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Meta;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: MetaTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class MetaTag extends ComponentTag {

	private static final long serialVersionUID = 1L;

	private String name;

	private String content;

	private String scheme;

	private String httpEquiv;

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public void setHttpEquiv(String httpEquiv) {
		this.httpEquiv = httpEquiv;
	}
	
	protected Component createComponent() {

		return new Meta(name, content, scheme, httpEquiv);
	}

}
