/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class TestUtils {

	private static final Log log = LogFactory.getLog(TestUtils.class);
	
	public static final void testOpenTag(String markup, String name) {

		String regex = new StrBuilder("<").append(name).append("\\b[^>]*>").toString();
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(markup);

		String msg = new StrBuilder("Expected: ").append(regex).append(
				", Found: ").append(markup).toString();
		
		log.info(msg);
		assert matcher.find();

	}


	public static final void testEndOpenTag(String markup) {

		String expected = ">";

		String msg = new StrBuilder("Expected: ").append(expected).append(
				", Found: ").append(markup).toString();
		
		log.info(msg);
		assert StringUtils.contains(markup, expected) : msg;

	}

	public static final void testOpenCloseTag(String markup, String name) {

		String regex = new StrBuilder("<").append(name).append("\\b[^>]*/>").toString();
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(markup);

		String msg = new StrBuilder("Expected: ").append(regex).append(
				", Found: ").append(markup).toString();
		
		log.info(msg);
		assert matcher.find();

	}

	public static final void testStartOpenTag(String markup, String name) {

		String expected = new StrBuilder("<").append(name).append(" ").toString();

		String msg = new StrBuilder("Expected: ").append(expected).append(
				", Found: ").append(markup).toString();
		
		log.info(msg);
		assert StringUtils.contains(markup, expected) : msg;

	}

	public static final void testCloseOpenTag(String markup) {

		String expected = "/>";

		String msg = new StrBuilder("Expected: ").append(expected).append(
				", Found: ").append(markup).toString();
		
		log.info(msg);
		assert StringUtils.contains(markup, expected) : msg;

	}

	public static final void testCloseTag(String markup, String name) {

		String expected = new StrBuilder("</").append(name).append(">")
				.toString();

		String msg = new StrBuilder("Expected: ").append(expected).append(
				", Found: ").append(markup).toString();
		
		log.info(msg);
		assert StringUtils.contains(markup, expected) : msg;

	}

	public static final void testAttribute(String markup, String name,
			String value) {

		if (StringUtils.isNotBlank(value)) {

			String expected = new StrBuilder().append(" ").append(name).append("=\"").append(value)
					.append("\"").toString();

			String msg = new StrBuilder("Expected: ").append(expected).append(
					", Found: ").append(markup).toString();

			log.info(msg);
			assert StringUtils.contains(markup, expected) : msg;
		}

	}
	
	public static final void testCssAttribute(String markup, String name,
			String value) {

		if (StringUtils.isNotBlank(value)) {

			String expected = new StrBuilder(name).append(":").append(value).toString();

			String msg = new StrBuilder("Expected: ").append(expected).append(
					", Found: ").append(markup).toString();
			
			String regex = new StrBuilder(name).append("\\s*:\\s*").append(value).toString();
			Pattern pattern = Pattern.compile(regex);
			
			log.info(msg);
			assert pattern.matcher(markup).find() : msg;
		}

	}
	
	public static final String getAttributeMarkup(String markup, String name){
		
		String regex = new StrBuilder(name).append("\\s*=\\\".*\\\"").toString();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(markup);
		
		if(matcher.find()){
			return markup.substring(matcher.start(), matcher.end());
		}
		else{
			return StringUtils.EMPTY;
		}
	}

	public static final void testBlank(String markup) {

		String msg = new StrBuilder("Expected: EMPTY").append(", Found: ")
				.append(markup).toString();

		log.info(msg);
		assert StringUtils.isBlank(markup) : msg;

	}

	public static final void testcontains(String markup, String test) {
		
		if(StringUtils.isNotBlank(test)){

			String msg = new StrBuilder("Expected: ").append(test).append(", Found: ")
					.append(markup).toString();
	
			log.info(msg);
			assert StringUtils.contains(markup, test) : msg;
		}

	}
	
	public static final void testParity(String markup) {
		
		int gtOccurences = StringUtils.countMatches(markup, ">");
		int ltOccurences = StringUtils.countMatches(markup, "<");
		
		String msg = new StrBuilder("Expected: ").append(ltOccurences).append(", Found: ").append(gtOccurences).toString();
		
		log.info(msg);
		assert StringUtils.countMatches(markup, ">") == StringUtils.countMatches(markup, "<") : msg;

	}
	
	
	// public static final void testGenerateStartTag(WallTag tag){
	//		
	// WallDocument document = new WallDocument();
	//		
	// TagsHandler tagsHandler =
	// (TagsHandler)EasyMock.createNiceMock(TagsHandler.class);
	// DocumentContext documentContext =
	// (DocumentContext)EasyMock.createNiceMock(DocumentContext.class);
	// PageContext pageContext =
	// (PageContext)EasyMock.createNiceMock(PageContext.class);
	// JspWriter writer = (JspWriter)EasyMock.createNiceMock(JspWriter.class);
	//				
	// tag.setPageContext(pageContext);
	// tag.setParent(document);
	//
	// EasyMock.reset(new Object[]{tagsHandler, documentContext, pageContext,
	// writer});
	//
	// EasyMock.expect(documentContext.getWallTagsHandler()).andReturn(tagsHandler);
	// EasyMock.expect(pageContext.getOut()).andReturn(writer);
	// EasyMock.expect(tagsHandler.generateBrStartTag(tag)).andReturn("test");
	// EasyMock.expect(pageContext.findAttribute(WallConstants.ATT_DOCUMENT_CONTEXT)).andReturn(documentContext);
	//		
	// writer.print("test");
	//		
	// EasyMock.replay(new Object[]{tagsHandler, documentContext, pageContext,
	// writer});
	//
	// int exitStatus = tag.generateStartTag();
	//		
	// EasyMock.verify(new Object[]{tagsHandler, documentContext, pageContext,
	// writer});
	//		
	// String msg = new
	// StrBuilder("Expected: ").append(TagSupport.SKIP_BODY).append(", Found: ").append(exitStatus).toString();
	//
	// log.info(msg);
	//		
	// assert exitStatus == TagSupport.SKIP_BODY : msg;
	// }

}
