package org.bjdrgs.bjwt.core.web;

import java.io.Serializable;
import java.util.List;
/**
 * DataGrid数据加载请求的返回数据
 * @author ying
 *
 * @param <T>
 */
public class GridPage<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int total;
	private List<T> rows;
	
	public GridPage(){
	}
	public GridPage(int total, List<T> rows){
		this.total = total;
		this.rows = rows;
	}
	
	public GridPage(Pagination<T> pagination){
		this.total = pagination.getRecordCount();
		this.rows = pagination.getResult();
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
