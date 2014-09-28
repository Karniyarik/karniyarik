/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Head;
import net.sourceforge.wurfl.wng.component.Title;

import org.apache.commons.lang.StringUtils;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: TitleTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class TitleTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	private String text;

	private String align;

	public void setText(String text) {
		this.text = text;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	protected Component createComponent() {
		return new Title(text);
	}
	
	protected void configureComponent(Component component) throws JspException {
		
		super.configureComponent(component);
		
		Title title = (Title) component;
		if (title != null) {
			title.getStyle().addProperty("text-align", align);
		}
	}

	protected void addComponentToParent(Component component)
			throws JspException {
		
		Title title = (Title)component;

		if (getParentComponent() instanceof Head) {
			Head head = (Head) getParentComponent();
			head.setTitle(title.getContent());
		}
		else{
			super.addComponentToParent(component);
		}
	}

	protected void postConfigureComponent(Component component) throws JspException {
		Title title = (Title) component;
		
		if(title !=null){
			
			boolean isContentInBody = getBodyContent() != null && StringUtils.isNotBlank(getBodyContent().getString());
			if(isContentInBody){
				title.setContent(getBodyContent().getString().trim());
			}
		}

	}

}
