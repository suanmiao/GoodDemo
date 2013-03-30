package com.irun.sm.ui.demo.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

/***
 * @author huangsm
 * @date 2012-6-19
 * @email huangsanm@gmail.com
 * @desc http请求
 */
public class HttpUtils {

	private static int TIMEOUT = 3000;
	public static final String REQUEST_SUCCESS = "SUCCESS";
	public static final String REQUEST_FAILED = "FAILED";
	public final static String GET = "get";
	public final static String POST = "post";
	private final static String CONTENT_TYPE = "multipart/form-data";
	private final static String CONTENT_TYPE_FILE = "application/x-www-form-urlencoded";
	private static String requestState;

	/***
	 * 打包json数据，返回object
	 * 
	 * @param srcCla
	 *            类类型
	 * @param jsonValue
	 *            需要打包的数据源
	 * @return 返回object
	 * @throws Exception
	 *             前台处理异常
	 */
	public static Object getJSONDataToObject(Class srcCla, String jsonValue)
			throws Exception {
		if (TextUtils.isEmpty(jsonValue))
			throw new RuntimeException("json 数据为空");
		Object object = srcCla.newInstance();
		JSONArray json = new JSONArray(jsonValue);
		for (int j = 0; j < json.length(); j++) {
			JSONObject obj = json.getJSONObject(j);
			for (int i = 0; i < srcCla.getDeclaredFields().length; i++) {
				Field field = srcCla.getDeclaredFields()[i];
				field.setAccessible(true);
				String fname = field.getName();
				String tname = field.getType().getName();

				if (tname.equals("int") || tname.equals("java.lang.Integer")) {
					field.set(object, obj.getInt(fname));
				} else if (tname.equals("long")
						|| tname.equals("java.lang.Long")) {
					field.set(object, obj.getLong(fname));
				} else {
					field.set(object, obj.getString(fname));
				}
			}
		}
		return object;
	}

	/***
	 * 打包json数据，返回List<Object>
	 * 
	 * @param srcCla
	 *            类类型
	 * @param jsonValue
	 *            需要打包的数据源
	 * @return 返回object
	 * @throws Exception
	 *             前台处理异常
	 */
	public static List<Object> getJSONDataToList(Class srcCla, String jsonValue)
			throws Exception {
		if (TextUtils.isEmpty(jsonValue))
			throw new RuntimeException("json 数据为空");
		List<Object> list = new ArrayList<Object>();
		JSONArray json = new JSONArray(jsonValue);
		for (int j = 0; j < json.length(); j++) {
			JSONObject obj = json.getJSONObject(j);
			Object object = srcCla.newInstance();
			for (int i = 0; i < srcCla.getDeclaredFields().length; i++) {
				Field field = srcCla.getDeclaredFields()[i];
				field.setAccessible(true);
				String fname = field.getName();
				String tname = field.getType().getName();

				if (tname.equals("int") || tname.equals("java.lang.Integer")) {
					field.set(object, obj.getInt(fname));
				} else if (tname.equals("long")
						|| tname.equals("java.lang.Long")) {
					field.set(object, obj.getLong(fname));
				} else {
					field.set(object, obj.getString(fname));
				}
			}
			list.add(object);
		}
		return list;
	}

	/***
	 * 请求远程数据直接返回object
	 * 
	 * @param url
	 *            请求url
	 * @param params
	 *            请求参数
	 * @param srcCla
	 *            class
	 * @return object
	 * @throws Exception
	 */
	public static Object getJSONDataToObject(String url, String params,
			Class srcCla) throws Exception {
		return getJSONDataToObject(srcCla,
				getRemoteData(params.getBytes(), url));
	}

	/***
	 * 请求远程数据直接返回list
	 * 
	 * @param url
	 *            请求url
	 * @param params
	 *            请求参数
	 * @param srcCla
	 *            class
	 * @return list<T>
	 * @throws Exception
	 */
	public static List<Object> getJSONDataToList(String url, String params,
			Class srcCla) throws Exception {
		return getJSONDataToList(srcCla, getRemoteData(params.getBytes(), url));
	}

