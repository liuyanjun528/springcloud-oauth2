package springcloud.outh2.project.common.page;

import java.io.Serializable;

public class Paging implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final long DEFAUT_START = 0L;
	private int page = 1;
	private int pageSize = 10;

	public Paging(int page) {
		this.setPage(page);
		this.setPageSize(10);
	}

	public Paging(int page, int pageSize) {
		this.setPage(page);
		this.setPageSize(pageSize);
	}

	public final int getPage() {
		return this.page;
	}

	public final int getPageSize() {
		return this.pageSize;
	}

	public final int getOffset() {
		return (this.page - 1) * this.pageSize;
	}

	public void setP(String p) {
		this.setPage(this.parseInt(p, 1));
	}

	public void setS(String s) {
		this.setPageSize(this.parseInt(s, 10));
	}

	public int parseInt(String num, int defaultValue) {
		try {
			return Integer.parseInt(num);
		} catch (Exception var4) {
			return defaultValue;
		}
	}

	public void setPage(int page) {
		if (page > 0) {
			this.page = page;
		}

	}

	public void setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}

	}

	public String toString() {
		return String.format("Paging: page = %s pageSize = %s", this.page, this.pageSize);
	}
}
