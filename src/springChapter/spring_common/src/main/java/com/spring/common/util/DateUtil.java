package com.spring.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    /**
     * 日期
     */
    public final static String DEFAILT_DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 日期时间
     */
    public final static String DEFAILT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final int DEFAULT = 0;
    public static final int YM = 1;
    public static final int YMR_SLASH = 11;
    public static final int YM_SLASH = 13;
    public static final int NO_SLASH = 2;
    public static final int YM_NO_SLASH = 3;
    public static final int DATE_TIME = 4;
    public static final int DATE_TIME_NO_SLASH = 5;
    public static final int DATE_HM = 6;
    public static final int TIME = 7;
    public static final int HM = 8;
    public static final int LONG_TIME = 9;
    public static final int SHORT_TIME = 10;
    public static final int DATE_TIME_LINE = 12;
    public static final int Y = 14;

    public static String dateToStr(Date date, String pattern) {
        if ((date == null) || (date.equals("")))
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String dateToStr(Date date) {
        return dateToStr(date, "yyyy/MM/dd");
    }

    public static String dateToStr(Date date, int type) {
        switch (type) {
            case DEFAULT:
                return dateToStr(date);
            case Y:
                return dateToStr(date, "yyyy");
            case YM:
                return dateToStr(date, "yyyy/MM");
            case NO_SLASH:
                return dateToStr(date, "yyyyMMdd");
            case YMR_SLASH:
                return dateToStr(date, "yyyy-MM-dd");
            case YM_SLASH:
                return dateToStr(date, "yyyy-MM");
            case YM_NO_SLASH:
                return dateToStr(date, "yyyyMM");
            case DATE_TIME:
                return dateToStr(date, "yyyy/MM/dd HH:mm:ss");
            case DATE_TIME_NO_SLASH:
                return dateToStr(date, "yyyyMMddHHmmss");
            case DATE_HM:
                return dateToStr(date, "yyyy/MM/dd HH:mm");
            case TIME:
                return dateToStr(date, "HH:mm:ss");
            case HM:
                return dateToStr(date, "HH:mm");
            case LONG_TIME:
                return dateToStr(date, "HHmmss");
            case SHORT_TIME:
                return dateToStr(date, "HHmm");
            case DATE_TIME_LINE:
                return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
        }
        throw new IllegalArgumentException("Type undefined : " + type);
    }

    /**
     * 转换日期得到指定格式的日期字符串
     * @param formatString 需要把目标日期格式化什么样子的格式。例如,yyyy-MM-dd HH:mm:ss
     * @param targetDate   目标日期
     * @return
     * @author zjc
     */
    public static String convertDate2String(String formatString, Date targetDate) {
        SimpleDateFormat format = null;
        String result = null;
        if (targetDate != null) {
            format = new SimpleDateFormat(formatString);
            result = format.format(targetDate);
        } else {
            return null;
        }
        return result;
    }

    /**
     * 转换日期字符串得到指定格式的日期类型
     * @param formatString 需要转换的格式字符串
     * @param targetDate   需要转换的时间
     * @return
     * @throws ParseException
     * @author zjc
     */
    public static final Date convertString2Date(String formatString,
                                                String targetDate) {
        if (StringUtils.isBlank(targetDate))
            return null;
        SimpleDateFormat format = null;
        Date result = null;
        format = new SimpleDateFormat(formatString);
        try {
            result = format.parse(targetDate);
        } catch (ParseException pe) {
            throw new RuntimeException("日期类型转换出错：", pe);
        }
        return result;
    }

    /**
     * 转换 yyyy-MM-dd HH:mm:ss 格式日期字符串得到指定格式的日期类型
     * @param targetDate 需要转换的时间
     * @return
     * @throws ParseException
     * @author zjc
     */
    public static final Date convertString2Date(String targetDate) {
        if (StringUtils.isBlank(targetDate)) return null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(DEFAILT_DATE_TIME_PATTERN);
            Date result = format.parse(targetDate);
            return result;
        } catch (ParseException pe) {
            throw new RuntimeException("日期类型转换出错：", pe);
        }
    }

    /**
     * 将时间字符串转换为时间
     * @param sdate 格式为2010-04-14
     */
    @SuppressWarnings("deprecation")
    public static Date getDate(String sdate) {
        if (sdate == null || "".equals(sdate))
            return null;
        String[] date = sdate.split("-");
        // System.out.println(Arrays.toString(date));
        // 要减去1900年
        int y = Integer.parseInt(date[0]) - 1900;
        // 要减1个月份
        int m = Integer.parseInt(delZero(date[1])) - 1;
        int d = Integer.parseInt(delZero(date[2]));
        return new Date(y, m, d);
    }

    /**
     * 时间加减
     */
    @SuppressWarnings("all")
    public static Date getDate(int type, Date beginDate) {
        return getDate(type, beginDate, 1);
    }

    /**
     * 时间加减
     */
    @SuppressWarnings("all")
    public static Date getDate(int type, Date beginDate, int num) {
        if (beginDate == null)
            return null;
        Calendar ca = Calendar.getInstance();
        ca.setTime(beginDate);
        if (type == 2) {
            // 包月 Calender
            ca.add(Calendar.MONTH, num);
        } else if (type == 3) {
            // 包年
            ca.add(Calendar.YEAR, num);
        } else if (type == 4) {
            // 包周
            ca.add(Calendar.DATE, 7 * num);
        } else if (type == 1) {
            // 包天
            ca.add(Calendar.DATE, num);
        }
        return ca.getTime();
    }

    /**
     * 将月份或者日期格式为'01'转成'1'
     * @param szero 含有0的月份和日期字符串
     */
    private static String delZero(String szero) {
        if (szero.indexOf("0") == 0) {
            szero = szero.substring(1);
        }
        return szero;
    }

    /**
     * 比较当前时间与时间date2的天相等 时间格式 2008-11-25 16:30:10 如:当前时间是2008-11-25
     * 16:30:10与传入时间2008-11-25 15:31:20 相比较,返回true即相等
     * @param date2
     * @return boolean; true:相等
     * @author zjc
     */
    public static boolean equalDate(String date2) {
        String date1 = convertDate2String(DEFAILT_DATE_TIME_PATTERN,
                new Date());
        date1.equals(date2);
        Date d1 = convertString2Date(DEFAILT_DATE_PATTERN, date1);
        Date d2 = convertString2Date(DEFAILT_DATE_PATTERN, date2);
        return d1.equals(d2);
    }

    public static boolean isSameDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }


    /**
     * 获得指定日期的前一天
     * @param specifiedDay
     * @return
     * @author zjc
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     * @param specifiedDay
     * @return
     * @author zjc
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }


    /**
     * 获取两个日期相差的月份
     * @param after
     * @param before
     * @return
     * @throws ParseException
     */
    public static int getMothDiff(Date after, Date before) {
        if (after == null || before == null) return -1;
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(after);
        aft.setTime(before);
        int result = (aft.get(Calendar.MONTH) + 1) - (bef.get(Calendar.MONTH) + 1);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 获取一个月的
     * @param monthNum
     * @return
     */
    public static String[] getMonthDay(Date date, Integer monthNum) {
        //获取当前时间
        Calendar lastMonthFirstDay = Calendar.getInstance();
        lastMonthFirstDay.setTime(date);
        //调到上个月
        if (monthNum != null) lastMonthFirstDay.add(Calendar.MONTH, monthNum);
        lastMonthFirstDay.set(Calendar.DATE, 1);
        //获取当前时间
        Calendar lastMonthLastDay = Calendar.getInstance();
        lastMonthLastDay.setTime(date);
        //调到上个月
        if (monthNum != null) lastMonthLastDay.add(Calendar.MONTH, monthNum);
        //得到一个月最最后一天日期(31/30/29/28)
        int MaxDay = lastMonthLastDay.getActualMaximum(Calendar.DAY_OF_MONTH);
        //按你的要求设置时间
        lastMonthLastDay.set(lastMonthLastDay.get(Calendar.YEAR), lastMonthLastDay.get(Calendar.MONTH), MaxDay, 23, 59, 59);
        //按格式输出
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new String[]{sdf.format(lastMonthFirstDay.getTime()), sdf.format(lastMonthLastDay.getTime())};
    }

    /**
     * 获取一个月的天数
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        if (date == null) return 1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}
