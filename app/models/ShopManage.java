package models;

import java.util.List;


public class ShopManage {

	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public static int deleteInfo(Long id) {
		List<User> user=User.find(" Shopid=? ", id).fetch();
		if (user.size()>0) {
			return -1;
		}
		return Shop.delete("id=?", id);
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
		Shop shop = Shop.find("Shopname=? ", name.trim()).first();
		boolean b = true;
		if (id==null) {
			b = (shop == null);
		} else {
			if (shop != null)
				b = (shop.id == Long.parseLong(id));
			else
				b = true;
		}
		return b;
	}

}
