/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: StyleContainer.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class StyleContainer extends LeafComponent {
	
    private static final long serialVersionUID = 10L;
    
    private Set/*CssComponent*/ styles = new HashSet();

    public StyleContainer() {
    	super();
    }

    public Set/*Css*/ getStyles() {
        return new HashSet(styles);
    }
    
    public void setStyles(Set/*Css*/ styles) {
		this.styles.clear();
		this.styles.addAll(styles);
	}
    
    // Business methods ***************************************************
    
    public void addStyles(Set/*Css*/ styles) {
    	
		for(Iterator it = IteratorUtils.getIterator(styles); it.hasNext(); ) {
			Css style = (Css)it.next();
			addStyle(style);
		}
    }

    public void addStyle(Css style) {
    	
    	Validate.notNull(style);
    	Validate.notEmpty(style.getSelector());
    	
        this.styles.add(style);
    }
        
    public void removeStyles(Set/*Css*/ styles) {
    	
		for(Iterator it = IteratorUtils.getIterator(styles); it.hasNext(); ) {
			Css style = (Css)it.next();
			removeStyle(style);
		}
    }
    
    public void removeStyle(Css style) {
    	
    	Validate.notNull(style);
    	
    	this.styles.remove(style);
    }
    
    public boolean containsStyle(String selector) {
    	
    	Validate.notEmpty(selector);
    	
    	return CollectionUtils.exists(styles, new SelectorEqualsPredicate(selector));
    }
    
    public Css findStyle(String selector) {
    	
    	Validate.notEmpty(selector);
    	
    	Css style = (Css)CollectionUtils.find(styles, new SelectorEqualsPredicate(selector));
    	
        return style;
    }
    
    // Commons methods ****************************************************

    public boolean equals(Object obj) {
    	EqualsBuilder builder = new EqualsBuilder();
    	
    	if(obj instanceof StyleContainer){
    		StyleContainer other = (StyleContainer)obj;
    		builder.appendSuper(super.equals(obj));
    		builder.append(styles, other.styles);
    	}
		else {
			builder.appendSuper(false);
		}

        return builder.isEquals();
    }

    public int hashCode() {
        HashCodeBuilder builder =  new HashCodeBuilder();
        
        builder.appendSuper(super.hashCode()).append(styles);

        
        return builder.toHashCode();
    }
  
    public String toString() {
    	
    	ToStringBuilder builder = new ToStringBuilder(this);
    	
    	builder.appendSuper(super.toString()).append(styles);
    	
    	return builder.toString();
    }
    
    // Support class ******************************************************
    
    static class SelectorEqualsPredicate implements Predicate {
    	
    	private String selector;
    	
    	public SelectorEqualsPredicate(String selector) {
			this.selector = selector;
		}

		public boolean evaluate(Object object) {
			Css other = (Css)object;
			
    		return StringUtils.equals(selector, other.getSelector());
    	}
    }


}
