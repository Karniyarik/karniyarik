/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.CssProperty;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: CssPropertyTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class CssPropertyTag extends BaseTag {

    private static final long serialVersionUID = 1L;
    
    private String name;

    private String value;

   
    protected void handleStart() throws JspException {

    	CssProperty property = new CssProperty(name, value);
    	getBuildingContext().getCurrentCss().addProperty(property);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
