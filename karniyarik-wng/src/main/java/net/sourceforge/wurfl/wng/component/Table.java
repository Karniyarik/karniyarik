/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class Table extends LeafComponent {

	private static final long serialVersionUID = 10L;
	
	private TableHeader header;
	
	private List/*TableRow*/ rows = new ArrayList();
	
	public Table() {
		// Empty
	}
	
	public TableHeader getHeader() {
		return header;
	}

	public void setHeader(TableHeader header) {
		
		if(this.header != null) {
			this.header.setParent(null);
		}
		
		if(header!=null) {
			header.setParent(this);
		}
		
		this.header = header;
	}

	public List/*TableRow*/ getRows() {
		
		// defensive programming
		return new ArrayList(rows);
	}

	public void setRows(List/*TableRow*/ rows) {
		
		removeRows(new ArrayList(this.rows));
		
		if(rows!=null) {
			addRows(rows);
		}
	}
	
	// Business methods ***************************************************
	
	public void addRows(List/*TableRow*/ rows) {
		
		Validate.notNull(rows, "the row must be not null");
		Validate.noNullElements(rows, "the row must not contains null elements");
		
		for(Iterator it = rows.iterator(); it.hasNext();) {
			TableRow row = (TableRow)it.next();
			addRow(row);
		}
	}
	
	public void addRow(TableRow row) {
		
		Validate.notNull(row, "the row is null");
		
		if(rows.add(row)){
			row.setParent(this);
		}
	}
	
	public void removeRows(List/*TableRow*/ rows) {
		
		Validate.notNull(rows, "the row must be not null");
		Validate.noNullElements(rows, "the row must not contains null elements");
		
		for(Iterator it = rows.iterator(); it.hasNext();) {
			TableRow row = (TableRow)it.next();
			removeRow(row);
		}
	}
	
	public void removeRow(TableRow row) {
		
		Validate.notNull(row, "the row is null");
		if(rows.remove(row)) {
			row.setParent(null);
		}
	}
		
	public boolean isLastRow(TableRow row) {
		
		Validate.isTrue(rows.contains(row), "The given row must be child of this table");

		return rows.indexOf(row) == rows.size() -1;
	}
	
	public void accept(final ComponentVisitor visitor) throws ComponentException {
		super.accept(visitor);
		
		if(header!=null){
			header.accept(visitor);
		}
		
		Iterator/*Component*/ cIt = rows.iterator();
		while(cIt.hasNext()){
			Component child = (Component)cIt.next();
			child.accept(visitor);
		}
	}
	
	
	public void validate() throws ValidationException {
		super.validate();
		if(this.header == null) {
			throw new ValidationException(this, "You need to set the header for the table");
		}
	}
	
	// Commons methods ****************************************************
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof Table) {
			Table other = (Table)obj;
			builder.appendSuper(super.equals(obj));
			builder.append(header, other.header);
			builder.append(rows, other.rows);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}
	
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.appendSuper(super.hashCode());
		
		builder.append(header).append(rows);
		
		return builder.toHashCode();
	}
	
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		
		builder.appendSuper(super.toString());
		builder.append(header).append(rows);
		
		return builder.toString();
	}



}
