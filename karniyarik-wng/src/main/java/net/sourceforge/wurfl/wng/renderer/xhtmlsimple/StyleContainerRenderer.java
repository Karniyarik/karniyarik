/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer.xhtmlsimple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Css;
import net.sourceforge.wurfl.wng.component.CssProperties;
import net.sourceforge.wurfl.wng.component.CssProperty;
import net.sourceforge.wurfl.wng.component.StyleContainer;
import net.sourceforge.wurfl.wng.renderer.AbstractComponentRenderer;
import net.sourceforge.wurfl.wng.renderer.DefaultRenderedComponent;
import net.sourceforge.wurfl.wng.renderer.MarkupWriter;
import net.sourceforge.wurfl.wng.renderer.RenderedComponent;
import net.sourceforge.wurfl.wng.renderer.RenderingContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.Validate;

/**
 * 
 * @author Asres Gizaw Fantayeneh
 * @version $Id$
 */
public class StyleContainerRenderer extends AbstractComponentRenderer {

	public StyleContainerRenderer() {
		// Empty
	}

	public StyleContainerRenderer(MarkupWriter markupWriter) {
		super(markupWriter);
	}

	public RenderedComponent renderComponent(Component component,
			RenderingContext renderingContext) {
		Validate.isTrue(component instanceof StyleContainer,
				"component must be type of StyleContainer");
		StyleContainer styleContainer = (StyleContainer) component;
		Set cssStyles = styleContainer.getStyles();

		List cssAttributesToFilter = new ArrayList();

		cssAttributesToFilter.add(CssProperties.BACKGROUND_IMAGE);
		cssAttributesToFilter.add(CssProperties.FONT_SIZE);
		cssAttributesToFilter.add(CssProperties.FLOAT);
		cssAttributesToFilter.add(CssProperties.DISPLAY);
		cssAttributesToFilter.add(CssProperties.LINE_WIDTH);

		CssAttributeFilter cssAttributeFilter = new CssAttributeFilter(
				cssAttributesToFilter);
		Collection filteredCss = CollectionUtils.collect(cssStyles,
				cssAttributeFilter);

		Map/* String,Object */parameters = new HashMap();
		parameters.put("styles", filteredCss);

		String markup = getMarkupWriter().writeMarkup(component, parameters);

		return new DefaultRenderedComponent(markup);

	}

	private static class CssAttributeFilter implements Transformer {

		private final List cssPropertiesToFilter;

		public CssAttributeFilter(List cssPropertiesToFilter) {
			this.cssPropertiesToFilter = cssPropertiesToFilter;
		}

		public Object transform(Object input) {
			Css css = (Css) input;
			Css filteredCss = new Css(css);
			for (Iterator iterator = css.getProperties().values().iterator(); iterator
					.hasNext();) {
				CssProperty cssProperty = (CssProperty) iterator.next();
				if (cssPropertiesToFilter.contains(cssProperty.getName())) {
					filteredCss.removeProperty(cssProperty.getName());
				}
			}

			return filteredCss;
		}

	}

}
