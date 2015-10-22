package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: SqlRowMapper
 * @Description: JDBC查询模版方法中 查询结果封装的回调函数接口
 * @author wlj
 * @date 2014-7-31 上午10:41:48
 * 
 * @param <T>
 */
public interface SqlRowMapper<T> {

	/**
	 * 映射每一条jdbc ResultSet中的查询结果到指定的泛型参数对象中
	 * 
	 * @author wlj
	 * @date 2014-7-31 上午10:42:03
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public T mapRow(ResultSet rs) throws SQLException;
}
