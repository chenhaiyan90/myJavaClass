package com.spring.common.web.vo;

import java.util.List;

/**
 * 
 * 返回json对象/用于jquery ui datagrid
 * 
 * @author duwenxue
 */
public class  Datagrid<T> {
	/** 返回成功与否 */
	private Integer errcode;
	/** 总记录 */
	private int total;
	/** 数组 */
	private List<T> rows;

	public Datagrid() {
		errcode = 0;
		total = 0;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}



}
