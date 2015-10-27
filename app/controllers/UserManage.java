package controllers;

import java.util.List;

import models.User;
import play.mvc.Controller;

public class UserManage extends Controller {
	public static void index() {
		render();
	}

	public static void userPage() {
		List<User> userList = models.UserManage.allInfos();
		render(userList);
	}
	
	public static void deleteUser(Integer id) {
		int success = models.UserManage.deleteInfo(id);
		renderJSON(success);
	}
	
	public static void editUserPage(Integer id) {
		User info = User.findById(id);
		render(info);
	}
	
	public static void addUser(User user) {
		boolean success = user.save().isPersistent();
		renderJSON(success);
	}

	public static void updateUser(User user) {

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
