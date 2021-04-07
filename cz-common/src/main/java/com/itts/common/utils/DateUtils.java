package com.itts.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static String SUBJECT_DATE = "yyyy/MM/dd";

    public static Date format(String time, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse(time.trim());
        return date;
    }

    public static boolean isBetween(String startStr, String endStr) throws ParseException {
        Date now = new Date();
        Date start = DateUtils.format(startStr, DATE_TIME_PATTERN);
        Date end = DateUtils.format(endStr, DATE_TIME_PATTERN);
        if (now.getTime() > start.getTime() && now.getTime() < end.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isToday(long time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    public static boolean isNotToday(long time) {
        return !isToday(time);
    }

    public static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
//        Date now = new Date();
//
//        Date start = DateUtils.format(DateUtils.format(now, "yyyy-MM-dd 07:00:00"), DATE_TIME_PATTERN);
//        Date end = DateUtils.format(DateUtils.format(now, "yyyy-MM-dd 10:30:00"), DATE_TIME_PATTERN);
//
//        System.out.println(now.getTime() > start.getTime());
//        System.out.println(now.getTime() < end.getTime());

    }

    public static Date format(Date date) throws ParseException {
        return format(date, DATE_PATTERN);
    }

    public static Date format(Date date, String pattern) throws ParseException {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(df.format(date));
        }
        return null;
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }
}
