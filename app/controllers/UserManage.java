package controllers;

import java.util.List;

import models.t_user;
import play.mvc.Controller;

public class UserManage extends Controller {
	public static void index() {
		render();
	}

	public static void userPage() {
		List<t_user> userList = models.UserManage.allInfos();
		render(userList);
	}
}
