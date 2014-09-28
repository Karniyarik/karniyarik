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
import net.sourceforge.wurfl.wng.component.TableCell;
import net.sourceforge.wurfl.wng.component.TableRow;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.Validate;

import edu.emory.mathcs.backport.java.util.LinkedList;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class TableRowRenderer extends AbstractComponentRenderer {

	public TableRowRenderer() {
		// Empty
	}

	public TableRowRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}

	public RenderedComponent renderComponent(Component component,
			RenderingContext context) {

		Validate.notNull(component);
		Validate.isTrue(component instanceof TableRow);

		TableRow row = (TableRow) component;

		List/* RenderedComponent */renderedCells = new LinkedList();
		for (Iterator it = IteratorUtils.getIterator(row.getCells()); it
				.hasNext();) {
			TableCell cell = (TableCell) it.next();
			RenderedComponent renderedCell = getComponentRenderer(cell, context)
					.renderComponent(cell, context);
			renderedCells.add(renderedCell);
		}

		Map/* String,Object */arguments = new HashMap();
		arguments.put("component", row);
		arguments.put("cells", renderedCells);

		String markup = generateMarkup(component, arguments);
		return new DefaultRenderedComponent(markup);
	}

}
