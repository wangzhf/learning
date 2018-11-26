package com.wangzhf.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具
 */
public class DateUtil {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd hh:mm:ss";

    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String getNow(String pattern) {
        return format(new Date(), pattern);
    }

    public static String getNow() {
        return format(new Date(), DEFAULT_PATTERN);
    }

}
