/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.TableColumn;
import net.sourceforge.wurfl.wng.component.TableHeader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class TableColumnTag extends ComponentTag {
	
	private static final long serialVersionUID = 10L;
	
	private static final Log logger = LogFactory.getLog(TableColumnTag.class);
	
	private String label;
	
	private String short_label;
	
	private int colspan = 1;

	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setShort_label(String short_label) {
		this.short_label = short_label;
	}
	
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	
	protected Component createComponent() {

		return new TableColumn();
	}
	
	protected void configureComponent(Component component) throws JspException {

		super.configureComponent(component);
		
		TableColumn column = (TableColumn)component;
		
		column.setLabel(label);
		column.setShortLabel(short_label);
		column.setColspan(colspan);
	}
	
	protected void addComponentToParent(Component component)
			throws JspException {
		
		TableColumn column = (TableColumn)component;
		TableHeader header = (TableHeader)getParentComponent();

		header.addColumn(column);
		logger.trace("Added column: " + column + " to header");
	}
}
