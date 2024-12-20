package com.bulgat.validation;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bulgat
 * @description 用于校验电话号码的工具类
 */
public class PhoneUtils {
    // 移动电话号码校验常量
    private static final String CHINESE_MOBILE_PHONE_PREFIX ="+86";
    private static final String CHINESE_MOBILE_PHONE_REGEX ="^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
    private static final Pattern CHINESE_MOBILE_PHONE_PATTERN =Pattern.compile(CHINESE_MOBILE_PHONE_REGEX);

    // end

    // 固定电话号码校验常量
    private static final String CHINESE_FIXED_PHONE_REGEX ="^\\d{3}-\\d{7,8}|\\d{4}-\\d{7,8}$";
    private static final Pattern CHINESE_FIXED_PHONE_PATTERN =Pattern.compile(CHINESE_FIXED_PHONE_REGEX);
    // end
    /**
     * @description: 用于校验国内移动电话号码
     * @author: Bulgat
     * @date: 2024/12/20 1:27
     * @params: [phone]
     * @return: boolean
     **/
    public static boolean validChineseMobilePhone(String phone) {
//        1. 先判空
        if(StringUtils.isBlank(phone)){
            return false;
        }
//        2. 判断开头有没有+86 ,如果有+86，则删掉+86
        if (StringUtils.startsWith(phone, CHINESE_MOBILE_PHONE_PREFIX)){
            phone=phone.substring(3);
        }
//        3. 判断长度是不是11位
        if(phone.length()!=11){
            return false;
        }
//        4. 正则表达式匹配
        Matcher matcher = CHINESE_MOBILE_PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }

    /**
     * @description: 用于校验国内固定电话号码(必须带区号，7到8位，格式：0532-88901158)
     * @author: Bulgat
     * @date: 2024/12/20 14:13
     * @params: [phone]
     * @return: boolean
     **/
    public static boolean validChineseFixedPhone(String phone) {
//        1. 先判空
        if(StringUtils.isBlank(phone)){
            return false;
        }
//        2. 正则表达式匹配
        Matcher matcher = CHINESE_FIXED_PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }
}
