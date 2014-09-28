package com.karniyarik.common.config.system;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;

/**
 * The configuration object for indexing and searching mechanism.
 * 
 * @author siyamed
 * 
 */
@SuppressWarnings("serial")
public class SearchConfig extends ConfigurationBase
{
	/**
	 * Default values for the directories to be used.
	 */
	private final String			INDEX_DIR						= "C:/index_product";
	private final boolean			PUBLISH_INDEX					= Boolean.FALSE;
	private final int				LUCENE_MAX_FIELD_LENGTH			= 256;
	private final int				LUCENE_MAX_MERGE_DOCS			= Integer.MAX_VALUE;
	private final int				LUCENE_MERGE_FACTOR				= 20;
	private final int				LUCENE_MAX_BUFFERED_DOCS		= 20;
	private final int				MIN_SEARCH_HIT_COUNT_TO_SUGGEST	= 5;
	private final double			MIN_SEARCH_HIT_SCORE_TO_SUGGEST	= 0.5;
	private final int				CLUSTER_SIZE					= 4;
	public static final int			DEFAULT_PAGE_SIZE				= 15;
	private final String			DOUBLE_INDEXING_FORMAT			= "000000000.00";
	private final int				MAX_RANK						= 100;
	private static int				MIN_CLUSTER_CONTAINS			= 5;
	private static int				MAX_CLUSTER_CONTAINS			= 150;
	private static int				MAX_CLUSTER_COUNT				= 10;
	private static int				MAX_SAMPLE_SIZE					= 400;
	private static int				MIN_SEARCH_RESULT_SIZE			= 100;
	private static int				SEARCH_LOG_CACHE_SIZE			= 500;

	public SearchConfig() throws Exception
	{
		super(ConfigurationURLUtil.getSearchConfig());
	}

	/**
	 * The directory to construct the search index in.
	 * 
	 * @return
	 */
	public String getIndexDirectory()
	{
		return getString("search.index[@dir]", INDEX_DIR);
	}

	public Integer getSearchLogCacheSize()
	{
		return getInteger("search.logindex[@size]", SEARCH_LOG_CACHE_SIZE);
	}
	
	
	public Boolean getPublishIndex()
	{
		return getBoolean("search.index[@publishindex]", PUBLISH_INDEX);
	}

	/**
	 * Performace parameter for lucene. Defines the maximum field length for the
	 * fields to be indexed.
	 * 
	 * @return
	 */
	public int getLuceneMaxFieldLength()
	{
		return getInt("search.performance[@maxfieldlength]", LUCENE_MAX_FIELD_LENGTH);
	}

	/**
	 * Performace parameter for lucene. Bigger value uses more memory but
	 * constructs index faster.
	 * 
	 * @return
	 */
	public int getLuceneMaxMergeDocs()
	{
		return getInt("search.performance[@maxmergedocs]", LUCENE_MAX_MERGE_DOCS);
	}

	/**
	 * Performace parameter for lucene. Bigger value uses more memory but
	 * constructs index faster.
	 * 
	 * @return
	 */
	public int getLuceneMergeFactor()
	{
		return getInt("search.performance[@mergefactor]", LUCENE_MERGE_FACTOR);
	}

	public int getLuceneMaxBufferedDocs()
	{
		return getInt("search.performance[@maxbuffereddocs]", LUCENE_MAX_BUFFERED_DOCS);
	}

	public String getDoubleFieldIndexingFormat()
	{
		return DOUBLE_INDEXING_FORMAT;
	}

	public int getMinHitCountToSuggest()
	{
		return getInt("search.suggest[@minresultcount]", MIN_SEARCH_HIT_COUNT_TO_SUGGEST);
	}

	public double getMinHitSoreToSuggest()
	{
		return getDouble("search.suggest[@minresultscore]", MIN_SEARCH_HIT_SCORE_TO_SUGGEST);
	}

	public int getClusterSize()
	{
		return getInt("search.pricecluster[@size]", CLUSTER_SIZE);
	}

	public int getDefaultPageSize()
	{
		return getInt("search[@pagesize]", DEFAULT_PAGE_SIZE);
	}

	public int getMaxRank()
	{
		return MAX_RANK;
	}

	public String getClusteringAlgorithm()
	{
		return getString("search.pricecluster[@algorithm]", "count");
	}

	public String getStemmer()
	{
		return getString("search.stemmer[@class]");
	}

	public void setLuceneIndexDirectory(String value)
	{
		setProperty("search.index[@dir]", value);
	}

	public void setSftpHost(String value)
	{
		setProperty("search.index.sftp.host", value);
	}

	public void setSftpUser(String value)
	{
		setProperty("search.index.sftp.user", value);
	}

	public void setSftpPassword(String value)
	{
		setProperty("search.index.sftp.password", value);
	}

	public void setSftpPublishDirectory(String value)
	{
		setProperty("search.index.sftp.publishDirectory", value);
	}

	public void setPublishIndex(boolean value)
	{
		setProperty("search.index[@publishindex]", value);
	}

	public void setLuceneMaxFieldLength(int value)
	{
		setProperty("search.performance[@maxfieldlength]", value);
	}

	public void setLuceneMaxMergeDocs(int value)
	{
		setProperty("search.performance[@maxmergedocs]", value);
	}

	public void setLuceneMergeFactor(int value)
	{
		setProperty("search.performance[@mergefactor]", value);
	}

	public void setLuceneMaxBufferedDocs(int value)
	{
		setProperty("search.performance[@maxbuffereddocs]", value);
	}

	public void setSpellIndexDirectory(String value)
	{
		setProperty("search.suggest[@dir]", value);
	}

	public void setSponsoredIndexDirectory(String value)
	{
		setProperty("search.sponsoredproduct[@dir]", value);
	}

	public void setMinHitCountToSuggest(int value)
	{
		setProperty("search.suggest[@minresultcount]", value);
	}

	public void setMinHitSoreToSuggest(double value)
	{
		setProperty("search.suggest[@minresultscore]", value);
	}

	public void setClusterSize(int value)
	{
		setProperty("search.pricecluster[@size]", value);
	}

	public void setDefaultPageSize(int value)
	{
		setProperty("search[@pagesize]", value);
	}

	public void setClusteringAlgorithm(String value)
	{
		setProperty("search.pricecluster[@algorithm]", value);
	}

	public void setStemmer(String value)
	{
		setProperty("search.stemmer[@class]", value);
	}

	public int getMinSearchResultSize()
	{
		return getInt("search.querysuggestion[@minsearchresultsize]", MIN_SEARCH_RESULT_SIZE);
	}

	public int getMaxConstructedClusterCount()
	{
		return getInt("search.querysuggestion[@maxconstructedclustercount]", MAX_CLUSTER_COUNT);
	}

	public int getMaxSampleSize()
	{
		return getInt("search.querysuggestion[@maxsamplesize]", MAX_SAMPLE_SIZE);
	}

	public int getMaxClusterContains()
	{
		return getInt("search.querysuggestion[@maxclustercontains]", MAX_CLUSTER_CONTAINS);
	}

	public int getMinClusterContains()
	{
		return getInt("search.querysuggestion[@minclustercontains]", MIN_CLUSTER_CONTAINS);
	}
}
