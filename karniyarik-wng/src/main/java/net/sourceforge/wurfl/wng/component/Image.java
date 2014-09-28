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
 * @author Filippo De Luca
 * 
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class Image extends LeafComponent {

	private static final long serialVersionUID = 10L;

	private String src;

	private String alt;

	private String width;

	private String height;

	private String align;

	private Link link;

	public Image() {
		// Empty
	}

	public Image(String src) {
		this.src = src;
	}

	public Image(String src, String alt, String width, String height) {
		this.src = src;
		this.alt = alt;
		this.width = width;
		this.height = height;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}
	
	// Business methods ***************************************************
	
	public void accept(ComponentVisitor visitor) throws ComponentException {
		if(link != null) {
			link.accept(visitor);
		}
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof Image) {
			Image other = (Image) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(src, other.src);
			builder.append(alt, other.alt);
			builder.append(width, other.width);
			builder.append(height, other.height);
			builder.append(align, other.align);
			builder.append(link, other.link);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(src);
		builder.append(alt);
		builder.append(width);
		builder.append(height);
		builder.append(align);
		builder.append(link);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(src);
		builder.append(alt);
		builder.append(width);
		builder.append(height);
		builder.append(align);
		builder.append(link);

		return builder.toString();
	}

}
