/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Css;
import net.sourceforge.wurfl.wng.component.StyleContainer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: CssTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class CssTag extends BaseTag {

	private static final long serialVersionUID = 10L;

	private static final Logger logger = LoggerFactory.getLogger(CssTag.class);

	private String selector;

	// State
	private Css css = null;

	
	protected void handleStart() throws JspException {
		css = new Css();
		
		if (getParentComponent() instanceof StyleContainer) {
			
			if (StringUtils.isEmpty(selector)) {
				throw new JspException("The selector of declared css must be not null");
			} 
			
			else if (getBuildingContext().getStyleContainer().containsStyle(selector)) {
				throw new JspException("The selector of declared css must be unique");
			}
		}
		
		else if(StringUtils.isNotEmpty(selector)){
			throw new JspException("The selector of component css must be null: " + selector);
		}
		
		css.setSelector(selector);

		getBuildingContext().setCurrentCss(css);
	}

	protected void handleEnd() throws JspException {
		
		Component parentComponent = getParentComponent();

		if (parentComponent instanceof StyleContainer) {
			logger.debug("adding style: {} to StyleContainer", css);
			StyleContainer styleContainer = (StyleContainer)parentComponent;
			styleContainer.addStyle(css);
		}
		else {
			logger.debug("Update component: {} with style: {}", parentComponent, css);
			parentComponent.getStyle().updateWith(css);
		}
		
		// release
		getBuildingContext().setCurrentCss(null);
	}

	protected void reset() {
		super.reset();

		css = null;
	}

	// Attribute methods **************************************************

	public void setSelector(String selector) {
		this.selector = selector;
	}

}
