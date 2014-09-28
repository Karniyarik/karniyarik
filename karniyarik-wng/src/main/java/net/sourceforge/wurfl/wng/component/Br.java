/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: Br.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class Br extends LeafComponent {

	private static final long serialVersionUID = 10L;
	
	public static final Br INSTANCE = new Br();
	
	public void setStyle(Css css) {

		Validate.isTrue(css==null || css.isEmpty(), "The Br must have a empty style");
		
		super.setStyle(css);
	}
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof Br) {
			builder.appendSuper(super.equals(obj));
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

  
}
