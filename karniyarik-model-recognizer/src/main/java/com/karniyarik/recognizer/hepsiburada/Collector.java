package com.karniyarik.recognizer.hepsiburada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;

import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.filters.StringInputStream;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.WebConfig;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.vo.CurrencyType;
import com.karniyarik.crawler.HttpClientContentFetcher;
import com.karniyarik.crawler.util.URLAnalyzer;
import com.karniyarik.datafeed.XMLDatafeedParser;
import com.karniyarik.parser.util.PriceUtil;
import com.karniyarik.recognizer.model.Model;
import com.karniyarik.recognizer.model.ModelFinder;

public class Collector
{
	public Collector()
	{

	}
	
	public void start()
	{
		System.out.println("Started:  " + new Date());
		HttpClientContentFetcher fetcher = new HttpClientContentFetcher("karniyarik", 120000, "UTF-8");
		HtmlCleaner cleaner = new HtmlCleaner();
		List<String> categoryIds = new ArrayList<String>();
		
		GelirOrtaklariProductList result = new GelirOrtaklariProductList();
		
		//List<GelirOrtaklariProduct> products = new LinkedList<GelirOrtaklariProduct>();
		try
		{
			String categoryPage = fetcher.getContent("http://www.hepsiburada.com/tumkategoriler.aspx");
			Configuration xqueryConfiguration = new Configuration();
			TagNode cleaned = cleaner.clean(categoryPage);
			Document dom = new DomSerializer(cleaner.getProperties(), true).createDOM(cleaned);
			
			DynamicQueryContext dynamicContext = new DynamicQueryContext(xqueryConfiguration);
			dynamicContext.setContextItem(xqueryConfiguration.buildDocument(new DOMSource(dom)));
			
			StaticQueryContext staticContext = new StaticQueryContext(xqueryConfiguration);
			XQueryExpression exp = staticContext.compileQuery("data(//div[@id=\"leftpart_lacking_master\"]//li/a/@href)");
			Object queryResultOject = exp.evaluate(dynamicContext);
			
			if (queryResultOject != null)
			{
				List list = (List) queryResultOject;
				for(Object resultObj: list)
				{
					String string = resultObj.toString();
					string = string.toLowerCase(Locale.ENGLISH);
					Map<String, String> queryParameters = URLAnalyzer.getQueryParameters(string);
					String catId = queryParameters.get("categoryid");
					if(StringUtils.isBlank(catId))
					{
						catId = queryParameters.get("catid");
					}
					
					if(StringUtils.isNotBlank(catId))
					{
						categoryIds.add(catId);
					}
				}				
			}
						
			System.out.println("Total catr size: " + categoryIds.size());
			
			int index = 0;
			
			for(String catId: categoryIds) 
			{
				index++;
				try
				{
					String url = "http://www.hepsiburada.com/liste/Rss.aspx?categoryId="+catId+"&t=2&c=50000";
					System.out.println("Fetching " + index + " of " + categoryIds.size() + " - " + url);
					String content = fetcher.getContent(url);
					List<GelirOrtaklariProduct> parse = parse(content);
					if(parse.size() < 1)
					{
						//System.out.println("Cannot fetch category: " +catId + ", url " + url );
					}
					result.getProducts().addAll(parse);
				}
				catch (Exception e)
				{
					//System.out.println(e.getMessage());
				}
			}
			
			System.out.println("Total product size: " + result.getProducts().size());
			writeToJSON(result);
			System.out.println("Ended:  " + new Date());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeToJSON(GelirOrtaklariProductList result) throws RuntimeException
	{
		try
		{
			String json = JSONUtil.getJSON(result);
			File file = getFile();
			System.out.println("Writing to : " +  file.getAbsolutePath());
			FileOutputStream os = new FileOutputStream(file);
			CompressorStreamFactory compressorStreamFactory = new CompressorStreamFactory();
			CompressorOutputStream gzippedOut = compressorStreamFactory.createCompressorOutputStream(CompressorStreamFactory.GZIP, os);		
			IOUtils.write(json, gzippedOut);
			gzippedOut.close();
		}
		catch (Throwable e)
		{
			//throw new RuntimeException(e);
			e.printStackTrace();
		}
	}
	
	public void write(GelirOrtaklariProductList result) throws RuntimeException
	{
        try
        {
        	File file = getFile();
			System.out.println("Writing to : " +  file.getAbsolutePath());
			FileOutputStream os = new FileOutputStream(file);
			
            JAXBContext context = JAXBContext.newInstance(GelirOrtaklariProductList.class);
            Marshaller marshaller = context.createMarshaller();
           
            marshaller.marshal(result, os);
            os.close();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
	}

	private File getFile() {
		//String tempDir = StreamUtil.getTempDir();
		WebConfig webConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig();
		String downloadRootPath = webConfig.getDownloadRootPath();
		File root = new File(downloadRootPath);
		if(!root.exists())
		{
			root.mkdirs();
		}
		
		File file = new File(root.getAbsoluteFile() + "/hepsiburada.json.gzip");
		return file;
	}

	public List<GelirOrtaklariProduct> parse(String content)
	{
		List<GelirOrtaklariProduct> productList = new LinkedList<GelirOrtaklariProduct>();
		
		if (StringUtils.isNotBlank(content))
		{
			try
			{
				DocumentBuilderFactory dbf = DocumentBuilderFactoryImpl.newInstance();
				dbf.setNamespaceAware(true);
				DocumentBuilder db = dbf.newDocumentBuilder();
				
				content = XMLDatafeedParser.stripNonValidXMLCharacters(content);
				StringInputStream stringInputStream = new StringInputStream(content, "UTF-8");
				org.w3c.dom.Document document = db.parse(stringInputStream);
				org.dom4j.Document dom4JDocument = new DOMReader().read(document);
				stringInputStream.close();
				Date fetchDate = new Date();
				
				List<Element> elements = XMLDatafeedParser.getProductElementList(dom4JDocument, "//rss//item");
				
				for (Element element : elements)
				{
					GelirOrtaklariProduct product = parseElement(element);
					if (product != null)
					{
						product.setFetchDate(fetchDate);
						productList.add(product);
					}
				}
			}
			catch (Throwable e)
			{
				//e.printStackTrace();				
			}
		}

		return productList;
	}

	private GelirOrtaklariProduct parseElement(Element element)
	{
		GelirOrtaklariProduct product = new GelirOrtaklariProduct();
		product.setUrl(getValue(element, "link"));
		product.setName(clear(getValue(element, "title")));
		
		String priceText = clear(getValue(element, "hb:strikeoutprice"));
		String discountPriceText = clear(getValue(element, "hb:price"));
		
		if(StringUtils.isBlank(priceText) && StringUtils.isNotBlank(discountPriceText))
		{
			priceText = discountPriceText;
			discountPriceText = "";
		}
		CurrencyType currency = PriceUtil.determineCurrency(priceText);
		float price = PriceUtil.parsePrice(priceText, null);
		float discountPrice = PriceUtil.parsePrice(discountPriceText, null);
		if(price > 0)
		{
			product.setPrice(price);	
		}
		
		if(discountPrice > 0)
		{
			product.setDiscountPrice(discountPrice);	
		}
		
		if(product.getDiscountPrice() > 0)
		{
			product.setDiscountAmount((product.getPrice() - product.getDiscountPrice())/product.getPrice()* 100);	
		}
		
		product.setPriceCurrency(currency.getSymbol());
		
		product.setCategory(clear(getValue(element, "category")));
		product.setImageUrl(getValue(element, "hb:thumbnail"));
		product.setBrand(clear(getValue(element, "hb:brand")));
		product.setId(clear(getValue(element, "hb:productid")));
		//product.setShipping(getValue(element, "hb:IsFastShipping").equals("1"));
		product.setHasPromotion(getValue(element, "hb:hasPromotion").equals("1"));
		
		Model model = ModelFinder.getInstance().getModel(product.getName(), product.getCategory());
		if(model != null)
		{
			product.setModel(model.getName());
		}
		
		return product;
	}
	
	public String getValue(Element element, String propertyXPath)
	{
		return XMLDatafeedParser.getValue(element, propertyXPath);
	}
	
	public String clear(String str)
	{
		return XMLDatafeedParser.clear(str);
	}
	
	public GelirOrtaklariProductList read()
	{
		try
		{
			String tempDir = StreamUtil.getTempDir();
			File file = new File(tempDir + "/hepsiburada.json");
			
			String str = IOUtils.toString(new FileInputStream(file));
			GelirOrtaklariProductList result = JSONUtil.parseJSON(str, GelirOrtaklariProductList.class);
			return result;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		Collector collector = new Collector();
		//collector.start();
		GelirOrtaklariProductList read = collector.read();
		collector.writeToJSON(read);
	}
}