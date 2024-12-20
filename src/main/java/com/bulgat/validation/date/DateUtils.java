package com.bulgat.validation.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author: Bulgat
 * @createTime: 2024-12-20 16:24
 * @description: 日期校验接口
 */
public class DateUtils {

    /**
     * @description: 获取字符串对应的日期
     * @author: Bulgat
     * @date: 2024/12/20 16:46
     * @params: [dateStr, formatter]
     * @return: java.util.Date
     **/
    public static Date getDate(String dateStr,String formatter){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(formatter);
        simpleDateFormat.setLenient(false);
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String dateStr=scanner.next();
        Date date = getDate(dateStr, "yyyyMMdd");
        System.out.println(date);
        scanner.close();
    }
}