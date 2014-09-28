/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sourceforge.wurfl.core.MarkUp;
import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.Component;
import net.sourceforge.wurfl.wng.component.Head;
import net.sourceforge.wurfl.wng.component.IllustratedItem;
import net.sourceforge.wurfl.wng.component.Image;
import net.sourceforge.wurfl.wng.component.Link;
import net.sourceforge.wurfl.wng.component.Meta;
import net.sourceforge.wurfl.wng.component.RackMenu;
import net.sourceforge.wurfl.wng.component.Text;
import net.sourceforge.wurfl.wng.component.TextArea;
import net.sourceforge.wurfl.wng.renderer.template.TemplateMarkupWriter;
import net.sourceforge.wurfl.wng.renderer.template.TemplateProcessor;
import net.sourceforge.wurfl.wng.renderer.template.TemplateProvider;
import net.sourceforge.wurfl.wng.renderer.template.st.STTemplateProcessor;
import net.sourceforge.wurfl.wng.renderer.template.st.STTemplateProvider;
import net.sourceforge.wurfl.wng.resource.ClassPathResourceLoader;
import net.sourceforge.wurfl.wng.resource.ResourceLoader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.easymock.classextension.EasyMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * 
 * @author fanta
 * @testng.test groups='func'
 */
public class DefaultComponentRendererTest {

	private ComponentRenderer defaultComponentRenderer;
	private RenderingContext renderingContext;
	private WNGDevice device;

	private static final String TEMPLATE_CLASSPATH = "net/sourceforge/wurfl/wng/renderer/st";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @testng.before-class
	 */
	protected void init() {
		ResourceLoader templatesLoader = new ClassPathResourceLoader(
				TEMPLATE_CLASSPATH);
		TemplateProvider templateProvider = new STTemplateProvider(
				templatesLoader, "xhtmladvanced");
		TemplateProcessor templateProcessor = new STTemplateProcessor();

		MarkupWriter markupWriter = new TemplateMarkupWriter(templateProvider,
				templateProcessor);

		defaultComponentRenderer = new DefaultComponentRenderer(markupWriter);

		device = (WNGDevice) EasyMock.createNiceMock(WNGDevice.class);
		// EasyMock.expect(resourceFinder.find(id)).andReturn(templateResource);
		EasyMock.expect(device.getMarkUp()).andReturn(MarkUp.XHTML_ADVANCED)
				.anyTimes();
		EasyMock.expect(device.getPreferredMimeType()).andReturn("").anyTimes();

		renderingContext = (RenderingContext) EasyMock
				.createNiceMock(RenderingContext.class);
		EasyMock.expect(renderingContext.getDevice()).andReturn(device)
				.anyTimes();
		EasyMock.expect(
				renderingContext.getRenderer((Component) EasyMock.anyObject()))
				.andReturn(defaultComponentRenderer).anyTimes();

		EasyMock.replay(new Object[] { device, renderingContext });
	}

	public void shouldRenderAnImage() {
		Image image = new Image("images/foo.gif");
		assertThatTagExistsWithContent(renderComponent(image), "img", "src");
	}

	public void shouldRenderAnImageWithLink() {
		Image image = new Image("wow image");
		Link link = new Link("http://www.example.com", "example.com");
		image.setLink(link);
		assertThatTagExistsWithContent(renderComponent(image), "a", "<img");
	}

//	public void shouldRenderANavigationBar() {
//		NavigationBar navigationBar = new NavigationBar(" || ");
//		navigationBar.add(new Link("http://www.example.com", "example.com"));
//		navigationBar.add(new Link("http://www.example2.com", "example2.com"));
//		navigationBar.add(new Link("http://www.example3.com", "example3.com"));
//		showOutput(navigationBar);
//		assertThatTagExistsWithContent(renderComponent(navigationBar), "div",
//				"||");
//
//	}

