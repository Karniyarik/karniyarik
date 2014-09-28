package com.karniyarik.brands.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.karniyarik.brands.BrandHolder;
import com.karniyarik.brands.util.BrandsFileUtil;
import com.karniyarik.common.util.StringUtil;

public class BrandsXmlFileService implements BrandsFileService {

	private static final String BRAND_HOLDERS_ELEMENT = "brandholders";
	private static final String BRAND_HOLDER_ELEMENT = "brandholder";
	private static final String ACTUAL_BRAND_ATTRIBUTE = "actual";
	private static final String ALTERNATE_BRAND_ELEMENT = "alternate";

	@Override
	public void exportBrandHolders(List<BrandHolder> brandHolderList, File file)
			throws IOException, IllegalArgumentException {

		if (brandHolderList == null || file == null) {
			throw new IllegalArgumentException(
					"Input parameters can not be null.");
		}

		Document doc = DocumentHelper.createDocument();
		Element brandHoldersElement = doc.addElement(BRAND_HOLDERS_ELEMENT);

		for (BrandHolder brandHolder : brandHolderList) {
			Element brandHolderElement = exportBrandHolder(brandHolder);
			brandHoldersElement.add(brandHolderElement);
		}

		writeDocument(doc, file);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BrandHolder> importBrandHolders(InputStream is)
			throws IOException, IllegalArgumentException {
		if (is == null) {
			throw new IllegalArgumentException("File can not be null.");
		}

		List<BrandHolder> brandHolders = new ArrayList<BrandHolder>();

		Document doc = readDocument(is);
		Element brandHoldersElement = doc.getRootElement();
		List<Element> brandHolderElements = brandHoldersElement
				.elements(BRAND_HOLDER_ELEMENT);

		for (Element brandHolderElement : brandHolderElements) {
			BrandHolder brandHolder = importBrandHolder(brandHolderElement);
			brandHolders.add(brandHolder);
		}

		return brandHolders;
	}

	@SuppressWarnings("unchecked")
	private static BrandHolder importBrandHolder(Element brandHolderElement) {
		BrandHolder brandHolder = new BrandHolder();

		String actualBrand = brandHolderElement
				.attributeValue(ACTUAL_BRAND_ATTRIBUTE);
		brandHolder.setActualBrand(actualBrand);

		List<String> alternateBrands = new ArrayList<String>();
		List<Element> alternateBrandElements = brandHolderElement
				.elements(ALTERNATE_BRAND_ELEMENT);
		for (Element alternateBrandElement : alternateBrandElements) {
			String alternateBrand = alternateBrandElement.getTextTrim();
			alternateBrands.add(alternateBrand);
		}

		brandHolder.setListOfAlternateBrands(alternateBrands);

		return brandHolder;
	}

	private static Element exportBrandHolder(BrandHolder brandHolder) {
		Element brandHolderElement = DocumentHelper
				.createElement(BRAND_HOLDER_ELEMENT);
		brandHolderElement.addAttribute(ACTUAL_BRAND_ATTRIBUTE, brandHolder
				.getActualBrand());

		for (String alternateBrand : brandHolder.getListOfAlternateBrands()) {
			Element alternateBrandElement = brandHolderElement
					.addElement(ALTERNATE_BRAND_ELEMENT);
			alternateBrandElement.setText(alternateBrand);
		}

		return brandHolderElement;
	}

	private static void writeDocument(Document document, File file)
			throws IOException {
		BrandsFileUtil.createFile(file);

		XMLWriter output = new XMLWriter(new FileWriter(file));
		output.write(document);
		output.close();

	}

	private static Document readDocument(InputStream is) throws IOException {

		SAXReader reader = new SAXReader();
		Document doc;
		try {
			BufferedReader isReader = new BufferedReader(new InputStreamReader(is, StringUtil.DEFAULT_ENCODING));
			doc = reader.read(isReader);
		} catch (DocumentException e) {
			throw new IOException(e);
		}

		return doc;
	}

}
