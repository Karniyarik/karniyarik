/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import junit.framework.Assert;
import net.sourceforge.wurfl.core.MarkUp;
import net.sourceforge.wurfl.wng.WNGDevice;
import net.sourceforge.wurfl.wng.component.Image;
import net.sourceforge.wurfl.wng.component.Table;
import net.sourceforge.wurfl.wng.component.TableCell;
import net.sourceforge.wurfl.wng.component.TableColumn;
import net.sourceforge.wurfl.wng.component.TableHeader;
import net.sourceforge.wurfl.wng.component.TableRow;
import net.sourceforge.wurfl.wng.component.Text;

import org.easymock.classextension.EasyMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Filippo De Luca
 * @testng.test groups = 'func'
 */
public class TableRendererTest {
	
	private static Logger logger = LoggerFactory.getLogger(TableRendererTest.class);

	private RendererGroupResolver rendererGroupResolver;
	
	/**
	 * @testng.before-class
	 */
	protected void createRendererGroupResolver() {
		rendererGroupResolver = new DefaultRendererGroupResolver();
	}

	/**
	 * @testng.test groups = 'func'
	 */
	public void renderComponentShouldRenderTable() throws Exception {

		WNGDevice device = (WNGDevice) EasyMock.createNiceMock(WNGDevice.class);
		EasyMock.expect(device.getMarkUp()).andReturn(MarkUp.XHTML_ADVANCED).anyTimes();
		EasyMock.expect(device.getPreferredMimeType()).andReturn("").anyTimes();
		

		EasyMock.replay(new Object[] {device});
		
		
		Table table = createTable();
		

		RendererGroup rendererGroup = rendererGroupResolver.resolveRendererGroup(device); 
		ComponentRenderer renderer = rendererGroup.getRenderer(table);
		
		RenderingContext context = new RenderingContext(device, rendererGroup);

		RenderedComponent rendered = renderer.renderComponent(table, context);
		
		//logger.debug("Rendered table: {}", rendered.getMarkup());
		
		org.testng.Assert.assertTrue(rendered.getMarkup().contains("<table"));
	}
	
	/**
	 * @testng.test groups = 'func'
	 */
	public void renderComponentShouldRenderDiv() throws Exception {

		WNGDevice device = (WNGDevice) EasyMock.createNiceMock(WNGDevice.class);
		EasyMock.expect(device.getMarkUp()).andReturn(MarkUp.XHTML_SIMPLE).anyTimes();
		EasyMock.expect(device.getPreferredMimeType()).andReturn("").anyTimes();
		

		EasyMock.replay(new Object[] {device});
		
		
		Table table = createTable();
		

		RendererGroup rendererGroup = rendererGroupResolver.resolveRendererGroup(device); 
		ComponentRenderer renderer = rendererGroup.getRenderer(table);
		
		RenderingContext context = new RenderingContext(device, rendererGroup);

		RenderedComponent rendered = renderer.renderComponent(table, context);
		
		//logger.debug("Rendered table: {}", rendered.getMarkup());
		
		org.testng.Assert.assertTrue(rendered.getMarkup().contains("<div"));
	}
	
	/**
	 * @testng.test groups = 'func'
	 */
	public void renderComponentShouldRenderP() throws Exception {

		WNGDevice device = (WNGDevice) EasyMock.createNiceMock(WNGDevice.class);
		EasyMock.expect(device.getMarkUp()).andReturn(MarkUp.WML).anyTimes();
		EasyMock.expect(device.getPreferredMimeType()).andReturn("").anyTimes();
		

		EasyMock.replay(new Object[] {device});
		
		
		Table table = createTable();
		

		RendererGroup rendererGroup = rendererGroupResolver.resolveRendererGroup(device); 
		ComponentRenderer renderer = rendererGroup.getRenderer(table);		
		RenderingContext context = new RenderingContext(device, rendererGroup);

		RenderedComponent rendered = renderer.renderComponent(table, context);
		
		//logger.debug("Rendered WML table: {}", rendered.getMarkup());
		
		Assert.assertTrue(rendered.getMarkup().contains("<p>"));
	}
	
	private Table createTable() throws Exception {
		Table table = new Table();
		TableHeader header = new TableHeader();
		header.addColumn(new TableColumn("Column 1", 1, "C1"));
		header.addColumn(new TableColumn("Column 234", 3, "C234"));
		header.addColumn(new TableColumn("Column 56", 2, "C56"));
		
		table.setHeader(header);
		
		for(int ri=0; ri< 10; ri++) {
			TableRow row = new TableRow();
			TableCell cell = null;
			
			row.addCell(new TableCell("R" + ri + "_C1"));

			row.addCell(new TableCell("R" + ri + "_C2"));

			cell = new TableCell();
			cell.add(new Text("R" + ri + "_C3"));
			row.addCell(cell);
			
			cell = new TableCell();
			cell.add(new Text("R" + ri + "_C4"));
			cell.add(new Image("/images/test.png"));
			row.addCell(cell);

			row.addCell(new TableCell("R" + ri + "_C56", 2));

			
			table.addRow(row);
		}
		
		TableRow summaryRow = new TableRow();
		summaryRow.addCell(new TableCell("S_C1"));
		TableCell summaryCell = new TableCell("Total234", 3);
		summaryCell.setSummary(true);
		summaryRow.addCell(summaryCell);
		summaryRow.addCell(new TableCell("S_C56"));
		table.addRow(summaryRow);
		
		
		return table;
	}
}
