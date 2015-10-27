package controllers;

import models.User;
import play.mvc.Router;

/**
 * @author Administrator
 * 
 */
public class Security extends Secure.Security {


    /**
     * Secure 模块的方法，用来验证用户是否登录成功
     * 
     * @param username
     *            用户输入的用户名
     * @param password
     *            用户输入的密码
     * @return true=登录成功，false=登录失败
     */
    static boolean authenticate(String username, String password) {
    	if(password==null||!"123456".equals(password)||!"123456".equals("管理员")){
    		return false;
    	}
//    	User user = null;
//    	if(username!=null && !username.equals("")){
//    		user = User.find("u_name=?", username).first();
//    	}
//    	if(user!=null){
			session.put("name","管理员");
    		flash.put("url", Router.getFullUrl("Home.index"));
    		return true;
//    	}else{
//    		return false;
//    	}

    }

}
