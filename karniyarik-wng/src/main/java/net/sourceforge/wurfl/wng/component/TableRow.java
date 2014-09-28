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
public class TableRow extends LeafComponent {

	private static final long serialVersionUID = 10L;
	
	private List/*TableCell*/ cells = new ArrayList();
	
	public TableRow() {
		// Empty
	}
	
	public List/*TableCell*/ getCells() {
		
		// Defensive programming
		return new ArrayList(cells);
	}
	
	public void setCells(List/*TableCell*/ cells) {
		
		
		assert cells != null : "this cells must be not null";
		removeCells(new ArrayList(this.cells));
		
		if(cells!=null) {
			addCells(cells);
		}
	}
	
	// Business methods ***************************************************
	
	public void accept(final ComponentVisitor visitor) throws ComponentException {
		super.accept(visitor);
		
		Iterator/*Component*/ cIt = cells.iterator();
		while(cIt.hasNext()){
			Component child = (Component)cIt.next();
			child.accept(visitor);
		}
	}
	
	public Table getTable() {
		return (Table)getParent();
	}
			
	public boolean isLast() {
		
		if(getTable() == null) {
			throw new IllegalStateException("The row have no table associated.");
		}
		
		return getTable().isLastRow(this);
	}
	
	public void addCells(List/*TableCell*/ cells) {
		
		Validate.notNull(cells, "The cells must be not null");
		Validate.noNullElements(cells, "The cells must not contain null elements");
		
		
		for(Iterator it = cells.iterator();it.hasNext();) {
			TableCell cell = (TableCell)it.next();
			addCell(cell);
		}
	}
	
	public void addCell(TableCell cell) {
		
		Validate.notNull(cell);
		if(cells.add(cell)){
			cell.setParent(this);
		}
	}
	
	public void removeCells(List/*TableCell*/ cells) {
		
		Validate.notNull(cells, "The cells must be not null");
		Validate.noNullElements(cells, "The cells must not contain null elements");
		
		
		for(Iterator it = cells.iterator();it.hasNext();) {
			TableCell cell = (TableCell)it.next();
			removeCell(cell);
		}
	}
	
	public void removeCell(TableCell cell) {
		Validate.notNull(cell);
		if(cells.remove(cell)){
			cell.setParent(null);
		}
	}
	
	public int getColumnIndex(TableCell cell) {
		
		Validate.isTrue(cells.contains(cell), "The given cell must be child of this row");
		final MutableInt column = new MutableInt();
		
		List previousCells = cells.subList(0, cells.indexOf(cell));
		CollectionUtils.forAllDo(previousCells, new Closure(){
			
			public void execute(Object input) {
				TableCell currentCell = (TableCell)input;
				column.add(currentCell.getColspan());
			}
		});
		
		return column.intValue();
	}
	
	public boolean isLastCell(TableCell cell) {
		
		Validate.isTrue(cells.contains(cell), "The given cell must be child of this row");
		
		return cells.indexOf(cell) == cells.size()-1;
	}
	
	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		
		EqualsBuilder builder = new EqualsBuilder();
		
		if(obj instanceof TableRow) {
			TableRow other = (TableRow)obj;
			builder.appendSuper(super.equals(obj));
			builder.append(cells, other.cells);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}
	
	public int hashCode() {
		
		HashCodeBuilder builder = new HashCodeBuilder();
		
		builder.appendSuper(super.hashCode());
		builder.append(cells);
		
		return builder.toHashCode();
	}
	
	public String toString() {
		
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append(cells);
		
		return builder.toString();
	}
}
