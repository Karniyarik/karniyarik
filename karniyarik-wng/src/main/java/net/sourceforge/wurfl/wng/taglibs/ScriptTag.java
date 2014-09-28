/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
/*
 * $Id: ScriptTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Script;

import org.apache.commons.lang.StringUtils;

/**
 * @author Filippo De Luca
 * @version $Id: ScriptTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class ScriptTag extends ComponentTag {

	private static final long serialVersionUID = 10L;
	
	private String type;
	
	private String src;
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	
	protected Component createComponent() {

		return new Script();
	}
	
	protected void configureComponent(Component component) throws JspException {
		
		super.configureComponent(component);
		
		Script script = (Script)component;
		
		if(StringUtils.isNotBlank(type)){
			script.setType(type);
		}
		
		if(StringUtils.isNotBlank(src)){
			script.setSrc(src);
		}
	}
	
	protected void postConfigureComponent(Component component) throws JspException {
				
		Script script = (Script)component;
		if(getBodyContent()!=null){
			String content = StringUtils.trimToNull(getBodyContent().getString());
			
			script.setContent(content);
		}
		
		super.postConfigureComponent(component);
	}

}
