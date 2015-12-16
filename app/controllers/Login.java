package controllers;

import java.util.List;
import java.util.Map;

import models.ResultInfo;
import models.Shop;
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
	    	
	    	String confirmed=user.get(0).get("confirmed").toString();
//	    	if (Integer.parseInt(confirmed)==0) {
//	    		renderJSON(result.error("未通过审核，不能登录"));
//			}
	    	
//	    	String expiredate=user.get(0).get("expiredate").toString();
//	    	if (expiredate!=null) {
//	    		try {
//		    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		    		Date date = sdf.parse(expiredate);
//		    		long s1=date.getTime();//将时间转为毫秒
//		    		long s2=System.currentTimeMillis();//得到当前的毫秒
//		    		int day=(int) ((s2-s1)/1000/60/60/24);
//		    		System.out.println("距现在已有"+day+"天，你得抓紧时间学习了" );
//		    		if (day>=0) {
//		    			renderJSON(result.error("超过授权使用时间"));
//		    			session.put("expiredate",day);
//					}
//	    		} catch (ParseException e) {
//	    			e.printStackTrace();
//	    		}
//			}
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
	
	public static void regester(String rname,String rpass,Long shopid){
		ResultInfo result=new ResultInfo();
		Shop tu = Shop.findById(shopid);
		if (tu != null) {
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
		}else{
			renderJSON(result.error("代理商ID不存在！"));
		}
	}
}
