/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sourceforge.wurfl.wng.component.Component;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public abstract class BaseTag extends BodyTagSupport {
	
    public static final String ATT_DOCUMENT_CONTEXT = "net.sourceforge.wurfl.wng.DOCUMENT_CONTEXT";

	protected static final Logger logger = LoggerFactory.getLogger(BaseTag.class);

	private static final long serialVersionUID = 10L;
	
	
	private String skip;

	private String applies_to;
		
	// Access methods *****************************************************
	
	public void setSkip(String skip) {
		this.skip = skip;
	}

	public void setApplies_to(String applies_to) {
		this.applies_to = applies_to;
	}

	// Lifecycle methods **************************************************
	
	public int doStartTag() throws JspException {        		
		checkAttributes();
		
		boolean skipCondition = evaluateSkipCondition();
		if(!skipCondition){
			
			handleStart();

			return EVAL_BODY_BUFFERED;

		}
		else{
			return SKIP_BODY;
		}		
	}

	protected void checkAttributes() throws JspException {
		
		// Check
		if (StringUtils.isNotBlank(this.applies_to)
				&& StringUtils.isNotBlank(this.skip)) {
			throw new JspException(
					"Can't apply both attributes: applies_to and skip");
		}
	}
	
	/**
	 * @throws JspException
	 */
	protected void handleStart() throws JspException {
		// Empty
	}
	
	public int doEndTag() throws JspException {

		handleEnd();
		
		clearBody();
		
		reset();
		

		return EVAL_PAGE;
	}
	
	protected void handleEnd() throws JspException {
		// Empty
	}
	
	private void clearBody() {
		if (getBodyContent() != null) {
			try {
				getBodyContent().clearBody();

			} catch (Throwable t) {
				logger.warn("Exception clearing body: " + t.getMessage());
			}
		}
	}
	
	public void release() {
		reset();
	}
	
	protected void reset() {
		this.applies_to = null;
		this.skip = null;
	}

	// Support methods ****************************************************
		
	protected Component getParentComponent() {
		return getBuildingContext().peekComponent();
	}
	
	protected BuildingContext getBuildingContext() {
		return (BuildingContext)pageContext.findAttribute(ATT_DOCUMENT_CONTEXT);
	}
	
	protected boolean evaluateSkipCondition() {
		if (StringUtils.isNotBlank(applies_to)) {
			return !BooleanUtils.toBoolean(applies_to);
		}
		
		return BooleanUtils.toBoolean(skip);
	}
}
