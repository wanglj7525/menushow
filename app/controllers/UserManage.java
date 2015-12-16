package controllers;

import java.util.List;

import models.Shop;
import models.User;
import play.mvc.Controller;
import play.mvc.With;
import utils.Crypto;
@With(Secure.class)
public class UserManage extends Controller {
	public static void index() {
		render();
	}

	public static void userPage() {
		List<User> userList = models.UserManage.allInfos();
		for (User user : userList) {
			if (user.Shopid!=null) {
				Shop shop=Shop.findById(user.Shopid);
				user.shopname=shop.Shopname;
			}else{
				user.shopname="无代理商";
			}
		}
		List<Shop> shopList = Shop.findAll();
		render(userList,shopList);
	}
	
	public static void deleteUser(Long id) {
		int success = models.UserManage.deleteInfo(id);
		renderJSON(success);
	}
	
	public static void editUserPage(Integer id) {
		User info = User.findById(id);
		List<Shop> shopList = Shop.findAll();
		render(info,shopList);
	}
	
	public static void addUser(User user) {
		String pass=Crypto.passwordHash(user.u_pass);
		user.u_pass=pass;
		boolean success = user.save().isPersistent();
		renderJSON(success);
	}

	public static void updateUser(User user) {
		String pass=Crypto.passwordHash(user.u_pass);
		user.u_pass=pass;
		boolean success = user.save().isPersistent();
		renderJSON(success);
	}
	
	
	/**
	 * 姓名是否存在
	 * 
	 * @param username
	 * @param id
	 * @return
	 */
	public static boolean nameIsExist(String name, String id) {
		// false 表示已经存在
		boolean b = models.UserManage.nameIsExist(name, id);
		return b;
	}
}
