package cn.zhuhongqing.lucene.base;

import java.util.Map;

import org.apache.lucene.search.Query;

public abstract class LuceneCURDSupport<T> extends LuceneCUDSupport<T> {

	public Map<T, Float> searchIndexInRamPages(String indexPoolName,
			String searchText, int firstPage, int lastPage,
			String... searchFields) {

		return searchIndexInRamPages(indexPoolName, searchText, firstPage,
				lastPage, 0, searchFields);

	}

	public Map<T, Float> searchIndexInRamPages(String indexPoolName,
			String searchText, int firstPage, int lastPage, int maxResult,
			String... searchFields) {

		if (maxResult != 0) {
			setLuceneOptionMaxResult(indexPoolName, maxResult);
		}

		setLuceneOptionPages(indexPoolName, firstPage, lastPage);

		return searchIndexInRamDefault(indexPoolName, searchText, searchFields);

	}

	public Map<T, Float> searchIndexInRamDefault(String indexPoolName,
			Query query) {

		return findLuceneCore(indexPoolName)
				.searchIndexInFile(query, beanClass);

	}

	public Map<T, Float> searchIndexInRamDefault(String indexPoolName,
			String searchText, String... searchFields) {

		return searchIndexInRamDefault(indexPoolName,
				createMultiFieldQuery(indexPoolName, searchText, searchFields));
	}
}
