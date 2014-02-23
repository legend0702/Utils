package cn.zhuhongqing.lucene.base;

import java.util.Collection;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

public class LuceneCUDSupport<T> extends LuceneBaseSupport<T> {

	public void createIndex(String indexPoolName, Collection<Document> documents) {

		findLuceneCore(indexPoolName).createIndex(documents);

		RefreshLuceneRamIndex(indexPoolName);

	}

	public void deleteIndexInFile(String indexPoolName, Query... querys) {

		findLuceneCore(indexPoolName).deleteIndexInFile(querys);

		RefreshLuceneRamIndex(indexPoolName);

	}

	public void updateIndexInFile(String indexPoolName,
			Collection<Document> documents, Query... querys) {

		findLuceneCore(indexPoolName).updateIndexInFile(documents, querys);

		RefreshLuceneRamIndex(indexPoolName);

	}

}
