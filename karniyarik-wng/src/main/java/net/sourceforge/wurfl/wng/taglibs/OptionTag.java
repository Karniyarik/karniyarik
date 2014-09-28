/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Option;

import org.apache.commons.lang.StringUtils;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: OptionTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class OptionTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	private String value;

	private String selected;

	private String label;

	public void setLabel(String label) {
		this.label = label;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	protected Component createComponent() {

		return new Option();
	}
	
	protected void configureComponent(Component component) throws JspException {
		
		super.configureComponent(component);
		
		Option option = (Option)component;
		option.setValue(value);
		option.setSelected(selected);
		
		if(StringUtils.isNotBlank(label)) {
			option.setLabel(label);
		}
	}
	
	protected void postConfigureComponent(Component component) throws JspException {
				
		Option option = (Option)component;
		
		// The body content overrides the label attribute
		boolean isLabelInBody = getBodyContent() != null && StringUtils.isNotBlank(getBodyContent().getString());
		if (isLabelInBody) {
			option.setLabel(getBodyContent().getString().trim());
		}
		
		super.postConfigureComponent(component);

		
	}

}
