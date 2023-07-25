package com.dz.ftsp.util;

import java.util.List;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Deprecated
public class DzPageHelper<T> {
	
	private List<T> list;
	
	
	public DzPageHelper(List<T> list) {
		super();
		this.list = list;
	}

	public static void startPage(int start,int length) {
		int pageNum =1;
		//lenght =0时，返回全部数据
		if(length !=0) {
			pageNum = start/length+1;
		}
		PageHelper.startPage(pageNum, length);
	}

	
	public PageData<T> getPageData() {
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		long total = pageInfo.getTotal();
		PageData<T> pageData = new PageData<T>();
		pageData.setData(list);
		pageData.setiTotalDisplayRecords(total);
		pageData.setiTotalRecords(list.size());
		return pageData;
	} 
	
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
