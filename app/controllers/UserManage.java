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
}
