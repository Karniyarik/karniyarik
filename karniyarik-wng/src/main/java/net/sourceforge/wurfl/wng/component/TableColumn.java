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
public class TableColumn extends Component {
	
	private static final long serialVersionUID = 10L;
	
	private String label;
	
	private String shortLabel;
	
	private int colspan = 1;
	
	public TableColumn() {
		// Empty
	}
	
	public TableColumn(String label) {
		this.label = label;
	}
	
	public TableColumn(String label, int colspan) {
		this.label = label;
		this.colspan = colspan;
	}

	public TableColumn(String label, int colspan, String shortLabel) {
		this.label = label;
		this.colspan = colspan;
		this.shortLabel = shortLabel;
	}

	// Access methods *****************************************************
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getShortLabel() {
		return shortLabel;
	}

	public void setShortLabel(String shortLabel) {
		this.shortLabel = shortLabel;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		
		Validate.isTrue(colspan>=1, "The colspan must be >= 1");
		
		this.colspan = colspan;
	}
	
	// Business methods ***************************************************

	public TableHeader getHeader() {
		return (TableHeader)getParent();
	}
	
	public Table getTable() {
		
		if(getHeader()==null) {
			throw new IllegalStateException("The column have no associated header");
		}
		
		return getHeader().getTable();
	}
	
	public int getIndex(){
		
		if(getHeader()==null) {
			throw new IllegalStateException("The column have no associated header");
		}
		
		return getHeader().getColumnIndex(this);
	}
	
	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof TableColumn) {
			
			TableColumn other = (TableColumn) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(label, other.label);
			builder.append(shortLabel, other.shortLabel);
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
		builder.append(label).append(shortLabel).append(colspan);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(label).append(shortLabel).append(colspan);

		return builder.toString();
	}
	
}
