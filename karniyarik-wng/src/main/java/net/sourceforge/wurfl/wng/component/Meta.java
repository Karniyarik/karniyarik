/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.apache.commons.lang.StringUtils;
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
public class Meta extends LeafComponent {
	
    private static final long serialVersionUID = 10L;

    private String name;

    private String content;

    private String scheme;

    private String httpEquiv;

    public Meta() {
        // Empty
    }

    public Meta(String name, String content, String scheme, String httpEquiv) {
    	this.name = name;
        this.content = content;
        this.scheme = scheme;
        this.httpEquiv = httpEquiv;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
		this.name = name;
	}

    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
		this.content = content;
	}

    public String getScheme() {
        return scheme;
    }
    
    public void setScheme(String scheme) {
		this.scheme = scheme;
	}

    public String getHttpEquiv() {
        return httpEquiv;
    }
    
    public void setHttpEquiv(String httpEquiv) {
		this.httpEquiv = httpEquiv;
	}
    
    // Business methods ***************************************************
    
    public void validate() throws ValidationException {
    	
    	if(StringUtils.isBlank(content)){
    		throw new ValidationException(this, "Content is required");
    	}
    	if(StringUtils.isBlank(httpEquiv) && StringUtils.isBlank(name)) {
    		throw new ValidationException(this, "One of name and htp-equiv is required");
    	}
    	if(StringUtils.isNotBlank(httpEquiv) && StringUtils.isNotBlank(name)) {
    		throw new ValidationException(this, "The name and htp-equiv are mutually esclusive");
    	}    	
    }
    
	public static Meta createHttpEquivMeta(String httpEquiv, String content) {
		Validate.notEmpty(httpEquiv, "httpEquiv must not be null or empty ");
		Validate.notEmpty(content, "content must not be null or empty ");
		
		Meta meta = new Meta();
		meta.setHttpEquiv(httpEquiv);
		meta.setContent(content);
		
		return meta;
	}

	public static Meta createNamedMeta(String name, String content) {
		Validate.notEmpty(name, "name must not be null or empty ");
		Validate.notEmpty(content, "content must not be null or empty ");
		
		Meta meta = new Meta();
		meta.setName(name);
		meta.setContent(content);

		return meta;
	}

    
	// Commons methods ****************************************************

	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();

		if (obj instanceof Meta) {
			Meta other = (Meta) obj;
			builder.appendSuper(super.equals(obj));
			builder.append(name, other.name)
				.append(scheme, other.scheme)
				.append(httpEquiv, other.httpEquiv)
				.append(content, other.content);
		}
		else {
			builder.appendSuper(false);
		}

		return builder.isEquals();
	}

	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.appendSuper(super.hashCode());
		builder.append(name).append(httpEquiv).append(scheme).append(content);

		return builder.toHashCode();
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);

		builder.appendSuper(super.toString());
		builder.append(name).append(httpEquiv).append(scheme).append(content);

		return builder.toString();
	}
	
}
