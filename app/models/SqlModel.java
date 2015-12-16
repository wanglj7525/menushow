package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
		User tu = User.find(" u_name=? ", username).first();
		if (tu != null) {
			return -1;
		}
		User user = new User();
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
		for (Map map : store) {
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		Date date= sdf.parse(map.get("expireddate").toString());
	    		long s1=date.getTime();//将时间转为毫秒
	    		long s2=System.currentTimeMillis();//得到当前的毫秒
	    		int day=(int)((s2-s1)/1000/60/60);
//	    		double day=(double)((s2-s1)/1000/60/60/24);
//	    		System.out.println("距现在已有"+day+"小时，你得抓紧时间学习了" );
	    		if (day>=0) {
	    			//过期
	    			map.put("expireddate", 0);
				}else{
					//没过期
					map.put("expireddate",1);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return store;
	}
	
	public static Integer addStoreInfo(String s_name,String dog_id,String userid) {
		Store tu = Store.find(" s_name=? ", s_name).first();
		if (tu != null) {
			return -1;
		}
		Store tu2 = Store.find(" dog_id=? ", dog_id).first();
		if (tu2 != null) {
			return -2;
		}
		Store store = new Store();
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
		Store tu = Store.find(" s_name=? and id<> ?", s_name,s_id).first();
		if (tu != null) {
			return -1;
		}
		Store tu2 = Store.find(" dog_id=? and id<> ?", dog_id,s_id).first();
		if (tu2 != null) {
			return -2;
		}
		Store store = Store.findById(s_id);
		store.s_name=s_name;
		store.dog_id=dog_id;
		if (store.save().isPersistent()) {
			return store.id;
		} else {
			return 0;
		}
	}

}
