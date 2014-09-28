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
public class CssProperty {
	
	private static final long serialVersionUID = 10L;

	private String name;

    private String value;
    
    public CssProperty(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
    
    // Commons methods ****************************************************

    public boolean equals(Object obj) {
    	EqualsBuilder builder = new EqualsBuilder();
    	
    	if(obj instanceof CssProperty){   		
    		CssProperty other = (CssProperty)obj;
    		builder.append(name, other.name).append(value, other.value);	
    	}
    	else {
    		builder.appendSuper(false);
    	}

        return builder.isEquals();
    }

    public int hashCode() {
        HashCodeBuilder builder =  new HashCodeBuilder();
        
        builder.append(getClass()).append(this.name).append(this.value);
        
        return builder.toHashCode();
    }

    public String toString() {
    	ToStringBuilder builder = new ToStringBuilder(this);
    	
    	builder.append(name).append(value);
    	
    	return builder.toString();
    }


}
