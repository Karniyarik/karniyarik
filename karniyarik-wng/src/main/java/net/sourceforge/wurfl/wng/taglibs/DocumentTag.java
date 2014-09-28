/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.sourceforge.wurfl.wng.Constants;
import net.sourceforge.wurfl.wng.component.Document;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: DocumentTag.java 1131 2009-03-26 15:25:54Z filippo.deluca $
 */
public class DocumentTag extends BaseTag {

	private static final long serialVersionUID = 10L;
	
	private static final Log log = LogFactory.getLog(DocumentTag.class);

	private String encoding;
	
	protected void handleStart() throws JspException {
		
		Document document = new Document();
		logger.debug("Document created");
		
		if(StringUtils.isNotBlank(encoding)){
			document.setEncoding(encoding);
		}

		BuildingContext context = createBuildingContext();
		pushBuildingContext(context);
		
		context.pushComponent(document);
	}
	
	protected void handleEnd() throws JspException {
		BuildingContext context = pollBuildingContext();
		Document document = (Document)context.pollComponent();		
		
		log.debug("Setting document: " + document);
		pageContext.setAttribute(Constants.ATT_DOCUMENT, document, PageContext.REQUEST_SCOPE);

		encoding = null;
	}

	public void release() {
		super.release();
		this.encoding = null;
	}
	
	protected BuildingContext createBuildingContext() {
		
		HttpServletRequest httpRequest = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse httpResponse = (HttpServletResponse) pageContext.getResponse();

		BuildingContext context = new BuildingContext(httpRequest, httpResponse);
		
		logger.debug("Buildingcontext created");
		
		return context;
	}

	private void pushBuildingContext(BuildingContext context) {
		
		pageContext.setAttribute(ATT_DOCUMENT_CONTEXT, context,
				PageContext.REQUEST_SCOPE);
		
		logger.debug("Buildingcontext pushed");
	}
	
	private BuildingContext pollBuildingContext() {
		
		BuildingContext context =  (BuildingContext)pageContext.getAttribute(ATT_DOCUMENT_CONTEXT, PageContext.REQUEST_SCOPE);
		pageContext.removeAttribute(ATT_DOCUMENT_CONTEXT, PageContext.REQUEST_SCOPE);		
	
		logger.debug("Buildingcontext polled");
		
		return context;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
