/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class Form extends CompositeComponent {

	private static final long serialVersionUID = 10L;

	private String action;

	private String method;

	public static final String METHOD_GET = "get";
	public static final String METHOD_POST = "post";

	public Form() {
		this.method = METHOD_GET;
	}

	public Form(String action) {
		this(action, METHOD_GET);
	}

	public Form(String action, String method) {		
		this.action = action;
		this.method = method;
	}

	public String getAction() {
		return this.action;
	}
	
	public void setAction(String action) {
		
		Validate.notNull(action, "The action must be not null");
		
		this.action = action;
	}

	public String getMethod() {
		return this.method;
	}
	
	public void setMethod(String method) {
		
		Validate.isTrue(METHOD_GET.equals(method) || METHOD_POST.equals(method),"The methods must be one of {get} or {post}");

		this.method = method;
	}
	
	// Business methods ***************************************************
	
	public Submit getSubmit() {
		return (Submit)CollectionUtils.find(getChildren(), SubmitComponent.INSTANCE);
	}

	public Collection getInputComponents() {
		return CollectionUtils.select(getChildren(), InputComponent.INSTANCE);
	}
	
	private static class SubmitComponent implements Predicate {
		public static final SubmitComponent INSTANCE = new SubmitComponent();
		public boolean evaluate(Object object) {
			return object instanceof Submit; 
		}
		
	}
	
	private static class InputComponent implements Predicate {
		public static final InputComponent INSTANCE = new  InputComponent();
		public boolean evaluate(Object object) {
			return ((object instanceof BaseInput) || (object instanceof Select) || (object instanceof TextArea)) &&  !(object instanceof Submit) ; 
		}
		
	}

	// Business methods ***************************************************
	
	protected boolean isChildAllowed(Component child) {

		return child instanceof BaseInput || child instanceof Select
				|| child instanceof Link || child instanceof Text
				|| child instanceof TextArea || child instanceof Br
				|| child instanceof Hr;
	}
		

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof Form) {
			builder.appendSuper(super.equals(obj));
			
			Form other = (Form) obj;
			builder.append(action, other.action).append(method, other.method);
		}
		else {
			builder.appendSuper(false);
		}


		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode()).append(action).append(method);

		return builder.toHashCode();
	}

	public String toString() {

		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(action).append(method);

		return builder.toString();
	}



}
