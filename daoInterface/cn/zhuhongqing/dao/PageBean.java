package cn.zhuhongqing.dao;

import java.util.Collection;

//service  根据 queryReslut 生成  pageBean
public class PageBean {

	//分页显示的记录
	private Collection<Object> beanCol;
	// 总记录数
	private int totalrecord;
	// 页面大小
	private int pagesize;
	// 总页数
	private int totalpage;
	// 当前页
	private int currentpage;
	// 上一页
	private int previouspage;
	// 下一页
	private int nextpage;

	public int getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalpage() {

		if (this.totalrecord % this.pagesize == 0) {
			this.totalpage = this.totalrecord / this.pagesize;
		} else {
			this.totalpage = this.totalrecord / this.pagesize + 1;
		}
		return totalpage;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getPreviouspage() {
		this.previouspage = this.currentpage - 1;
		if (this.previouspage < 1) {
			this.previouspage = 1;
		}
		return previouspage;
	}

	public int getNextpage() {
		this.nextpage = this.currentpage + 1;
		if (this.nextpage > this.totalpage) {
			this.nextpage = this.totalpage;
		}
		return nextpage;
	}

	public int[] getPagebar() {

		// 17 11 20 17-7+1
		// 25 21 30 25-5+1
		// 5 1 10 5-5+1
		// 10 1 10-10 + 1

		int startpage = this.currentpage
				- (this.currentpage % 10 == 0 ? 10 : this.currentpage % 10) + 1;

		int endpage = startpage + 9;

		if (endpage > this.totalpage) {
			endpage = this.totalpage;
		}

		int pagebar[] = new int[endpage - startpage + 1]; // 21 22 23 24 25 0 0
															// 0 0 0
		int index = 0;
		for (int i = startpage; i <= endpage; i++) { // 21 25
			pagebar[index++] = i;
		}

		return pagebar;
	}

	public Collection<Object> getBeanCol() {
		return beanCol;
	}

	public void setBeanCol(Collection<Object> beanCol) {
		this.beanCol = beanCol;
	}

}
