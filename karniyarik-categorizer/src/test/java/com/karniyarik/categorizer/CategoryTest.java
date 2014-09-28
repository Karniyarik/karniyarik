package com.karniyarik.categorizer;

import junit.framework.TestCase;

import com.karniyarik.categorizer.xml.category.CategoryType;

public class CategoryTest extends TestCase
{
	public void testCategoryFinder() throws Exception
	{
		ProductClassifier classifier = ProductClassifier.getInstance(true);

		String[][] testItems = new String[][] {
				{ "DATALOGIC Kablosuz Renkli El Terminali(sayım programı+adaptörü) (MEMOR)", "DATALOGIC", " Ucuza Alışveriş» Bilgisayar Bileşenleri» Barkod Ürünleri» Barkod El Terminalleri", "" },
				{ "Liebherr CUPesf 2721 A+ Comfort SmartFrost Smart Steel 55cm Inox Buzdolabı", "Liebherr", "Beyaz Eşya Mutfak Beyaz Eşya Ankastre Buzdolapları", "1.5.1.5" },
				{ "htc hd2 telefon cep telefonu", "htc", "telefon cep telefonu", "1.4.2.2" }, { "NOKIA e71 CEP TELEFONU", "Nokia", "Telefon Cep Telefonu", "1.4.2.2" },
				{ "Nokia N86 8 GB Cep Telefonu-3G", "Nokia", "Ana Sayfa Telefon Faks Cep Telefonu NOKIA", "1.4.2.2" },
				{ "SBS V21872 NOKIA CEP TELEFONU KULAKLIK", "SBS", "CEP TELEFONU AKSESUAR", "1.4.2.1.2" },
				{ "MCA QTrek BluetalkrMini Bluetooth lu iPhone ve Cep Telefonu Kulaklığı (Siyah)", "MCA", "Ana Sayfa Telefon/Faks Cep Telefonu Aksesuarları Kulaklıklar ", "1.4.2.1.2" },
				{ "Ntv 2009 Almanak", "", "Kitap Derleme", "1.11.23" }, { "GKB IP CAMERA - IC SERIES(yellow)", "", "Güvenlik Kamera ve Aksesuarları oem", "1.7.2.11.4" },
				{ "KAWAİ KW-160 ADAPTÖR", "Kawai", " Oto Aksesuar FM Transmitter Cihazları KAWAI", "1.16.3.5" },
				{ "6149200 Kek Kalıbı Tek", "", "KEK/BİSKÜVİ KALIPLARI 6149200 Kek Kalıbı Tek", "1.5.2.8" },
				{ "Arifoğlu Kekik 100 Gr", "", "Sağlık Yaşam Bitkisel Ürünler Poşet Baharatlar Arifoğlu", "1.26" },
				{ "Hayvanlar Dünyası", "", "Bilgisayar Ürünleri Yazılım Ürünleri Hobi Eğlenceli Yazılımlar Doğa/Hayvanlar Alemi Yazılımları", "1.1.1.1" },
				{ "Shinelingerie art9001 Desen Ekose Etek", "Shinelingerie", "İç Giyim Shinelingerie Ürünleri", "1.21.4.1.1" },
				{ "Scooter 9405 Bayan Bot", "Scooter", "Ayakkabı Çanta Ayakkabı Bot-Çizme Bot", "1.13.1.2.1" }, { "Sosyal Bilgiler 6", "", "Kitap Eğitim Başvuru Test", "1.11.27.5" },
				{ "ilköğretimde hayat bilgisi ve sosyal bilgiler öğretimi", "", "Diğer Çeşitli Eğitim Pedagoji", "1.11.27.6" },
				{ "Swatch YNS107 Bayan Kol Saati", "Swatch", "Saat Bayan Swatch", "1.24.1.1" }, { "SEOUL HIREMCO X1 UYDU ALICI", "Seoul", "Dijital Uydu Alıcıları  Seoul", "1.7.2.3" },
				{ "SAMİ 96 PRÇ YEMEK TK DEKOR", "", "GÜRAL PORSELEN 96 Parça Yemek Takımları SAMİ PRÇ YEMEK TK DEKOR", "1.5.2.19" },
				{ "Canon EOS 500D Body Dijital Fotoğraf Makinesi", "Canon", "Görüntü Sistemleri Dijital Fotoğraf Makinesi Canon Canon EOS 500D Body Dijital Fotoğraf Makinesi", "1.17.1.13" },
				{ "Nokia N73 Kapak (gri)", "Nokia", "Telefon Cep Telefonu Cep Telefonu Kapakları", "1.4.2.15" } };
		
		
	}

	private static void print(CategoryType type, String text)
	{
		if (type == null)
		{
			System.out.println("null --> " + text);
		}
		else
		{
			System.out.print(text + " --> ");
			System.out.print(type.getId() + ", ");
			String[] path = type.getId().split("\\.");
			StringBuffer currentPath = new StringBuffer();
			for (String pathEl : path)
			{
				currentPath.append(pathEl);
				System.out.print(ProductClassifier.getInstance(true).getCategoryName(currentPath.toString()));
				System.out.print(" > ");
				currentPath.append(".");
			}
			System.out.println("");
		}
	}
}
