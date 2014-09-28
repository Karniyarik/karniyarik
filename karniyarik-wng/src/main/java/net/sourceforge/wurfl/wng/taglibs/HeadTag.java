/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Document;
import net.sourceforge.wurfl.wng.component.Head;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: HeadTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class HeadTag extends ComponentTag {

    private static final long serialVersionUID = 10L;

    
    protected Component createComponent() {

    	return new Head();
    }
    
    protected void addComponentToParent(Component component)
    		throws JspException {
    	
    	Head head = (Head)component;
    	
        Document document = (Document)getParentComponent();
        document.setHead(head);
        
        logger.debug("Added head to document");
    }

}
