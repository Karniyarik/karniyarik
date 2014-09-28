package com.karniyarik.search.searcher.suggest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;

import com.karniyarik.search.searcher.logger.SearchLogIndexer;
import com.karniyarik.search.searcher.query.Query;
import com.karniyarik.search.solr.SOLRSearchProxy;

public class Suggester
{
	public Suggester()
	{
	}

	public String[] suggest(QueryResponse response, int catType, SOLRSearchProxy searcher, String categoryName)
	{
		List<String> result = new ArrayList<String>();
		
		if(response != null && response.getSpellCheckResponse() != null)
		{
			if(response.getResults().getNumFound() < 1)
			{
				SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
				List<String> wordList = new ArrayList<String>();
				
				for(Suggestion suggestion: spellCheckResponse.getSuggestions())
				{
					String wordToBeAdded = null;
					double maxScore = 0;
					for(String word: suggestion.getAlternatives())
					{
						double score = SearchLogIndexer.getInstance().docFreq(word, catType);
						if(score >= maxScore)
						{
							wordToBeAdded = word;
							maxScore = score;
						}
					}
					if(wordToBeAdded != null)
					{
						wordToBeAdded = wordToBeAdded.trim();
						if(!wordList.contains(wordToBeAdded))
						{
							wordList.add(wordToBeAdded);	
						}				
					}
				}
				
				String suggestionStr = StringUtils.join(wordList, " ");
//				if(StringUtils.isNotBlank(spellCheckResponse.getCollatedResult()) && !result.contains(spellCheckResponse.getCollatedResult()))
//				{
//					result.add(spellCheckResponse.getCollatedResult());
//				}
				if(StringUtils.isNotBlank(suggestionStr))
				{
					Query query = new Query();
					query.setCategory(categoryName);
					query.setQueryString(suggestionStr);
					query.setLuceneQuery(suggestionStr);
					query.setPageSize(1);
					query.setPageNumber(1);
					QueryResponse search = searcher.search(query);
					if(search != null && search.getResults() != null && search.getResults().getNumFound() > 0)
					{
						result.add(suggestionStr);	
					}
				}
			}
		}
		
		return (String[]) result.toArray(new String[result.size()]);
	}
}