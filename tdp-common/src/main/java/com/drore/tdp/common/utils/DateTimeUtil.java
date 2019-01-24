package com.drore.tdp.common.utils;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.Instant;
import java.util.*;

/**
 * @Description
 * @Project my-shopping-parent
 * @Package com.zl.study.common.utils
 * @ClassName DateUtil
 * @Author 曾灵
 * @QQ|Email 3195690389|17363645521@163.com
 * @Date 2018-10-14 下午9:36
 * @Version 1.0
 * @Modified By
 */
public class DateTimeUtil {
    /**
     * 标准时间格式化
     */
    public static final String FORMAT_STANDARD_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_STANDARD_DATE = "yyyy-MM-dd";
    public static final String FORMAT_STANDARD_BEGIN = "yyyy-MM-dd 00:00:00";
    public static final String FORMAT_STANDARD_END = "yyyy-MM-dd 23:59:59";

    /**
     * 时间格式化
     *
     * @return
     */
    public static DateTimeFormatter dtf(String pattern) {
        return DateTimeFormat.forPattern(pattern);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String nowDateTimeString() {
        return nowDateTimeString(FORMAT_STANDARD_DATETIME);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String nowDateString() {
        return nowDateTimeString(FORMAT_STANDARD_DATE);
    }

    public static String nowDateTimeString(String pattern) {
        return LocalDateTime.now().toString(pattern);
    }

    public static Date nowDateTimeDate() {
        return LocalDateTime.now().toDate();
    }

    public static Long nowDateTimeTimestamp() {
        return Instant.now().getEpochSecond();
    }

    /**
     * 获取昨日日期
     *
     * @return
     */

    /**
     * 获取昨日日期
     *
     * @return
     */
    public static String yesterdayDateString() {
        return yesterdayDateTimeString(FORMAT_STANDARD_DATE);
    }

    public static String yesterdayDateTimeString(String pattern) {
        return stringPlusDays(nowDateTimeString(pattern), pattern, -1);
    }


    /**
     * @param time 2019-01-09 17:19:14
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String time) {
        return LocalDateTime.parse(time, dtf(FORMAT_STANDARD_DATETIME));
    }


    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.toString(FORMAT_STANDARD_DATETIME);
    }

    public static String localDateToString(LocalDate localDate) {
        return localDate.toString(FORMAT_STANDARD_DATE);
    }

    public static String localDateToString(LocalDate localDate, String pattern) {
        return localDate.toString(pattern);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime, String pattern) {
        return localDateTime.toString(pattern);
    }

    /**
     * 格式化字符串日期转LocalDateTime(时间格式保持一致)
     *
     * @param time    2019-01-09 17:19
     * @param pattern yyyy-MM-dd HH:mm
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String time, String pattern) {
        return LocalDateTime.parse(time, dtf(pattern));
    }

    /**
     * @param time
     * @return
     */
    public static Date stringToDate(String time) {
        return stringToLocalDateTime(time).toDate();
    }

    /**
     * 格式化字符串日期转Date(时间格式保持一致)
     *
     * @param time    2019-01-09 17:19
     * @param pattern yyyy-MM-dd HH:mm
     * @return
     */
    public static Date stringToDate(String time, String pattern) {
        return stringToLocalDateTime(time, pattern).toDate();
    }

    /**
     * @param time
     * @return
     */
    public static Long stringToTimestamp(String time) {
        return stringToDate(time).getTime();
    }

    /**
     * 格式化字符串日期转Timestamp(时间格式保持一致)
     *
     * @param time    2019-01-09 17:19
     * @param pattern yyyy-MM-dd HH:mm
     * @return
     */
    public static Long stringToTimestamp(String time, String pattern) {
        return stringToDate(time, pattern).getTime();
    }

    /**
     * 时间戳转格式化字符串日期
     *
     * @param timestamp
     * @return
     */
    public static String timestampToString(Long timestamp) {
        Date date = new Date(timestamp);
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(FORMAT_STANDARD_DATETIME);
    }

    /**
     * 格式化字符串日期加减自定义秒数
     *
     * @param time
     * @param seconds
     * @return
     */
    public static String stringPlusSeconds(String time, int seconds) {
        LocalDateTime localDateTime = stringToLocalDateTime(time).plusSeconds(seconds);
        return localDateTimeToString(localDateTime);
    }

    public static String stringPlusSeconds(String time, String pattern, int seconds) {
        LocalDateTime localDateTime = stringToLocalDateTime(time, pattern).plusSeconds(seconds);
        return localDateTimeToString(localDateTime, pattern);
    }

    /**
     * 格式化字符串日期加减自定义分钟数
     *
     * @param time
     * @param minutes
     * @return
     */
    public static String stringPlusMinutes(String time, int minutes) {
        LocalDateTime localDateTime = stringToLocalDateTime(time).plusMinutes(minutes);
        return localDateTimeToString(localDateTime);
    }

    public static String stringPlusMinutes(String time, String pattern, int minutes) {
        LocalDateTime localDateTime = stringToLocalDateTime(time, pattern).plusMinutes(minutes);
        return localDateTimeToString(localDateTime, pattern);
    }

    /**
     * 格式化字符串日期加减自定义小时数
     *
     * @param time
     * @param hours
     * @return
     */
    public static String stringPlusHours(String time, int hours) {
        LocalDateTime localDateTime = stringToLocalDateTime(time).plusHours(hours);
        return localDateTimeToString(localDateTime);
    }

    public static String stringPlusHours(String time, String pattern, int hours) {
        LocalDateTime localDateTime = stringToLocalDateTime(time, pattern).plusHours(hours);
        return localDateTimeToString(localDateTime, pattern);
    }

    /**
     * 格式化字符串日期加减自定义天数
     *
     * @param time
     * @param days
     * @return
     */
    public static String stringPlusDays(String time, int days) {
        LocalDateTime localDateTime = stringToLocalDateTime(time).plusDays(days);
        return localDateTimeToString(localDateTime);
    }

    public static String stringPlusDays(String time, String pattern, int days) {
        LocalDateTime localDateTime = stringToLocalDateTime(time, pattern).plusDays(days);
        return localDateTimeToString(localDateTime, pattern);
    }

    /**
     * 格式化字符串日期加减自定义月数
     *
     * @param time
     * @param months
     * @return
     */
    public static String stringPlusMonths(String time, int months) {
        LocalDateTime localDateTime = stringToLocalDateTime(time).plusMonths(months);
        return localDateTimeToString(localDateTime);
    }

    public static String stringPlusMonths(String time, String pattern, int months) {
        LocalDateTime localDateTime = stringToLocalDateTime(time, pattern).plusMonths(months);
        return localDateTimeToString(localDateTime, pattern);
    }

    /**
     * 格式化字符串日期加减自定义年数
     *
     * @param time
     * @param years
     * @return
     */
    public static String stringPlusYears(String time, int years) {
        LocalDateTime localDateTime = stringToLocalDateTime(time).plusYears(years);
        return localDateTimeToString(localDateTime);
    }

    public static String stringPlusYears(String time, String pattern, int years) {
        LocalDateTime localDateTime = stringToLocalDateTime(time, pattern).plusYears(years);
        return localDateTimeToString(localDateTime, pattern);
    }

    /**
     * 本周第一天(自定义时间格式)
     *
     * @return
     */
    public static String firstDayDateOfWeek() {
        return firstDayDateTimeOfWeek(FORMAT_STANDARD_DATE);
    }

    public static String firstDayDateTimeOfWeek(String pattern) {
        DateTime today = new DateTime();
        today = today.withDayOfWeek(DateTimeConstants.MONDAY);
        return today.toString(pattern);
    }

    /**
     * 本周最后一天
     *
     * @return
     */
    public static String lastDayDateOfWeek() {
        return lastDayDateTimeOfWeek(FORMAT_STANDARD_DATE);
    }

    public static String lastDayDateTimeOfWeek(String pattern) {
        DateTime today = new DateTime();
        today = today.withDayOfWeek(DateTimeConstants.SUNDAY);
        return today.toString(pattern);
    }

    /**
     * 本周日期集合(包含今天)
     *
     * @return
     */
    public static List<String> listCurrentWeekDate() {
        return listCurrentWeekDate(true);
    }

    public static List<String> listCurrentWeekDate(Boolean contain) {
        String begin = firstDayDateOfWeek();
        String end;
        if (contain) {
            end = lastDayDateOfWeek();
        } else {
            end = yesterdayDateString();
        }
        return listDay(begin, end);
    }

    /**
     * 本月第一天
     *
     * @return
     */
    public static String firstDayDateOfMonth() {
        return firstDayDateTimeOfMonth(FORMAT_STANDARD_DATE);
    }

    public static String firstDayDateTimeOfMonth(String pattern) {
        DateTime today = new DateTime();
        today = today.dayOfMonth().withMinimumValue();
        return today.toString(pattern);
    }

    /**
     * 本月最后一天
     *
     * @return
     */
    public static String lastDayDateOfMonth() {
        return lastDayDateTimeOfMonth(FORMAT_STANDARD_DATE);
    }

    public static String lastDayDateTimeOfMonth(String pattern) {
        DateTime today = new DateTime();
        today = today.dayOfMonth().withMaximumValue();
        return today.toString(pattern);
    }


    /**
     * 本月日期集合(包含今天)
     *
     * @return
     */
    public static List<String> listCurrentMonthDate() {
        return listCurrentMonthDate(true);
    }

    public static List<String> listCurrentMonthDate(Boolean contain) {
        String begin = firstDayDateOfMonth();
        String end;
        if (contain) {
            end = lastDayDateOfMonth();
        } else {
            end = yesterdayDateString();
        }
        return listDay(begin, end);
    }

    /**
     * 本年第一天
     *
     * @return
     */
    public static String firstDayDateOfYear() {
        return firstDayDateTimeOfYear(FORMAT_STANDARD_DATE);
    }

    public static String firstDayDateTimeOfYear(String pattern) {
        DateTime today = new DateTime();
        today = today.dayOfYear().withMinimumValue();
        return today.toString(pattern);
    }

    /**
     * 本年最后一天
     *
     * @return
     */
    public static String lastDayDateOfYear() {
        return lastDayDateTimeOfYear(FORMAT_STANDARD_DATE);
    }

    public static String lastDayDateTimeOfYear(String pattern) {
        DateTime today = new DateTime();
        today = today.dayOfYear().withMaximumValue();
        return today.toString(pattern);
    }

    /**
     * 本年日期集合(包含今天)
     *
     * @return
     */
    public static List<String> listCurrentYearDate() {
        return listCurrentYearDate(true);
    }

    public static List<String> listCurrentYearDate(Boolean contain) {
        String begin = firstDayDateOfYear();
        String end;
        if (contain) {
            end = lastDayDateOfYear();
        } else {
            end = yesterdayDateString();
        }
        return listDay(begin, end);
    }

    /**
     * 遍历获取两个日期之间天数集合
     *
     * @param begin 格式yyyy-MM-dd 2019-01-07
     * @param end   格式yyyy-MM-dd 2019-01-10
     * @return 含头含尾 [2019-01-07, 2019-01-08, 2019-01-09, 2019-01-10]
     */
    public static List<String> listDay(String begin, String end) {
        return listDay(begin, end, FORMAT_STANDARD_DATE);
    }

    /**
     * 遍历获取两个日期之间天数集合
     *
     * @param begin   格式自定义 2019/01/10
     * @param end     格式自定义 2019/01/15
     * @param pattern 自定义 yyyy/MM/dd
     * @return 含头含尾 [2019-01-10, 2019-01-11, 2019-01-12, 2019-01-13, 2019-01-14, 2019-01-15]
     */
    public static List<String> listDay(String begin, String end, String pattern) {
        return listDay(begin, end, pattern, FORMAT_STANDARD_DATE);
    }

    /**
     * 遍历获取两个日期之间天数集合
     *
     * @param begin      格式自定义 2019-01-10
     * @param end        格式自定义 2019-01-15
     * @param oldPattern 自定义 yyyy-MM-dd
     * @param newPattern 自定义 yyyy/MM/dd
     * @return 含头含尾 [2019/01/10, 2019/01/11, 2019/01/12, 2019/01/13, 2019/01/14, 2019/01/15]
     */
    public static List<String> listDay(String begin, String end, String oldPattern, String newPattern) {
        LocalDateTime beginDate = stringToLocalDateTime(begin, oldPattern);
        LocalDateTime endDate = stringToLocalDateTime(end, oldPattern);
        int days = Days.daysBetween(beginDate, endDate).getDays();
        List<String> listDates = new ArrayList<>();
        for (int i = 1; i <= days + 1; i++) {
            LocalDateTime localDateTime = beginDate.plusDays(i - 1);
            LocalDate localDate = localDateTime.toLocalDate();
            listDates.add(localDateToString(localDate, newPattern));
        }
        return listDates;
    }

    /**
     * @return {begin=2019-01-07 00:00:00, end=2019-01-13 23:59:59}
     */
    public static Map<String, String> getCurrentDayDateTime() {
        String begin = nowDateTimeString(FORMAT_STANDARD_BEGIN);
        String end = nowDateTimeString(FORMAT_STANDARD_END);
        return getBeginAndEnd(begin, end);
    }

    /**
     * 本周日期范围(包含今日)
     *
     * @return {begin=2019-01-07, end=2019-01-13}
     */
    public static Map<String, String> getCurrentWeekDate() {
        return getCurrentWeekDate(true);
    }

    /**
     * @param contain false|true 是否包含今天
     * @return {begin=2019-01-07, end=2019-01-09}
     */
    public static Map<String, String> getCurrentWeekDate(Boolean contain) {
        String begin = firstDayDateOfWeek();
        String end;
        if (contain) {
            end = lastDayDateOfWeek();
        } else {
            end = yesterdayDateString();
        }
        return getBeginAndEnd(begin, end);
    }

    /**
     * 本周日期时间范围(包含今日)
     *
     * @return {begin=2019-01-07 00:00:00, end=2019-01-13 23:59:59}
     */
    public static Map<String, String> getCurrentWeekDateTime() {
        return getCurrentWeekDateTime(true);
    }

    /**
     * @param contain false|true 是否包含今天
     * @return {begin=2019-01-07 00:00:00, end=2019-01-09 23:59:59}
     */
    public static Map<String, String> getCurrentWeekDateTime(Boolean contain) {
        String begin = firstDayDateTimeOfWeek(FORMAT_STANDARD_BEGIN);
        String end;
        if (contain) {
            end = lastDayDateTimeOfWeek(FORMAT_STANDARD_END);
        } else {
            end = yesterdayDateTimeString(FORMAT_STANDARD_END);
        }
        return getBeginAndEnd(begin, end);
    }

    /**
     * 本月日期范围(包含今日)
     *
     * @return {begin=2019-01-07, end=2019-01-13}
     */
    public static Map<String, String> getCurrentMonthDate() {
        return getCurrentMonthDate(true);
    }

    /**
     * @param contain false|true 是否包含今天
     * @return {begin=2019-01-07, end=2019-01-09}
     */
    public static Map<String, String> getCurrentMonthDate(Boolean contain) {
        String begin = firstDayDateOfMonth();
        String end;
        if (contain) {
            end = lastDayDateOfMonth();
        } else {
            end = yesterdayDateString();
        }
        return getBeginAndEnd(begin, end);
    }

    /**
     * 本月日期时间范围(包含今日)
     *
     * @return {begin=2019-01-07 00:00:00, end=2019-01-13 23:59:59}
     */
    public static Map<String, String> getCurrentMonthDateTime() {
        return getCurrentMonthDateTime(true);
    }

    /**
     * @param contain false|true 是否包含今天
     * @return {begin=2019-01-07 00:00:00, end=2019-01-09 23:59:59}
     */
    public static Map<String, String> getCurrentMonthDateTime(Boolean contain) {
        String begin = firstDayDateTimeOfMonth(FORMAT_STANDARD_BEGIN);
        String end;
        if (contain) {
            end = lastDayDateTimeOfMonth(FORMAT_STANDARD_END);
        } else {
            end = yesterdayDateTimeString(FORMAT_STANDARD_END);
        }
        return getBeginAndEnd(begin, end);
    }

    /**
     * 本年日期范围(包含今日)
     *
     * @return {begin=2019-01-07, end=2019-01-13}
     */
    public static Map<String, String> getCurrentYearDate() {
        return getCurrentYearDate(true);
    }

    /**
     * @param contain false|true 是否包含今天
     * @return {begin=2019-01-07, end=2019-01-09}
     */
    public static Map<String, String> getCurrentYearDate(Boolean contain) {
        String begin = firstDayDateOfYear();
        String end;
        if (contain) {
            end = lastDayDateOfYear();
        } else {
            end = yesterdayDateString();
        }
        return getBeginAndEnd(begin, end);
    }

    /**
     * 本年日期时间范围(包含今日)
     *
     * @return {begin=2019-01-07 00:00:00, end=2019-01-13 23:59:59}
     */
    public static Map<String, String> getCurrentYearDateTime() {
        return getCurrentYearDateTime(true);
    }

    /**
     * @param contain false|true 是否包含今天
     * @return {begin=2019-01-07 00:00:00, end=2019-01-09 23:59:59}
     */
    public static Map<String, String> getCurrentYearDateTime(Boolean contain) {
        String begin = firstDayDateTimeOfYear(FORMAT_STANDARD_BEGIN);
        String end;
        if (contain) {
            end = lastDayDateTimeOfYear(FORMAT_STANDARD_END);
        } else {
            end = yesterdayDateTimeString(FORMAT_STANDARD_END);
        }
        return getBeginAndEnd(begin, end);
    }

    public static Map<String, String> getBeginAndEnd(String begin, String end) {
        Map<String, String> map = new HashMap<>(2);
        map.put("begin", begin);
        map.put("end", end);
        return map;
    }

    /**
     * 自定义的时间格式的字符串相互转换
     *
     * @param time
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String parseAnotherPattern(String time, String oldPattern, String newPattern) {
        LocalDateTime localDateTime = stringToLocalDateTime(time, oldPattern);
        return localDateTimeToString(localDateTime, newPattern);
    }
}
