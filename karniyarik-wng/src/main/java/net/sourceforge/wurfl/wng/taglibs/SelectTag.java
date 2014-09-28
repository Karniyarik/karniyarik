/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Select;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: SelectTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class SelectTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	private String name;

	private String disabled;

	private String size;

	private String title;

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
		
	protected Component createComponent() {

		return new Select(name);
	}
	
	protected void configureComponent(Component component) throws JspException{

		super.configureComponent(component);

		Select select = (Select)component;
		select.setDisabled(disabled);
		select.setSize(size);
		select.setTitle(title);	
	}

}
