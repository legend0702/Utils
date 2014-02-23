package cn.zhuhongqing.lucene.core;

import java.util.Collection;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;

public interface LuceneCore<T> {

	public abstract void createIndex(Collection<Document> documents);

	public abstract void deleteIndexInFile(Query... querys);

	public abstract void updateIndexInFile(Collection<Document> documents,
			Query... querys);

	public abstract Map<T, Float> searchIndexInFile(Query query,
			Class<T> beanClass);

	public abstract Map<T, Float> searchIndexInRam(Query query,
			Class<T> beanClass);

	public abstract Map<T, Float> searchIndex(Directory directory, Query query,
			Class<T> beanClass);

	public abstract Map<Document, Float> searchIndex(Directory directory,
			Query query);

}