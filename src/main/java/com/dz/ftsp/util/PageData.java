package com.dz.ftsp.util;

import java.util.List;

@Deprecated
public class PageData<T> {
	
	private List<T> data;  //分页查询到的数据
	
	private long iTotalDisplayRecords;  //过滤后总记录数
	
	private long iTotalRecords;     //总记录数

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	
	
}
