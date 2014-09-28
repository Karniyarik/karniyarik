/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: BannerRow.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class BannerRow extends CompositeComponent {

	private static final long serialVersionUID = 10L;

	public BannerRow() {
		// Empty
	}
	
	protected boolean isChildAllowed(Component child) {
		return (child instanceof Image);
	}

	protected void initStyle() {
		getStyle().addProperty(CssProperties.PADDING, "0px");
		getStyle().addProperty(CssProperties.MARGIN, "0px");
		getStyle().addProperty(CssProperties.BORDER, "0px");
	}
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof BannerRow) {
			builder.appendSuper(super.equals(obj));
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

}