	/**
	 * 请求远程数据
	 * 
	 * @param url
	 *            请求url
	 * @param params
	 *            url后的参数
	 * @param method
	 *            请求方式[get, post]，默认get请求方式
	 * @return object
	 */
	public static Object getJSONDataToObject(String url,
			Map<String, String> params, Class srcCal, String method)
			throws Exception {
		return getJSONDataToObject(srcCal, getRemoteData(url, params, method));
	}

	/**
	 * 请求远程数据
	 * 
	 * @param url
	 *            请求url
	 * @param params
	 *            url后的参数
	 * @param method
	 *            请求方式[get, post]，默认get请求方式
	 * @return object
	 */
	public static Object getJSONDataToList(String url,
			Map<String, String> params, Class srcCla, String method)
			throws Exception {
		return getJSONDataToList(srcCla, getRemoteData(url, params, method));
	}

	/***
	 * 请求远程数据
	 * 
	 * @param url
	 *            请求地址
	 * @param name
	 *            参数名
	 * @param val
	 *            参数值
	 * @param srcCla
	 *            class
	 * @param method
	 *            请求方式post/get
	 * @return object
	 * @throws Exception
	 */
	public static Object getJSONDataToObject(String url, String[] name,
			Object[] val, Class srcCla, String method) throws Exception {
		return getJSONDataToObject(srcCla,
				getRemoteData(url, name, val, method));
	}

	/***
	 * 请求远程数据
	 * 
	 * @param url
	 *            请求地址
	 * @param jsonParams
	 *            参数为json数据格式
	 * @param pname
	 *            参数名
	 * @param srcCla
	 *            class
	 * @param method
	 *            请求方式post/get
	 * @return object
	 * @throws Exception
	 */
	public static Object getJSONDataToObject(String url, String jsonParams,
			String pname, Class srcCla, String method) throws Exception {
		return getJSONDataToObject(srcCla,
				getRemoteData(url, jsonParams, pname, method));
	}

	/***
	 * 请求远程数据
	 * 
	 * @param url
	 *            请求地址
	 * @param jsonParams
	 *            参数为json数据格式
	 * @param pname
	 *            参数名
	 * @param srcCla
	 *            class
	 * @param method
	 *            请求方式post/get
	 * @return list
	 * @throws Exception
	 */
	public static Object getJSONDataToList(String url, String jsonParams,
			String pname, Class srcCla, String method) throws Exception {
		return getJSONDataToList(srcCla,
				getRemoteData(url, jsonParams, pname, method));
	}
	
