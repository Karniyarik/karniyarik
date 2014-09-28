/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.TableColumn;

import org.apache.commons.lang.Validate;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class TableColumnRenderer extends AbstractComponentRenderer {

	public TableColumnRenderer() {
		// Empty
	}

	public TableColumnRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}

	public RenderedComponent renderComponent(Component component,
			RenderingContext context) {

		Validate.notNull(component);
		Validate.isTrue(component instanceof TableColumn);

		TableColumn column = (TableColumn) component;

		Map/* String,Object */arguments = new HashMap();
		arguments.put("useColspan", Boolean.valueOf(column.getColspan() > 1));

		String markup = generateMarkup(component, arguments);
		return new DefaultRenderedComponent(markup);
	}

}
