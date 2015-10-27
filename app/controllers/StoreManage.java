package controllers;

import java.util.List;
import java.util.Map;

import models.ResultInfo;
import models.SqlModel;
import models.Store;
import play.mvc.Controller;
import utils.Crypto;

public class StoreManage extends Controller {

	public static void index(Integer id) {
		List<Map> store = SqlModel.getStore(id);
		render(store);
	}
	
	public static void add(Integer us_id, String s_name,String dog_id,String pswd){
		ResultInfo result=new ResultInfo();
		if ( !Crypto.passwordHash(pswd).equals(session.get("password"))) {
			renderJSON(result.error("登录密码错误"));
		}else{
			String userid=session.get("userid");
			Integer sid;
			if (us_id==-1) {
				//添加门店
				sid=SqlModel.addStoreInfo(s_name, dog_id, userid);
			}else{
				//修改门店
				sid=SqlModel.updateStoreInfo(us_id, s_name, dog_id);
			}
			if (sid == null || sid == 0) {
				renderJSON(result.error("操作失败！"));
			} else if(sid==-1){
				renderJSON(result.error("门店名称已经存在！"));
			}else{
				List<Map> store = SqlModel.getStore(Integer.parseInt(userid));
				renderJSON(store);
			}
		}
		
	}
	
	public static void deleteStore(Integer sid){
		ResultInfo result=new ResultInfo();
		int did=Store.delete(" id =?", sid);
		if (did==1) {
			String userid=session.get("userid");
			List<Map> store = SqlModel.getStore(Integer.parseInt(userid));
			renderJSON(store);
		}else{
			renderJSON(result.error("删除失败！"));
		}
		
	}
	public static void first(Integer s_id) {
		render(s_id);
	}
}
