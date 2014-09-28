/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import net.sourceforge.wurfl.core.MarkUp;
import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.CompositeComponent;
import net.sourceforge.wurfl.wng.component.Document;
import net.sourceforge.wurfl.wng.component.Form;
import net.sourceforge.wurfl.wng.component.GridMenu;
import net.sourceforge.wurfl.wng.component.Hr;
import net.sourceforge.wurfl.wng.component.IllustratedItemNoWrap;
import net.sourceforge.wurfl.wng.component.RackMenu;
import net.sourceforge.wurfl.wng.component.StyleContainer;
import net.sourceforge.wurfl.wng.component.Submit;
import net.sourceforge.wurfl.wng.component.Table;
import net.sourceforge.wurfl.wng.component.TableCell;
import net.sourceforge.wurfl.wng.component.TableColumn;
import net.sourceforge.wurfl.wng.component.TableHeader;
import net.sourceforge.wurfl.wng.component.TableRow;
import net.sourceforge.wurfl.wng.renderer.template.TemplateMarkupWriter;
import net.sourceforge.wurfl.wng.renderer.template.TemplateProcessor;
import net.sourceforge.wurfl.wng.renderer.template.TemplateProvider;
import net.sourceforge.wurfl.wng.renderer.template.st.STTemplateProcessor;
import net.sourceforge.wurfl.wng.renderer.template.st.STTemplateProvider;
import net.sourceforge.wurfl.wng.renderer.wml.FormRenderer;
import net.sourceforge.wurfl.wng.renderer.xhtmladvanced.GridMenuRenderer;
import net.sourceforge.wurfl.wng.renderer.xhtmladvanced.RackMenuRenderer;
import net.sourceforge.wurfl.wng.renderer.xhtmlsimple.StyleContainerRenderer;
import net.sourceforge.wurfl.wng.resource.ClassPathResourceLoader;
import net.sourceforge.wurfl.wng.resource.ResourceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class DefaultRendererGroupResolver implements RendererGroupResolver {

	private static final String TEMPLATE_CLASSPATH = "net/sourceforge/wurfl/wng/renderer/st";

	private RendererGroup wmlRenderGroup;
	private RendererGroup xhtmlAdvancedRenderGroup;
	private RendererGroup xhtmlSimpleRenderGroup;

	private final Logger logger = LoggerFactory
			.getLogger(DefaultRendererGroupResolver.class);

	public DefaultRendererGroupResolver() {

		wmlRenderGroup = createWmlRenderGroup();
		xhtmlAdvancedRenderGroup = createXhtmlAdvancedRenderGroup();
		xhtmlSimpleRenderGroup = createXhtmlSimpleRenderGroup();
	}

	public RendererGroup resolveRendererGroup(WNGDevice device) {

		MarkUp markUp = device.getMarkUp();
		logger.debug("MaurkuP is {}", markUp);

		if (MarkUp.XHTML_ADVANCED.equals(markUp)) {
			return xhtmlAdvancedRenderGroup;
		}
		if (MarkUp.XHTML_SIMPLE.equals(markUp)) {
			return xhtmlSimpleRenderGroup;
		}

		return wmlRenderGroup;
	}

	private RendererGroup createXhtmlSimpleRenderGroup() {
		ResourceLoader templatesLoader = new ClassPathResourceLoader(
				TEMPLATE_CLASSPATH);
		TemplateProvider templateProvider = new STTemplateProvider(
				templatesLoader, "xhtmlsimple");
		TemplateProcessor templateProcessor = new STTemplateProcessor();

		MarkupWriter markupWriter = new TemplateMarkupWriter(templateProvider,
				templateProcessor);

		ComponentRenderer defaultComponentRenderer = new DefaultComponentRenderer(
				markupWriter);

		DefaultRendererGroup renderGroup = new DefaultRendererGroup("xhtmlsimple");
		renderGroup.setDefaultRenderer(defaultComponentRenderer);

		// Table
		renderGroup.registerRenderer(Table.class, new TableRenderer(markupWriter));
		renderGroup.registerRenderer(TableHeader.class, NullComponentRenderer.INSTACE);
		renderGroup.registerRenderer(TableColumn.class, NullComponentRenderer.INSTACE);
		renderGroup.registerRenderer(TableRow.class, new TableRowRenderer(markupWriter));
		renderGroup.registerRenderer(TableCell.class, new TableCellRenderer(markupWriter));

		renderGroup.registerRenderer(Document.class, new DocumentComponentRenderer(markupWriter));
		renderGroup.registerRenderer(StyleContainer.class, new StyleContainerRenderer(markupWriter));
		
		renderGroup.registerRenderer(IllustratedItemNoWrap.class,new IllustratedItemNoWrapRenderer(markupWriter));

		
		renderGroup.registerRenderer(CompositeComponent.class, new CompositeComponentRenderer(markupWriter));

		return renderGroup;
	}

	private RendererGroup createXhtmlAdvancedRenderGroup() {
		ResourceLoader templatesLoader = new ClassPathResourceLoader(TEMPLATE_CLASSPATH);
		TemplateProvider templateProvider = new STTemplateProvider(	templatesLoader, "xhtmladvanced");
		TemplateProcessor templateProcessor = new STTemplateProcessor();

		MarkupWriter markupWriter = new TemplateMarkupWriter(templateProvider,
				templateProcessor);

		ComponentRenderer defaultComponentRenderer = new DefaultComponentRenderer(
				markupWriter);

		DefaultRendererGroup renderGroup = new DefaultRendererGroup(
				"xhtmladvanced");
		renderGroup.setDefaultRenderer(defaultComponentRenderer);

		renderGroup.registerRenderer(Table.class, new TableRenderer(markupWriter));
		renderGroup.registerRenderer(TableHeader.class, new TableHeaderRenderer(markupWriter));
		renderGroup.registerRenderer(TableColumn.class, new TableColumnRenderer(markupWriter));
		renderGroup.registerRenderer(TableRow.class, new TableRowRenderer(markupWriter));
		renderGroup.registerRenderer(TableCell.class, new TableCellRenderer(markupWriter));

		renderGroup.registerRenderer(Document.class, new DocumentComponentRenderer(markupWriter));

		renderGroup.registerRenderer(IllustratedItemNoWrap.class,new IllustratedItemNoWrapRenderer(markupWriter));

		renderGroup.registerRenderer(GridMenu.class, new GridMenuRenderer(markupWriter));
		renderGroup.registerRenderer(RackMenu.class, new RackMenuRenderer(markupWriter));
		
		renderGroup.registerRenderer(CompositeComponent.class, new CompositeComponentRenderer(markupWriter));

		return renderGroup;
	}

	private RendererGroup createWmlRenderGroup() {
		ResourceLoader templatesLoader = new ClassPathResourceLoader(
				TEMPLATE_CLASSPATH);
		TemplateProvider templateProvider = new STTemplateProvider(
				templatesLoader, "wml");
		TemplateProcessor templateProcessor = new STTemplateProcessor();

		MarkupWriter markupWriter = new TemplateMarkupWriter(templateProvider,
				templateProcessor);

		ComponentRenderer defaultComponentRenderer = new DefaultComponentRenderer(
				markupWriter);

		DefaultRendererGroup renderGroup = new DefaultRendererGroup("wml");
		renderGroup.setDefaultRenderer(defaultComponentRenderer);

		renderGroup.registerRenderer(Document.class,
				new DocumentComponentRenderer(markupWriter));

		renderGroup.registerRenderer(Table.class, new TableRenderer(markupWriter));
		renderGroup.registerRenderer(TableHeader.class, NullComponentRenderer.INSTACE);
		renderGroup.registerRenderer(TableColumn.class, NullComponentRenderer.INSTACE);
		renderGroup.registerRenderer(TableRow.class, new TableRowRenderer(markupWriter));
		renderGroup.registerRenderer(TableCell.class, new TableCellRenderer(markupWriter));

		renderGroup.registerRenderer(IllustratedItemNoWrap.class,new IllustratedItemNoWrapRenderer(markupWriter));

		renderGroup.registerRenderer(Form.class, new FormRenderer(markupWriter));
		renderGroup.registerRenderer(StyleContainer.class,NullComponentRenderer.INSTACE);
		renderGroup.registerRenderer(Submit.class,NullComponentRenderer.INSTACE);
		renderGroup.registerRenderer(Hr.class, NullComponentRenderer.INSTACE);
		
		renderGroup.registerRenderer(CompositeComponent.class, new CompositeComponentRenderer(markupWriter));
		
		return renderGroup;
	}

}
