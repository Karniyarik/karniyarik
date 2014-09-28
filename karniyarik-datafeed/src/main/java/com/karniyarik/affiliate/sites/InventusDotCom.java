package com.karniyarik.affiliate.sites;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.datafeed.WebServiceDatafeedParser;
import com.karniyarik.datafeed.XMLDatafeedParser;
import com.karniyarik.datafeed.ws.inventus.KullaniciBilgileri;
import com.karniyarik.datafeed.ws.inventus.WsUrunFiyat;
import com.karniyarik.datafeed.ws.inventus.WsUrunFiyat_Service;
import com.karniyarik.parser.pojo.Product;

public class InventusDotCom extends WebServiceDatafeedParser {
	
	
	@Override
	public List<Product> parse(String content) {
		List<Product> productList = new ArrayList<Product>();
		
		WsUrunFiyat wsUrunFiyat = new WsUrunFiyat_Service().getWsUrunFiyat();
		KullaniciBilgileri kullaniciBilgileri = new KullaniciBilgileri();
		kullaniciBilgileri.setKullaniciAdi("Karniyarik");
		kullaniciBilgileri.setKullaniciSifre("KY_d379W");
		String xmlUrunFiyatKarniyarik = wsUrunFiyat.xmlUrunFiyatKarniyarik(kullaniciBilgileri);
		
		if (StringUtils.isNotBlank(xmlUrunFiyatKarniyarik))
		{
			XMLDatafeedParser parser = new XMLDatafeedParser(getSiteConfig(), getCategoryConfig(), getUrlManager());
			productList = parser.parse(xmlUrunFiyatKarniyarik);
		}
		
		return productList;
	}
	
}