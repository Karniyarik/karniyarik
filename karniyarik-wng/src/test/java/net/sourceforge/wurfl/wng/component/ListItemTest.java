/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

import org.testng.Assert;

/**
 * 
 * @author fanta
 *
 * @testng.test groups = "unit";
 */
public class ListItemTest extends BaseTest {
		
	private ListItem listItem;
	
	/**
	 * @testng.before-class
	 */
	protected void createListItem() {
		this.listItem = new ListItem();
	}
	
	
	
	public void shouldSetAMainLink() {
		Link link = new Link("href");
		this.listItem.setMainLink(link);
		org.testng.Assert.assertEquals(link, listItem.getMainLink());
	}
	
	public void shoulSetLeftImage() {
		Image image = new Image();
		this.listItem.setLeftImage(image);
		Assert.assertEquals(image, listItem.getLeftImage());
	}
	
	public void shoulSetRightImage() {
		Image image = new Image();
		this.listItem.setRightImage(image);
		Assert.assertEquals(image, listItem.getRightImage());
	}
	
	
	protected Object[][] componentsProvider() throws Exception {
		return new Object[][] { 
				{ new ListItem(), new ListItem() },
				{ new ListItemBuilder().withId("id").build(), new ListItemBuilder().withId("id").build()}
		};
	}

	protected Object[][] differentComponentsProvider() throws Exception {
		return new Object[][] { 
				{ new ListItemBuilder().withId("id1").build(), new ListItemBuilder().withId("id").build()}
		};
	}
	
	private static class ListItemBuilder {
		private final ListItem listItem;
		public ListItemBuilder() {
			this.listItem = new ListItem();
		}
		
		public ListItemBuilder withId(String id) {
			this.listItem.setId(id);
			return this;
		}

		public ListItem build() {
			return this.listItem;
		}
		
	}
		
	
	
}
