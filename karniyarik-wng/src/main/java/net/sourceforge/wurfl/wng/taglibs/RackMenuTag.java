/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.RackMenu;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: RackMenuTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class RackMenuTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

    // attributes
    private String background_color1;
    private String background_color2;

    public void setBackground_color1(String background_color1) {
        this.background_color1 = background_color1;
    }

    public void setBackground_color2(String background_color2) {
        this.background_color2 = background_color2;
    }

    protected Component createComponent() {

    	return new RackMenu();
    }
    
    protected void configureComponent(Component component) throws JspException {
    	
    	super.configureComponent(component);
    	
    	RackMenu rackMenu = (RackMenu)component;
    	
    	rackMenu.setOddBackgroundColor(background_color1);
    	rackMenu.setEvenBackgroundColor(background_color2);   	
    }
    
    protected void postConfigureComponent(Component component) throws JspException {
    	    	
    	getBuildingContext().setInsideRackMenuTag(false);
    	
    	super.postConfigureComponent(component);

    }
    
    protected void reset() {
        this.background_color1 = null;
        this.background_color2 = null;
        
        super.reset();
    }
}
