/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.style;

import java.util.HashSet;
import java.util.Set;

import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.BannerRow;
import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.ComponentException;
import net.sourceforge.wurfl.wng.component.ComponentVisitor;
import net.sourceforge.wurfl.wng.component.Css;
import net.sourceforge.wurfl.wng.component.CssProperties;
import net.sourceforge.wurfl.wng.component.Header;
import net.sourceforge.wurfl.wng.component.RackMenu;
import net.sourceforge.wurfl.wng.component.StyleContainer;
import net.sourceforge.wurfl.wng.component.Image;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This visitor is responsible to optimize the component CSS style. At end of
 * it's execution the style container will have only used class CSS and the
 * element CSS.
 * 
 * For each component this visitor will set the default style, optimize the
 * assigned style and add custom style properties.
 * 
 * @author Filippo De Luca
 * @version $Id$
 */
public class StyleOptimizerVisitor implements ComponentVisitor {

	private static final Logger logger = LoggerFactory
			.getLogger(StyleOptimizerVisitor.class);

	private final StyleContainer styleContainer;

	private final WNGDevice device;

	private Set/*Css*/classStyles;

	public StyleOptimizerVisitor(WNGDevice device, StyleContainer styleContainer) {
		
		Validate.notNull(device, "The device must be not null");
		Validate.notNull(styleContainer, "The styleContainer must be not null");
		
		this.device = device;
		this.styleContainer = styleContainer;

		classStyles = new HashSet(CollectionUtils.select(styleContainer
				.getStyles(), new IsClassStylePredicate()));
		styleContainer.removeStyles(classStyles);
	}

	public void visit(Component component) throws ComponentException {

		applyDefaultStyle(component);

		optimizeStyle(component);

		addCustomStyles(component);
	}

	private void applyDefaultStyle(Component component) {
		// Header
		if (component.getClass() == Header.class) {
			updateWidthCssProperty(component);
		} else if (component instanceof RackMenu) {
			updateWidthCssProperty(component);
		}

		// BannerRow
		else if (component.getClass() == BannerRow.class) {
			BannerRow item = (BannerRow) component;
			if (!item.getStyle().containsProperty("width")) {
				SumClosure sumClosure = new SumClosure("width");
				CollectionUtils.forAllDo(item.getChildren(), sumClosure);
				String widthInPixel = String.valueOf(sumClosure.getSumValue())
						+ "px";
				item.getStyle().addProperty("width", widthInPixel);
			}
		}

	}

	private void updateWidthCssProperty(Component component) {
		if (!component.getStyle().containsProperty(CssProperties.WIDTH)) {
			String widthInPixel = device.getCapability("resolution_width");
			if (StringUtils.isNotBlank(widthInPixel)) {
				widthInPixel = widthInPixel + "px";
				component.getStyle().addProperty(CssProperties.WIDTH,
						widthInPixel);
			}

		}
	}

	private void optimizeStyle(Component component) {

		Css style = component.getStyle();

		if (!style.isEmpty()) {
			Css optimizedStyle = findOrCreateOptimizedStyle(style);

			component.setStyle(optimizedStyle);
		}
	}

	private Css findOrCreateOptimizedStyle(Css style) {
		Css optimizedStyle = (Css) CollectionUtils.find(styleContainer
				.getStyles(), new hasSamePropertiesPredicate(style));

		if (optimizedStyle == null) {
			optimizedStyle = createOptimizedStyle(style);
			styleContainer.addStyle(optimizedStyle);
		}
		return optimizedStyle;
	}

	private Css createOptimizedStyle(Css style) {
		Css optimizedStyle = null;
		Css declaredStyle = (Css) CollectionUtils.find(classStyles,
				new hasSamePropertiesPredicate(style));

		if (declaredStyle != null) {
			classStyles.remove(declaredStyle);
			optimizedStyle = declaredStyle;
		} else {
			optimizedStyle = new Css();
			optimizedStyle.updateWith(style);
			initSelector(optimizedStyle);
		}

		return optimizedStyle;
	}

	private void addCustomStyles(Component component) {

		// RackMenu
		if (component instanceof RackMenu) {
			RackMenu rackMenu = (RackMenu) component;

			if (StringUtils.isNotBlank(rackMenu.getOddBackgroundColor())) {
				Css oddElementStyle = new Css();
				addRackMenuCommonStyleProperties(oddElementStyle);

				oddElementStyle.addProperty(CssProperties.BACKGROUND_COLOR,
						rackMenu.getOddBackgroundColor());
				oddElementStyle.setSelector(rackMenu.getStyle().getSelector()
						+ "_odd");

				styleContainer.addStyle(oddElementStyle);
			}

			if (StringUtils.isNotBlank(rackMenu.getEvenBackgroundColor())) {
				Css evenElementStyle = new Css();
				addRackMenuCommonStyleProperties(evenElementStyle);
				evenElementStyle.addProperty(CssProperties.BACKGROUND_COLOR,
						rackMenu.getEvenBackgroundColor());
				evenElementStyle.setSelector(rackMenu.getStyle().getSelector()
						+ "_even");
				styleContainer.addStyle(evenElementStyle);
			}
		}
	}

	private void addRackMenuCommonStyleProperties(Css elementStyle) {
		elementStyle.addProperty(CssProperties.TEXT_ALIGN, "center");
		elementStyle.addProperty(CssProperties.WIDTH, "50%");
	}

	private void initSelector(Css style) {
		int hashcode = new HashCodeBuilder(33, 57)
				.append(style.getProperties()).toHashCode();

		String id = IdGenerator.idOf(hashcode);
		StrBuilder selectorBuilder = new StrBuilder(".").append(id);
		style.setSelector(selectorBuilder.toString());
	}

	static class IsClassStylePredicate implements Predicate {

		public boolean evaluate(Object object) {
			Css style = (Css) object;

			return StringUtils.defaultString(style.getSelector()).startsWith(
					".");
		}

	}

	static class hasSamePropertiesPredicate implements Predicate {

		private Css style;

		public hasSamePropertiesPredicate(Css style) {
			this.style = style;
		}

		public boolean evaluate(Object object) {
			Css other = (Css) object;

			return other.getProperties().equals(style.getProperties());
		}

	}

	private static class SumClosure implements Closure {

		private String property;

		private Integer sum = Integer.valueOf(0);

		public SumClosure(String property) {
			this.property = property;
		}

		public void execute(Object input) {

			try {
				Integer value = Integer.valueOf((String)PropertyUtils.getProperty(input, property));
				if (value != null) {
					sum = Integer.valueOf(value.intValue() + sum.intValue());
				}
			} catch (Throwable e) {
				logger.warn("Error reading integer {} property", property);
			}
		}

		public int getSumValue() {
			return sum.intValue();
		}
	}

    

}
