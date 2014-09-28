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
 * @version $Id$
 */
public class TextBlock extends CompositeComponent {

	private static final long serialVersionUID = 10L;

	protected boolean isChildAllowed(Component child) {

		return child instanceof Text || child instanceof Link
				|| child instanceof Br;
	}
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof TextBlock) {
			builder.appendSuper(super.equals(obj));
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

}
