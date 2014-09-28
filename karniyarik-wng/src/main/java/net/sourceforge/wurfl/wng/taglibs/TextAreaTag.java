/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.TextArea;

import org.apache.commons.lang.StringUtils;

/**
 * @author Filippo de Luca
 * @version $Id$
 */
public class TextAreaTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	private String value;

	private String name;

	private String title;

	private int rows, cols;

	public void setValue(String value) {
		this.value = value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}


	protected Component createComponent() {

		return new TextArea();
	}

	protected void configureComponent(Component component) throws JspException {

		super.configureComponent(component);

		TextArea textArea = (TextArea) component;

		textArea.setName(name);
		textArea.setRows(rows);
		textArea.setCols(cols);

		textArea.setTitle(title);
		if (StringUtils.isNotBlank(value)) {
			textArea.setValue(value);
		}
	}

	protected void postConfigureComponent(Component component)
			throws JspException {

		TextArea textArea = (TextArea) component;

		boolean isValueInbody = getBodyContent() != null
				&& StringUtils.isNotBlank(getBodyContent().getString());
		if (isValueInbody) {
			textArea.setValue(getBodyContent().getString().trim());
		}

		super.postConfigureComponent(component);
	}
}
