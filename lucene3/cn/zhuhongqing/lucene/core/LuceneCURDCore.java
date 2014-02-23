package cn.zhuhongqing.lucene.core;

import java.util.Collection;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;

import cn.zhuhongqing.forbean.LuceneOption;

public class LuceneCURDCore<T> extends LuceneBaseCore<T> implements
		LuceneCore<T> {

	private LuceneCUDCore<T> luceneCUDCore;

	private LuceneRCore<T> luceneRCore;

	public LuceneCURDCore() {

		luceneCUDCore = new LuceneCUDCore<T>();

		luceneRCore = new LuceneRCore<T>();

	}

	public void setLuceneOption(LuceneOption luceneOption) {
		this.luceneOption = luceneOption;
		luceneCUDCore.setLuceneOption(luceneOption);
		luceneRCore.setLuceneOption(luceneOption);
	}

	public void createIndex(Collection<Document> documents) {
		luceneCUDCore.createIndex(documents);
	}

	public Map<T, Float> searchIndexInFile(Query query, Class<T> beanClass) {
		return luceneRCore.searchIndexInFile(query, beanClass);
	}

	public Map<T, Float> searchIndexInRam(Query query, Class<T> beanClass) {
		return luceneRCore.searchIndexInRam(query, beanClass);
	}

	public Map<T, Float> searchIndex(Directory directory, Query query,
			Class<T> beanClass) {
		return luceneRCore.searchIndex(directory, query, beanClass);
	}

	public Map<Document, Float> searchIndex(Directory directory, Query query) {
		return luceneRCore.searchIndex(directory, query);
	}

	public void deleteIndexInFile(Query... querys) {
		luceneCUDCore.deleteIndexInFile(querys);
	}

	public void updateIndexInFile(Collection<Document> documents,
			Query... querys) {
		luceneCUDCore.updateIndexInFile(documents, querys);
	}

}
