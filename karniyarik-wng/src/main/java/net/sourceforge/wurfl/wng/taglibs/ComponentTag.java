/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.CompositeComponent;
import net.sourceforge.wurfl.wng.component.Css;
import net.sourceforge.wurfl.wng.component.InvalidContainmentException;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: ComponentTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public abstract class ComponentTag extends BaseTag {

	private static final long serialVersionUID = 10L;

	// Common attributes
	private String text_color;

	private String background_color;

	private String align;

	private String css_ref;

	// private boolean created = false;

	// LifeCycle methods **************************************************

	protected final void handleStart() throws JspException {

		Component component = createComponent();

		if (component == null) {
			throw new JspException(
					"Problem creating the component associated with current tag "
							+ ClassUtils.getShortClassName(this.getClass()));
		}
		configureComponent(component);
		getBuildingContext().pushComponent(component);
		logger.debug("Component {} pushed", ClassUtils
				.getShortClassName(component.getClass()));
	}

	protected abstract Component createComponent();

	protected void addComponentToParent(Component component)
			throws JspException {
        
		assert component!=null : "The component must be not null";
		
		if (getParentComponent() instanceof CompositeComponent) {
			CompositeComponent parentComposite = (CompositeComponent) getParentComponent();

			try {
				parentComposite.add(component);
				logger.debug("Added {} to {}", ClassUtils
						.getShortClassName(component.getClass()), ClassUtils
						.getShortClassName(parentComposite.getClass()));

			} catch (InvalidContainmentException e) {
				
				StrBuilder msgBuilder = new StrBuilder();
				msgBuilder.append("The component: ");
				msgBuilder.append(ClassUtils.getShortClassName(e.getInvalidChild().getClass()));
				msgBuilder.append(" can not be nested inside component: ");
				msgBuilder.append(ClassUtils.getShortClassName(e.getSource().getClass()));
				
				throw new JspException(msgBuilder.toString(), e);
			}
		}
		
		else {
			// XXX throw exception? it is a wng developer issue.
            logger.warn("Trying to add component {} to {}", component.getClass(), getParentComponent().getClass());
			logger.warn("The parent component must be CompositeComponent instance");
		}
	}

	/**
	 * The extending classes had to call the super method.
	 * 
	 * @param component The component to configure
	 * @throws JspException in case of error.
	 */
	protected void configureComponent(Component component) throws JspException {
		component.setId(getId());

		updateComponentStyle(component);

	}

	private void updateComponentStyle(Component component) throws JspException {

		updateStyleWithCssRef(component.getStyle());

		updateStyleWithTagAttributes(component.getStyle());
	}

	
	protected void updateStyleWithCssRef(Css style) throws JspException {
		// Add css_ref (lowest priority)
		if (StringUtils.isNotBlank(css_ref)) {
			String styleSelector = new StrBuilder(".").append(css_ref)
					.toString();
			Css referringStyle = getBuildingContext().getStyleContainer()
					.findStyle(styleSelector);

			if (referringStyle == null) {
				StrBuilder message = new StrBuilder();
				message.append("The css_ref ").append(css_ref).append(
						" refers to an undefined style");
				throw new JspException(message.toString());
			}

			style.updateWith(referringStyle);
		}
	}

	protected void updateStyleWithTagAttributes(Css style) {
		// Add css attibutes (mid priority)
		style.addProperty("color", text_color);
		style.addProperty("background-color", background_color);
		style.addProperty("align", align);
	}

	/**
	 * @throws JspException
	 */
	protected final void handleEnd() throws JspException {

		Component component = getBuildingContext().pollComponent();
		logger.debug("Component {} polled", ClassUtils
				.getShortClassName(component.getClass()));

		postConfigureComponent(component);
		addComponentToParent(component);
	}

	protected void postConfigureComponent(Component component)
			throws JspException {
		// Empty by default
	}

	protected void reset() {
		// this.created = false;
		this.background_color = null;
		this.css_ref = null;
		this.align = null;

		super.reset();
	}

	// Attributes methods *************************************************

	public void setText_color(String text_color) {
		this.text_color = text_color;
	}

	public void setBackground_color(String background_color) {
		this.background_color = background_color;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setCss_ref(String css_ref) {
		this.css_ref = css_ref;
	}

}
