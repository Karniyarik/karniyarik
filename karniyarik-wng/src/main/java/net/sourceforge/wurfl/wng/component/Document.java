/**
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
 * @version $Id: CssComponent.java 1132 2009-03-26 15:28:41Z filippo.deluca $
 */
public class Document extends Component {

	private static final long serialVersionUID = 10L;

	public static final String DEFAULT_ENCODING = "UTF-8";

	private String encoding;

	private Head head = new Head();

	private Body body = new Body();

	public Document() {
		this(DEFAULT_ENCODING);
	}

	public Document(String encoding) {
		Validate.notEmpty(encoding, "encoding must not be null or empty");
		this.encoding = encoding;
		this.head.setParent(this);
		this.body.setParent(this);
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {

		Validate.notNull(head, "head must not be null");

		if (this.head != null) {
			this.head.setParent(null);
		}

		head.setParent(this);
		this.head = head;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {

		Validate.notNull(body, "body must not be null");

		if (this.body != null) {
			this.body.setParent(null);
		}

		body.setParent(this);
		this.body = body;
	}

	public String getTitle() {
		if (head != null) {
			return head.getTitle();
		} else {
			return null;
		}
	}

	// Business methods ***************************************************

	public void accept(ComponentVisitor visitor) throws ComponentException {
		
		super.accept(visitor);
		
		if (head != null) {
			head.accept(visitor);
		}
		if (body != null) {
			body.accept(visitor);
		}
	}
	
	public void validate() throws ValidationException {

		if (head == null) {
			throw new ValidationException(this, "The head must be not null");
		}
		if (body == null) {
			throw new ValidationException(this, "The body must be not null");
		}
	}

	public void addMeta(Meta meta) {
		Validate.notNull(meta, "meta must not be null");
		try {
			addToHead(meta);
		} catch (InvalidContainmentException e) {
			// it is not possible
			assert false;
		}
	}
	
	public void addScript(String src, String type) {
		Validate.notEmpty(src, "src must not be null or empty");
		Validate.notEmpty(type, "type must not be null or empty");
		try {
			addToHead(new Script(src, type));
		} catch (InvalidContainmentException e) {
			// It is not possible
			assert false;
		}
	}

	public void addToHead(Component component) throws InvalidContainmentException {
		Validate.notNull(component, "component must not be null");
		this.head.add(component);
	}

	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		
		if (obj instanceof Document) {
			builder.appendSuper(super.equals(obj));
			Document other = (Document) obj;

			builder.append(encoding, other.encoding);
			builder.append(head, other.head);
			builder.append(body, other.body);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(encoding);
		builder.append(head);
		builder.append(body);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(encoding);
		builder.append(head);
		builder.append(body);

		return builder.toString();
	}

}
