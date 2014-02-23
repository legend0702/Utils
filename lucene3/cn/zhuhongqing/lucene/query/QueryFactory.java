package cn.zhuhongqing.lucene.query;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

public class QueryFactory {

	public static Query createUniqueQuery(String indexName, String indexValue) {
		Term term = new Term(indexName, indexValue);
		return new TermQuery(term);
	}

	public static Query createWildCardQuery(String indexName, String indexValue) {
		Term term = new Term(indexName, indexValue);
		return new WildcardQuery(term);
	}

	public static Query createAllQuery() {
		return new MatchAllDocsQuery();
	}

	public static Query createBooleanQuery(Map<Query, Occur> queryMap) {
		BooleanQuery booleanQuery = new BooleanQuery();
		Iterator<Entry<Query, Occur>> it = queryMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Query, Occur> entry = it.next();
			booleanQuery.add(entry.getKey(), entry.getValue());
		}

		return booleanQuery;
	}

}
