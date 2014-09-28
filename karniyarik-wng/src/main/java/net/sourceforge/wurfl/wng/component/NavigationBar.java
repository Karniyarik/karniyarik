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
public class NavigationBar extends CompositeComponent {

    private static final long serialVersionUID = 10L;

    private static final String DEFAULT_SEPARATOR = " | ";

    private String separator;

    public NavigationBar() {
        this(DEFAULT_SEPARATOR);
    }

    public NavigationBar(String aSeparator) {
        this.separator = aSeparator;
    }

    public String getSeparator() {
        return separator;
    }
    
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	
	/* (non-Javadoc)
	 * @see net.sourceforge.wurfl.wng.component.Component#initStyle()
	 */
	protected void initStyle() {
		getStyle().addProperty(CssProperties.PADDING, "2px 1px 2px 0px");
	}

	
	
	// Business methods ***************************************************
	
	protected boolean isChildAllowed(Component child) {

		return child instanceof Link || child instanceof Text;
	}
   
    
	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof NavigationBar) {
			NavigationBar other = (NavigationBar) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(separator, other.separator);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(separator);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(separator);

		return builder.toString();
	}


}
