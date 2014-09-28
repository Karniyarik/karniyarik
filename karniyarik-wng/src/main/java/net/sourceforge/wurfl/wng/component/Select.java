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
 * @version $Id: Select.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class Select extends CompositeComponent {

    private static final long serialVersionUID = 10L;
    
    private String name;

    private String disabled;

    private String size;

    private String title;

    public Select() {
		// empty
	}

    public Select(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
       
    // Business methods ***************************************************
    
    protected boolean isChildAllowed(Component child) {

    	return child instanceof Option;
    }
    
    public void addOption(Option option) {
    	
    	Validate.notNull(option, "The option must not be null");
    	
    	try {
			add(option);
		} catch (InvalidContainmentException e) {
			// It is not possible
			assert false;
		}
    }
    
	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof Select) {
			Select other = (Select) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(name, other.name);
			builder.append(size, other.size);
			builder.append(title, other.title);
			builder.append(disabled, other.disabled);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(name);
		builder.append(size);
		builder.append(title);
		builder.append(disabled);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(name);
		builder.append(size);
		builder.append(title);
		builder.append(disabled);

		return builder.toString();
	}

}
