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
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class IllustratedItemNoWrap extends LeafComponent {

    private static final long serialVersionUID = 10L;
    
    private LeftPart leftPart;
    private RightPart rightPart;
    
    
    public IllustratedItemNoWrap() {
	}


	public LeftPart getLeftPart() {
		return leftPart;
	}


	public void setLeftPart(LeftPart leftPart) {
		Validate.notNull(leftPart, "IllustratedItemNoWrap: leftPart must not be null");
		this.leftPart = leftPart;
	}


	public RightPart getRightPart() {
		return rightPart;
	}


	public void setRightPart(RightPart rightPart) {
		Validate.notNull(rightPart, "IllustratedItemNoWrap: rightPart must not be null");
		this.rightPart = rightPart;
	}
    
	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		if (obj instanceof IllustratedItemNoWrap) {
			IllustratedItemNoWrap other = (IllustratedItemNoWrap) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(leftPart, other.leftPart);
			builder.append(rightPart, other.rightPart);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}
	
	
	public void accept(ComponentVisitor visitor) throws ComponentException {
		super.accept(visitor);
		if(leftPart != null) {
			leftPart.accept(visitor);
		}
		if(rightPart != null) {
			rightPart.accept(visitor);
		}
	}


	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(leftPart);
		builder.append(rightPart);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(leftPart);
		builder.append(rightPart);

		return builder.toString();
	}
	
}
