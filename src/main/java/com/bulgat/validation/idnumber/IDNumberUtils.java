package com.bulgat.validation.idnumber;

import com.bulgat.validation.date.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Bulgat
 * @createTime: 2024-12-20 15:34
 * @description: 校验身份号码
 */
public class IDNumberUtils {

    // 18位身份证号码加权因子
    private static final int[] CHINESE_ID_NUMBER_WEIGHT_FACTORS_18 = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    // 18位身份证号码校验码对应表
    private static final Map<Integer, String> CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18 = new HashMap<>();


    static {
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(0, "1");
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(1, "0");
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(2, "X");
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(3, "9");
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(4, "8");
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(5, "7");
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(6, "6");
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(7, "5");
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(8, "4");
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(9, "3");
        CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.put(10, "2");
    }

    /**
     * @description: 校验国内身份证号码（18位）
     * @author: Bulgat
     * @date: 2024/12/20 15:36
     * @params: [chineseIDNumber]
     * @return: boolean
     * 参考： https://baike.baidu.com/item/%E5%85%AC%E6%B0%91%E8%BA%AB%E4%BB%BD%E5%8F%B7%E7%A0%81/11042821
     **/
    public static boolean validateChineseIDNumber18(String chineseIDNumber){
        //        1. 先判空
        if(StringUtils.isBlank(chineseIDNumber)){
            return false;
        }
        // 2.先判断整体格式，前17位是否全为数字，第18位是否为数字或者X（针对18位身份证号码）
        if (chineseIDNumber.length() != 18 || (!chineseIDNumber.substring(0, 17).matches("\\d{17}") ||
                (!chineseIDNumber.substring(17).matches("\\d") &&!chineseIDNumber.substring(17).equals("X")))) {
            return false;
        }
        // 3.验证地址码（前6位）
        if (!validateChineseAddressCode(chineseIDNumber.substring(0, 6))) {
            return false;
        }
        //4. 校验生日
        if(!validateBirthDate(chineseIDNumber.substring(6,14))){
            return false;
        }
        // 5.校验校验码是否对应
        if(!validateChinesVerificationCode(chineseIDNumber)){
            return false;
        }
        return true;
    }

    private static boolean validateChineseAddressCode(String addressCode) {
        if (addressCode == null || addressCode.length()!= 6) {
            return false;
        }
        String regionCode = addressCode.substring(0, 1);
        String provinceCode = addressCode.substring(0, 2);
        String cityCode = addressCode.substring(2, 4);
        String countyCode = addressCode.substring(4, 6);

        // 依次校验各部分
        if (!validateRegionCode(regionCode)) {
            return false;
        }
        if (!validateProvinceCode(regionCode, provinceCode)) {
            return false;
        }
        if (!validateCityCode(cityCode)) {
            return false;
        }
        return validateCountyCode(countyCode);
    }

    // 校验国内区域代码是否合法
    private static boolean validateRegionCode(String regionCode) {
        if (regionCode == null || regionCode.length()!= 1) {
            return false;
        }
        int code = Integer.parseInt(regionCode);
        return code >= 1 && code <= 6;
    }

    // 校验省（直辖市，自治区，特别行政区）代码是否合法，基于给定的各区域范围
    private static boolean validateProvinceCode(String regionCode, String provinceCode) {
        if (provinceCode == null || provinceCode.length()!= 2) {
            return false;
        }
        int region = Integer.parseInt(regionCode);
        int province = Integer.parseInt(provinceCode);
        switch (region) {
            case 1:
                return province >= 11 && province <= 15;
            case 2:
                return province >= 21 && province <= 23;
            case 3:
                return province >= 31 && province <= 37;
            case 4:
                return province >= 41 && province <= 46;
            case 5:
                return province >= 50 && province <= 54;
            case 6:
                return province >= 61 && province <= 65;
            case 8:
                return province >= 81 && province <= 82;
            default:
                return false;
        }
    }

    // 校验城市代码是否合法（简单校验格式以及针对直辖市的特殊情况判断逻辑初步校验）
    private static boolean validateCityCode(String cityCode) {
        if (cityCode == null || cityCode.length()!= 2) {
            return false;
        }
        try {
            int code = Integer.parseInt(cityCode);
            return code >= 01 && code <= 99;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 校验区县代码是否合法（包含各种不同类型区县指代的逻辑校验）
    private static boolean validateCountyCode(String countyCode) {
        if (countyCode == null || countyCode.length()!= 2) {
            return false;
        }
        try {
            int code = Integer.parseInt(countyCode);
            if (code == 00) {
                return true;
            }
            int e = code / 10;
            return (e == 0 || e == 1 || e == 2 || e == 8) && code >= 01 && code <= 99;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @description: 验证出生日期码是否合法
     * @author: Bulgat
     * @date: 2024/12/20 16:04
     * @params: [birthDateStr]
     * @return: boolean
     **/
    private static boolean validateBirthDate(String birthDateStr) {
        // 1.校验字符串
        if(StringUtils.isBlank(birthDateStr)||birthDateStr.length()!=8){
            return false;
        }
        //2. 获取对应日期
        Date birthDate= DateUtils.getDate(birthDateStr,"yyyyMMdd");
        if (birthDate==null){
            return false;
        }
        //3.判断是不是在现在之后的日期
        Date nowDate=new Date();
        if (!birthDate.before(nowDate)){
            return false;
        }
        return true;
    }

    /**
     * @description: 校验校验码是否符合规则
     * @author: Bulgat
     * @date: 2024/12/20 16:05
     * @params: [chineseIDNumber]
     * @return: boolean
     **/
    private static boolean validateChinesVerificationCode(String chineseIDNumber) {
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (chineseIDNumber.charAt(i) - '0') * CHINESE_ID_NUMBER_WEIGHT_FACTORS_18[i];
        }
        int remainder = sum % 11;
        return CHINESE_ID_NUMBER_VERIFICATION_CODE_MAP_18.get(remainder).equals(chineseIDNumber.substring(17));
    }
}
