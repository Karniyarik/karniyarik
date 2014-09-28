/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Table;
import net.sourceforge.wurfl.wng.component.TableHeader;
import net.sourceforge.wurfl.wng.component.TableRow;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;

import edu.emory.mathcs.backport.java.util.LinkedList;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class TableRenderer extends AbstractComponentRenderer {
	
	public TableRenderer() {
		// empty
	}
	
	public TableRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}

	public RenderedComponent renderComponent(Component component,
			RenderingContext context) {
		
		Table table = (Table)component;
		
		// Render header
		RenderedComponent renderedHeader;
		if(table.getHeader()!=null) {
			renderedHeader = renderHeader(table.getHeader(), context);
		}
		else{
			renderedHeader = new DefaultRenderedComponent(StringUtils.EMPTY);
		}

		// Render rows
		List/*RenderedComponent*/ renderedRows = new LinkedList();
		for(Iterator it = IteratorUtils.getIterator(table.getRows()); it.hasNext();) {
			TableRow row = (TableRow)it.next();
			RenderedComponent renderedRow = renderRow(row, context);
			renderedRows.add(renderedRow);
		}
		
		Map/* String,Object */arguments = new HashMap();
		arguments.put("component", table);
		arguments.put("header", renderedHeader);
		arguments.put("rows", renderedRows);
		
		String markup = generateMarkup(component, arguments);
		return new DefaultRenderedComponent(markup);
	}
	
	private RenderedComponent renderHeader(TableHeader header, RenderingContext context) {
		
		assert header != null;
		
		return getComponentRenderer(header, context).renderComponent(header, context);
	}
	
	private RenderedComponent renderRow(TableRow row, RenderingContext context) {
		
		assert row != null;
		
		return getComponentRenderer(row, context).renderComponent(row, context);
	}

}