	public static void getRemote(String url, List<NameValuePair> list){
		HttpGet get = new HttpGet(url);
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 远程请求
	 * 
	 * @param params
	 * @param strUrl
	 * @return
	 */
	public static String getRemoteData(byte[] params, String strUrl)
			throws Exception {
		StringBuffer result = new StringBuffer();
		URL url = new URL(strUrl);
		HttpURLConnection httpUrlConn = (HttpURLConnection) url
				.openConnection();
		httpUrlConn.setConnectTimeout(3 * 1000);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setDoOutput(true);
		httpUrlConn.setUseCaches(false);
		httpUrlConn.setRequestMethod("POST");
		httpUrlConn.setRequestProperty("Charset", "UTF-8");
		httpUrlConn.setRequestProperty("Content-type", CONTENT_TYPE);

		httpUrlConn.connect();

		DataOutputStream dataos = new DataOutputStream(
				httpUrlConn.getOutputStream());
		dataos.write(params);
		dataos.flush();
		dataos.close();

		// 接收返回的数据
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				httpUrlConn.getInputStream()));
		String line = "";
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		reader.close();
		httpUrlConn.disconnect();
		return result.toString();
	}

	/**
	 * Http请求
	 * 
	 * @param url
	 *            请求url
	 * @param params
	 *            url后的参数
	 * @param method
	 *            请求方式[get, post]，默认get请求方式
	 * @return json
	 */
	public static String getRemoteData(String url, Map<String, String> params,
			String method) throws Exception {
		StringBuffer data = new StringBuffer(url);
		// 设置请求超时
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpResponse response = null;
		if (!TextUtils.isEmpty(method) && POST.equals(method)) {
			// post请求
			HttpPost httpPost = new HttpPost(url);
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			for (String key : params.keySet()) {
				parameters.add(new BasicNameValuePair(key, params.get(key)));
			}
			// 设置参数
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,
					"UTF-8");
			httpPost.setEntity(entity);
			response = client.execute(httpPost);
		} else {
			String httpUrl = url + convertMapToParams(params);
			HttpGet httpGet = new HttpGet(httpUrl);
			response = client.execute(httpGet);
		}
		StatusLine status = response.getStatusLine();
		if (status.getStatusCode() == HttpStatus.SC_OK) {
			requestState = REQUEST_SUCCESS;
			InputStream is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			for (String line = reader.readLine(); line != null; line = reader
					.readLine()) {
				data.append(line);
			}
		} else {
			requestState = REQUEST_FAILED;
		}
		System.out.println("data:" + data.toString());
		return TextUtils.isEmpty(data) ? data.toString() : null;
	}

	/***
	 * Http请求
	 * 
	 * @param url
	 *            请求url
	 * @param name
	 *            ['param1', 'param2',...]
	 * @param val
	 *            [val1, val2, ...]
	 * @param method
	 *            请求方式[get, post]，默认get请求方式
	 * @return json
	 */
	public static String getRemoteData(String url, String[] name, Object[] val,
			String method) throws Exception {
		StringBuffer data = new StringBuffer(url);
		// 设置请求超时
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpResponse response = null;
		if (!TextUtils.isEmpty(method) && POST.equals(method)) {
			// post请求
			HttpPost httpPost = new HttpPost(url);// application/x-www-form-urlencoded
			if (name != null && val != null) {
				Object[] obj = val;
				if (name.length > val.length) {
					obj = new Object[name.length];
					System.arraycopy(val, 0, obj, 0, val.length);
				}
				List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				for (int i = 0; i < name.length; i++) {
					parameters
							.add(new BasicNameValuePair("", obj[i].toString()));
				}
				// 设置参数
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						parameters, "UTF-8");
				httpPost.setEntity(entity);
			}
			response = client.execute(httpPost);
		} else {
			String httpUrl = url + convertArrayToParams(name, val);
			HttpGet httpGet = new HttpGet(httpUrl);
			response = client.execute(httpGet);
		}
		StatusLine status = response.getStatusLine();
		if (status.getStatusCode() == HttpStatus.SC_OK) {
			requestState = REQUEST_SUCCESS;
			InputStream is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				data.append(line);
			}
		} else {
			requestState = REQUEST_FAILED;
		}
		System.out.println("data:" + data.toString());
		return TextUtils.isEmpty(data) ? data.toString() : null;
	}

	/**
	 * Http请求
	 * 
	 * @param url
	 * @param object
	 *            参数对象
	 * @param method
	 *            请求方式[get, post]，默认get请求方式
	 * @return
	 */
	public static String getRemoteData(String url, Object object, String method)
			throws Exception {
		StringBuffer data = new StringBuffer(url);
		// 设置请求超时
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpResponse response = null;
		if (!TextUtils.isEmpty(method) && POST.equals(method)) {
			// post请求
			HttpPost httpPost = new HttpPost(url);
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			Map<String, String> params = convertObjectToMap(object);
			for (String key : params.keySet()) {
				parameters.add(new BasicNameValuePair(key, params.get(key)));
			}
			// 设置参数
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,
					"UTF-8");
			httpPost.setEntity(entity);
			response = client.execute(httpPost);
		} else {
			String httpUrl = url
					+ convertMapToParams(convertObjectToMap(object));
			HttpGet httpGet = new HttpGet(httpUrl);
			response = client.execute(httpGet);
		}
		StatusLine status = response.getStatusLine();
		if (status.getStatusCode() == HttpStatus.SC_OK) {
			requestState = REQUEST_SUCCESS;
			InputStream is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			for (String line = reader.readLine(); line != null; line = reader
					.readLine()) {
				data.append(line);
			}
		} else {
			requestState = REQUEST_FAILED;
		}
		System.out.println("data:" + data.toString());
		return TextUtils.isEmpty(data) ? data.toString() : null;
	}

	/***
	 * Http请求
	 * 
	 * @param url
	 *            请求地址
	 * @param jsonParams
	 *            json数据格式参数
	 * @param pname
	 *            参数名
	 * @param method
	 *            请求方式
	 * @return json
	 * @throws Exception
	 */
	public static String getRemoteData(String url, String jsonParams,
			String pname, String method) throws Exception {
		StringBuffer data = new StringBuffer(url);
		// 设置请求超时
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpResponse response = null;
		if (!TextUtils.isEmpty(method) && POST.equals(method)) {
			// post请求
			HttpPost httpPost = new HttpPost(url);
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair(pname, jsonParams));
			// 设置参数
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,
					"UTF-8");
			httpPost.setEntity(entity);
			response = client.execute(httpPost);
		} else {
			String httpUrl = url + "?" + pname + "=" + jsonParams;
			HttpGet httpGet = new HttpGet(httpUrl);
			response = client.execute(httpGet);
		}
		StatusLine status = response.getStatusLine();
		if (status.getStatusCode() == HttpStatus.SC_OK) {
			requestState = REQUEST_SUCCESS;
			InputStream is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			for (String line = reader.readLine(); line != null; line = reader
					.readLine()) {
				data.append(line);
			}
		} else {
			requestState = REQUEST_FAILED;
		}
		System.out.println("data:" + data.toString());
		return TextUtils.isEmpty(data) ? data.toString() : null;
	}

	private static Map<String, String> convertObjectToMap(Object params) {
		Map<String, String> maps = new HashMap<String, String>();
		try {
			Class<?> cal = params.getClass();
			for (int i = 0; i < cal.getDeclaredFields().length; i++) {
				Field field = cal.getDeclaredFields()[i];
				field.setAccessible(true);
				String fname = field.getName();
				String value = field.get(params).toString();
				maps.put(fname, value);
				/*
				 * String strLitter = fname.substring(0, 1).toUpperCase();
				 * 
				 * String getName = "get" + strLitter + fname.substring(1);
				 * String setName = "set" + strLitter + fname.substring(1);
				 * 
				 * Method getmethod = cal.getMethod(getName, new Class[]{});
				 * cal.getMethod(setName, new Class[]{field.getType()});
				 * 
				 * String value = getmethod.invoke(params, new
				 * Object[]{}).toString();
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maps;
	}

	private static String convertMapToParams(Map<String, String> map) {
		if (map.isEmpty())
			return "";
		StringBuffer params = new StringBuffer("?");
		for (String key : map.keySet()) {
			params.append(key).append("=")
					.append(map.get(key).replace(' ', '#')).append("&");
		}
		return params.toString().substring(0,
				params.toString().lastIndexOf("&"));
	}

	private static String convertArrayToParams(String[] name, Object[] val) {
		if (name == null || val == null)
			return "";
		if (name.length != val.length)
			throw new RuntimeException("参数数目不匹配！");
		StringBuffer params = new StringBuffer("?");
		for (int i = 0; i < name.length; i++) {
			params.append("&")
					.append(name[i])
					.append("=")
					.append(val[i] != null ? "" : val[i].toString().replace(
							' ', '#'));
		}
		return params.toString().substring(0,
				params.toString().lastIndexOf("&"));
	}

	public static String getRequestState() {
		return requestState;
	}
}
