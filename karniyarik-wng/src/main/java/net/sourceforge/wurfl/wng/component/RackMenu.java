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
 * A menu of links displayed on two columns for high-end devices.
 * 
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: RackMenu.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class RackMenu extends CompositeComponent {

	private static final long serialVersionUID = 10L;
	
	private String oddBackgroundColor;

	private String evenBackgroundColor;
	
	public RackMenu() {
		oddBackgroundColor = "#C4CDD5";
	}

	protected void initStyle() {
		getStyle().addProperty(CssProperties.PADDING, "2px");
		//getStyle().addProperty(CssProperties.SPACING, "2px");
		getStyle().addProperty(CssProperties.BORDER, "1px");
		getStyle().addProperty(CssProperties.BORDER_SPACING, "1px");
		getStyle().addProperty(CssProperties.BORDER_COLLAPSE, "collapse");
		getStyle().addProperty(CssProperties.BACKGROUND_COLOR, "#C4CDD5");
		getStyle().addProperty(CssProperties.BORDER_WIDTH, "1px");
		getStyle().addProperty(CssProperties.TEXT_ALIGN, "center");
	}
	
	public String getEvenBackgroundColor() {
		return evenBackgroundColor;
	}
	
	public void setEvenBackgroundColor(String evenBackgroundColor) {
		this.evenBackgroundColor = evenBackgroundColor;
	}
	
	public String getOddBackgroundColor() {
		return oddBackgroundColor;
	}
	
	public void setOddBackgroundColor(String oddBackgroundColor) {
		this.oddBackgroundColor = oddBackgroundColor;
	}
	
	public void addLink(Link link) {
		Validate.notNull(link, "link must not be null");
		
		try {
			add(link);
		} catch (InvalidContainmentException e) {
			// It is not possible
			assert false;
		}
	}
	
	protected boolean isChildAllowed(Component child) {
		return child instanceof Link;
	}
	
	// Commons methods ****************************************************
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof RackMenu) {
			RackMenu other = (RackMenu)obj;
			builder.appendSuper(super.equals(obj));
			builder.append(oddBackgroundColor, other.oddBackgroundColor);
			builder.append(evenBackgroundColor, other.evenBackgroundColor);
		}
		else {
			builder.appendSuper(false);
		}
		
		return builder.isEquals();
	}
	
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		
		builder.appendSuper(super.hashCode());
		builder.append(oddBackgroundColor);
		builder.append(evenBackgroundColor);
		
		return builder.toHashCode();
	}
	
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		
		builder.appendSuper(super.toString());
		builder.append(oddBackgroundColor);
		builder.append(evenBackgroundColor);
		
		return builder.toString();
	}
	
}
