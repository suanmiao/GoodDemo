package com.irun.sm.ui.demo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * @author huangsm
 * @date 2012-6-19
 * @email huangsanm@gmail.com
 * @desc JSON数据格式封装
 */
public class JSONUtils {

	public static String stringToJson(String s) {
		if (s == null) {
			return nullTOJson();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	/***
	 * 对象转换成为json 这里是描述这个方法的作用
	 * 
	 * @return
	 * @author Java
	 * @date 2011-7-22 下午02:59:13
	 */
	public static String objectToJson(Object obj) {
		StringBuffer json = new StringBuffer();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof Integer) {
			json.append("\"").append(numberToJson((Integer) obj)).append("\"");
		} else if (obj instanceof Boolean) {
			json.append("\"").append(booleanToJson((Boolean) obj)).append("\"");
		} else if (obj instanceof String) {
			json.append("\"").append(stringToJson(obj.toString())).append("\"");
		} else if (obj instanceof Object[]) {
			json.append("\"").append(arrayToJson((Object[]) obj)).append("\"");
		} else if (obj instanceof List) {
			json.append("\"").append(listToJson((List<?>) obj)).append("\"");
		} else if (obj instanceof Map) {
			json.append("\"").append(mapToJson((Map<?, ?>) obj)).append("\"");
		} else if (obj instanceof java.sql.Date) {
			json.append("\"").append(dateToJson((Date) obj)).append("\"");
		} else if (obj instanceof java.util.Date) {
			json.append("\"").append(dateToJson((Date) obj)).append("\"");
		} else {
			json.append("\"").append(stringToJson(obj.toString())).append("\"");
		}
		return json.toString();
	}

	/**
	 * 这里是描述这个方法的作用
	 * 
	 * @param obj
	 * @return {["pname":"val"],["pname":"val"]...}
	 * @author Java
	 * @date 2011-7-22 下午03:02:49
	 */
	public static String beanToJson(Object obj) {
		StringBuffer json = new StringBuffer("{");
		try {
			Class cal = obj.getClass();
			for (int i = 0; i < cal.getDeclaredFields().length; i++) {
				Field field = cal.getDeclaredFields()[i];
				String fname = field.getName();
				field.setAccessible(true);
				
				String value = field.get(obj).toString();
				String pname = objectToJson(fname);
				String val = objectToJson(value);
				json.append(pname);
				json.append(":");
				json.append(val);
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public static String beanToJson(Object obj, String params) {
		StringBuffer json = new StringBuffer("{");
		try {
			Class cal = obj.getClass();
			for (int i = 0; i < cal.getDeclaredFields().length; i++) {
				Field field = cal.getDeclaredFields()[i];
				String fname = field.getName();
				String strLitter = fname.substring(0, 1).toUpperCase();

				String getName = "get" + strLitter + fname.substring(1);
				String setName = "set" + strLitter + fname.substring(1);

				Method getmethod = cal.getMethod(getName, new Class[] {});
				cal.getMethod(setName, new Class[] { field.getType() });

				String value = getmethod.invoke(obj, new Object[] {})
						.toString();
				String pname = objectToJson(fname);
				String val = objectToJson(value);
				json.append(pname);
				json.append(":");
				json.append(val);
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * 这里是描述这个方法的作用
	 * 
	 * @param obj
	 * @return
	 * @see
	 * @author Java
	 * @date 2011-7-22 下午03:02:27
	 */
	public static String mapToJson(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append("" + objectToJson(key) + "");
				json.append(":");
				json.append("" + objectToJson(map.get(key)) + "");
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * 这里是描述这个方法的作用
	 * 
	 * @param obj
	 * @return
	 * @author Java
	 * @date 2011-7-22 下午03:02:15
	 */
	public static String listToJson(List<?> list) {
		StringBuffer json = new StringBuffer();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(beanToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String listToJson(List<?> list, String params) {
		StringBuffer json = new StringBuffer();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(beanToJson(obj, params));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * 这里是描述这个方法的作用
	 * 
	 * @param obj
	 * @return
	 * @author Java
	 * @date 2011-7-22 下午03:02:05
	 */
	private static String arrayToJson(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	public static String dateToJson(Date date) {
		return date.toString();
	}

	public static String numberToJson(Number number) {
		return number.toString();
	}

	public static String booleanToJson(Boolean bool) {
		return bool.toString();
	}

	public static String nullTOJson() {
		return "";
	}

	/**
	 * 非空验证 这里是描述这个方法的作用
	 * @param arg0
	 * @return
	 * @author Java
	 * @date 2011-7-22 下午02:51:43
	 */
	private static Boolean isNull(Object arg0) {
		if (arg0 == null)
			return true;
		String arg = (String) arg0;
		return ("".equals(arg.trim()) || "null".equals(arg)) ? true : false;
	}
}
