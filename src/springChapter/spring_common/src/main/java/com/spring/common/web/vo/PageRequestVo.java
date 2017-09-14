package com.spring.common.web.vo;

/**
 * 分页请求对象
 * 
 * @author chenhaiyan
 *
 */
public class PageRequestVo<T> {
	/**
	 * 页码
	 */
	private int pageNum;
	/**
	 * 分页大小
	 */
	private int pageSize;
	/**
	 * 过滤参数
	 */
	private T params;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public T getParams() {
		return params;
	}

	public void setParams(T params) {
		this.params = params;
	}

}
