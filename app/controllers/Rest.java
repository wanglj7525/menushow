package controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import play.libs.XML;
import utils.DataUtil;
import utils.JsonUtil;
import utils.Timer;

public class Rest {
	
	private static final String CHARSET = "utf-8";
//	public static final String restIp = Play.configuration.get("rest_url")+"";

	private Map<String, Object> maps = new HashMap<String, Object>();
	
	private String restIp;
	

	public void setRestIp(String restIp) {
		this.restIp = restIp;
	}

	private String url;

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Rest启动静态方法
	 * 
	 * @param url
	 * @return
	 */
	public static Rest url(String url) {
		Rest rest = new Rest();
		rest.setUrl(url);
		return rest;
	}

	/**
	 * 以GET/POST方式调用REST，返回JSONArray格式的字符串，或JSONObject格式的字符串
	 * 
	 * @param isPost
	 * @param isArray
	 * @return
	 */
	private String exec(boolean isPost, boolean isArray) {
		String result = null;
		Long timeMill = System.currentTimeMillis();
		if (isPost)
			result = this.sendPostRequest();
		else
			result = this.sendGetRequest();
		if (StringUtils.isEmpty(result)) {
			if (isArray)
				result = "[]";
			else
				result = "{}";
		}
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ":rest---" + this.toString() + "---"
				+ (System.currentTimeMillis() - timeMill) + "ms");
		return result;
	}

	/**
	 * 以GET方式调用REST，返回String（格式根据
	 * 
	 * @param isArray
	 * @return
	 */
	public String get(boolean isArray) {
		return exec(false, isArray);
	}

	/**
	 * 以POST方式调用REST，返回String
	 * 
	 * @param isArray
	 * @return
	 */
	private String post(boolean isArray) {
		return exec(true, isArray);
	}

	/**
	 * 以GET/POST方式调用REST，返回JSONObject
	 * 
	 * @param isPost
	 * @return
	 */
	public JSONObject json(boolean isPost) {
		if (isPost)
			return JSONObject.fromObject(post(false));
		else
			return JSONObject.fromObject(get(false));
	}

	// 微博往后台存数据调用
	public JSONObject jsons(boolean isPost) {
		return JSONObject.fromObject(posts(false));
	}

	private String posts(boolean isArray) {
		return execs(true, isArray);
	}

	private String execs(boolean isPost, boolean isArray) {
		String result = null;
		Long timeMill = System.currentTimeMillis();
		result = this.sendPostRequests();

		if (StringUtils.isEmpty(result)) {
			if (isArray)
				result = "[]";
			else
				result = "{}";
		}
		System.out.println("rest---" + this.toStrings() + "---" + (System.currentTimeMillis() - timeMill) + "ms");
		return result;
	}

	public String sendPostRequests() {
		Timer timer = Timer.create("RestAPI - POST");
		StringBuffer stringBuffer = new StringBuffer("");
		String POSTURL = "";
		try {
			POSTURL = restIp + url + getParamString(maps, true);
			URL postUrl = new URL(restIp + url);
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(getParamString(maps, false));
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		timer.log(POSTURL);
		// System.out.println("REST POST "+timer.result().startTime +
		// " "+timer.result().endTime+" "+timer.result().timeresult.entrySet().iterator().next().getValue()+" "+POSTURL);
		return stringBuffer.toString();
	}

	public String toStrings() {
		return restIp + url + getParamString(maps, true);
	}

	public JSONArray arrays(boolean isPost) {
		if (isPost)
			return JSONArray.fromObject(posts(true));
		else
			return JSONArray.fromObject(get(true));
	}

	/**
	 * 以GET/POST方式调用REST，返回String
	 * 
	 * @param isPost
	 * @return
	 */
	public String string(boolean isPost) {
		if (isPost)
			return post(false);
		else
			return get(false);
	}

	/**
	 * 以GET/POST方式调用REST，返回xml org.w3c.dom.Document
	 * 
	 * @param isPost
	 * @return
	 */
	public Document xml(boolean isPost) {
		if (isPost)
			return XML.getDocument(post(false));
		else
			return XML.getDocument(get(false));
	}

	/**
	 * 以GET/POST方式调用REST，返回JSONArray
	 * 
	 * @param isPost
	 * @return
	 */
	public JSONArray array(boolean isPost) {
		if (isPost)
			return JSONArray.fromObject(post(true));
		else
			return JSONArray.fromObject(get(true));
	}

	/**
	 * 以GET方式调用REST，返回JSONObject
	 * 
	 * @return
	 */
	public JSONObject json() {
		return json(false);
	}

	/**
	 * 以GET方式调用REST，返回JSONArray
	 * 
	 * @return
	 */
	public JSONArray array() {
		return array(false);
	}

	public <T> T obj(Type type, boolean isPost) {
		String json = null;
		if (isPost) {
			json = post(false);
		} else {
			json = get(false);
		}
		T t = JsonUtil.json2bean(json, type);
		return t;
	}

	public <T> T obj(Type type) {
		return obj(type, false);
	}

	public <T> List<T> array(Type type) {
		String json = get(true);
		List<T> list = JsonUtil.json2bean(json, type);
		if (list == null) {
			list = new ArrayList<T>();
		}
		return list;
	}

	public <T> List<T> array(Type type, boolean isPost) {
		String json;
		if (isPost) {
			json = post(true);
		} else {
			json = get(true);
		}
		List<T> list = JsonUtil.json2bean(json, type);
		if (list == null) {
			list = new ArrayList<T>();
		}
		return list;
	}

	public <T> List<T> array(Type type, boolean isPost, String key) {
		String jsonStr;
		if (isPost) {
			jsonStr = post(true);
		} else {
			jsonStr = get(true);
		}
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONArray jsonKey = json.getJSONArray(key);
		List<T> list = JsonUtil.json2bean(jsonKey.toString(), type);
		if (list == null) {
			list = new ArrayList<T>();
		}
		return list;
	}

	/**
	 * 设置RESTAPI参数
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Rest set(String key, Object value) {
		maps.put(key, value);
		return this;
	}

	/**
	 * 添加参数
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            值，例如：setParam("name","value")
	 * @return 当前对象实例
	 */
	public Rest setParam(String key, Object value) {
		return set(key, value);
	}

	/**
	 * 移除参数
	 * 
	 * @param key
	 * @return 当前对象实例
	 */
	public Rest removeParam(String key) {
		maps.remove(key);
		return this;
	}

	/**
	 * 向rest api 发送get请求。
	 * 
	 * @return 返回的字符串
	 */
	public String sendGetRequest() {
		Timer timer = Timer.create("RestAPI - GET");
		StringBuffer stringBuffer = new StringBuffer("");
		String getURL = "";
		try {
			getURL = restIp + url + getParamString(maps, true);
			URL getUrl = new URL(getURL);

			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);
			if (connection.getResponseCode() == 200) {
				// connection.connect();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String lines;
				while ((lines = reader.readLine()) != null) {
					stringBuffer.append(lines+"\t");
				}
				reader.close();
			} else {
				throw new Exception("REST 连接异常，请联系REST维护人员！:" + getURL);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		timer.log(getURL);
		// System.out.println(getURL);
		// System.out.println("REST GET "+timer.result().startTime +
		// " "+timer.result().endTime+" "+timer.result().timeresult.entrySet().iterator().next().getValue()+" "+getURL);
		return stringBuffer.toString();
	}

	/**
	 * 向rest api 发送post请求。
	 * 
	 * @return 回的字符串
	 */
	public String sendPostRequest() {
		Timer timer = Timer.create("RestAPI - POST");
		StringBuffer stringBuffer = new StringBuffer("");
		String POSTURL = "";
		try {
			POSTURL = restIp + url + getParamString(maps, true);
			URL postUrl = new URL(restIp + url);
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(getParamString(maps, false));
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		timer.log(POSTURL);
		// System.out.println("REST POST "+timer.result().startTime +
		// " "+timer.result().endTime+" "+timer.result().timeresult.entrySet().iterator().next().getValue()+" "+POSTURL);
		return stringBuffer.toString();
	}

	/**
	 * 拼接参数列表
	 * 
	 * @param maps
	 *            ：参数表列，例如：p1=2,则key=p1，value=2
	 * @param get
	 *            :布尔值，如果是get请求则true，反之亦然。
	 * @return
	 */
	private String getParamString(Map<String, Object> maps, boolean isGet) {
		String s = "";
		if (maps == null)
			return "";
		if (isGet)
			s = maps.size() > 0 ? "?" : "";

		try {
			for (Map.Entry<String, Object> en : maps.entrySet()) {
				String param = en.getKey();
				String value = DataUtil.getNoNull(en.getValue());
				if (!"".equals(value))
					s += param + "=" + URLEncoder.encode(value, CHARSET) + "&";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!"".equals(s))
			s = s.substring(0, s.length() - 1);
		return s;
	}

	public String toString() {
		return restIp + url + getParamString(maps, true);
	}

	/**
	 * 向url 发送get请求。
	 * 
	 * @return 返回的字符串
	 */
	public String sendGetRequest(String url, Map<String, Object> param) {
		Timer timer = Timer.create("RestAPI - GET");
		StringBuffer stringBuffer = new StringBuffer("");
		String getURL = "";
		try {
			getURL = url + getParamString(param, true);
			URL getUrl = new URL(getURL);

			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			if (connection.getResponseCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String lines;
				while ((lines = reader.readLine()) != null) {
					stringBuffer.append(lines);
				}
				reader.close();
			} else {
				throw new Exception("REST 连接异常，请联系REST维护人员！:" + getURL);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		timer.log(getURL);
		return stringBuffer.toString();
	}

	/**
	 * 向url 发送post请求。
	 * 
	 * @return 返回的字符串
	 */
	public String sendPostRequest(String url, Map<String, Object> param) {
		Timer timer = Timer.create("RestAPI - POST");
		StringBuffer stringBuffer = new StringBuffer("");
		String POSTURL = "";
		try {
			POSTURL = url + getParamString(param, true);
			URL postUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(getParamString(param, false));
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		timer.log(POSTURL);
		return stringBuffer.toString();
	}

	public static void main(String[] args) {
		// Sample
		JSONObject json = Rest.url("/yqpt2/api/doc/new").set("s", 21).set("ps", 3).json();
		JSONArray array = Rest.url("/yqpt2/api/doc/new").set("s", 21).set("ps", 3).array();
		System.out.println(json);
		System.out.println(array);
	}

}
