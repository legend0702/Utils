package cn.zhuhongqing.lucene.core;

import java.util.Collection;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.Query;

public class LuceneCUDCore<T> extends LuceneBaseCore<T> {

	public void createIndex(Collection<Document> documents) {

		IndexWriter indexWriter = null;

		try {
			indexWriter = new IndexWriter(getLuceneOption().getDirectory(),
					getLuceneOption().getAnalyzer(), MaxFieldLength.LIMITED);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {

			Iterator<Document> it = documents.iterator();
			while (it.hasNext()) {
				indexWriter.addDocument(it.next());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				indexWriter.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
	}

	public void updateIndexInFile(Collection<Document> documents,
			Query... querys) {

		IndexWriter indexWriter = null;

		try {
			indexWriter = new IndexWriter(getLuceneOption().getDirectory(),
					getLuceneOption().getAnalyzer(), MaxFieldLength.LIMITED);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {

			// indexWriter.updateDocument(term, document);

			indexWriter.deleteDocuments(querys);

			Iterator<Document> it = documents.iterator();
			while (it.hasNext()) {
				indexWriter.addDocument(it.next());
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				indexWriter.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
	}

	public void deleteIndexInFile(Query... querys) {

		IndexWriter indexWriter = null;

		try {
			indexWriter = new IndexWriter(getLuceneOption().getDirectory(),
					getLuceneOption().getAnalyzer(), MaxFieldLength.LIMITED);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {

			indexWriter.deleteDocuments(querys);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				indexWriter.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
	}
}
