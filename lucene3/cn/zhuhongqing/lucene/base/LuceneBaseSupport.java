package cn.zhuhongqing.lucene.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

import cn.zhuhongqing.forbean.LuceneOption;
import cn.zhuhongqing.lucene.core.LuceneCURDCore;
import cn.zhuhongqing.lucene.core.LuceneCore;
import cn.zhuhongqing.utils.GenericsUtils;

public abstract class LuceneBaseSupport<T> {

	protected static ThreadLocal<Map<String, LuceneOption>> threadMap;

	@SuppressWarnings("unchecked")
	protected Class<T> beanClass = (Class<T>) GenericsUtils
			.getClassGenricType(this.getClass());

	public LuceneBaseSupport() {

		threadMap = new ThreadLocal<Map<String, LuceneOption>>();

		threadMap.set(new HashMap<String, LuceneOption>());
	}

	public void RefreshLuceneRamIndex() {

		Collection<LuceneOption> luceneOptions = threadMap.get().values();

		for (LuceneOption lo : luceneOptions) {
			lo.refreshRam();
		}

	}

	public void RefreshLuceneRamIndex(String indexPoolName) {

		threadMap.get().get(indexPoolName).refreshRam();

	}

	public void createLuceneOption(String indexPoolName, String indexPath,
			int maxResult) {
		LuceneOption luceneOption = new LuceneOption();

		luceneOption.setDirectory(indexPath);

		luceneOption.setAnalyzer(null);

		luceneOption.setVersion(Version.LUCENE_30);

		luceneOption.setMaxResult(maxResult);

		threadMap.get().put(indexPoolName, luceneOption);

	}

	public void createLuceneOption(String indexPoolName, String indexPath,
			Analyzer analyzer, Version version, int maxResult) {

		LuceneOption luceneOption = new LuceneOption();

		luceneOption.setDirectory(indexPath);

		luceneOption.setAnalyzer(analyzer);

		luceneOption.setVersion(version);

		luceneOption.setMaxResult(maxResult);

		threadMap.get().put(indexPoolName, luceneOption);

	}

	public void setAnalyzer(String indexPoolName, Analyzer analyzer) {
		threadMap.get().get(indexPoolName).setAnalyzer(analyzer);
	}

	public void setLuceneOptionPages(String indexPoolName, int firstPage,
			int lastPage) {
		LuceneOption luceneOption = threadMap.get().get(indexPoolName);

		luceneOption.setFirstPage(firstPage);

		luceneOption.setLastPage(lastPage);
	}

	public void setLuceneOptionMaxResult(String indexPoolName, int maxResult) {
		threadMap.get().get(indexPoolName).setMaxResult(maxResult);
	}

	protected LuceneCore<T> findLuceneCore(String indexPoolName) {

		LuceneCURDCore<T> luceneCore = new LuceneCURDCore<T>();

		luceneCore.setLuceneOption(threadMap.get().get(indexPoolName));

		return luceneCore;
	}

	protected Query createMultiFieldQuery(String indexPoolName,
			String searchText, String... searchFields) {

		LuceneOption luceneOption = threadMap.get().get(indexPoolName);

		QueryParser queryParser = new MultiFieldQueryParser(
				luceneOption.getVersion(), searchFields,
				luceneOption.getAnalyzer());

		try {
			return queryParser.parse(searchText);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
