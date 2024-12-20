package com.bulgat.validation.demo;

import java.util.regex.Pattern;
import java.util.regex.Matcher;  
  
public class PhoneNumberValidator {  
  
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");  
  
    public static boolean isValidPhoneNumber(String phoneNumber) {  
        if (phoneNumber == null || phoneNumber.isEmpty()) {  
            return false;  
        }  
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);  
        return matcher.matches();  
    }  
  
    public static void main(String[] args) {  
        System.out.println(isValidPhoneNumber("13800138000")); // 输出: true  
        System.out.println(isValidPhoneNumber("12800138000")); // 输出: false，因为第二位不是3-9  
        System.out.println(isValidPhoneNumber("1380013800"));  // 输出: false，因为长度不是11位  
        System.out.println(isValidPhoneNumber(null));        // 输出: false，因为输入为null  
    }  
}
