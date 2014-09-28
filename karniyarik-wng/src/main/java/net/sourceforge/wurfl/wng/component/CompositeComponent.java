/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id: CompositeComponent.java 1132 2009-03-26 15:28:41Z
 *          filippo.deluca $
 */
public abstract class CompositeComponent extends Component {

	private static final long serialVersionUID = 10L;

	private List/* Component */children = new ArrayList();

	public CompositeComponent() {
		// Empty
	}

	// business methods ***************************************************

	public void accept(final ComponentVisitor visitor) throws ComponentException {
		super.accept(visitor);
		
		Iterator/*Component*/ cIt = children.iterator();
		while(cIt.hasNext()){
			Component child = (Component)cIt.next();
			child.accept(visitor);
		}
	}
	
	public void add(Component component) throws InvalidContainmentException {
		Validate.notNull(component);

		if (!isChildAllowed(component)) {
			throw new InvalidContainmentException(this, component);
		}
		
		children.add(component);
		component.setParent(this);
	}

	public void remove(Component child) {

		Validate.notNull(child, "child is null");

		child.setParent(null);
		children.remove(child);
	}

	public List/*Component*/ getChildren() {
		return children;
	}

	public void setChildren(List/*Component*/ children) {
		if (children == null) {
			this.children = new ArrayList();
		} else {
			Validate.allElementsOfType(children, Component.class);
			this.children = children;
		}
	}

	// Support methods ****************************************************

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	protected boolean isChildAllowed(Component child) {
		return false;
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof CompositeComponent) {
			builder.appendSuper(super.equals(obj));
			CompositeComponent other = (CompositeComponent) obj;

			builder.appendSuper(super.equals(obj));
			builder.append(children, other.children);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		// XXX children or ListUtils.hashCodeForList(children)?
		builder.appendSuper(super.hashCode());
		builder.append(ListUtils.hashCodeForList(children));

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(children);

		return builder.toString();
	}

}
