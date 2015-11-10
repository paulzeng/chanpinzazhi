package com.chanpinzazhi.manager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.EditText;

/**
 * 字符串操作工具包
 */
public class StringManager {

	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) { 
			flag = false;
		}

		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) { 
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格\s、制表符\t 、回车符\n、换行符\r 组成的字符串
	 * 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		return input == null || "".equals(input);
	}

	/**
	 * 判断是否手机号码
	 * 
	 * @param cellphone
	 * @return
	 */
	public static boolean isBadCellphone(String cellphone) {
		return cellphone.length() != 11;
	}

	/**
	 * 判断密码的格式
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean isBadPwd(String pwd) {
		return false;// 密码没有做最少位数限制
	}

	public static boolean isBadVerificationCode(String code) {
		return false;
	}

	/**
	 * 将EditText中中文本获取到
	 */
	public static String getStringByET(EditText et) {
		return et.getText().toString().trim();
	}

	/**
	 * 像素和sp 相互转换
	 */
	public static int spToPix(Context context, int sp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (sp * scale + 0.5f);
	}

	/**
	 * 根据文件绝对路径获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (isEmpty(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 获取图片的绝对路径
	 * 
	 * @param rUrl
	 * @return
	 */
	public static String getImgUrl(Context cx,String rUrl) {
		return ConfigManager.getConfigs(cx,"url_pro_type") + rUrl;
	}

	/**
	 * 获取图片的绝对路径
	 * 
	 * @param rUrl
	 * @return
	 */
	public static String getBigImgUrl(Context cx,String rUrl) {
		return ConfigManager.getConfigs(cx,"url_pro_big") + rUrl;
	}

	public static String getPhotoImgUrl(Context cx,String rUrl) {
		return ConfigManager.getConfigs(cx,"url_pro_other") + rUrl;
	}

	/**
	 * 删除Html标签
	 * 
	 * @param inputString
	 * @return
	 */
	public static String htmlRemoveTag(String inputString) {
		if (inputString == null)
			return null;
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
			// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}
	@SuppressLint("SimpleDateFormat")
	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}
}
