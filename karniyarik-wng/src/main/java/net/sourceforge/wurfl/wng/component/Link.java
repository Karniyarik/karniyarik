/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: Link.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class Link extends LeafComponent {

	private static final long serialVersionUID = 10L;

	private String href;

	private String text;

	private String accesskey;

	public Link() {
		// Empty
	}

	public Link(String href) {
		this.href = href;
	}

	public Link(String href, String text) {
		this.href = href;
		this.text = text;
	}

	public Link(String href, String text, String accesskey) {
		this.href = href;
		this.text = text;
		this.accesskey = accesskey;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {

		Validate.isTrue(accesskey == null || accesskey.length() == 1,
				"The accesskey must be one character long");
		Validate.isTrue(accesskey == null
				|| Character.isLetterOrDigit(accesskey.charAt(0)),
				"The accesskey must be one letter or digit");

		this.accesskey = accesskey;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof Link) {
			Link other = (Link) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(href, other.href);
			builder.append(text, other.text);
			builder.append(accesskey, other.accesskey);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(href).append(text).append(accesskey);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(href).append(text).append(accesskey);

		return builder.toString();
	}

}
