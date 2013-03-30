package com.irun.sm.ui.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/***
 * @author huangsm
 * @date 2013-1-6
 * @email huangsanm@gmail.com
 * @desc sdk 2.2-
 */
public class TrafficStatsAgo {

	/**
	* 获取网络流量信息
	* 利用读取系统文件的方法来进行获取网络流量
	* 主要意义在于可以应用于2.2以前的没有提供TrafficStats接口的版本
	*/
	public static String readInStream(FileInputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}
			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i("FileTest", e.getMessage());
		}
		return null;
	}
	// //////////////////////////////////获取手机2G/3G的下载流量//////////////////////////////////////
	public static long getMobileRxBytes() {
		long ReturnLong = 0; // 查询到的结果
		try {
			File file = new File("/proc/net/dev");
			FileInputStream inStream = new FileInputStream(file);
			String a = readInStream(inStream);
			int startPos = a.indexOf("rmnet0:");
			a = a.substring(startPos);
			Pattern p = Pattern.compile(" \\d+ ");
			Matcher m = p.matcher(a);
			while (m.find()) {
				ReturnLong = Long.parseLong(m.group().trim());
				break;
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return ReturnLong;
	}

	// //////////////////////////////////获取手机2G/3G的上传流量//////////////////////////////////////
	public static long getMobileTxBytes() {
		long ReturnLong = 0; // 查询到的结果
		try {
			int count = 0; // 返回结果时的计数器
			File file = new File("/proc/net/dev");
			FileInputStream inStream = new FileInputStream(file);
			String a = readInStream(inStream);
			int startPos = a.indexOf("rmnet0:");
			a = a.substring(startPos);
			Pattern p = Pattern.compile(" \\d+ ");
			Matcher m = p.matcher(a);
			while (m.find()) {
				if (count == 8) {
					ReturnLong = Long.parseLong(m.group().trim());
					break;
				}
				count++;

			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return ReturnLong;
	}

	// //////////////////////////////////获取手机Wifi的下载流量//////////////////////////////////////
	public static long getWifiRxBytes() {
		long ReturnLong = 0; // 查询到的结果
		try {
			File file = new File("/proc/net/dev");
			FileInputStream inStream = new FileInputStream(file);
			String a = readInStream(inStream);
			int startPos = a.indexOf("wlan0:");
			a = a.substring(startPos);
			Pattern p = Pattern.compile(" \\d+ ");
			Matcher m = p.matcher(a);
			while (m.find()) {
				ReturnLong = Long.parseLong(m.group().trim());
				break;
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return ReturnLong;
	}

	// //////////////////////////////////获取手机Wifi的上传流量//////////////////////////////////////
	public static long getWifiTxBytes() {
		long ReturnLong = 0; // 查询到的结果
		try {
			int count = 0; // 返回结果时的计数器
			File file = new File("/proc/net/dev");
			FileInputStream inStream = new FileInputStream(file);
			String a = readInStream(inStream);
			int startPos = a.indexOf("wlan0:");
			a = a.substring(startPos);
			Pattern p = Pattern.compile(" \\d+ ");
			Matcher m = p.matcher(a);
			while (m.find()) {
				if (count == 8) {
					ReturnLong = Long.parseLong(m.group().trim());
					break;
				}
				count++;
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return ReturnLong;
	}

	// //////////////////////////////////根据uid获取进程的下载流量//////////////////////////////////////
	public static long getUidRxBytes(int uid) {
		long ReturnLong = 0; // 查询到的结果

		try {
			String url = "/proc/uid_stat/" + String.valueOf(uid) + "/tcp_rcv";
			File file = new File(url);
			FileInputStream inStream;
			if (file.exists()) {
				inStream = new FileInputStream(file);
				ReturnLong = Long.parseLong(readInStream(inStream).trim());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Log.i(url+"文件并不存在","可能原因为该文件在开机后并没有上过网，所以没有流量记录");
		return ReturnLong;
	}

	// //////////////////////////////////根据uid获取进程的上传流量//////////////////////////////////////
	public static long getUidTxBytes(int uid) {
		long ReturnLong = 0; // 查询到的结果
		try {
			String url = "/proc/uid_stat/" + String.valueOf(uid) + "/tcp_snd";
			File file = new File(url);
			if (file.exists()) {
				FileInputStream inStream = new FileInputStream(file);
				ReturnLong = Long.parseLong(readInStream(inStream).trim());
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return ReturnLong;
	}
}
