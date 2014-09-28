package com.karniyarik.search.searcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FieldStatsInfo;

import com.karniyarik.ir.vo.ResultFilterInfo;

public class ResultProperty {
	private FacetField field;
	private FieldStatsInfo stat;
	private String name;
	
	public ResultProperty(String name, FacetField field, FieldStatsInfo stat) {
		this.field = field;
		this.stat = stat;
		this.name = name;
	}

	public FacetField getField() {
		return field;
	}

	public FieldStatsInfo getStat() {
		return stat;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
//	public List<ResultFilterInfo> getClusters() {
//		return clusters;
//	}
//	
//	public void setClusters(List<ResultFilterInfo> clusters) {
//		this.clusters = clusters;
//	}
}
