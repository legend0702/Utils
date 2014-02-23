package cn.zhuhongqing.lucene.core;

import cn.zhuhongqing.forbean.LuceneOption;

public abstract class LuceneBaseCore<T> {

	protected LuceneOption luceneOption;

	public LuceneOption getLuceneOption() {
		return luceneOption;
	}

	public void setLuceneOption(LuceneOption luceneOption) {
		this.luceneOption = luceneOption;
	}

}
