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
import net.sourceforge.wurfl.wng.component.TableHeader;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class TableHeaderTag extends ComponentTag {
	
	private static final long serialVersionUID = 10L;

	protected Component createComponent() {

		return new TableHeader();
	}
	
	protected void addComponentToParent(Component component)
			throws JspException {
		
		TableHeader header = (TableHeader)component;
		Table table = (Table)getParentComponent();
		
		table.setHeader(header);
	}

}
