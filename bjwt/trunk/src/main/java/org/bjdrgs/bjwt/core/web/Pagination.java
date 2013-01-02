package org.bjdrgs.bjwt.core.web;

import java.io.Serializable;
import java.util.List;

/**
 * 分页封装对象
 * @author yyc
 * @date 2012-12-12
 *
 */
public class Pagination<T> implements Serializable{
	private static final long serialVersionUID = 1L;

	//每页记录数
	private int pageSize=10;

	//当前页编号
	private int pageNo=1;

	//总记录数
	private int recordCount;

	//返回的结果集
	private List<T> result;


	public Pagination() {
	}

	public Pagination(List<T> result, int pageSize, int pageNo, int recordCount) {
		this.result = result;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.recordCount = recordCount;
	}

	public Pagination(List<T> result, int pageSize, int pageNo, int recordCount, int pageCount) {
		this.result = result;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.recordCount = recordCount;
	}

	/**
	 * @return
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return
	 */
	public int getRecordCount() {
		return recordCount;
	}

	/**
	 * @param recordCount
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * @return
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * @param result
	 */
	public void setResult(List<T> result) {
		this.result = result;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}
	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public int getTotalPages() {
		if (recordCount < 0) {
			return -1;
		}

		int count = Long.valueOf(recordCount / pageSize).intValue();
		if (recordCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}
}
