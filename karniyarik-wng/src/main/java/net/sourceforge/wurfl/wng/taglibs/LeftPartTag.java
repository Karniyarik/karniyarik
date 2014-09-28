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
import net.sourceforge.wurfl.wng.component.LeftPart;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: IllustratedItemNoWrapLeftPartTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class LeftPartTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	
	protected Component createComponent() {
		return new LeftPart();
	}


	protected void addComponentToParent(Component component)
			throws JspException {
		Component parent = getParentComponent();
		if(parent instanceof IllustratedItemNoWrap) {
			setIllustratedItemLeftPart((IllustratedItemNoWrap)parent, (LeftPart)component);
		} else {
			super.addComponentToParent(component);
		}
	}


	
	private void setIllustratedItemLeftPart(IllustratedItemNoWrap parent,
			LeftPart leftPart) {
		parent.setLeftPart(leftPart);
	}
	
	


}
