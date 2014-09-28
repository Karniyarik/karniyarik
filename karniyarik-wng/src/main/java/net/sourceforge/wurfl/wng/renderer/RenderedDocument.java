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
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Filippo De Luca
 *
 * @version $Id$
 */
public class RenderedDocument implements Serializable {

	private static final long serialVersionUID = 10L;
	
	private String contentType;
	
	private String markup;
	
	public RenderedDocument(String contentType, String markup) {
		this.contentType = contentType;
		this.markup = markup;
	}

	public String getContentType() {
		return contentType;
	}
	
	public String getMarkup() {
		return markup;
	}
	
	// Commons methods ****************************************************
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		builder.appendSuper(obj instanceof RenderedDocument);
		if(obj instanceof RenderedDocument) {
			RenderedDocument other = (RenderedDocument)obj;
			
			builder.append(contentType, other.contentType);
			builder.append(markup, other.markup);
		}
		
		return builder.isEquals();
	}
	
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder(13,11);
		
		builder.append(getClass()).append(contentType).append(markup);
		
		return builder.toHashCode();
	}
	
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		
		builder.append(contentType);
		builder.append(markup);
		
		return builder.toString();
	}

}
