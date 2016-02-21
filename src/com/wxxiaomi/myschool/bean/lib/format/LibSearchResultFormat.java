package com.wxxiaomi.myschool.bean.lib.format;

import java.util.List;

import com.wxxiaomi.myschool.bean.lib.BookInfo;

public class LibSearchResultFormat {

	public List<BookInfo> list;
	
	/**
	 * 下一页地址url
	 */
	public String nextPageUrl;
	/**
	 * 总页数
	 */
	public int pageCount;
	/**
	 * 当前是第几页
	 */
	public int currentPage;
}
