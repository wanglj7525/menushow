package controllers;

import models.ResultInfo;
import models.User;
import play.mvc.Controller;

public class Login extends Controller {
	
	public static void index(){
		render();
	}

	public static void login(String username,String password){
		System.out.println(username+"==="+password);
//		redirect(Router.getFullUrl("StoreManage.index"));
		User user=new User();
		user.id=1l;
		user.u_name="test";
		renderJSON(new ResultInfo().success(user));
	}
}
