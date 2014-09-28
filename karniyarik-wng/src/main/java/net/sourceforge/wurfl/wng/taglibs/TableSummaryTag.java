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

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class TableSummaryTag extends TableCellTag {

	private static final long serialVersionUID = 10L;
		
	protected void configureComponent(Component component) throws JspException {
		super.configureComponent(component);
		
		TableCell cell = (TableCell)component;
		cell.setSummary(true);
	}

}
