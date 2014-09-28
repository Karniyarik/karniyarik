/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.xhtmladvanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.GridCell;
import net.sourceforge.wurfl.wng.component.GridMenu;
import net.sourceforge.wurfl.wng.component.Image;
import net.sourceforge.wurfl.wng.renderer.AbstractComponentRenderer;
import net.sourceforge.wurfl.wng.renderer.ComponentRenderer;
import net.sourceforge.wurfl.wng.renderer.DefaultRenderedComponent;
import net.sourceforge.wurfl.wng.renderer.MarkupWriter;
import net.sourceforge.wurfl.wng.renderer.RenderedComponent;
import net.sourceforge.wurfl.wng.renderer.RenderingContext;
import net.sourceforge.wurfl.wng.renderer.RenderingException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 
 * @author Asres Gizaw Fantayeneh
 * @version $Id$
 */
public class GridMenuRenderer extends AbstractComponentRenderer {

	public GridMenuRenderer() {
		// empty
	}

	public GridMenuRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}

	public RenderedComponent renderComponent(Component component,
			RenderingContext renderingContext) {

		GridMenu gridMenu = (GridMenu) component;

		int deviceResolution = renderingContext.getDevice()
				.getResolutionWidth();
		int numCol = numberOfColumns(deviceResolution, gridMenu);

		List/*RenderedComponent*/ renderedChildren = renderChildren(component, renderingContext);
		List groupedChildrenByRow = new ArrayList();

		// FIXME Use RenderedCompinent in template!!!
		List/* String */childrenMarkups = new ArrayList(CollectionUtils.collect(renderedChildren,
				new Transformer() {
					public Object transform(Object input) {
						RenderedComponent renderedChild = (RenderedComponent) input;
						return renderedChild.getMarkup();
					}
				}));
		
		int fromIndex = 0;
		int totalSize = childrenMarkups.size();
		while (fromIndex + numCol < totalSize) {
			groupedChildrenByRow.add(childrenMarkups.subList(fromIndex,
					fromIndex + numCol));
			fromIndex += numCol;
		}
		if (fromIndex < totalSize) {
			groupedChildrenByRow.add(childrenMarkups.subList(fromIndex,
					totalSize));
		}
		
		Map/* String,Object */arguments = new HashMap();
		arguments.put("children", groupedChildrenByRow);

		String markup = generateMarkup(component, arguments);
		return new DefaultRenderedComponent(markup);
	}

	private List renderChildren(Component component,
			RenderingContext context) {

		List/* RenderedComponent */renderedChildren = new LinkedList();

		Iterator childrenIterator = IteratorUtils.getIterator(component
				.getChildren());
		while (childrenIterator.hasNext()) {
			Component child = (Component) childrenIterator.next();
			ComponentRenderer childRenderer = getComponentRenderer(child, context);
			RenderedComponent renderedChild = childRenderer.renderComponent(
					child, context);
			renderedChildren.add(renderedChild);
		}

		return renderedChildren;
		
	}

	private int numberOfColumns(int deviceResolution, GridMenu gridMenu) {
		GridCell firstCell = gridMenu.getCell(0);
		if (firstCell == null) {
			throw new RenderingException(
					"You must add at least one gridcell in the gridmenu");
		}
		int cellWidth = getCellWidth(firstCell);
		return deviceResolution / cellWidth;
	}

	private int getCellWidth(GridCell firstCell) {
		Image icon = firstCell.getIcon();
		if (!isIconWidthDefined(icon)) {
			throw new RenderingException(
					"You must set the image with its width in the gridcell");
		}

		return NumberUtils.toInt(icon.getWidth());

	}

	private boolean isIconWidthDefined(Image icon) {
		return icon != null && (NumberUtils.toInt(icon.getWidth()) > 0);
	}

}
