/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.wurfl.core.Device;
import net.sourceforge.wurfl.core.WURFLHolder;
import net.sourceforge.wurfl.core.WURFLManager;
import net.sourceforge.wurfl.wng.Constants;
import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.ComponentException;
import net.sourceforge.wurfl.wng.component.Document;
import net.sourceforge.wurfl.wng.component.StyleContainer;
import net.sourceforge.wurfl.wng.component.ValidationException;
import net.sourceforge.wurfl.wng.component.ValidatorVisitor;
import net.sourceforge.wurfl.wng.renderer.DefaultDocumentRenderer;
import net.sourceforge.wurfl.wng.renderer.DefaultRendererGroupResolver;
import net.sourceforge.wurfl.wng.renderer.DocumentRenderer;
import net.sourceforge.wurfl.wng.renderer.RenderedDocument;
import net.sourceforge.wurfl.wng.renderer.RendererGroupResolver;
import net.sourceforge.wurfl.wng.style.StyleOptimizerVisitor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.functors.InstanceofPredicate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 */
public class WNGContextFilter implements Filter {

	public static final String PAR_WURFL_HOLDER_KEY = "net.sourceforge.wurfl.core.WURFL_HOLDER_KEY";

	private static final Logger logger = LoggerFactory.getLogger(WNGContextFilter.class);

	private WURFLHolder wurflHolder;

	private DocumentRenderer documentRenderer;
	
	// LifeCycle methods **************************************************

	public void init(FilterConfig config) throws ServletException {

		wurflHolder = createWURFLHolder(config);
		documentRenderer = createDocumentRenderer(config);

		logger.info("WNGContextFilter initialized.");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (isHttpRequest(request)) {
			logger.debug("Handling request");
			
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			
			handle(httpRequest, httpResponse, chain);
		} else {
			logger.debug("Ignoring request");
			chain.doFilter(request, response);
		}

	}

	public void destroy() {
		// Empty
	}
	
	// Business methods ***************************************************
	
	protected void handle(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		try {
			// Resolve device
			WNGDevice device = resolveDevice(request);
			
			// Prepare request
			request.setAttribute("device", device);
			request.setAttribute("capabilities", device.getCapabilities());

			// Chain filters
			BufferedHttpServletResponse bufferedHttpServletResponse = new BufferedHttpResponseWrapper(response);

			chain.doFilter(request, bufferedHttpServletResponse);


			if (isValidRequest(request)) {
				Document document = resolveDocument(request);
				
				StyleContainer styleContainer = (StyleContainer)CollectionUtils.find(document.getHead().getChildren(), new InstanceofPredicate(StyleContainer.class));
				if(styleContainer==null){
					styleContainer = new StyleContainer();
					document.addToHead(styleContainer);
				}
				
				StyleOptimizerVisitor visitor = new StyleOptimizerVisitor(device, styleContainer);
				document.accept(visitor);
				
				RenderedDocument renderedDocument = documentRenderer.renderDocument(document, device);
				
				writeDocument(renderedDocument, response);
			} 
			else {
				bufferedHttpServletResponse.writeTo(response.getOutputStream());
			}

		} catch (ValidationException ve){
			logger.error("The Document is not valid: source: {}, message: {}",ve.getSource(), ve.getMessage());
			throw new ServletException(ve.getMessage());
		}
		catch (Throwable e) {
			logger.error("WNG Exception: " + e.getLocalizedMessage(), e);
			throw new ServletException(e.getMessage());
		} 

	}

	private void writeDocument(RenderedDocument renderedDocument,
			HttpServletResponse response) throws IOException {
 
		response.setContentType(renderedDocument.getContentType());
		response.getWriter().print(renderedDocument.getMarkup());
		response.getWriter().flush();
	}
		
	// Support methods ****************************************************
	
	protected boolean isHttpRequest(ServletRequest request) {
		return request instanceof HttpServletRequest;
	}

	protected boolean isValidRequest(ServletRequest request) {
		return request.getAttribute(Constants.ATT_DOCUMENT) != null;
	}

	protected Document resolveDocument(ServletRequest request) throws ComponentException {

		Document document = (Document) request.getAttribute(Constants.ATT_DOCUMENT);
		
		ValidatorVisitor validatorVisitor = new ValidatorVisitor();
		document.accept(validatorVisitor);

		logger.debug("Resolved document: " + document);

		return document;
	}
	
	protected WNGDevice resolveDevice(HttpServletRequest request) {
		WURFLManager wurflManager = wurflHolder.getWURFLManager();
		Device device = wurflManager.getDeviceForRequest(request);
		return new WNGDevice(device);
	}

	// Factory methods ****************************************************

	protected WURFLHolder createWURFLHolder(FilterConfig config)
			throws ServletException {
		String wurflHolderKey = "net.sourceforge.wurfl.core.WURFLHolder";
		if (!StringUtils.isEmpty(config.getInitParameter(PAR_WURFL_HOLDER_KEY))) {
			wurflHolderKey = config.getInitParameter(PAR_WURFL_HOLDER_KEY);
		}

		Object wurflHolderObject = config.getServletContext().getAttribute(wurflHolderKey);
		if (!(wurflHolderObject instanceof WURFLHolder)) {
			throw new ServletException(
					"WURFLHolder instance not found at key: " + wurflHolderKey);
		}

		return (WURFLHolder) wurflHolderObject;
	}

	protected DocumentRenderer createDocumentRenderer(FilterConfig config) {
		
		RendererGroupResolver rendererGroupResolver=null;
		
		String renderGroupResolverClassName = config.getInitParameter(Constants.PAR_RENDERER_GROUP_RESOLVER);
		if(StringUtils.isNotBlank(renderGroupResolverClassName)) {
			try {
				Class renderGroupResolverClass = Class.forName(renderGroupResolverClassName);
				rendererGroupResolver = (RendererGroupResolver)renderGroupResolverClass.newInstance();
			} catch (Exception e) {
				logger.error("The RenderGroupResolver: {} can not be instanziate: {}", renderGroupResolverClassName, e);
			} 
		}

		if(rendererGroupResolver==null) {
			rendererGroupResolver = new DefaultRendererGroupResolver();
		}
		
		DocumentRenderer defaultDocumentRenderer = new DefaultDocumentRenderer(rendererGroupResolver);
		logger.info("DocumentRenderer initialized");
		
		return defaultDocumentRenderer;
	}	

}
