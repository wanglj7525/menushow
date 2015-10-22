package models;

import play.mvc.Controller;

/**
 * 返回结果信息类
 * 
 * @author 
 * 
 * @date 2014-11-18
 */
public class ResultInfo{

	/**
	 * 操作结果：        success | error | repeat | timeout | ipchanged | iplimited
	 */
	public String result;
	
	/**
	 * 错误提示消息: ｛错误内容｝
	 */
	public String msg;
	
	/**
	 * http请求标识
	 */
	public String key;
	
	/**
	 * 数据结果 : object
	 */
	public Object info;
	
	/**
	 * 获取一个通用的操作成功的对象	
	 * @param info 查询操作所返回的数据
	 * @param key http请求标识是否过期的标识
	 * @return
	 */
	public static ResultInfo success(Object info) {
		ResultInfo result = new ResultInfo();
		result.msg = "操作成功";
		result.result = "success";	
		result.info = info;
		return result;
	}
	/**
	 * 获取一个通用的操作成功的对象	 * 
	 * @param info 查询操作所返回的数据
	 * @param key http请求标识是否过期的标识
	 * @return
	 */
	public static ResultInfo success(Object info, String key) {
		ResultInfo result = new ResultInfo();
		result.msg = "操作成功";
		result.result = "success";
		result.key = key;
		result.info = info;
		return result;
	}
	public static ResultInfo success() {
		return success(null);
	}
	
	/**
	 * 获取一个通用的操作失败的对象	 *
	 * @param key http请求标识是否过期的标识
	 * @return
	 */
	public static ResultInfo error() {
		return error("操作失败");
	}
	
	/**
	 * 获取一个通用的操作失败的对象	 * 
	 * @param key http请求标识是否过期的标识
	 * @return
	 */
	public static ResultInfo error(String msg) {
		ResultInfo result = new ResultInfo();
		result.msg = msg;
		result.result = "error";	
		return result;
	}
	
	/**
	 * 获取一个数据重复的操作失败的对象
	 * @param key http请求标识是否过期的标识
	 * @return
	 */
	public static ResultInfo repeat() {
		return repeat("名称已存在");
	}
	
	public static ResultInfo repeat(String msg) {
		ResultInfo result = new ResultInfo();
		result.result = "repeat";
		result.msg = msg;	
		return result;
	}
		
   
}
