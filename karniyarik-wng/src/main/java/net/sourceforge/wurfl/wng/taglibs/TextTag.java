/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Header;
import net.sourceforge.wurfl.wng.component.Text;

import org.apache.commons.lang.StringUtils;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: TextTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class TextTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	private String text;

	public void setText(String text) {
		this.text = text;
	}

	protected Component createComponent() {

		return new Text();
	}
	
	protected void configureComponent(Component component) throws JspException {

		super.configureComponent(component);
		
		Text textComponent = (Text)component;
		
		if(StringUtils.isNotBlank(text)){
			textComponent.setContent(text);
		}
	}
	
	protected void postConfigureComponent(Component component) throws JspException {

		Text textComponent = (Text)component;
		
		boolean isContentInbody = getBodyContent() != null && StringUtils.isNotBlank(getBodyContent().getString());
		if(isContentInbody) {
			textComponent.setContent(getBodyContent().getString().trim());
		}
		
		super.postConfigureComponent(component);
	}
	
	protected void addComponentToParent(Component component)
			throws JspException {
		Component parentComponent = getParentComponent();
		Text textComponent = (Text) component;
		
		if(parentComponent instanceof Header){
			Header parentHeader = (Header)parentComponent;
			parentHeader.setText(textComponent);
		} 
		
		else {
			super.addComponentToParent(component);
		}
	}
}
