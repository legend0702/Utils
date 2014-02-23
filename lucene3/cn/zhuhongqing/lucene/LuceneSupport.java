package cn.zhuhongqing.lucene;

import java.util.Collection;
import java.util.Map;

import cn.zhuhongqing.forbean.DocumentBean;
import cn.zhuhongqing.lucene.base.LuceneCURDSupport;
import cn.zhuhongqing.lucene.query.QueryFactory;
import cn.zhuhongqing.utils.DocumentUtil;

public class LuceneSupport<T> extends LuceneCURDSupport<T> {

	protected DocumentBean documentBean;

	public void createIndexDefault(String indexPoolName, Collection<T> beans) {

		createIndex(indexPoolName,
				DocumentUtil.Bean2Documents(getDocumentBean(), beans.toArray()));

	}

	public Map<T, Float> searchAllInRam(String indexPoolName) {
		return searchIndexInRamDefault(indexPoolName,
				QueryFactory.createAllQuery());
	}

	public Map<T, Float> searchUniquInRam(String indexPoolName,
			String searchText, String searchField) {
		return searchIndexInRamDefault(indexPoolName,
				QueryFactory.createUniqueQuery(searchField, searchText));
	}

	public Map<T, Float> searchWildCardInRam(String indexPoolName,
			String searchText, String searchField) {
		return searchIndexInRamDefault(indexPoolName,
				QueryFactory.createWildCardQuery(searchField, searchText));
	}

	public void updateIndexInFileDefaultByUnique(String indexPoolName, T bean,
			String searchTest, String searchField) {

		updateIndexInFile(indexPoolName,
				DocumentUtil.Bean2Documents(getDocumentBean(), bean),
				QueryFactory.createUniqueQuery(searchField, searchTest));
	}

	public void deleteIndexInFileDefaultByUnique(String indexPoolName,
			String searchTest, String searchField) {

		deleteIndexInFile(indexPoolName,
				QueryFactory.createUniqueQuery(searchField, searchTest));
	}

	public DocumentBean getDocumentBean() {
		return documentBean;
	}

	public void setDocumentBean(DocumentBean documentBean) {
		this.documentBean = documentBean;
	}

}
