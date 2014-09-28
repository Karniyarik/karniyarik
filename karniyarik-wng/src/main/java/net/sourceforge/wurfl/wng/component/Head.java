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
public class Head extends CompositeComponent {

	private static final long serialVersionUID = 10L;

	private String title;

	public Head() {
		// Empty
	}

	public Head(Document document) {
		setParent(document);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Document getDocument() {
		return (Document) getParent();
	}

	protected boolean isChildAllowed(Component child) {

		return child instanceof Meta || child instanceof Script || child instanceof StyleContainer;
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if (obj instanceof Head) {
			builder.appendSuper(super.equals(obj));
			Head other = (Head) obj;
			builder.append(title, other.title);
		}
		else {
			builder.appendSuper(false);
		}


		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(title);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(title);

		return builder.toString();
	}

}
