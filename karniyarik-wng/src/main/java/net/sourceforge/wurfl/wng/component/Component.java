/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import java.io.Serializable;
import java.util.List;

import net.sourceforge.wurfl.wng.renderer.template.Templatizable;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: Component.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public abstract class Component implements Templatizable, Serializable {

	private static final long serialVersionUID = 10L;
	
	private String id;
	
    private Component parent;

    private Css style = new Css();
    
    // Constructors *******************************************************
    
    public Component() {
        init();
    }
    
    private void init() {
        initStyle();
    }

    protected void initStyle() {
    	// Empty by default
    }
    
    // Access methods *****************************************************

    public String getId() {
    	return this.id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }

    
    public Css getStyle() {
    	
		return style;
	}
    
    public void setStyle(Css css) {
    	
    	if(css==null) {
    		this.style = new Css();
    	}
    	else {
    		this.style = css;
    	}
        
    }
    
    public Component getParent() {
        return parent;
    }

    public void setParent(Component parentComponent) {
        this.parent = parentComponent;
    }
    
    // Business methods ***************************************************
    
    public String getSelector() {
    	String cssSelector = getStyle().getSelector();
    	if(StringUtils.isNotBlank(cssSelector)) {
    		return cssSelector.substring(1);
    	}
    	return null;
    }
    
    public boolean isIdOrClassDefined() {
    	return StringUtils.isNotBlank(id) || StringUtils.isNotBlank(getStyle().getSelector());
    }
    
    public String getStyleClass() {
    	if(style.getSelector() !=null && style.getSelector().startsWith(".")){
    		return style.getSelector().substring(1);
    	}
    	else{
    		return null;
    	}
    }
    
    public void mergeStyle(Css style){
    	getStyle().updateWith(style);
    }
    
    public String getTemplateName() {
    	return getClass().getSimpleName();
    }
    
    public void addTo(CompositeComponent parent) throws InvalidContainmentException {
    	parent.add(this);
    }
    	
	public void validate() throws ValidationException {
		// Do nothing by default
	}
	
	public void accept(final ComponentVisitor visitor) throws ComponentException {
		
		visitor.visit(this);	
	}
	
	public boolean hasChildren() {
		return false;
	}

	public List getChildren() {
		return ListUtils.EMPTY_LIST;
	}
	   
    // Commons methods ****************************************************

    public boolean equals(Object obj) {
    	EqualsBuilder builder = new EqualsBuilder();
    	   	
    	if(obj instanceof Component){
    		Component other = (Component)obj;
    		builder.append(id, other.id);
    		builder.append(style, other.style);
    	}
    	else {
    		builder.appendSuper(false);
    	}

        return builder.isEquals();
    }

    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        
        builder.append(getClass()).append(id).append(style);
        return builder.toHashCode();
    }

    public String toString() {
    	ToStringBuilder builder = new ToStringBuilder(this);
    	builder.append(id);
    	builder.append(style);
    	
    	return builder.toString();
    }


}
