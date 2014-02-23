package cn.zhuhongqing.dao;

import java.util.ArrayList;
import java.util.Collection;

public class QueryResult<T> {

	private Collection<T> resultCol = new ArrayList<T>(0);

	private Number totalRecord;

	public QueryResult() {
	}

	public QueryResult(Collection<T> resultCol, Number totalRecord) {
		setResultCol(resultCol);
		setTotalRecord(totalRecord);
	}

	public Number getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Number totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Collection<T> getResultCol() {
		return resultCol;
	}

	public void setResultCol(Collection<T> resultCol) {
		this.resultCol = resultCol;
	}

}
