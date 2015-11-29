package utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import play.Play;
import controllers.Rest;

/**
 * 系统公共方法
 * 
 * @version 1.0
 * @author open sourc added by yuezengguang
 */
public class DataUtil {

	/**
	 * 判断输入值是否为空，如果为空则返回“”，否则返回输入值
	 * 
	 * @param strInput
	 * @return
	 */
	public static String getNoNull(String strInput) {
		return strInput == null ? "" : strInput;
	}

	/**
	 * 判断输入值是否为空，如果为空则返回“”，否则返回输入值
	 * 
	 * @param obInput
	 * @return
	 */
	public static String getNoNull(Object obInput) {
		return obInput == null ? "" : String.valueOf(obInput);
	}

	/**
	 * 判断输入值是否为空，如果为空则返回“”，否则返回输入值
	 * 
	 * @param dtInput
	 * @return
	 */
	public static String getNoNull(Double dtInput) {
		return dtInput == null ? "" : String.valueOf(dtInput);
	}

	/**
	 * 判断输入值是否为空，如果为空则返回“”，否则返回输入值
	 * 
	 * @param obInput
	 * @return
	 */
	public static String getReturnZero(Object obInput) {
		return obInput == null ? "0" : String.valueOf(obInput);
	}

	/**
	 * 判断输入值是否为空，如果为空则返回“0”，否则返回输入值
	 * 
	 * @param strInput
	 * @return
	 */
	public static String getReturnZero(String strInput) {
		return ((strInput != null) && (!strInput.equals(""))) ? strInput : "0";
	}

	/**
	 * 判断输入值是否为空，如果为空则返回0，否则返回输入值
	 * 
	 * @param strInput
	 * @return
	 */
	public static Double getDReturnZero(String strInput) {
		if ((strInput != null) && (!strInput.equals(""))) {
			return Double.valueOf(strInput);
		} else {
			return Double.valueOf("0");
		}
	}

	/**
	 * 判断输入值是否为空，如果为空则返回“0”，否则返回输入值
	 * 
	 * @param d
	 * @return
	 */
	public static Double getDReturnZero(Double d) {
		String strResult = getNoNull(d);
		if ((strResult != null) && (!strResult.equals(""))) {
			return d;
		} else {
			return Double.valueOf("0");
		}
	}

	/**
	 * 判断输入值是否为空，如果为空则返回“0”，否则返回输入值
	 * 
	 * @param l
	 * @return
	 */
	public static Long getLReturnZero(long l) {
		String strResult = getNoNull(l);
		return ((strResult != null) && (!strResult.equals(""))) ? l : 0l;
	}

	/**
	 * 判断输入值是否为空，如果为空则返回“0”，否则返回输入值
	 * 
	 * @param l
	 * @return
	 */
	public static Long getLReturnZero(Long l) {
		String strResult = getNoNull(l);
		return ((strResult != null) && (!strResult.equals(""))) ? l : 0l;
	}

	/**
	 * 判断输入值是否为空，如果为空则返回“0”，否则返回输入值
	 * 
	 * @param astrInput
	 * @return
	 */
	public static Double getReturnDouble(Double astrInput) {
		return astrInput != null ? astrInput : 0.0;
	}

	/**
	 * 判断输入值是否为空，如果为空则返回“0”，否则返回输入值
	 * 
	 * @param astrInput
	 * @return
	 */
	public static int getReturnInt(String astrInput) {
		return astrInput != null ? Integer.parseInt(astrInput) : 0;
	}

	/**
	 * 判断输入值是否为空，如果为空则返回“0”，否则返回输入值
	 * 
	 * @param astrInput
	 * @return
	 */
	public static int getReturnInt(Object astrInput) {
		return astrInput != null ? Integer.parseInt(astrInput.toString()) : 0;
	}

	/**
	 * 将小数转化成百分数
	 * 
	 * @param param
	 *            :小数
	 * @param i
	 *            ：保留转化后百分数小数点后的位数；例如：param = 0.123789，i=2时，转化后的百分数为12.4%：
	 * @return
	 */
	public static String getPercent(double param, int i) {

		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(i);

		return nf.format(param);
	}

	/**
	 * 获取十六进制的颜色代码.例如 "#6E36B4" , For HTML ,
	 * 
	 * @return String
	 */
	public static String getRandColorCode() {
		String r, g, b;
		Random random = new Random();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();

		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;

		return r + g + b;
	}

	/**
	 * 
	 * @return
	 */
	public static String getBrowser(String desc) {
		desc = desc.toLowerCase();
		String broser = "";

		if (desc.contains("msie"))
			broser = "IE";
		else if (desc.contains("firefox"))
			broser = "Firefox";
		else if (desc.contains("chrome"))
			broser = "Chrome";
		else if (desc.contains("opera"))
			broser = "Opera";
		else if (desc.contains("safari"))
			broser = "safari";
		else
			broser = "unkown browser";
		return broser;
	}

