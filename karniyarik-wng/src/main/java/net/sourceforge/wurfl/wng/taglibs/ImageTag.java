/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;

import net.sourceforge.wurfl.wng.ImageDispenser;
import net.sourceforge.wurfl.wng.component.BillBoard;
import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.GridCell;
import net.sourceforge.wurfl.wng.component.Header;
import net.sourceforge.wurfl.wng.component.IllustratedItem;
import net.sourceforge.wurfl.wng.component.Image;
import net.sourceforge.wurfl.wng.component.LeftPart;
import net.sourceforge.wurfl.wng.component.ListItem;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: ImageTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class ImageTag extends ComponentTag {

	private static final long serialVersionUID = 10L;

	// Attributes
	private String src;

	private String width;

	private String height;

	private String alt;

	private Object imageRetriever;

	public void setSrc(String src) {
		this.src = src;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public Object getImageRetriever() {
		return imageRetriever;
	}

	public void setImageRetriever(Object imageRetriever) {
		this.imageRetriever = imageRetriever;
	}

	protected Component createComponent() {
		Image image;
		if (this.imageRetriever != null) {
			image = ((ImageDispenser) imageRetriever).getImage();
		} else {
			image = new Image(src, alt, width, height);
		}

		return image;
	}

	protected void addComponentToParent(Component component)
			throws JspException {
		Component parentComponent = getParentComponent();
		Image image = (Image) component;
		if(parentComponent instanceof GridCell){
			GridCell parentCell = (GridCell)getParentComponent();
			parentCell.setIcon(image);
		} else if(parentComponent instanceof ListItem) {
			addImageToListItem((ListItem)parentComponent, image);
		} else if(parentComponent instanceof IllustratedItem) {
			addImageToIllustratedItem((IllustratedItem) parentComponent, image);
		} else if(parentComponent instanceof BillBoard) {
			BillBoard billBoard = (BillBoard)parentComponent;
			billBoard.setImage(image);
		}
		else if(parentComponent instanceof Header) {
			Header header = (Header)parentComponent;
			header.setImage(image);
		}else if(parentComponent instanceof LeftPart) {
			LeftPart leftPart = (LeftPart) parentComponent;
			leftPart.setImage(image);
		}
		else{
			super.addComponentToParent(component);
		}
	}

	private void addImageToIllustratedItem(IllustratedItem illustratedItem,
			Image image) {
		illustratedItem.setImage(image);
	}

	private void addImageToListItem(ListItem listItem, Image image) {
		boolean leftImageInserted = listItem.getLeftImage() != null;
		boolean rightImageInserted = listItem.getRightImage() != null;
		
		if (!leftImageInserted) {
			listItem.setLeftImage(image);
		} else if(!rightImageInserted){
			listItem.setRightImage(image);
		}
	}
	
	

}
