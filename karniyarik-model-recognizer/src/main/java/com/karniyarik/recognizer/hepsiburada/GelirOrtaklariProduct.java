package com.karniyarik.recognizer.hepsiburada;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.karniyarik.common.vo.CurrencyType;

@XmlAccessorType(XmlAccessType.FIELD)
public class GelirOrtaklariProduct
{
	@XmlElement(name = "urun_url")
	private String					url				= "";

	@XmlElement(name = "urun_id")
	private String					id 				= "";
	
	@XmlElement(name = "baslik")
	private String					name			= "";
	
	@XmlElement(name = "marka")
	private String					brand			= "";
	
	@XmlElement(name = "model")
	private String					model			= "";
	
	@XmlElement(name = "fiyat")
	private float					price			= 0f;
	
	@XmlElement(name = "indirimlifiyat")
	private float					discountPrice	= 0f;
	
	@XmlElement(name = "indirimorani")
	private float					discountAmount	= 0f;
	
	@XmlElement(name = "birim")
	private String					priceCurrency	= CurrencyType.TL.getSymbol();
	
//	@XmlElement(name = "name")
//	private boolean					shipping		= false;
//	

	@XmlElement(name = "kategori")
	private String					category		= "";
	
	@XmlElement(name = "stok")
	private boolean	 				instock 		= false;
	
	@XmlElement(name = "kampanyali")
	private boolean	 				hasPromotion	= false;
	
	@XmlElement(name = "resimurl")
	private String					imageUrl		= "";
	
	@XmlElement(name = "guncelleme")
	private Date					fetchDate;
	
	public GelirOrtaklariProduct()
	{
		// TODO Auto-generated constructor stub
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public float getPrice()
	{
		return price;
	}

	public void setPrice(float price)
	{
		this.price = price;
	}

	public float getDiscountPrice()
	{
		return discountPrice;
	}

	public void setDiscountPrice(float discountPrice)
	{
		this.discountPrice = discountPrice;
	}

	public float getDiscountAmount()
	{
		return discountAmount;
	}

	public void setDiscountAmount(float discountAmount)
	{
		this.discountAmount = discountAmount;
	}

//	public boolean isShipping()
//	{
//		return shipping;
//	}
//
//	public void setShipping(boolean shipping)
//	{
//		this.shipping = shipping;
//	}

	public boolean isInstock()
	{
		return instock;
	}

	public void setInstock(boolean instock)
	{
		this.instock = instock;
	}

	public boolean isHasPromotion()
	{
		return hasPromotion;
	}

	public void setHasPromotion(boolean hasPromotion)
	{
		this.hasPromotion = hasPromotion;
	}

	public String getPriceCurrency()
	{
		return priceCurrency;
	}

	public void setPriceCurrency(String priceCurrency)
	{
		this.priceCurrency = priceCurrency;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Date getFetchDate()
	{
		return fetchDate;
	}

	public void setFetchDate(Date fetchDate)
	{
		this.fetchDate = fetchDate;
	}
}

/*
<urun>
<urun_url> http://www.hepsiburada.com/Liste/nikon-d3000-18-55mm-vr-lens-102mp-30-slr-dijital-fotograf-makinesi/ProductDetails.aspx?productId=bd97014&categoryId=1100301</urun_url>    
<urun_id> bd97014 </urun_id>
<marka>Nikon</marka>
<model>D3000 </model>
<baslik>Nikon D3000 18-55mm VR Lens 10.2MP 3.0" SLR Dijital Fotoğraf Makinesi</baslik>

<aciklama1> Nikon D3000 (max 45 karakter!)</aciklama1>
<aciklama218-55mm VR Lens 10.2MP (max 45 karakter!)</aciklama2>
<aciklama3>3.0" SLR Dijital Fotoğraf Makinesi (max 45 karakter!)</aciklama3>
<metin></ metin>
<ozellikler>  Kamera Tipi|Tek lensli refleks dijital kamera	Etkin Piksel|10.2 Milyon	Görüntü Algılayıcı|… (Bu kısma ürünün teknik özellikleri girilecektir)</ozellikler>
<kargo>1</kargo>
		<kurulum>0</kurulum>
<indirimorani>30</indirimorani>
<birim>TL</birim>
<sehir>(i)</sehir>
<kategori>Fotoğraf &Kamera</kategori>
<altkategori>Fotoğraf</altkategori>
<etiket> Nikon D3000 Fotoğraf Makinesi</etiket>
<coksatan>(ii)</coksatan> 
<kampanyali>1**</kampanyali> 
<kategoriurl> http://www.hepsiburada.com/Liste/nikon-d3000-18-55mm-vr-lens-102mp-30-slr-dijital-fotograf-makinesi/ProductDetails.aspx?productId=bd97014&categoryId=1100301 </kategoriurl> 
<cinsiyet>3*</cinsiyet>
<resimurl> http://images.hepsiburada.com/assets/Camera/200/bd97014.jpg </resimurl>
<stok>1**</stok>
<kupon>0**</kupon>
<kuponkod>(iii)</kuponkod>
<baslangictarihi>(iv)</ baslangictarihi>
<bitistarihi>(v)</ bitistarihi> 
</urun>


*/