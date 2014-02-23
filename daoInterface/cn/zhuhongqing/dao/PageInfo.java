package cn.zhuhongqing.dao;


//用于封装分页请求信息
public class PageInfo {

	private int currentpage = 1;
	private int pagesize = 10;
	private int startindex;
	
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getStartindex() {
		this.startindex = (this.currentpage-1)*this.pagesize;
		return startindex;
	}
	
}
