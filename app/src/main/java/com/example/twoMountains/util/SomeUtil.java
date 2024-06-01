package com.example.twoMountains.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SomeUtil {
    public static Boolean isPhone(String phone) {
        String pattern = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(phone);
        return m.matches();
    }

    public static Boolean isEmail(String email) {
        if (email == null) {
            return false;
        }
        String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(email);
        return m.matches();
    }

    public static Boolean isTruePwd(String pwd) {
        // 正则表达式，匹配8至16位字符，必须包含字母和数字
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(pwd);
        return m.matches();
    }

    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


}
