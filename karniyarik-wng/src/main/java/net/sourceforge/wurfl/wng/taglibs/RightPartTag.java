/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.IllustratedItemNoWrap;
import net.sourceforge.wurfl.wng.component.RightPart;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: IllustratedItemNoWrapRightPartTag.java 1131 2009-03-26
 *          15:25:54Z filippo.deluca $
 */
public class RightPartTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	protected Component createComponent() {

		return new RightPart();
	}

	protected void addComponentToParent(Component component)
			throws JspException {
		Component parent = getParentComponent();
		if (parent instanceof IllustratedItemNoWrap) {
			setIllustratedItemRightPart((IllustratedItemNoWrap) parent,
					(RightPart) component);
		} else {
			super.addComponentToParent(component);
		}
	}

	private void setIllustratedItemRightPart(IllustratedItemNoWrap parent,
			RightPart component) {
		parent.setRightPart(component);
		
	}

}
