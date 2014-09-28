/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public class TableCell extends CompositeComponent {
	
	private static final long serialVersionUID = 10L;
	
	private int colspan = 1;
	
	private String text;
	
	private String shortText;
	
	private boolean summary = false;
	
	public TableCell() {
		// Empty
	}
	
	public TableCell(int colspan) {
		
		Validate.isTrue(colspan>=1, "The colspan must be >= 1");
		
		this.colspan = colspan;
	}
	
	public TableCell(String text) {
		this.text = text;
	}
	
	public TableCell(String text, int colspan) {
		
		Validate.isTrue(colspan>=1, "The colspan must be >= 1");
		
		this.text = text;
		this.colspan = colspan;
	}

	public TableCell(String text, int colspan, String shortText) {
		
		Validate.isTrue(colspan>=1, "The colspan must be >= 1");
		
		this.text = text;
		this.colspan = colspan;
		this.shortText = shortText;
	}

	public int getColspan() {
		return colspan;
	}
	
	public void setColspan(int colspan) {
		
		Validate.isTrue(colspan>=1, "The colspan must be >= 1");

		this.colspan = colspan;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getShortText() {
		return shortText;
	}

	public void setShortText(String shortText) {
		this.shortText = shortText;
	}
	
	public void setSummary(boolean summary) {
		this.summary = summary;
	}
	
	public boolean isSummary() {
		return summary;
	}
	
	// Business methods ***************************************************
	
	public TableColumn getStartingColumn() {
		
		int columnIndex = getRow().getColumnIndex(this);
		return getTable().getHeader().getColumnStartingAtIndex(columnIndex);
	}
	
	public TableColumn getColumn() {
		
		int columnIndex = getRow().getColumnIndex(this);
		return getTable().getHeader().getColumnAtIndex(columnIndex);
	}
	
	public TableRow getRow() {
		return (TableRow)getParent();
	}
	
	public Table getTable() {
		
		if(getRow() == null) {
			throw new IllegalStateException("The cell have no row associated.");
		}
		
		return getRow().getTable();
	}
	
	public int getIndex() {
		
		if(getRow() == null) {
			throw new IllegalStateException("The cell have no row associated.");
		}
		
		return getRow().getColumnIndex(this);
	}
	
	public boolean isLast() {
		
		if(getRow() == null) {
			throw new IllegalStateException("The cell have no row associated.");
		}
		
		return getRow().isLastCell(this);
	}
	
	protected boolean isChildAllowed(Component child) {
			
		return (child instanceof Link || 
			child instanceof Image ||
			child instanceof Text);
	}
	
	// Common methods *****************************************************
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof TableCell) {
			TableCell other = (TableCell)obj;
			builder.appendSuper(super.equals(obj));
			builder.append(text, other.text);
			builder.append(shortText, other.shortText);
			builder.append(colspan, other.colspan);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}
	
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		
		builder.appendSuper(super.hashCode());
		builder.append(text);
		builder.append(shortText);
		builder.append(colspan);
		
		return builder.toHashCode();
	}
	
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		
		builder.appendSuper(super.toString());
		builder.append(text);
		builder.append(shortText);
		builder.append(colspan);
		
		return builder.toString();
	}


}
