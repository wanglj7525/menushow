package controllers;

import models.t_user;
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
    	if(password==null||!"123456".equals(password)){
    		return false;
    	}
    	t_user user = null;
    	if(username!=null && !username.equals("")){
    		user = t_user.find("u_name=?", username).first();
    	}
    	if(user!=null){
//    		String key = UUID.randomUUID().toString();
//    		user.key = key;
//    		user.save();
			session.put("name",user.u_name);
    		flash.put("url", Router.getFullUrl("Home.index"));
//    		if (user.level==2) {
//    			//值班员
//    			flash.put("url", Router.getFullUrl("ManOnDuty.index"));
//			}else if(user.level==1){
//				//值班局长
//				flash.put("url", Router.getFullUrl("ManOnDirector.index"));
//			}else{
//				//办领导
//				flash.put("url", Router.getFullUrl("ManOnLeader.index"));
//			}
    		return true;
    	}else{
    		return false;
    	}

    }

}
