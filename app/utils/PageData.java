package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询结果的数据封装对象
 * 以及sql查询相关的参数封装
 * 提供数据格式 {page: 页号, total: 总页数, records: 总条数, rows:[分页查询结果]}
 * @author rainyhao haojz@golaxy.cn
 * @since 2013-07-02 09:36:00
 */
@SuppressWarnings("rawtypes")
public class PageData {
	
	public PageData() {
	}
	public PageData(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}
	public PageData(int page, int records, int pageSize) {
		this.page = page;
		this.records = records;
		this.pageSize = pageSize;
	}
	// 当前要查询的页号
	private int page;
	// 数据总条数
	private int records;
	// 页尺寸
	private int pageSize;
	// 总页数
	public int total;
	// 分页查询结果
	private List rows;
	
	/**
	 * 当前页号
	 */
	public int getPage() {
		// 如果没有接收到查询页号, 从第一页查
		if (0 == page) {
			return 1;
		}
		return page;
	}
	/**
	 * 设置当前页号
	 */
	public void setPage(Integer page) {
		if (null == page) {
			page = 1;
		}
		this.page = page.intValue();
		calcTotal();
	}
	/**
	 * 总页数
	 */
	public void calcTotal() {
		if (0 != records) {
			int c = records / getPageSize();
			int left = records % getPageSize();
			this.total = (left > 0) ? c + 1 : c; 
		} else {
			this.total = 0;
		}
	}
	/**
	 * 记录总数
	 */
	public int getRecords() {
		return records;
	}
	/**
	 * 设置记录总数
	 */
	public void setRecords(int records) {
		this.records = records;
		calcTotal();
	}
	/**
	 * 每页条数据条数
	 */
	public int getPageSize() {
		// 如果没有接收到查询页号, 默认每页查20行
		if (0 == pageSize) {
			return 10;
		}
		return pageSize;
	}
	/**
	 * 设置页尺寸
	 */
	public void setPageSize(Integer pageSize) {
		if (null == pageSize) {
			pageSize = 10;
		}
		this.pageSize = pageSize.intValue();
		calcTotal();
	}
	
	public int getTotal() {
		calcTotal();
		return total;
	}
	
	/**
	 * 获取查询结果
	 */
	public List getRows() {
		if (null == rows) {
			return new ArrayList();
		}
		return rows;
	}
	/**
	 * 保存查询结果
	 */
	public void setRows(List rows) {
		this.rows = rows;
	}
}
