/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.TableColumn;
import net.sourceforge.wurfl.wng.component.TableHeader;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.Validate;

/**
 * 
 * @author Filippo De Luca
 * @version $Id$
 */
public class TableHeaderRenderer extends AbstractComponentRenderer {

	public TableHeaderRenderer() {
		// empty
	}

	public TableHeaderRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}

	public RenderedComponent renderComponent(Component component,
			RenderingContext context) {

		Validate.notNull(component);
		Validate.isTrue(component instanceof TableHeader);

		TableHeader header = (TableHeader) component;

		List/* RenderedComponent */renderedColumns = new ArrayList();

		for (Iterator it = IteratorUtils.getIterator(header.getColumns()); it
				.hasNext();) {
			TableColumn column = (TableColumn) it.next();
			RenderedComponent renderedColumn = getComponentRenderer(column, context).renderComponent(column, context);
			renderedColumns.add(renderedColumn);
		}

		Map/*String,Object*/arguments = new HashMap();
		arguments.put("columns", renderedColumns);

		String markup = generateMarkup(component, arguments);
		return new DefaultRenderedComponent(markup);
	}

}
