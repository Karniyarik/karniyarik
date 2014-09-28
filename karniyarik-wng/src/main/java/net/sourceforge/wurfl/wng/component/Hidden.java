/*
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
 * @version $Id$
 */
public class Hidden extends BaseInput {
	
    private static final long serialVersionUID = 10L;

	public static final String TYPE = "hidden";
	
	public Hidden() {
		super(TYPE);
	}

	public Hidden(String name, String value) {
		super(TYPE,name, value);
	}
	
	public void setName(String name) {
		
		Validate.notEmpty(name, "input name must not be empty");
		
		super.setName(name);
	}
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof Hidden) {
			builder.appendSuper(super.equals(obj));
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}


}
