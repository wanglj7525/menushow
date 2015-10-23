package models;

import java.util.List;
import java.util.Map;

import utils.JSONRowMapper;
import utils.SqlHelper;

public class SqlModel {

	/**
	 * 登录
	 * 
	 * @author wlj
	 * @date 2015-10-23 下午02:09:00
	 * @param username
	 * @param password
	 * @return
	 */
	public static List login(String username, String password) {
		String loginsql = "select * from t_user a where a.u_name=? and a.u_pass=?";
		List<Map> user = SqlHelper
				.queryForMapList(loginsql, username, password);
		return user;
	}

	/**
	 * 注册
	 * 
	 * @author wlj
	 * @date 2015-10-23 下午02:09:07
	 * @param username
	 * @param password
	 * @return
	 */
	public static Integer saveUserInfo(String username, String password) {
		t_user tu = t_user.find(" u_name=? ", username).first();
		if (tu != null) {
			return -1;
		}
		t_user user = new t_user();
		user.u_name = username;
		user.u_pass = password;
		user.Nf=200;
		if (user.save().isPersistent()) {
			return user.id;
		} else {
			return 0;
		}
	}

	/**
	 * 门店列表
	 * @author wlj
	 * @date 2015-10-23 下午02:12:02
	 * @param id
	 * @return
	 */
	public static List getStore(Integer id) {
		String storesql = "select * from t_store a where a.u_id=?";
		List<Map> store = SqlHelper.queryForMapList(storesql, id);
		return store;
	}
	
	public static Integer addStoreInfo(String s_name,String dog_id,String userid) {
		t_store tu = t_store.find(" s_name=? ", s_name).first();
		if (tu != null) {
			return -1;
		}
		t_store store = new t_store();
		store.s_name=s_name;
		store.dog_id=dog_id;
		store.u_id=Long.parseLong(userid);
		if (store.save().isPersistent()) {
			return store.id;
		} else {
			return 0;
		}
	}
	public static Integer updateStoreInfo(Integer s_id,String s_name,String dog_id) {
		t_store tu = t_store.find(" s_name=? and id<> ?", s_name,s_id).first();
		if (tu != null) {
			return -1;
		}
		t_store store = t_store.findById(s_id);
		store.s_name=s_name;
		store.dog_id=dog_id;
		if (store.save().isPersistent()) {
			return store.id;
		} else {
			return 0;
		}
	}

}
