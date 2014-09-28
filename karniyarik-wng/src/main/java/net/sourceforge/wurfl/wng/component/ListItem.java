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

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class ListItem extends CompositeComponent {

	private static final long serialVersionUID = 10L;

	private Link mainLink;
	
	private Image leftImage;
	
	private Image rightImage;

	public ListItem() {
		// Empty
	}

	public Link getMainLink() {
		return mainLink;
	}

	public void setMainLink(Link mainLink) {
		Validate.notNull(mainLink, "mainLink must not be null");
		this.mainLink = mainLink;
	}

	public Image getLeftImage() {
		return leftImage;
	}

	public void setLeftImage(Image leftImage) {
		Validate.notNull(leftImage, "leftImage must not be null");
		this.leftImage = leftImage;
	}

	public Image getRightImage() {
		return rightImage;
	}

	public void setRightImage(Image rightImage) {
		Validate.notNull(rightImage, "rightImage must not be null");
		this.rightImage = rightImage;
	}
	
	// Business methods ***************************************************
	
	public void accept(ComponentVisitor visitor) throws ComponentException {
		
		super.accept(visitor);
		
		if(leftImage!=null) {
			leftImage.accept(visitor);
		}
		if(mainLink!=null) {
			mainLink.accept(visitor);
		}
		if(mainLink!=null) {
			mainLink.accept(visitor);
		}
	}
	
	protected boolean isChildAllowed(Component child) {
		return child instanceof Text || child instanceof Br || child instanceof Link;
	}
	
	
	/* (non-Javadoc)
	 * @see net.sourceforge.wurfl.wng.component.Component#initStyle()
	 */
	protected void initStyle() {
		getStyle().addProperty(CssProperties.FONT_SIZE, "9px");
		getStyle().addProperty(CssProperties.FONT_WEIGHT, "bold");
		getStyle().addProperty(CssProperties.FONT_FAMILY, "Verdana, Arial");
	}

	
	// Commons methods ****************************************************
	
    public boolean equals(Object obj) {
    	EqualsBuilder builder = new EqualsBuilder();
    	
    	
    	if(obj instanceof ListItem) {
    		ListItem other = (ListItem)obj;
    		builder.appendSuper(super.equals(obj));
    		builder.append(leftImage, other.leftImage);
    		builder.append(mainLink, other.mainLink);
    		builder.append(rightImage, other.rightImage);
    	}
		else {
			builder.appendSuper(false);
		}

    	return builder.isEquals();
    }
    
    public int hashCode() {
    	HashCodeBuilder builder = new HashCodeBuilder();
    	builder.appendSuper(super.hashCode());
    	builder.append(leftImage);
    	builder.append(mainLink);
    	builder.append(rightImage);
    	
    	return builder.toHashCode();
    }
}
