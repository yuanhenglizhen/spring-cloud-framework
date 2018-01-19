package com.huilian.hlej.common.core.base.vo;

public class PaginRequest extends Request {
	private int pageIndex = 1;
	private int pageSize = 20;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPaginLimit() {
		return this.pageSize;
	}

	public int getPaginOffset() {
		int paginOffset = (pageIndex - 1) * 20;
		return paginOffset > 0 ? paginOffset : 0;
	}

}
