package springcloud.outh2.project.common.page;

import java.io.Serializable;
import java.util.List;

public class PagingResult<E>implements Serializable {

	private static final long serialVersionUID = 1L;
	private int count;
	private int current;
	private int pageSize;
	private List<E> results;

	public PagingResult() {
	}

	public PagingResult(Paging paging) {
		this.current = paging.getPage();
		this.pageSize = paging.getPageSize();
	}

	public PagingResult(Paging paging, Number count) {
		this.current = paging.getPage();
		this.pageSize = paging.getPageSize();
		this.setCount(count);
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCurrent() {
		return this.current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<E> getResults() {
		return this.results;
	}

	public void setResults(List<E> results) {
		this.results = results;
	}

	public void setCount(Number count) {
		this.setCount(count == null ? 0 : count.intValue());
	}

	public int getTotalPage() {
		return (this.getCount() + this.getPageSize() - 1) / this.getPageSize();
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PagingResult [count=");
		builder.append(this.count);
		builder.append(", current=");
		builder.append(this.current);
		builder.append(", pageSize=");
		builder.append(this.pageSize);
		builder.append(", totalPage()=");
		builder.append(this.getTotalPage());
		builder.append(", results=");
		builder.append(this.results);
		builder.append("]");
		return builder.toString();
	}
}
