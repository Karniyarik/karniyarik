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

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.mutable.MutableInt;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public class TableHeader extends LeafComponent {
	
	private static final long serialVersionUID = 10L;
	
	private List/*TableColumn*/ columns = new ArrayList();
	
	public TableHeader() {
		// Empty
	}
	
	public List/*TableColumn*/ getColumns() {
		// For safe
		return new ArrayList(columns);
	}
	
	public void setColumns(List columns) {
		
		// Do not pass this.columns to prevent concurrent modification exception
		removeColumns(new ArrayList(this.columns));
				
		if(columns != null) {
			addColumns(columns);
		}
	}
	
	public int getColumnsSize() {
		
		Iterator/*TableColumn*/ cIt = getColumns().iterator();
		int size = 0;
		while(cIt.hasNext()){
			TableColumn column = (TableColumn)cIt.next();
			size = size + column.getColspan();
		}
		
		return size;
	}
		
	// Business methods ***************************************************
	
	protected boolean isChildAllowed(Component child) {
		return (child instanceof TableColumn);
	}
	
	public void accept(final ComponentVisitor visitor) throws ComponentException {
		super.accept(visitor);
		
		Iterator/*Component*/ cIt = columns.iterator();
		while(cIt.hasNext()){
			Component child = (Component)cIt.next();
			child.accept(visitor);
		}
	}

	public Table getTable() {
		return (Table)getParent();
	}
	
	public void addColumns(List/*TableColumn*/ columns) {
		
		for(Iterator it = columns.iterator();it.hasNext();) {
			TableColumn column = (TableColumn)it.next();
			addColumn(column);
		}
	}
	
	public void addColumn(TableColumn column) {
		
		Validate.notNull(column);
		
		if(columns.add(column)){
			column.setParent(this);
		}
	}
	
	public void removeColumns(List/*TableColumn*/ columns) {
		
		for(Iterator it = columns.iterator();it.hasNext();) {
			TableColumn column = (TableColumn)it.next();
			removeColumn(column);
		}
	}

	
	public void removeColumn(TableColumn column) {
		
		Validate.notNull(column);
		
		if(columns.remove(column)){
			column.setParent(null);
		}
	}
	
	public TableColumn getColumnStartingAtIndex(int index) {
		
		TableColumn column = getColumnAtIndex(index);
		
		if(column.getIndex()==index) {
			return column;
		}
		else {
			return null;
		}
	}
	
	public TableColumn getColumnAtIndex(int index) {
		
		TableColumn column = null;
		
		int columnIndex = 0;
		Iterator/*TableColumn*/ columnIt = columns.iterator();
		while(columnIndex <= index && columnIt.hasNext()){
			column = (TableColumn)columnIt.next();
			columnIndex = columnIndex + column.getColspan();
		}
		
		return column;
	}
	
	public int getColumnIndex(TableColumn column) {
		
		Validate.isTrue(columns.contains(column), "The given cell must be child of this row");
		final MutableInt columnIndex = new MutableInt();
		
		List previousColumns = columns.subList(0, columns.indexOf(column));
		CollectionUtils.forAllDo(previousColumns, new Closure(){
			
			public void execute(Object input) {
				TableColumn currentColumn = (TableColumn)input;
				columnIndex.add(currentColumn.getColspan());
			}
		});
		
		return columnIndex.intValue();
	}
	
	// Common methods *****************************************************
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof TableHeader) {
			TableHeader other = (TableHeader)obj;
			builder.appendSuper(super.equals(obj));
			builder.append(columns, other.columns);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}
	
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.appendSuper(super.hashCode());
		
		builder.append(columns);
		
		return builder.toHashCode();
	}
	
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		
		builder.appendSuper(super.toString());
		builder.append(columns);
		
		return builder.toString();
	}
}
