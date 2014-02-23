package cn.zhuhongqing.lucene.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import cn.zhuhongqing.utils.DocumentUtil;

public class LuceneRCore<T> extends LuceneBaseCore<T> {

	public Map<T, Float> searchIndexInFile(Query query, Class<T> beanClass) {

		return searchIndex(luceneOption.getDirectory(), query, beanClass);

	}

	public Map<T, Float> searchIndexInRam(Query query, Class<T> beanClass) {

		return searchIndex(luceneOption.getRamDirectory(), query, beanClass);

	}

	public Map<T, Float> searchIndex(Directory directory, Query query,
			Class<T> beanClass) {

		Map<T, Float> tSet = new HashMap<T, Float>();

		Map<Document, Float> documentMap = searchIndex(directory, query);

		Iterator<Entry<Document, Float>> it = documentMap.entrySet().iterator();

		while (it.hasNext()) {
			Entry<Document, Float> entry = it.next();

			tSet.put(DocumentUtil.Document2Bean(entry.getKey(), beanClass),
					entry.getValue());
		}

		return tSet;

	}

	public Map<Document, Float> searchIndex(Directory directory, Query query) {

		IndexSearcher indexSearcher = null;

		try {
			indexSearcher = new IndexSearcher(directory, true);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {

			TopDocs topDocs = indexSearcher.search(query,
					luceneOption.getMaxResult());

			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			List<ScoreDoc> scoreDocList = Arrays.asList(scoreDocs);

			if (luceneOption.getLastPage() != 0) {

				int realLastPage = scoreDocList.size() > luceneOption
						.getLastPage() ? luceneOption.getLastPage()
						: scoreDocList.size();

				scoreDocList = scoreDocList.subList(
						luceneOption.getFirstPage(), realLastPage);

				luceneOption.refreshPages();
			}

			Map<Document, Float> documentMap = new HashMap<Document, Float>();

			for (ScoreDoc scoreDoc : scoreDocList) {

				documentMap
						.put(indexSearcher.doc(scoreDoc.doc), scoreDoc.score);
			}

			return documentMap;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				indexSearcher.close();
				indexSearcher = null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