	public static String decimalFormat(Long value) {
		DecimalFormat df = new DecimalFormat("##,###");
		return df.format(value);
	}

	public static String decimalFormat2(Double value) {
		DecimalFormat df = new DecimalFormat("##.00");
		return df.format(value);
	}

	public static boolean isMblogUrl(String url) {
		boolean ism = false;
		if (UtilValidate.isNotEmpty(url)) {
			List<String> domainMblog = new ArrayList<String>();
			domainMblog.add("weibo.com");
			domainMblog.add("t.qq");
			domainMblog.add("t.163");
			domainMblog.add("t.sohu");

			for (int i = 0; i < domainMblog.size(); i++) {
				if (url.indexOf(domainMblog.get(i)) > -1) {
					ism = true;
					break;
				}
			}
		}

		return ism;
	}

	public static String getSrcByurl(String url) {
		String src = "";
		if (UtilValidate.isNotEmpty(url)) {
			if (url.indexOf("weibo.com") > -1) {
				src = "新浪微博";
			} else if (url.indexOf("t.qq") > -1) {
				src = "腾讯微博";
			} else if (url.indexOf("t.163") > -1) {
				src = "网易微博";
			} else if (url.indexOf("t.sohu") > -1) {
				src = "搜狐微博";
			}
		}

		return src;
	}

	/**
	 * 提取标题关键词然后排序然后计算关键词集合MD5服务
	 * 
	 * @param content
	 * @return
	 */
	public static String keysmd5(String content) {

		if (UtilValidate.isEmpty(content)) {
			return "";
		}

		// String cutf8 = utf8(content);

		String keysmd5Url = Play.configuration.getProperty("application.keysmd5");
		if (StringUtils.isEmpty(keysmd5Url)) {
			keysmd5Url = "http://10.20.7.33/fcgi-bin/keysmd5.fcgi";
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("content", content);
		Rest rest = new Rest();
		String md5 = rest.sendPostRequest(keysmd5Url, param);

		// Map<String, Object> param = new HashMap<String, Object>();
		// param.put("content", content);
		// String md5 = ServerSupport.getServerResult("keysmd5", param);
		// System.out.println(">>>"+md5);

		// 返回：
		// 正常：db53b565e86ef6ea84c3850c6770b7c0
		// post key不为content:post has no key:content
		// post value为空：NULL:input NULL
		// post value提取不出关键词：NULL:create md5 NULL
		if (UtilValidate.areEqual("post has no key:content", md5) || UtilValidate.areEqual("NULL:input NULL", md5)
				|| UtilValidate.areEqual("NULL:create md5 NULL", md5)) {
			md5 = "";
		}

		// Logger.info("%s ++++++title_hash is++++++ %s", content, md5);

		return md5;
	}

	public static String utf8(String value) {
		String result = "";
		try {
			// result = new String(value.getBytes("UTF-8"),"UTF-8");

			String xmString = new String(value.getBytes("UTF-8"));
			// System.out.println(xmString);
			result = URLEncoder.encode(xmString, "UTF-8");
			// System.out.println(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String urlencode(String value) {
		String result = "";
		return result;
	}

	public static Map convertBean(Object bean) {
		Class type = bean.getClass();
		Map returnMap = new HashMap();

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);

			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result);
					} else {
						returnMap.put(propertyName, "");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	public static Map parseJson2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJson2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	public static String getSinaMblog(String url) {
		String sinamblogUrl = Play.configuration.getProperty("application.sinamblog");
		if (StringUtils.isEmpty(sinamblogUrl)) {
			sinamblogUrl = "http://221.0.111.138/newweibo/getWeiboContent.do";
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", "gxb@golaxy.cn");
		param.put("pw", "gxb");
		param.put("url", url);
		Rest rest = new Rest();
		String result = rest.sendPostRequest(sinamblogUrl, param);

		// Map<String, Object> param = new HashMap<String, Object>();
		// param.put("url", url);
		// String result = ServerSupport.getServerResult("weibocontent", param);
		// System.out.println(">>>"+result);

		return result;
	}

	public static String getParamString(Map<String, Object> maps) {
		String s = "";
		if (UtilValidate.isEmpty(maps)) {
			return s;
		}

		try {
			for (Map.Entry<String, Object> en : maps.entrySet()) {
				String key = en.getKey();
				String value = DataUtil.getNoNull(en.getValue());
				if (UtilValidate.isNotEmpty(value)) {
					s += key + "=" + value + "&";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (UtilValidate.isNotEmpty(s)) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println(keysmd5("奥巴马&'会见'/习近平%25a b&&c（高清大图）意图(高清曝光图)"));
	}
}