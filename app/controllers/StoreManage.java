package controllers;

import java.util.List;
import java.util.Map;

import models.ResultInfo;
import models.SqlModel;
import play.mvc.Controller;
import utils.Crypto;

public class StoreManage extends Controller {

	public static void index(Integer id) {
		List<Map> store = SqlModel.getStore(id);
		render(store);
	}
	
	public static void add(String s_name,String dog_id,String pswd){
		ResultInfo result=new ResultInfo();
		System.out.println("add="+session);
		if ( !Crypto.passwordHash(pswd).equals(session.get("password"))) {
			renderJSON(result.error("登录密码错误"));
		}else{
			String userid=session.get("userid");
			Integer sid=SqlModel.addStoreInfo(s_name, dog_id, userid);
			if (sid == null || sid == 0) {
				renderJSON(result.error("添加失败！"));
			} else if(sid==-1){
				renderJSON(result.error("门店名称已经存在！"));
			}else{
				List<Map> store = SqlModel.getStore(Integer.parseInt(userid));
				renderJSON(store);
			}
		}
		
	}
}
