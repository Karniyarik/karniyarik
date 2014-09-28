/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.TableCell;
import net.sourceforge.wurfl.wng.component.TableRow;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class TableCellTag extends ComponentTag {
	
	private static final long serialVersionUID = 10L;
	
	private static final Log logger = LogFactory.getLog(TableCellTag.class);

	private String text;
	
	private String short_text;

	private int colspan = 1;
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setShort_text(String short_text) {
		this.short_text = short_text;
	}
	
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
		
	protected Component createComponent() {
		
		return new TableCell();
	}
	
	protected void configureComponent(Component component) throws JspException {

		super.configureComponent(component);
		
		TableCell cell = (TableCell)component;
		
		if(StringUtils.isNotBlank(text)) {
			cell.setText(text);
		}
		
		if(StringUtils.isNotBlank(short_text)) {
			cell.setShortText(short_text);
		}
		
		cell.setColspan(colspan);		
	}

	protected void addComponentToParent(Component component)
			throws JspException {

		//super.addComponentToParent(component);
	
		TableCell cell = (TableCell)component;
		
		TableRow row = (TableRow)getParentComponent();
		row.addCell(cell);
		
		logger.trace("Added cell: " + cell + " to row");		
	}
	
}
