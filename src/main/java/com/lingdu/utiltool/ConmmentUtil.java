package com.lingdu.utiltool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConmmentUtil {
	/**
	 * 判断字符是否是ip规则
	 * @param str
	 * @return
	 */
    public static Boolean  ipRegex(String str) {  
        Pattern p = Pattern.compile("^((25[0-5]|2[0-4]\\d|[1]{1}\\d{1}\\d{1}|[1-9]{1}\\d{1}|\\d{1})($|(?!\\.$)\\.)){4}$");  
        Matcher m = p.matcher(str);  
        return m.matches();
    }
    /**
     * 获得 yyyy-MM-dd hh:mm:ss 格式的时间
     * @return
     */
    public static String getformter(){
    	Date date=new Date(); 
	    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return df.format(date);
    }
    public static void getdex(){
    	String str = "as;asdfasdfasdfas";
    	int i = str.indexOf(";");
    	System.out.println(i);
    	String str1 = str.substring(0,i);
    	System.out.println(str1);
    }
    
   
    public static void main(String[] args) {
    	getdex();
	}
}
