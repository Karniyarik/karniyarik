/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca\
 * 
 * @version $Id: IllustratedItemNoWrapRightPart.java 1046 2009-03-09 15:57:13Z
 *          filippo.deluca $
 */
public class RightPart extends CompositeComponent {

	private static final long serialVersionUID = 10L;

	protected boolean isChildAllowed(Component child) {
		return child instanceof Link || child instanceof Text
				|| child instanceof Br;
	}

	protected void initStyle() {
		getStyle().addProperty(CssProperties.VERTICAL_ALIGN, "top");
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof RightPart) {
			builder.appendSuper(super.equals(obj));
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.appendSuper(super.hashCode()).toHashCode();
	}

	public String toString() {

		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());

		return builder.toString();
	}

}
