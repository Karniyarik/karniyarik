/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class GridMenu extends CompositeComponent {

    private static final long serialVersionUID = 10L;
    
    public GridMenu() {
		// Empty
	}
    
    public GridCell getCell(int index) {
    	return (GridCell)getChildren().get(index);
    }
    
    public void addCell(GridCell cell) {
    	
    	Validate.notNull(cell, "The cell must be not null");
    	
    	try {
    		add(cell);
		} catch (InvalidContainmentException e) {
			// It is not possible
			assert false;
		}    
	}

    protected boolean isChildAllowed(Component child) {

    	return child instanceof GridCell;
    }
    
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof GridMenu) {
			builder.appendSuper(super.equals(obj));
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

}
