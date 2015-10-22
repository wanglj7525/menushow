package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.QueryException;

import play.Logger;
import play.db.jpa.JPA;

/**
 * @ClassName: SqlHelper
 * @Description: 数据库操作常用方法
 * @author wlj
 * @date 2014-7-31 上午10:36:22
 * 
 */
public class SqlHelper {

	/**
	 * 发送sql的更新语句
	 * 
	 * @author wlj
	 * @date 2014-7-31 上午10:42:28
	 * @param sql
	 *            更新sql
	 * @param param
	 *            预处理参数
	 * @return
	 */
	public static int updateBySql(String sql, Object... param) {
		int result = -1;
		try {
			Query query = JPA.em().createNativeQuery(sql);
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i + 1, param[i]);
			}
			result = query.executeUpdate();
		} catch (Exception e) {
			Logger.error("Error execute sql query: " + sql);
			throw new QueryException(e);
		}
		return result;
	}

	/**
	 * 使用指定的jdbc连接发送sql更新语句
	 * 
	 * @author wlj
	 * @date 2014-7-31 上午10:42:50
	 * @param con
	 *            JDBC Connection
	 * @param sql
	 *            更新sql
	 * @param param
	 *            预处理参数
	 * @return
	 */
	public static int updateBySql(Connection con, String sql, Object... param) {
		PreparedStatement ps = null;
		;
		int rows = -1;
		;
		try {
			ps = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			rows = ps.executeUpdate();
		} catch (SQLException e) {
			Logger.error("Error execute update SqlHelper.updateBySql" + sql);
			throw new QueryException(e);
		}
		DBUtil.closeStatement(ps);
		return rows;
	}

	/**
	 * 使用指定的jdbc连接针对同一个sql的多个不同参数做批量更新
	 * 
	 * @author wlj
	 * @date 2014-7-31 上午10:43:20
	 * @param con
	 *            JDBC Connection
	 * @param sql
	 *            更新sql
	 * @param param
	 *            sql预处理参数, List中每一个Object数组的值与sql是预处理参数的位置对应
	 */
	public static void batchUpdate(Connection con, String sql,
			List<Object[]> param) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			for (Object[] p : param) {
				for (int i = 0; i < p.length; i++) {
					ps.setObject(i + 1, p[i]);
				}
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			Logger.error("Error execute update SqlHelper.batchUpdateBySql: "
					+ sql);
			throw new QueryException(e);
		}
		DBUtil.closeStatement(ps);
	}

	/**
	 * 发送查询单个值的sql
	 * 
	 * @author wlj
	 * @date 2014-7-31 上午10:43:46
	 * @param sql
	 *            查询sql
	 * @param param
	 *            预处理参数
	 * @return
	 */
	public static Object singleResultBySql(String sql, Object... param) {
		Query query = JPA.em().createNativeQuery(sql);
		for (int i = 0; i < param.length; i++) {
			query.setParameter(i + 1, param[i]);
		}
		return query.getSingleResult();
	}

	/**
	 * @author wlj
	 * @date 2014-7-31 上午10:44:11
	 * @param <T>
	 * @param sql
	 * @param page
	 * @param pageSize
	 * @param rowMapper
	 * @param param
	 * @return
	 */
	public static <T> List<T> queryForList(String sql, Integer page,
			Integer pageSize, SqlRowMapper<T> rowMapper, Object... param) {
		if (null == page || page.intValue() < 1) {
			page = 1;
		}
		if (null == pageSize || pageSize.intValue() < 1) {
			pageSize = 10;
		}
		List<T> lstQueryResult = new ArrayList<T>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBUtil.db.getConnection();
			ps = con.prepareStatement(sql + " limit " + (pageSize * (page - 1))
					+ ", " + pageSize);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				T t = rowMapper.mapRow(rs);
				lstQueryResult.add(t);
			}
		} catch (SQLException e) {
			Logger.error("Error execute query SqlHelper.queryForList: " + sql);
			throw new QueryException(e);
		} finally {
			if (null != rs) {
				DBUtil.dispose(con, ps, rs);
			}
		}
		return lstQueryResult;
	}

	/**
	 * 发送查询语句, 并将每一条数据封装成map map的key是sql中列被as成的别名, 别名有多个单词的, 用下划线隔开
	 * 此方法会把用下划线隔开的as别名自动转换成以驼峰命名方法转换成成map的key
	 * 
	 * @author wlj
	 * @since 2014-7-31 上午10:44:21
	 * @param sql
	 *            查询语句
	 * @param page
	 *            分页参数 第几嶥
	 * @param pageSize
	 *            颁参数 第页多少条
	 * @param param
	 *            预处理参数
	 */
	public static List<Map> queryForMapList(String sql, Integer page,
			Integer pageSize, Object... param) {
		return queryForList(sql, page, pageSize, new JSONRowMapper(), param);
	}

	/**
	 * 发送查询sql, 将每一条查询结果封装成指定泛型参数的类型
	 * 
	 * @author wlj
	 * @date 2014-7-31 上午10:44:34
	 * @param sql
	 * @param rowMapper
	 * @param param
	 * @return
	 */
	public static <T> List<T> queryForList(String sql,
			SqlRowMapper<T> rowMapper, Object... param) {
		List<T> lstQueryResult = new ArrayList<T>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBUtil.db.getConnection();
			ps = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				T t = rowMapper.mapRow(rs);
				lstQueryResult.add(t);
			}
		} catch (SQLException e) {
			Logger.error("Error execute query SqlHelper.queryForList: " + sql);
			throw new QueryException(e);
		} finally {
			if (null != rs) {
				DBUtil.dispose(con, ps, rs);
			}
		}
		return lstQueryResult;
	}
	public static <T> List<T> queryForList(String sql,
			SqlRowMapper<T> rowMapper) {
		List<T> lstQueryResult = new ArrayList<T>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBUtil.db.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				T t = rowMapper.mapRow(rs);
				lstQueryResult.add(t);
			}
		} catch (SQLException e) {
			Logger.error("Error execute query SqlHelper.queryForList: " + sql);
			throw new QueryException(e);
		} finally {
			if (null != rs) {
				DBUtil.dispose(con, ps, rs);
			}
		}
		return lstQueryResult;
	}

	public static List<Map> queryForMapList(String sql, Object... param) {
		return queryForList(sql, new JSONRowMapper(), param);
	}

	public static <T> T queryForObject(String sql, SqlRowMapper<T> mapper,
			Object... param) {
		List<T> result = queryForList(sql, mapper, param);
		return (null == result || 0 == result.size()) ? null : result.get(0);
	}

	public static String queryForString(String sql, Object... param) {
		String r = queryForObject(sql, new SqlRowMapper<String>() {
			public String mapRow(ResultSet rs) throws SQLException {
				return rs.getString(1);
			}

		}, param);
		return null == r ? "" : r;
	}

	/**
	 * 查询一条数据的详细信息, 用sql中as后边的别名作为map的key, 用来输出json
	 * 
	 * @author wlj
	 * @date 2014-7-31 上午10:44:51
	 * @param sql
	 *            保证sql只返回一行记录
	 * @param param
	 *            sql 预处理参数
	 * @return
	 */
	public static Map singleMap(String sql, Object... param) {
		List<Map> result = queryForMapList(sql, param);
		return (null == result || 0 == result.size()) ? null : result.get(0);
	}

	public static Map queryForCountJSON(String sql, Object... param) {
		Map out = new HashMap();
		Object count = singleResultBySql(sql, param);
		out.put("ct", count);
		return out;
	}
	
	public static PageData queryForPage(String sql, String count, Integer page, Integer pageSize, Object... param) {
		return queryForPage(sql, count, page, pageSize, new JSONRowMapper(), param);
	}
	
	public static PageData queryForPage(String sql, String count, Integer page, Integer pageSize, SqlRowMapper rowMapper, Object... param) {
		if (null == page || page.intValue() < 1) {
			page = 1;
		}
		if (null == pageSize || pageSize.intValue() < 1) {
			pageSize = 10;
		}
		List rows = queryForList(sql, page, pageSize, rowMapper, param);
		Object records = singleResultBySql(count, param);
		PageData pageData = new PageData();
		pageData.setRows(rows);
		pageData.setPage(page);
		pageData.setPageSize(pageSize);
		pageData.setRecords(null == records ? 0 : Integer.parseInt(records.toString()));
		return pageData;
	}
	
	public static int queryForInt(String sql, Object... param) {
		Integer result = queryForObject(sql, new SqlRowMapper<Integer>() {
			public Integer mapRow(ResultSet rs) throws SQLException {
				return rs.getInt(1);
			}
		}, param);
		return null == result ? 0 : result.intValue();
	}
}
