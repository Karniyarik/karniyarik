/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.taglibs;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.wurfl.wng.Constants;
import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Css;
import net.sourceforge.wurfl.wng.component.StyleContainer;

import org.apache.commons.lang.BooleanUtils;

import edu.emory.mathcs.backport.java.util.Deque;
import edu.emory.mathcs.backport.java.util.LinkedList;

/**
 * This class has the responsibility to support the JSP building process.
 * 
 * @author Filippo De Luca
 * @version $Id$
 */
public class BuildingContext {

	private static final long serialVersionUID = 10L;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private Deque componentsStack = new LinkedList();

	private Css currentCss;

	private boolean insideRackMenuTag = false;

	private StyleContainer styleContainer = null;

	public BuildingContext(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void pushComponent(Component component) {

		if (component instanceof StyleContainer) {
			styleContainer = (StyleContainer) component;
		}

		componentsStack.push(component);
	}

	public Component peekComponent() {
		return (Component) componentsStack.peek();
	}

	public Component pollComponent() {
		return (Component) componentsStack.poll();
	}

	public Css getCurrentCss() {
		return currentCss;
	}

	public void setCurrentCss(Css currentCss) {
		this.currentCss = currentCss;
	}

	public StyleContainer getStyleContainer() {

		if (styleContainer == null) {
			throw new IllegalStateException(
					"StyleContainer not added to document");
		}

		return styleContainer;
	}

	public boolean isInsideRackMenuTag() {
		return insideRackMenuTag;
	}

	public void setInsideRackMenuTag(boolean insideRackMenuTag) {
		this.insideRackMenuTag = insideRackMenuTag;
	}

	public boolean isEncodeURL() {

		// False by default
		boolean encodeURL = false;

		ServletContext context = this.request.getSession().getServletContext();

		if (context.getAttribute(Constants.PAR_ENCODE_URL) == null) {
			// null = false
			encodeURL = BooleanUtils.toBoolean(context
					.getInitParameter(Constants.PAR_ENCODE_URL));
			context.setAttribute(Constants.PAR_ENCODE_URL, Boolean
					.valueOf(encodeURL));
		} else {
			encodeURL = BooleanUtils.toBoolean((Boolean) context
					.getAttribute(Constants.PAR_ENCODE_URL));
		}

		return encodeURL;
	}

	public String encodeURLWhenRequested(String sourceUrl) {

		if (isEncodeURL()) {
			return response.encodeURL(sourceUrl);
		} else {
			return sourceUrl;
		}
	}

}
