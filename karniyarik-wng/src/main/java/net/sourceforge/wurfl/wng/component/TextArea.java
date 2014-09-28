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
public class TextArea extends LeafComponent {

	private static final long serialVersionUID = 10L;

	private String name;

	private int rows;
	private int cols;

	private String value;
	private String title;

	
	public TextArea() {
		// Empty
	}
	
	public TextArea(String name, int rows, int cols) {
		
		Validate.notEmpty(name, "name must not be null or empty");
		Validate.isTrue(rows > 0, "rows must be a positive integer");
		Validate.isTrue(cols > 0, "rows must be a positive integer");
		
		this.name = name;
		this.rows = rows;
		this.cols = cols;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	// Commons methods ****************************************************
	
	
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof TextArea) {
			TextArea other = (TextArea) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(name, other.name);
			builder.append(rows, other.rows);
			builder.append(cols, other.cols);
			builder.append(value, other.value);
			builder.append(title, other.title);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(name);
		builder.append(rows);
		builder.append(cols);
		
		builder.append(value);
		builder.append(title);


		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(name);
		builder.append(rows);
		builder.append(cols);
		
		builder.append(value);
		builder.append(title);

		return builder.toString();
	}

}
