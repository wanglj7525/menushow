package utils;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.Constants;

/**
 * @ClassName: DBUtil
 * @Description: 数据库工具类
 * @author wlj
 * @date 2014-7-31 上午10:41:15
 *
 */ 
public class DBUtil {

    // mysql数据库对象
    public static play.db.DB db = new play.db.DB();
    
    /**
     * 关闭mysql数据库连接
     * 
     * @param conn
     *            连接对象
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void closeStatement(Statement ps) {
    	if (null != ps) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    public static void closeResultSet(ResultSet rs) {
    	if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    public static void dispose(Connection con, Statement ps, ResultSet rs) {
    	closeResultSet(rs);
    	closeStatement(ps);
    	closeConnection(con);
    }
}
