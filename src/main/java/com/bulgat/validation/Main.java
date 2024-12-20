package com.bulgat.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String dateStr = "2021-13-12";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.err.println("输入的日期格式不正确，请检查输入内容，格式需符合yyyy-MM-dd且日期值要合理。");
            System.exit(1);  // 直接终止程序，返回非0状态码表示程序异常结束
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(year + "," + month + "," + day);
    }
}