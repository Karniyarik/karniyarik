/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Form;

import org.apache.commons.lang.StringUtils;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: FormTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class FormTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	private String action;

	private String method;
	
	public void setAction(String action) {
		this.action = action;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	protected Component createComponent() {

		return new Form();
	}
	
	protected void configureComponent(Component component) throws JspException {
		
		super.configureComponent(component);
		
		Form form = (Form)component;
		
		String encodedAction = getBuildingContext().encodeURLWhenRequested(action);
		form.setAction(encodedAction);
		
		
		if(StringUtils.isNotBlank(method)) {
			form.setMethod(method);
		}
		
	}

}
