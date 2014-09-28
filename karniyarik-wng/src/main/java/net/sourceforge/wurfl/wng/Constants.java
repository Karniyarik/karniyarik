/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng;


/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: Constants.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public final class Constants {

	public static final String ACCEPT_HEADER_VND_WAP_XHTML_XML = "application/vnd.wap.xhtml+xml;charset=UTF-8";

	public static final String ACCEPT_HEADER_XHTML_XML = "application/xhtml+xml;charset=UTF-8";

	public static final String ACCEPT_HEADER_TEXT_HTML = "text/html";

	public static final String WML_CONTENT_TYPE = "text/vnd.wap.wml";
	
	public static final String XHTML_CONTENT_TYPE = "html_wi_oma_xhtmlmp_1_0";

	// Capabilities names *************************************************
	
	public static final String CN_XHTMLMP_PREFERRED_MIME_TYPE = "xhtmlmp_preferred_mime_type";

	public static final String CN_RESOLUTION_WIDTH = "resolution_width";

	public static final String CN_RESOLUTION_HEIGHT = "resolution_height";
	
	public static final String CN_MAX_IMAGE_WIDTH = "max_image_width";

	public static final String CN_MAX_IMAGE_HEIGHT = "max_image_height";
	
	// Capabilities values ************************************************
	
	public static final String CV_WML_1_1 = "wml_1_1";

    public static final String ENCODING = "encoding";
    
    // Attributes *********************************************************

    public static final String ATT_DOCUMENT = "net.sourceforge.wurfl.wng.DOCUMENT";

    public static final String ATT_ENCODE_URL = "net.sourceforge.wurfl.wng.ENCODE_URL";

    // Parameters *********************************************************
    
    public static final String PAR_ENCODE_URL = "net.sourceforge.wurfl.wng.ENCODE_URL";

	public static final String PAR_RENDERER_GROUP_RESOLVER = "net.sourceforge.wurfl.wng.RENDERER_GROUP_RESOLVER";

    private Constants() {
		// Private constructor to avoid
	}
}
