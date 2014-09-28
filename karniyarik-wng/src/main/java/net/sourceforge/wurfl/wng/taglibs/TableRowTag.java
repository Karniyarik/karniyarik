/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Table;
import net.sourceforge.wurfl.wng.component.TableRow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class TableRowTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	private static final Log logger = LogFactory.getLog(TableRowTag.class);
	
	protected Component createComponent() {

		return new TableRow();
	}
	
	protected void addComponentToParent(Component component)
			throws JspException {
		
		TableRow row = (TableRow)component;
		Table table = (Table)getParentComponent();
		
		table.addRow(row);
		logger.trace("Added row: " + row + " to table");
	}
}
