package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String showtime=df.format(new Date());
		render(store,showtime);
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
			}else if(sid==-2){
				renderJSON(result.error("注册码已经使用！"));
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
	
	private static String doRest(String dogid, String sp, String start_time,
			String end_time) {
		if (start_time.length()!=8) {
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//			Calendar lastDate = Calendar.getInstance();
//			lastDate.set(Calendar.DATE,1);//设为当前月的1 号
//			start_time=sdf.format(lastDate.getTime());
			start_time="20151101";
		}
		if (end_time.length()!=8) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
			System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			end_time=df.format(new Date());
		}
		Rest rest=new Rest();
		rest.setRestIp("http://121.41.106.61:8181/");
		rest.setUrl("");
		rest.setParam("dogid", dogid);
		rest.setParam("sp", sp);
		rest.setParam("start_time", start_time);
		rest.setParam("end_time", end_time);
		String result = rest.string(false);
		System.out.println(result);
		return result;
	}
	
	public static void first(String dogid,String sp,String start_time,String end_time) {
		String result = doRest(dogid, sp, start_time, end_time);
		ArrayList<String> strArray = new ArrayList<String> ();
		for (int i = 0; i < result.split("\t").length; i++) {
			if (result.split("\t")[i].trim().length()!=0) {
				strArray.add(result.split("\t")[i]);
			}
		}
		System.out.println(strArray);
		render(strArray);
	}

	public static void second(String dogid,String sp,String start_time,String end_time) {
		String result = doRest(dogid, sp, start_time, end_time);
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
	public static void third(String dogid,String sp,String start_time,String end_time) {
		String result = doRest(dogid, sp, start_time, end_time);
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
	public static void fouth(String dogid,String sp,String start_time,String end_time) {
		String result = doRest(dogid, sp, start_time, end_time);
		
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
	public static void fifth(String dogid,String sp,String start_time,String end_time) {
		String result = doRest(dogid, sp, start_time, end_time);
		String trueResult="";
		for (int i = 0; i < result.split("\t").length; i++) {
			if (result.split("\t")[i].trim().length()!=0) {
				trueResult+=result.split("\t")[i]+",";
			}
		}
		trueResult=trueResult.substring(0,trueResult.length()-1);
		System.out.println(11+trueResult);
		String num1="0",num2="0",num3="0",num4="0";
		if (trueResult.length()>4) {
			num1=trueResult.split(",")[1];
			num2=trueResult.split(",")[3];
			num3=trueResult.split(",")[5];
			num4=trueResult.split(",")[7];
		}
		render(num1,num2,num3,num4);
	}
	
	public static void sixth(String dogid,String sp,String start_time,String end_time) {
		String result = doRest(dogid, sp, start_time, end_time);
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
	public static void seventh(String dogid,String sp,String start_time,String end_time) {
		String result = doRest(dogid, sp, start_time, end_time);
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
