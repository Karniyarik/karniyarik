/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.TableCell;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class TableCellRenderer extends CompositeComponentRenderer {
	
	public TableCellRenderer() {
		// Empty
	}
	
	public TableCellRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}
	
	public RenderedComponent renderComponent(Component component,
			RenderingContext renderingContext) {

		TableCell cell = (TableCell)component;
		
		Collection/*RenderedComponent*/ children = renderChildren(component, renderingContext);

		Map/* String,Object */arguments = new HashMap();
		arguments.put("children", children);
		arguments.put("component", component);
		arguments.put("useColspan", Boolean.valueOf(cell.getColspan()>1));
		arguments.put("startingColumn", cell.getStartingColumn());
		arguments.put("column", cell.getColumn());
		arguments.put("hasChildren", Boolean.valueOf(!cell.getChildren().isEmpty()));

		String markup = generateMarkup(component, arguments);
		return new DefaultRenderedComponent(markup);

	}
}
