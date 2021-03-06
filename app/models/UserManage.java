package models;

import java.util.List;

public class UserManage {

	public static List<User> allInfos() {
		return User.find("order by id desc").fetch();
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public static int deleteInfo(Long id) {
		List<Store> store=Store.find(" u_id=? ",id).fetch();
		if (store.size()>0) {
			//有门店信息 不能删除 TODO
			return -1;
		}else{
			return User.delete("id=?", id);
		}
	}
	
	/**
	 * 验证
	 * 
	 * @param
	 * @param id
	 *            修改时，该账号的id
	 * @return
	 */
	public static boolean nameIsExist(String name, String id) {
		User user = User.find("u_name=? ", name.trim()).first();
		boolean b = true;
		if (id==null) {
			b = (user == null);
		} else {
			if (user != null)
				b = (user.id == Long.parseLong(id));
			else
				b = true;
		}
		return b;
	}

}
