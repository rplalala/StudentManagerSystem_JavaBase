package com.ding.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpHelp {
	
	/**
	 * 
	 * 判断 待检测的字符串s 是否满足 正则表达式regEx
	 * */
	public boolean checkRegexp(String regEx,String s) {
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}
	
	/**
	 * 
	 * 正则表达式检验：输入的是否是 m-n 位的非0正整数
	 * */
	public boolean notZeroAndPositiveAndLimitDigit(String s,int m,int n) {
		String regEx1 = "^\\+?[1-9][0-9]*$";//字符串只能为非0的正整数
		String regEx2 = "^\\d{"+m+","+n+"}$";//字符串只能为m-n位数字
		boolean judge1 = checkRegexp(regEx1,s);//判断是否满足 非0的正整数
		boolean judge2 = checkRegexp(regEx2,s);//判断是否满足 m-n位的数字
		return (judge1 && judge2); //判断是否满足 m-n位的非0正整数;
	}
	
	/**
	 * 
	 * 正则表达式检验：第一位 1-4 ；第二位 0-3；第三位1-9；格式的3位数房间号
	 * */
	public boolean formatRoomNum(String s) {
		String regEx = "^[1-4][0-3][1-9]$";//房间号格式
		return checkRegexp(regEx,s);
	}
	
	/**
	 * 正则表达式检验：输入的是否是 2-4位汉字
	 * 
	 */
	public boolean formatChinese(String s) {
		String regEx = "^[\\u4e00-\\u9fa5]{2,4}$";
		return checkRegexp(regEx,s);
	}
	
	/**
	 * 正则表达式检验：字母开头，5-16字节，允许字母数字下划线
	 * 用来检验管理员账号和密码
	 */
	public boolean checkUserNameAndUserPassword(String s) {
		String regEx = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
		return checkRegexp(regEx,s);
	}
	

}
