package com.ryit.commons.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ryit
 */
public class DateUtil {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate () {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate () {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime () {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow () {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow (final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime (final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr (final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime (final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath () {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime () {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 累加天数
     *
     * @param date
     * @param i
     * @return
     */
    public static Date addDay (Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, i);
        Date newDate = c.getTime();
        return newDate;
    }

    /**
     * 将秒数转成00:00格式
     *
     * @param duration
     * @return
     */
    public static String getDuration (BigDecimal duration) {
        if (duration == null) {
            return "00:00";
        }
        Integer time = duration.intValue();
        String minuteStr = "", secondStr = "";
        //分钟
        Integer minute = time / 60;
        //秒数
        Integer second = time % 60;
        minuteStr = minute < 10 ? "0" + minute : minute + "";
        secondStr = second < 10 ? "0" + second : second + "";
        return minuteStr + ":" + secondStr;
    }


    public static String stringToString (String dateStr, String fmt) {
        Date date = dateTime(fmt, dateStr);
        return parseDateToStr(fmt, date);
    }

    public static String timeCala (Date date) {
        int time = (int)((System.currentTimeMillis() - date.getTime()) / (1000 * 60)); // 计算分钟
        if(time <= 60){
            return time+"分钟前";
        }else if(time>=60 && time < 1440){
            time /= 60;
            return time+"小时前";
        }else if(time >= 1440&& time <= 2880){
            return "1天前";
        }else{
            return "3天前";
        }
    }

}
