package controllers;

import java.util.List;
import java.util.Map;

import models.ResultInfo;
import models.SqlModel;
import play.mvc.Controller;
import utils.Crypto;

public class Login extends Controller {
	
	public static void index(){
		render();
	}

	public static void login(String username,String password){
		ResultInfo result=new ResultInfo();
	    List<Map> user=SqlModel.login(username, Crypto.passwordHash(password));
	    if (user.size()>0) {
			//登录成功
	    	session.put("username", user.get(0).get("uName"));
	    	session.put("password",  Crypto.passwordHash(password));
	    	session.put("userid", user.get(0).get("id"));
	    	session.put("shopid", user.get(0).get("shopid"));
	    	session.put("Confirmed", user.get(0).get("confirmed"));
	    	session.put("Nf", user.get(0).get("nf"));
	    	System.out.println("login="+session);
		}else{
			renderJSON(result.error("用户名或密码错误"));
		}
		renderJSON(result.success(user.get(0)));
	}
	
	public static void regester(String rname,String rpass){
		ResultInfo result=new ResultInfo();
		Integer uid=SqlModel.saveUserInfo(rname, Crypto.passwordHash(rpass));
		if (uid == null || uid == 0) {
			renderJSON(result.error("注册失败！"));
		} else if(uid==-1){
			renderJSON(result.error("用户名已经存在！"));
		}else{
			session.put("username", rname);
	    	session.put("password",  Crypto.passwordHash(rpass));
	    	session.put("userid", uid);
	    	session.put("shopid",null);
	    	session.put("Confirmed",0);
	    	session.put("Nf",200);
			renderJSON(result.success(uid));
		}
	}
}
