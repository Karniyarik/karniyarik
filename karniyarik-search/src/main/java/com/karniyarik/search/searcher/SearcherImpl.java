package com.karniyarik.search.searcher;


public class SearcherImpl 
{}

//implements ISearcher
//{
//	private static int				MIN_QUERY_LENGTH			= 1;
//	private static int				MAX_RESULT_COUNT			= 5000;
//
//	private SpellChecker			spellChecker;
//	private IndexSearcher			searcher;
//	private final CategoryConfig	categoryConfig;
//	private final int				categoryType;
//	private final int				clusterSize;
//	private final ReadWriteLock		lock;
//	private final QueryParserFactory qpFactory;
//	private SponsoredMerchantService smService;
//
//	public SearcherImpl(
//			IndexSearcher searcher,
//			SpellChecker spellChecker,
//			CategoryConfig categoryConfig,
//			QueryParserFactory queryParserFactory,
//			int categoryType,
//			int clusterSize,
//			SponsoredMerchantService smService)
//	{
//		this.searcher = searcher;
//		this.spellChecker = spellChecker;
//		this.categoryConfig = categoryConfig;
//		this.qpFactory = queryParserFactory;
//		this.categoryType = categoryType;
//		this.clusterSize = clusterSize;
//		this.lock = new ReentrantReadWriteLock();
//		this.smService = smService;
//	}
//
//	@Override
//	public void refresh(IndexSearcher newSearcher, SpellChecker newSpellChecker)
//	{
//		try
//		{
//			lock.writeLock().lock();
//			searcher.close();
//			searcher = newSearcher;
//			
//			// TODO: this method is called as a hack
//			// so that spell checker closes the index searcher
//			// this line must be replaced with spellChecker.close()
//			// when such a method is implemented.
//			spellChecker.setSpellIndex(new RAMDirectory());
//			spellChecker = newSpellChecker;
//		}
//		catch (Throwable e)
//		{
//			getLogger().error("Can not replace searcher and spell checker with new ones", e);
//		}
//		finally
//		{
//			lock.writeLock().unlock();
//		}
//	}
//
//	public void refreshSponsoredMerchantService(SponsoredMerchantService newService) {
//		
//		try
//		{
//			lock.writeLock().lock();
//			smService = newService;
//		}
//		catch (Throwable e)
//		{
//			getLogger().error("Can not refresh sponsored links data", e);
//		}
//		finally
//		{
//			lock.writeLock().unlock();
//		}
//	}
//
////	private IndexSearcher refreshSearcher(IndexReader oldReader)
////	{
////		IndexSearcher newSearcher = null;
////		try
////		{
////			IndexReader newReader = oldReader.reopen();
////			if (newReader != oldReader)
////			{
////				newSearcher = new IndexSearcher(newReader);
////			}
////		}
////		catch (Throwable e)
////		{
////			getLogger().error("Can not refresh searcher", e);
////		}
////		return newSearcher;
////	}
//
//	private void refreshSpellIndex()
//	{
//		Dictionary aNameDictionary = null;
//		Dictionary aBreadCrumbDictionary = null;
//		Dictionary aBrandDictionary = null;
//		try
//		{
//			aNameDictionary = new LuceneDictionary(searcher.getIndexReader(), SearchConstants.NAME);
//			aBreadCrumbDictionary = new LuceneDictionary(searcher.getIndexReader(), SearchConstants.BREADCRUMB);
//			aBrandDictionary = new LuceneDictionary(searcher.getIndexReader(), SearchConstants.BRAND);
//
//			spellChecker.clearIndex();
//			spellChecker.indexDictionary(aNameDictionary);
//			spellChecker.indexDictionary(aBreadCrumbDictionary);
//			spellChecker.indexDictionary(aBrandDictionary);
//		}
//		catch (Throwable e)
//		{
//			getLogger().error("Can not create spell index on web server", e);
//			throw new KarniyarikSearchException("Can not create spell index on web server", e);
//		}
//		finally
//		{
//			aNameDictionary = null;
//			aBreadCrumbDictionary = null;
//			aBrandDictionary = null;
//		}
//
//	}
//
//	@Override
//	public int countDocsBySite(String siteName)
//	{
//		try
//		{
//			lock.readLock().lock();
//			int docCount = 0;
//			org.apache.lucene.queryParser.QueryParser parser = new org.apache.lucene.queryParser.QueryParser(Version.LUCENE_29, SearchConstants.STORE, new KeywordAnalyzer());
//			try
//			{
//				org.apache.lucene.search.Query luceneQuery = parser.parse(siteName);
//				//TopDocCollector topDocCollector = new TopDocCollector(MAX_RESULT_COUNT);
//				//TopDocsCollector<ScoreDoc> collector = new Top
//				TopDocs topDocs = searcher.search(luceneQuery, MAX_RESULT_COUNT);
//				docCount = topDocs.totalHits;
//			}
//			catch (ParseException e)
//			{
//				throw new KarniyarikSearchException("Exception occured, countDocsBySite for " + siteName, e);
//			}
//			catch (IOException e)
//			{
//				throw new KarniyarikSearchException("Exception occured, countDocsBySite for " + siteName, e);
//			}
//
//			return docCount;
//		}
//		finally
//		{
//			lock.readLock().unlock();
//		}
//	}
//
//	@Override
//	public int getTotalProductCountInSystem()
//	{
//		try
//		{
//			lock.readLock().lock();
//			int total = 0;
//			Map<String, Integer> counts = getSiteProductCounts();
//
//			for (Integer count : counts.values())
//			{
//				total += count;
//			}
//
//			return total;
//		}
//		finally
//		{
//			lock.readLock().unlock();
//		}
//	}
//
//	@Override
//	public Map<String, Integer> getSiteProductCounts()
//	{
//		try
//		{
//			lock.readLock().lock();
//			Map<String, Integer> aResult = new HashMap<String, Integer>();
//			Collection<SiteConfig> siteList = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfigList();
//
//			for (SiteConfig siteConfig : siteList)
//			{
//				if (siteConfig.getCategory().equals(categoryConfig.getName()))
//				{
//					aResult.put(siteConfig.getSiteName(), countDocsBySite(siteConfig.getSiteName()));
//				}
//			}
//
//			return aResult;
//		}
//		finally
//		{
//			lock.readLock().unlock();
//		}
//	}
//
//	@Override
//	public QueryResult search(String productUrl, boolean doLog)
//	{
//		try
//		{
//			lock.readLock().lock();
//			QueryResult queryResult = new QueryResult();
//
//			org.apache.lucene.queryParser.QueryParser parser = new org.apache.lucene.queryParser.QueryParser(Version.LUCENE_29, SearchConstants.PRODUCT_URL, new KeywordAnalyzer());
//
//			//TopDocCollector topDocCollector = new TopDocCollector(MAX_RESULT_COUNT);
//			TopDocs topDocs = null; 
//			try
//			{
//				org.apache.lucene.search.Query luceneQuery = parser.parse(org.apache.lucene.queryParser.QueryParser.escape(productUrl));
//
//				topDocs = searcher.search(luceneQuery, MAX_RESULT_COUNT);
//			}
//			catch (ParseException e)
//			{
//				throw new KarniyarikSearchException("Exception occured, search single product for " + productUrl, e);
//			}
//			catch (IOException e)
//			{
//				throw new KarniyarikSearchException("Exception occured, search single product for " + productUrl, e);
//			}
//
//			if (topDocs.totalHits > 0)
//			{
//				Product product = null;
//
//				if (topDocs.totalHits > 1)
//				{
//					new ExceptionNotifier().sendException("duplicate-product", "Duplicate Product", "Search by product id matched multiple products with same url" + productUrl);
//				}
//
//				try
//				{
//					product = ProductDocumentUtil.prepareProduct(searcher.doc(topDocs.scoreDocs[0].doc), categoryConfig);
//				}
//				catch (CorruptIndexException e)
//				{
//					throw new KarniyarikSearchException("Exception occured, search single product for " + productUrl, e);
//				}
//				catch (IOException e)
//				{
//					throw new KarniyarikSearchException("Exception occured, search single product for " + productUrl, e);
//				}
//
//				if (product != null)
//				{
//					// new ProductInfoDAO().fillProductFromDB(product);
//					queryResult.getResults().add(product);
//					queryResult.setSimilarProducts(getSimilarProducts(product, 14));
//					
//					if(smService != null && smService.isSponsored(product.getSourceName())) {
//						product.setSponsored(true);
//					}
//					
//					if(doLog)
//					{
//						SearchLog searchLog = new SearchLog();
//						searchLog.setQuery(productUrl);
//						List<ProductSummary> products = new ArrayList<ProductSummary>();
//						products.add(new ProductSummary(product.getLink(), product.getSourceName(), product.getName(), false));
//						for (Product similarProduct : queryResult.getSimilarProducts())
//						{
//							products.add(new ProductSummary(similarProduct.getLink(), similarProduct.getSourceName(), similarProduct.getName(), false));
//						}
//						searchLog.setProducts(products);
//						searchLog.setResultCount(products.size());
//						searchLog.setCategory(CategorizerConfig.getCategoryString(categoryType));
//						searchLog.setSpam(false);
//						StatisticsWebServiceUtil.sendSearchLog(searchLog);
//					}
//				}
//			}
//
//			return queryResult;
//		}
//		finally
//		{
//			lock.readLock().unlock();
//		}
//	}
//
//	public List<String> autocomplete(String term)
//	{
//		return AutoCompleteEngine.getInstance().autocomplete(term, categoryType);
//	}
//	
//	@Override
//	public QueryResult search(Query aQuery, boolean doLog)
//	{
//		QueryResult result = new QueryResult();
//
//		try
//		{
//			lock.readLock().lock();
//			
//			long aStartDate = Calendar.getInstance().getTimeInMillis();
//	
//			TopDocs topDocs = null;
//	
//			String[] aSuggestedWords = null;
//	
//			//boolean isSuggestedToConvertSpacesToOr = false;
//	
//			if (aQuery != null && StringUtils.isNotBlank(aQuery.getQueryString()) && aQuery.getQueryString().length() > MIN_QUERY_LENGTH)
//			{
//				
////				System.out.println("Borrow search SearcherImpl search(query)");
//				NewTurkishAnalyzer searchAnalyzer = TurkishAnalyzerPool.getInstance().borrowSearchAnalyzer();
//				try
//				{
//					Map<String, Float> boosts = new HashMap<String, Float>();
//					boosts.put(SearchConstants.KEYWORDS, 1f);
//					boosts.put(SearchConstants.NAME, 10f);
//					NewQueryParser qp = qpFactory.createQueryParser(new String[]{SearchConstants.KEYWORDS, SearchConstants.NAME}, searchAnalyzer, boosts);
//					
//					String queryString = aQuery.getQueryString();
//					org.apache.lucene.search.Query aLuceneQuery = qp.parse(queryString);
//	
//					aQuery.setLuceneQuery(aLuceneQuery);
//	
//					// mLuceneQuery.rewrite(reader);
//					topDocs = searchProducts(aQuery);
//	
//					Suggester aSuggester = new Suggester(getWords(aQuery.getQueryString()), spellChecker, searcher, categoryType);
//	
//					aSuggestedWords = suggest(topDocs, aSuggestedWords, qp, aSuggester);
//	
//					//isSuggestedToConvertSpacesToOr = aSuggester.isItRequiredToUseOr(aQuery.isConvertSpacesToOr(), qp.getWordCount());
//				}
//				catch (Throwable e)
//				{
//					throw new KarniyarikSearchException("Exception occured during querying/search. query: " + aQuery.getQueryString(), e);
//				} finally {
//					TurkishAnalyzerPool.getInstance().returnSearchAnalyzer(searchAnalyzer);
//				}
//
//				QueryResultConstructor constructor = new QueryResultConstructor(searcher, topDocs, categoryConfig, clusterSize, smService);
//
//				result = constructor.construct(aQuery, aSuggestedWords/*, isSuggestedToConvertSpacesToOr*/);
//				if (result.getResults().isEmpty() && aQuery.getPageNumber() > 1) {
//					aQuery.setPageNumber(1);
//					result = constructor.construct(aQuery, aSuggestedWords/*, isSuggestedToConvertSpacesToOr*/);
//				}
//		
//				long anEndDate = Calendar.getInstance().getTimeInMillis();
//		
//				result.setTimeTaken((anEndDate - aStartDate) / 1000.0);
//		
//				if (aQuery != null && doLog && topDocs != null && result != null)
//				{
//					SearchLog searchLog = prepareSearchLog(result);
//					StatisticsWebServiceUtil.sendSearchLog(searchLog);
//					SearchLogIndexer.getInstance().log(aQuery.getQueryString(), categoryType);
//				}
//			}
//		}
//		finally
//		{
//			lock.readLock().unlock();
//		}
//
//		return result;
//	}
//
//	private SearchLog prepareSearchLog(QueryResult result)
//	{
//		SearchLog searchLog = new SearchLog();
//		searchLog.setQuery(result.getQuery().getQueryString());
//		searchLog.setResultCount(result.getTotalHits());
//		searchLog.setSuggestionList(result.getSuggestedWords());
//		searchLog.setTime(result.getTimeTaken());
//		searchLog.setCategory(CategorizerConfig.getCategoryString(categoryType));
//		searchLog.setHttpAgent(result.getQuery().getHttpAgent());
//		searchLog.setApiKey(result.getQuery().getApiKey());
//		searchLog.setSpam(SpamController.getInstance().isSpam(result.getQuery().getQueryString()));
//		List<ProductSummary> products = new ArrayList<ProductSummary>();
//		for (Product product : result.getResults())
//		{
//			products.add(new ProductSummary(product.getLink(), product.getSourceName(), product.getName(), false));
//		}
//		
//		for (Product product : result.getSponsoredResults())
//		{
//			products.add(new ProductSummary(product.getLink(), product.getSourceName(), product.getName(), true));
//		}
//		searchLog.setProducts(products);
//		
//		return searchLog;
//	}
//
//	private List<Product> getSimilarProducts(Product product, int maxResults)
//	{
//		String[] searchFields = new String[] { SearchConstants.NAME, SearchConstants.BREADCRUMB, SearchConstants.KEYWORDS };
//
//		IndexReader ir = searcher.getIndexReader();
//		MoreLikeThis mlt = new MoreLikeThis(ir, new DefaultSimilarity());
//
//		Analyzer searchAnalyzer = TurkishAnalyzerPool.getInstance().borrowSearchAnalyzer();
//		mlt.setAnalyzer(searchAnalyzer);
//		mlt.setFieldNames(searchFields);
//		mlt.setMinTermFreq(1);
//
//		List<Product> result = new LinkedList<Product>();
//		List<Product> sponsored = new LinkedList<Product>();
//		StringReader reader = new StringReader(product.getName() + " " + product.getBreadcrumb() + " " + product.getPropertyString());
//
//		try
//		{
//			org.apache.lucene.search.Query query = mlt.like(reader);
//			TopDocs topDocs = searcher.search(query, maxResults + 1);
//
//			for (ScoreDoc scoreDoc : topDocs.scoreDocs)
//			{
//				Document document = searcher.doc(scoreDoc.doc);
//				Product similarProduct = ProductDocumentUtil.prepareProduct(document, categoryConfig);
//				
//				if(similarProduct.getLink().equalsIgnoreCase(product.getLink()))
//				{
//					continue;
//				}
//				if(smService != null && smService.isSponsored(similarProduct.getSourceName())) {
//					sponsored.add(similarProduct);
//					similarProduct.setSponsored(true);
//				}
//				else
//				{
//					result.add(similarProduct);
//				}
//			}
//			result.addAll(0, sponsored);
//			
//			if (result.size() > 0)
//			{
//				result.remove(0);
//			}
//		}
//		catch (Throwable e)
//		{
//			throw new KarniyarikSearchException("Cannot find similar products", e);
//		} finally {
//			TurkishAnalyzerPool.getInstance().returnSearchAnalyzer(searchAnalyzer);
//		}
//
//		return result;
//	}
//
//	private TopDocs searchProducts(Query aQuery) throws IOException
//	{
//		KarniyarikSort aSort = aQuery.getSort();
//		org.apache.lucene.search.Query aLuceneQuery = aQuery.getLuceneQuery();
//
//		KarniyarikFilterr aFilter = prepareLuceneFilter(aQuery);
//
//		TopFieldCollector collector = TopFieldCollector.create(
//				  (aSort == null || aSort.getLuceneSort() == null) ? new Sort() : aSort.getLuceneSort(),
//			      MAX_RESULT_COUNT, 
//			      false,         // fillFields - not needed, we want score and doc only
//			      true,          // trackDocScores - need doc and score fields
//			      true,          // trackMaxScore - related to trackDocScores
//			      false); // should docs be in docId order?
//		
//		if (aSort != null && aFilter != null)
//		{
//			searcher.search(aLuceneQuery, aFilter.getLuceneFilter(), collector);
//		}
//		else if (aSort != null)
//		{
//			searcher.search(aLuceneQuery, collector);
//		}
//		else if (aFilter != null)
//		{
//			searcher.search(aLuceneQuery, aFilter.getLuceneFilter(), collector);
//		}
//		else
//		{
//			searcher.search(aLuceneQuery, collector);
//		}
//		
//		return collector.topDocs();
//	}
//
//	private String[] suggest(TopDocs topDocs, String[] aSuggestedWords, NewQueryParser aParser, Suggester aSuggester) throws Throwable
//	{
//		org.apache.lucene.search.Query aLuceneQuery;
//
//		if (aSuggester.isItRequiredToSuggest(topDocs))
//		{
//			aSuggestedWords = aSuggester.suggest();
//
//			// make sure that the suggested words return a result
//			if (aSuggestedWords != null && aSuggestedWords.length > 0)
//			{
//				aLuceneQuery = aParser.parse(aSuggestedWords[0]);
//
//				if (searcher.search(aLuceneQuery, MAX_RESULT_COUNT).totalHits == 0)
//				{
//					aSuggestedWords = null;
//				}
//			}
//		}
//		return aSuggestedWords;
//	}
//
//	private KarniyarikFilterr prepareLuceneFilter(Query query)
//	{
//		KarniyarikFilterr aFilter = null;
//
//		List<KarniyarikFilterr> aFilterList = new ArrayList<KarniyarikFilterr>();
//
//		aFilterList.addAll(query.getCategorySpecificFilterList());
//
//		aFilter = prepareBooleanFilter(aFilterList);
//
//		return aFilter;
//	}
//
//	private KarniyarikFilterr prepareBooleanFilter(List<KarniyarikFilterr> filterList)
//	{
//		if (filterList.size() == 0)
//		{
//			return null;
//		}
//		else if (filterList.size() == 1)
//		{
//			return filterList.get(0);
//		}
//		else
//		{
//			KarniyarikFilterr filter = filterList.remove(0);
//			return new KarniyarikBooleanFilter(filter, prepareBooleanFilter(filterList));
//		}
//	}
//
//	private String[] getWords(String queryString) throws Throwable
//	{
//		List<String> aWords = new ArrayList<String>();
//
////		System.out.println("Borrow search SearcherImpl getWords");
//		Analyzer searchAnalyzer = TurkishAnalyzerPool.getInstance().borrowSearchAnalyzer();
//		TokenStream aTokenStream = searchAnalyzer.tokenStream(null, new StringReader(queryString));
//
//		Token aToken = aTokenStream.next();
//		
//		String aWord = null;
//
//		while (aToken != null)
//		{
//			aWord = aToken.term().trim();
//			if (StringUtils.isNotBlank(aWord))
//			{
//				aWords.add(aWord);
//			}
//			aToken = aTokenStream.next();
//		}
//
////		System.out.println("Return search SearcherImpl getWords");
//		TurkishAnalyzerPool.getInstance().returnSearchAnalyzer(searchAnalyzer);
//		
//		return aWords.toArray(new String[] {});
//	}
//
//	private Logger getLogger()
//	{
//		return Logger.getLogger(this.getClass().getName());
//	}
//}
