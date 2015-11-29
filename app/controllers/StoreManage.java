package controllers;

import java.util.ArrayList;
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
	public static void first(String dogid,String sp) {
		Rest rest=new Rest();
		rest.setRestIp("http://121.41.106.61:8181/");
		rest.setUrl("");
		rest.setParam("dogid", "123654");
		rest.setParam("sp", sp);
		rest.setParam("start_time", "20151003");
		rest.setParam("end_time", "20151105");
		String result = rest.string(false);
		ArrayList<String> strArray = new ArrayList<String> ();
		for (int i = 0; i < result.split("\t").length; i++) {
//			if (result.split("\t")[i].contains("付款信息")) {
//				strArray.add(result.split("\t")[i].substring(0, result.split("\t")[i].length()-4));
//				strArray.add("付款信息");
//			}else{
			if (result.split("\t")[i].trim().length()!=0) {
				strArray.add(result.split("\t")[i]);
			}
//			}
		}
		System.out.println(strArray);
		
		render(strArray);
	}
	public static void second(String dogid,String sp) {
		Rest rest=new Rest();
		rest.setRestIp("http://121.41.106.61:8181/");
		rest.setUrl("");
		rest.setParam("dogid", "123654");
		rest.setParam("sp", sp);
		rest.setParam("start_time", "20151003");
		rest.setParam("end_time", "20151105");
		String result = rest.string(false);
		System.out.println(result);
		ArrayList<String> oneArray = new ArrayList<String> ();
		ArrayList<String> twoArray = new ArrayList<String> ();
		ArrayList<String> threeArray = new ArrayList<String> ();
		String trueResult="";
		for (int i = 0; i < result.split("\t").length; i++) {
			if (result.split("\t")[i].trim().length()!=0) {
				trueResult+=result.split("\t")[i]+",";
			}
		}
		trueResult=trueResult.substring(0,trueResult.length()-1);
		System.out.println(trueResult);
		for (int i = 0; i < trueResult.split(",").length/3; i++) {
			oneArray.add(trueResult.split(",")[i*3]);
			twoArray.add(trueResult.split(",")[i*3+1]);
			threeArray.add(trueResult.split(",")[i*3+2]);
		}
		String one=oneArray.toString().substring(1, oneArray.toString().lastIndexOf("]"));
		String two=twoArray.toString().substring(1, twoArray.toString().lastIndexOf("]"));
		String three=threeArray.toString().substring(1, threeArray.toString().lastIndexOf("]"));
		render(one,two,three);
	}
	public static void third(String dogid,String sp) {
		Rest rest=new Rest();
		rest.setRestIp("http://121.41.106.61:8181/");
		rest.setUrl("");
		rest.setParam("dogid", "123654");
		rest.setParam("sp", sp);
		rest.setParam("start_time", "20151003");
		rest.setParam("end_time", "20151105");
		String result = rest.string(false);
		System.out.println(result);
		ArrayList<ArrayList<String>> allArray = new ArrayList<ArrayList<String>> ();
		
		String trueResult="";
		for (int i = 0; i < result.split("\t").length; i++) {
			if (result.split("\t")[i].trim().length()!=0) {
				trueResult+=result.split("\t")[i]+",";
			}
		}
		trueResult=trueResult.substring(0,trueResult.length()-1);
		System.out.println(trueResult);
		
		for (int i = 0; i < trueResult.split(",").length/4; i++) {
			ArrayList<String> oneArray = new ArrayList<String> ();
			oneArray.add(trueResult.split(",")[i*4]);
			oneArray.add(trueResult.split(",")[i*4+1]);
			oneArray.add(trueResult.split(",")[i*4+2]);
			oneArray.add(trueResult.split(",")[i*4+3]);
			allArray.add(oneArray);
		}
		System.out.println(allArray);
		render(allArray);
	}
	public static void fouth(String dogid,String sp) {
		Rest rest=new Rest();
		rest.setRestIp("http://121.41.106.61:8181/");
		rest.setUrl("");
		rest.setParam("dogid", "123654");
		rest.setParam("sp", sp);
		rest.setParam("start_time", "20151003");
		rest.setParam("end_time", "20151105");
		String result = rest.string(false);
		System.out.println(result);
		ArrayList<String> oneArray = new ArrayList<String> ();
		ArrayList<String> twoArray = new ArrayList<String> ();
		ArrayList<String> threeArray = new ArrayList<String> ();
		ArrayList<String> fourArray = new ArrayList<String> ();
		
		String trueResult="";
		for (int i = 0; i < result.split("\t").length; i++) {
			if (result.split("\t")[i].trim().length()!=0) {
				trueResult+=result.split("\t")[i]+",";
			}
		}
		trueResult=trueResult.substring(0,trueResult.length()-1);
		System.out.println(trueResult);
		
		for (int i = 0; i < trueResult.split(",").length/4; i++) {
			oneArray.add(trueResult.split(",")[i*4]);
			twoArray.add(trueResult.split(",")[i*4+1]);
			threeArray.add(trueResult.split(",")[i*4+2]);
			fourArray.add(trueResult.split(",")[i*4+3]);
		}
		String one=oneArray.toString().substring(1, oneArray.toString().lastIndexOf("]"));
		String two=twoArray.toString().substring(1, twoArray.toString().lastIndexOf("]"));
		String three=threeArray.toString().substring(1, threeArray.toString().lastIndexOf("]"));
		String four=fourArray.toString().substring(1, threeArray.toString().lastIndexOf("]"));
		render(one,two,three,four);
	}
	public static void fifth(String dogid,String sp) {
		Rest rest=new Rest();
		rest.setRestIp("http://121.41.106.61:8181/");
		rest.setUrl("");
		rest.setParam("dogid", "123654");
		rest.setParam("sp", sp);
		rest.setParam("start_time", "20151003");
		rest.setParam("end_time", "20151105");
		String result = rest.string(false);
		System.out.println(result);
		String trueResult="";
		for (int i = 0; i < result.split("\t").length; i++) {
			if (result.split("\t")[i].trim().length()!=0) {
				trueResult+=result.split("\t")[i]+",";
			}
		}
		trueResult=trueResult.substring(0,trueResult.length()-1);
		System.out.println(trueResult);
		String num1=trueResult.split(",")[1];
		String num2=trueResult.split(",")[3];
		String num3=trueResult.split(",")[5];
		String num4=trueResult.split(",")[7];
		render(num1,num2,num3,num4);
	}
	
	public static void sixth(String dogid,String sp) {
		Rest rest=new Rest();
		rest.setRestIp("http://121.41.106.61:8181/");
		rest.setUrl("");
		rest.setParam("dogid", "123654");
		rest.setParam("sp", sp);
		rest.setParam("start_time", "20151003");
		rest.setParam("end_time", "20151105");
		String result = rest.string(false);
		System.out.println(result);
		ArrayList<ArrayList<String>> allArray = new ArrayList<ArrayList<String>> ();
		
		String trueResult="";
		for (int i = 0; i < result.split("\t").length; i++) {
			if (result.split("\t")[i].trim().length()!=0) {
				trueResult+=result.split("\t")[i]+",";
			}
		}
		trueResult=trueResult.substring(0,trueResult.length()-1);
		System.out.println(trueResult);
		
		for (int i = 0; i < trueResult.split(",").length/4; i++) {
			ArrayList<String> oneArray = new ArrayList<String> ();
			oneArray.add(trueResult.split(",")[i*4]);
			oneArray.add(trueResult.split(",")[i*4+1]);
			oneArray.add(trueResult.split(",")[i*4+2]);
			oneArray.add(trueResult.split(",")[i*4+3]);
			allArray.add(oneArray);
		}
		System.out.println(allArray);
		render(allArray);
	}
	public static void seventh(String dogid,String sp) {
		Rest rest=new Rest();
		rest.setRestIp("http://121.41.106.61:8181/");
		rest.setUrl("");
		rest.setParam("dogid", "123654");
		rest.setParam("sp", sp);
		rest.setParam("start_time", "20151003");
		rest.setParam("end_time", "20151105");
		String result = rest.string(false);
		System.out.println(result);
		ArrayList<ArrayList<String>> allArray = new ArrayList<ArrayList<String>> ();
		
		String trueResult="";
		for (int i = 0; i < result.split("\t").length; i++) {
			if (result.split("\t")[i].trim().length()!=0) {
				trueResult+=result.split("\t")[i]+",";
			}
		}
		trueResult=trueResult.substring(0,trueResult.length()-1);
		System.out.println(trueResult);
		
		for (int i = 0; i < trueResult.split(",").length/3; i++) {
			ArrayList<String> oneArray = new ArrayList<String> ();
			oneArray.add(trueResult.split(",")[i*4]);
			oneArray.add(trueResult.split(",")[i*4+1]);
			oneArray.add(trueResult.split(",")[i*4+2]);
			allArray.add(oneArray);
		}
		System.out.println(allArray);
		render(allArray);
	}
}
