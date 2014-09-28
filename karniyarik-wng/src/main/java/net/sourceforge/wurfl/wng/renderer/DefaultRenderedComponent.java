/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class DefaultRenderedComponent implements RenderedComponent, Serializable {
	
	private static final long serialVersionUID = 10L;
	
	private final String markup;

	public DefaultRenderedComponent(String markup) {
		this.markup = markup;
	}

	public String getMarkup() {
		return markup;
	}
	
	// Commons methods ****************************************************
	
	public boolean equals(Object obj) {
		EqualsBuilder eb = new EqualsBuilder();
		
		eb.appendSuper(obj instanceof DefaultRenderedComponent);
		if(obj instanceof DefaultRenderedComponent) {
			DefaultRenderedComponent other = (DefaultRenderedComponent)obj;
			eb.append(markup, other.markup);
		}

		return eb.isEquals();
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(getClass()).append(markup).toHashCode();
	}

	public String toString() {
		return this.markup;
	}
}
