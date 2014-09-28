/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Image;
import net.sourceforge.wurfl.wng.component.Link;
import net.sourceforge.wurfl.wng.component.ListItem;

import org.apache.commons.lang.StringUtils;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: LinkTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class LinkTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	private String href;

	private String text;

	private String accesskey;

	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}


    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }
    
    protected Component createComponent() {
    	
    	Component component = new Link();
    	
    	return component;
    }
    
    protected void configureComponent(Component component) throws JspException {
    	
    	super.configureComponent(component);
    	
    	Link link = (Link)component;
    	
    	String encodedHref = getBuildingContext().encodeURLWhenRequested(href);
    	
    	link.setHref(encodedHref);
    	link.setAccesskey(accesskey);
    	
		if(StringUtils.isNotBlank(text)){
			link.setText(text);
		}
    	
    }
    
    protected void addComponentToParent(Component component)
    		throws JspException {
    	
    	Link link = (Link)component;

    	Component parent = getParentComponent();
    	
    	// Handle Image link
    	if(parent instanceof Image) {
    		Image image = (Image)parent;
    		image.setLink(link);
    	} else if(parent instanceof ListItem) {
    		ListItem listItem = (ListItem)parent;
    		if(listItem.getMainLink() == null){
    			listItem.setMainLink(link);
    		}
    	} else{
    		super.addComponentToParent(component);
    	}
    }
    
    protected void postConfigureComponent(Component component)
    		throws JspException {
    	Link link = (Link)component;
    	
    	// Content has priority over attribute
    	boolean isTextInBody = getBodyContent() != null && StringUtils.isNotBlank(getBodyContent().getString());
    	if(isTextInBody) {
    		link.setText(getBodyContent().getString());
    	}

    	super.postConfigureComponent(component);
    }
	
    public void reset() {
		this.href = null;
		this.text = null;
		this.accesskey = null;
		super.reset();
	}

}
