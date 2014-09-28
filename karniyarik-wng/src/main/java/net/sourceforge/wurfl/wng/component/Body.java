/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: Body.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class Body extends CompositeComponent {

    private static final long serialVersionUID = 10L;
    
    private static ArrayList/*Class*/ validComponents = new ArrayList();
    
    static {
    	validComponents.add(Header.class);
        validComponents.add(BillBoard.class);
        validComponents.add(RackMenu.class);
        validComponents.add(Image.class);
        validComponents.add(Text.class);
        validComponents.add(TextBlock.class);
        validComponents.add(BannerRow.class);
        validComponents.add(Link.class);
        validComponents.add(IllustratedItem.class);
        validComponents.add(ListItem.class);
        validComponents.add(Title.class);
        validComponents.add(IllustratedItemNoWrap.class);
        validComponents.add(Br.class);
        validComponents.add(NavigationBar.class);
        validComponents.add(Form.class);
        validComponents.add(Hr.class);
        validComponents.add(GridMenu.class);
        validComponents.add(Table.class);
        validComponents.add(Script.class);
    }
       
    public Body() {
		// Empty
	}
    
    public Body(Document document) {
		setParent(document);
	}

	public Document getDocument() {
		return (Document)getParent();
	}
	

	
	protected boolean isChildAllowed(Component child) {
		
		Iterator validIterator = validComponents.iterator();
		while(validIterator.hasNext()){
			Class clazz = (Class)validIterator.next();
			if(clazz.isInstance(child)){
				return true;
			}
		}
		
		return false;
	}

	protected void initStyle() {
		getStyle().addProperty(CssProperties.MARGIN, "0px");
		getStyle().addProperty(CssProperties.PADDING, "0px");
	}
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof Body) {
			builder.appendSuper(super.equals(obj));
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	
	

}