	public void shouldRenderARackMenu() throws Exception {

		RackMenu rackMenu = new RackMenu();
		rackMenu.add(new Link("http://www.example.com", "example.com"));
		rackMenu.add(new Link("http://www.example2.com", "example2.com"));
		rackMenu.add(new Link("http://www.example3.com", "example3.com"));

		rackMenu.add(new Link("http://www.example4.com", "example4.com"));

	}

	public void shoudlRenderTheHead() throws Exception {

		Head head = new Head();
		head.setTitle("documentTitle");
		head.add(Meta.createHttpEquivMeta("x", "y"));
		head.add(Meta.createNamedMeta("MobileOptimized", "770"));

		new AssertTag(renderComponent(head)).isTypeOf("head").hasContent(
				"title").hasContent("documentTitle");

	}

//	public void shoudRenderListItem() {
//
//		ListItem listItem = new ListItem();
//		listItem.setLeftImage(new Image("src/leftImage"));
//		listItem.setRightImage(new Image("src/rightImage"));
//		listItem.setMainLink(new Link("http://www.example.com", "example.com"));
//		listItem.add(new Text("content"));
//
//		// expect().element("").withNoAttributes().withInnerContent(content);
//		new AssertTag(renderComponent(listItem)).isTypeOf("div").hasContent(
//				"src/leftImage").hasContent("content");
//
//	}

	public void shoudRenderIllustratedItem() throws Exception {

		IllustratedItem illustratedItem = new IllustratedItem();
		illustratedItem.setImage(new Image("illustrated/image"));
		illustratedItem.add(new Text("text"));
		new AssertTag(renderComponent(illustratedItem)).isTypeOf("div")
				.hasContent("illustrated/image");
	}

	private static class AssertTag {
		private final String markup;

		private String buildAttribute(String name, String value) {
			return new StrBuilder().append(name).append("=\"").append(value)
					.append("\"").toString();
		}

		public AssertTag(String markup) {
			Assert.assertTrue(StringUtils.isNotBlank(markup));
			this.markup = markup;
		}

		public AssertTag hasAttribute(String name, String value) {
			String nameValue = buildAttribute(name, value);
			Assert.assertTrue(markup.indexOf(nameValue) > -1,
					lookingFor(nameValue));
			return this;
		}

		private String lookingFor(String nameValue) {
			return new StrBuilder().append("looking for : {").append(nameValue)
					.append("} in ").append(markup).toString();
		}

		public AssertTag isTypeOf(String element) {

			return this;
		}

		public AssertTag hasContent(String content) {
			Assert.assertTrue(markup.indexOf(content) > -1);
			return this;
		}

		public AssertTag hasAttributes(Map attributes) {
			Assert.assertNotNull(attributes);
			for (Iterator it = attributes.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Entry) it.next();
				String nameValue = buildAttribute((String) entry.getKey(),
						(String) entry.getValue());
				Assert.assertTrue(markup.indexOf(nameValue) > -1);
			}

			return this;
		}

	}

	public void shoudRenderTextArea() {
		TextArea textArea = new TextArea("name", 5, 20);
		assertThatTagExistsWithContent(renderComponent(textArea), "textarea", "name");
	}

	private String renderComponent(Component component) {
		RenderedComponent rc = defaultComponentRenderer.renderComponent(
				component, renderingContext);
		return rc.getMarkup();
	}

	private void showOutput(Component component) {
		RenderedComponent rc = defaultComponentRenderer.renderComponent(
				component, renderingContext);
		logger
				.debug("Component:{}: \n{}", component.getClass(), rc
						.getMarkup());
	}

	private void assertThatTagExistsWithContent(String markUp, String tag,
			String content) {
		assertTagExist(markUp, tag);
		Assert.assertTrue(markUp.contains(content), content);
	}

	private void assertTagExist(String markUp, String tag) {
		Assert.assertTrue(markUp.contains("<" + tag));
		String endTag = "</" + tag + ">";
		String emptyEndTag = "/>";
		Assert.assertTrue(markUp.contains(endTag) || markUp.contains(emptyEndTag), markUp);
	}

}
