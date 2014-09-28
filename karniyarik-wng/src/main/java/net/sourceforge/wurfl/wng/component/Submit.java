/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public class Submit extends BaseInput {

	private static final long serialVersionUID = 10L;

	public static final String TYPE = "submit";

	private boolean disabled;

	public Submit() {
		super(TYPE);
	}

	public Submit(String name, String value) {
		super(TYPE, name, value);
	}

	public Submit(String name, String value, boolean disabled) {
		this(name, value);
		this.disabled = disabled;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof Submit) {
			Submit other = (Submit) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(disabled, other.disabled);

		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(disabled);

		return builder.toHashCode();
	}

}
