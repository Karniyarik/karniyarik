/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class InvalidContainmentException extends ComponentException {
	
    private static final long serialVersionUID = 10L;
    
    private Component invalidChild;

	public InvalidContainmentException(Component source, Component invalidChild) {
		super(source);
		this.invalidChild = invalidChild;
	}

	public InvalidContainmentException(Component source, Component invalidChild, String message) {
		super(source, message);
		this.invalidChild = invalidChild;
	}
	
	public Component getInvalidChild() {
		return invalidChild;
	}

}
