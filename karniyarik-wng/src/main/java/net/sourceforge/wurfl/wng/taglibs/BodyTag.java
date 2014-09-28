/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Body;
import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Document;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: BodyTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class BodyTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	protected Component createComponent() {

		return new Body();
	}
	
	protected void addComponentToParent(Component component)
			throws JspException {

		Document document = (Document)getParentComponent();
		document.setBody((Body)component);
		
		logger.debug("Added body to document");
	}

}
