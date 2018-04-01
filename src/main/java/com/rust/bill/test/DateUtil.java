package com.rust.bill.test;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

  private static String where = "DateUtil";

  public static final String PATTERN_YYYY = "yyyy";
  public static final String PATTERN_MM = "MM";
  public static final String PATTERN_DD = "dd";
  public static final String PATTERN_YYYY_MM_DD_1 = "yyyy-MM-dd";
  public static final String PATTERN_YYYY_MM_DD_2 = "yyyy.MM.dd";
  public static final String PATTERN_YYYY_MM_DD_3 = "yyyyMMdd";
  public static final String PATTERN_YYYY_MM = "yyyyMM";

  public static final String PATTERN_HH_MM_1 = "HH:mm";

  public static final String PATTERN_FULL_1 = "yyyy-MM-dd HH:mm:ss";
  public static final String PATTERN_FULL_2 = "yyyyMMddHHmmss";
  public static final String PATTERN_FULL_3 = "yyyy/MM/dd HH:mm";
  public static final String PATTERN_FULL_4 = "yyyy.MM.dd HH:mm";
  public static final String PATTERN_FULL_5 = "yyyy年MM月dd日 HH:mm:ss";

  public static final String DATEFORMAT_5 = "yyyy/MM/dd HH:mm";
  public static final String DATEFORMAT_6 = "HHmmss";

  /**
   * 生成T日yyyymmdd000001至T+1日yyyymmdd000000 自动任务有效时间段为0点至12点
   */
  public static final String TIME_TYPE_01 = "01";
  /**
   * 生成T-1日yyyymmdd150001至T日yyyymmdd150000；周末时间跳过，周一的T-1日为上一周的周五 自动任务有效时间为15点至24点
   */
  public static final String TIME_TYPE_02 = "02";

  public static final String STR_FUND_START = "150000";
  public static final String STR_FUND_END = "145959";

  public static final int INT_VALUE_0 = 0;
  public static final int INT_VALUE_1 = 1;
  public static final int INT_VALUE_6 = 6;


  public static class DateRange {
    public Date to;
    public Date from;
  }




  // 增加或减少天数
  public static Date addDay(final Date date, final int num) {
    final Calendar startDT = Calendar.getInstance();
    startDT.setTime(date);
    startDT.add(Calendar.DAY_OF_MONTH, num);
    return startDT.getTime();
  }

  // 增加或减少小时
  public static Date addHours(final Date date, final int hour) {
    final Calendar startDT = Calendar.getInstance();
    startDT.setTime(date);
    startDT.add(Calendar.HOUR_OF_DAY, hour);
    return startDT.getTime();
  }

  // 增加或减少小时
  public static Date addMins(final Date date, final int mins) {
    final Calendar startDT = Calendar.getInstance();
    startDT.setTime(date);
    startDT.add(Calendar.MINUTE, mins);
    return startDT.getTime();
  }


  /**
   * 日期 T+n
   * 
   * @param myDate
   * @param n(T+n)
   * @returnyyyy-MM-dd
   */
  public static String addDayString2(String myDate, int n) {
    Date strTo2Date = strToDate(myDate);
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(strTo2Date);
    calendar.add(calendar.DATE, n);
    Date time = calendar.getTime();
    SimpleDateFormat sim = new SimpleDateFormat(PATTERN_YYYY_MM_DD_1);
    return sim.format(time);
  }

  /* 将字符串转换成日期 */
  public static Date getDateTimeByString(final String rq) {
    final DateFormat df = new SimpleDateFormat(PATTERN_FULL_1);
    // DateFormat df = DateFormat.getDateTimeInstance();
    Date d = null;
    try {
      d = df.parse(rq);
    } catch (final Exception e) {
    }
    return d;
  }

  /* 将字符串转换成日期 */
  public static Date getDateTimeByString3(final String rq) {
    final DateFormat df = new SimpleDateFormat(PATTERN_YYYY_MM_DD_3);
    Date d = null;
    try {
      d = df.parse(rq);
    } catch (final Exception e) {
    }
    return d;
  }

  /* 将字符串转换成日期 */
  public static Date getDateTimeByString5(final String rq) {
    final DateFormat df = new SimpleDateFormat(PATTERN_YYYY_MM_DD_1);
    // DateFormat df = DateFormat.getDateTimeInstance();
    Date d = null;
    try {
      d = df.parse(rq);
    } catch (final Exception e) {
    }
    return d;
  }

  /**
   * yyyyMMddHHmmss 转成 yyyy年MM月dd日 HH:mm:ss
   * 
   * @param rq
   * @return
   */
  public static String getDateTimeByString4(String rq) {
    DateFormat df = new SimpleDateFormat(PATTERN_FULL_2);
    DateFormat df2 = new SimpleDateFormat(PATTERN_FULL_5);
    String d = null;
    try {
      d = df2.format(df.parse(rq));
    } catch (final Exception e) {
    }
    return d;
  }

  /**
   * 返回两个时间的差 天为单位
   */
  public static double getDateDifference(Date startDate, Date endDate) {
    double diff = endDate.getTime() - startDate.getTime();
    return diff / (1000 * 60 * 60 * 24);
  }

  /**
   * 将字符串转化为TimeStamp
   * 
   * @param req
   * @return
   */
  public static Timestamp getTimestampByString(String req) {
    Timestamp timestamp = null;
    try {
      long time = Long.parseLong(req);
      timestamp = new Timestamp(time);
      return timestamp;
    } catch (Exception e) {
    }
    return timestamp;
  }

  /**
   * 字符串转为日期
   * 
   * @param str 日期对应的字符串
   * @param pattern 使用pattern解析str
   * @return 格式化后的日期,若使用pattern无法解析str,则返回null
   * @author yanjiawei 2014.09.13
   */
  public static Date parseString2Date(final String str, final String pattern) {
    if (StringUtils.isBlank(str) || StringUtils.isBlank(pattern)) {
      return null;
    }
    final DateFormat dateformate = new SimpleDateFormat(pattern);
    Date date = null;
    try {
      date = dateformate.parse(str);
    } catch (ParseException e) {
      return null;
    }
    return date;
  }

  /**
   * @param str 日期对应的字符串，格式必须为<strong>yyyy.MM.dd</strong>
   * @return 格式化后的日期
   */
  public static Date parseString2DateWithPattern2(final String str) {
    return parseString2Date(str, PATTERN_YYYY_MM_DD_2);
  }

  /* 判断myDate是否为null */
  public static Date isDate(final Date myDate) {
    if (myDate == null) {
      return new Date();
    }
    return myDate;
  }

  /* 格式化日期为短形式 */
  public static String getShortDate(final Date myDate) {
    final SimpleDateFormat fd = new SimpleDateFormat(PATTERN_YYYY_MM_DD_3);
    return (fd.format(myDate));
  }

  /* 格式化日期为标准形式 */
  public static String formatDateTime(Date myDate, final String pattern) {
    // myDate = isDate(myDate);
    // SimpleDateFormat fd = new SimpleDateFormat(pattern, Locale.CHINA);
    final SimpleDateFormat fd = new SimpleDateFormat(pattern);
    return (fd.format(myDate));
  }

  /** 格式话日期为yyyy-MM-dd形式 */
  public static String formatDate(final Date myDate) {
    return formatDateTime(myDate, PATTERN_YYYY_MM_DD_1);
  }

  public static String formatDatePattern(final Date myDate) {
    return formatDateTime(myDate, PATTERN_YYYY_MM_DD_2);
  }

  public static String formatDateFullPattern(final Date myDate) {
    return formatDateTime(myDate, DATEFORMAT_5);
  }

  public static String formatTime(final Date myDate) {
    return formatDateTime(myDate, PATTERN_HH_MM_1);
  }

  public static String formatDateToStringBR(final Date myDate) {
    return changeDateToString(myDate, PATTERN_YYYY_MM_DD_2) + "<br/>"
        + changeDateToString(myDate, PATTERN_HH_MM_1);
  }

  public static Date formatToDate(final Date date) {
    final String sDate = DateUtil.getShortDate(date);
    final String sDate1 = sDate.substring(0, 4) + "-" + sDate.substring(4, 6) + "-"
        + sDate.substring(6, 8) + " " + "00:00:00";
    return getDateTimeByString(sDate1);
  }

  public static String formatDate(final Date date, final String format) {
    return formatDateTime(date, format);
  }

  /**
   * 将指定的日期格式转换成想要的格式
   * 
   * @param date
   * @param sourceFormat 指定的格式
   * @param targetFormat 想要的格式
   * @return
   */
  public static String formatDate(final String date, final String sourceFormat,
                                  final String targetFormat) {
    if (StringUtils.isBlank(date)) {
      return "";
    }
    final SimpleDateFormat fd = new SimpleDateFormat(sourceFormat);
    final SimpleDateFormat fdt = new SimpleDateFormat(targetFormat);
    try {
      Date sourceDate = fd.parse(date);
      return fdt.format(sourceDate);
    } catch (ParseException e) {
      return "";
    }
  }

  public static String formatToString(final String date) {

    final String sDate1 =
        date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " "
            + date.substring(8, 10) + ":" + date.substring(10, 12) + ":" + date.substring(12, 14);
    return sDate1;
  }

  /**
   * 将yyyyMM转换成yyyy年MM月
   * 
   * @param date
   * @return
   */
  public static String formatToyyyyMMString(final String date) {
    if (StringUtils.isBlank(date)) {
      return "";
    }
    final String sDate1 = date.substring(0, 4) + "年" + date.substring(4, 6) + "月";
    return sDate1;
  }

  public static String changeDateToStringFull2(final Date date) {
    return changeDateToString(date, PATTERN_FULL_2);
  }

  public static Date changeStringToDateFull2(final String dateStr) {
    return changeStringToDate(dateStr, PATTERN_FULL_2);
  }


  public static String convertDate(final String endDate, final String hhmmss) {
    return changeDateToStringFull2(addHHMMSSToDate(endDate, hhmmss));
  }

  // date pattern is: yyyy-MM-dd.
  public static Date addHHMMSSToDate(final String dateStr, final String hhmmss) {
    return changeStringToDate(dateStr + " " + hhmmss, DateUtil.PATTERN_FULL_1);
  }

  public static Date changeStringToDate(final String dateStr, final String pattern) {
    final SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
    Date date = null;
    try {
      date = formatDate.parse(dateStr);
    } catch (final ParseException e) {
    }

    return date;
  }

  public static String changeDateToString(final Date date, final String pattern) {
    if (date == null) {
      return null;
    }
    final SimpleDateFormat fd = new SimpleDateFormat(pattern);
    return (fd.format(date));
  }

  public static String changeFromPatternToPattern3(final String date) {
    if (StringUtils.isBlank(date)) {
      return "";
    } else {
      return date.replace("-", "");
    }
  }

  // 将短时间格式字符串转换为时间 yyyy-MM-dd
  public static Date strToDate(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_YYYY_MM_DD_1);
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }

  // 将字符串转为指定格式日期
  public static Date strToDate(String strDate, String pattern) {
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }

  /**
   * return if the amount of time already elapsed from the date
   * 
   * @param date
   * @param timeType e.g. Calendar.MINUTE, Calendar.DAY...
   * @param amount
   * @return
   */
  public static boolean isTimeAlreadyElapsed(Date date, int timeType, int amount) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(timeType, -amount);
    Date cal = calendar.getTime();

    if (date != null && date.compareTo(cal) < 0) {
      return true;
    }

    return false;

  }

  /**
   * 将yyyyMMdd转换成yyyy-MM-dd
   */
  public static String dateStrAddDash(final String date) {
    if (!date.matches("\\d{8}")) {
      return date;
    }
    SimpleDateFormat formatter1 = new SimpleDateFormat(PATTERN_YYYY_MM_DD_3);
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter1.parse(date, pos);
    SimpleDateFormat formatter2 = new SimpleDateFormat(PATTERN_YYYY_MM_DD_1);
    return formatter2.format(strtodate);
  }

  /**
   * 将yyyyMMddHHmmss转换成yyyy-MM-dd HH:mm:ss
   */
  public static String fullDateStrFormatTransform(final String date) {
    String resultDateStr = null;
    try {
      if (!date.matches("\\d{14}")) {
        return date;
      }
      SimpleDateFormat formatter1 = new SimpleDateFormat(PATTERN_FULL_2);
      Date strtodate = formatter1.parse(date);
      SimpleDateFormat formatter2 = new SimpleDateFormat(PATTERN_FULL_1);
      resultDateStr = formatter2.format(strtodate);
    } catch (Exception e) {
    }
    return resultDateStr;
  }

  /**
   * 比较String类型的yyyy-MM-dd格式的字符串大小
   * 
   * @throws ParseException
   */
  public static boolean compareStringDate(final String startTime, final String endTime) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date start = format.parse(startTime);
      Date end = format.parse(endTime);
      if (start.compareTo(end) <= 0) {
        return false;
      } else {
        return true;
      }
    } catch (ParseException e) {
      throw new NumberFormatException();
    }

  }

  /**
   * 根据年出生的年月日判断用户是否满18岁
   * 
   * @param strDate
   * @return
   */
  public static boolean CalculationAge(Date strDate) {
    Date date = new Date();
    if (strDate != null) {
      if (date.getYear() - strDate.getYear() == 18) {
        if (date.getMonth() - strDate.getMonth() > 0) {
          return true;
        } else if (date.getMonth() - strDate.getMonth() == 0) {
          if (date.getDate() - strDate.getDate() >= 0) {
            return true;
          } else {
            return false;// false
          }
        } else {
          return false;
        }
      } else if (date.getYear() - strDate.getYear() < 18) {
        return false;
      } else {
        return true;
      }
    }
    return false;
  }

  public static long getCurrTime() {
    return new Date().getTime();
  }


  /**
   * 
   * 获取现在时间,
   * 
   * @return 返回时间格式YYYYMMddHHmmss
   */

  public static String getStringDateYYYYMMddHHmmss() {

    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_FULL_2);
    String dateString = formatter.format(currentTime);
    return dateString;

  }


  /**
   * 获取当前时间
   * @param formatReg format格式
   * @return
   */
  public static String getCurrentDateTime(String formatReg) {
    Date currentDate = Calendar.getInstance(Locale.CHINA).getTime();
    SimpleDateFormat formatter = new SimpleDateFormat(formatReg);
    return formatter.format(currentDate);
  }


  public static String getStringDate5() {
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_FULL_5);
    String dateString = formatter.format(currentTime);
    return dateString;

  }

  /**
   * 
   * 获取现在时间,
   * 
   * @return 返回时间格式YYYYMMddHHmmss
   */

  public static String getStringDateYYYYMMdd() {

    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_YYYY_MM_DD_1);
    String dateString = formatter.format(currentTime);
    return dateString;

  }

  /**
   * 
   * 获取现在时间,
   * 
   * @return 返回时间格式YYYYMMdd
   */

  public static String getStringDateYYYYMMDD() {

    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_YYYY_MM_DD_3);
    String dateString = formatter.format(currentTime);
    return dateString;

  }

  /**
   * 
   * 获取现在时间,
   * 
   * @return 返回时间格式 PATTERN_FULL_1
   */

  public static String getStringDateFULL1() {
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_FULL_1);
    String dateString = formatter.format(currentTime);
    return dateString;
  }


  /**
   * 
   * 获取现在时间,指定返回时间格式
   * 
   * @return 返回时间格式YYYYMMddHHmmss
   */

  public static String getStringDate(String pattern) {

    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    String dateString = formatter.format(currentTime);
    return dateString;

  }

  /**
   * 返回两个日期相差的天数
   * 
   * @param startDate
   * @param endDate
   * @return
   */
  public static long getDistDates(Date startDate, Date endDate) {
    long totalDate = 0;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startDate);

    long timestart = calendar.getTimeInMillis();
    calendar.setTime(endDate);
    long timeend = calendar.getTimeInMillis();

    totalDate = (timeend - timestart) / (1000 * 60 * 60 * 24);
    return totalDate;
  }

  /**
   * 接受YYYY-MM-DD的日期字符串参数,返回两个日期相差的天数
   * 
   * @param start
   * @param end
   * @return
   * @throws ParseException
   */
  public static long getDistDates(String start, String end) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM_DD_1);
    Date startDate = sdf.parse(start);
    Date endDate = sdf.parse(end);
    return getDistDates(startDate, endDate);
  }

  /**
   * 返回两个日期相差的分钟数
   * 
   * @param startDate
   * @param endDate
   * @return
   */
  public static long getDistDateMin(Date startDate, Date endDate) {
    long totalMinutes = 0;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startDate);

    long timestart = calendar.getTimeInMillis();
    calendar.setTime(endDate);
    long timeend = calendar.getTimeInMillis();

    totalMinutes = (timeend - timestart) / (1000 * 60);
    return totalMinutes;
  }

  /**
   * 获取当前月之前的N个月份 ，包含当月
   * 
   * @param BeforeMonth
   * @return [201406, 201405, 201404, 201403]
   */
  public static List<String> getBeforeDates(int beforeMonth) {
    return getBeforeDates(null, PATTERN_YYYY_MM, beforeMonth);
  }

  /**
   * 获取指定月之前的N个月份
   * 
   * @param date
   * @param pattern 指定得到的日期格式
   * @param beforeMonth 前N个月
   * @return
   */
  public static List<String> getBeforeDates(String date, String pattern, int beforeMonth) {
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    Calendar calendar = Calendar.getInstance();
    Date startDate = null;
    List<String> list = new ArrayList<String>();
    try {
      if (StringUtils.isNotBlank(date)) {
        startDate = format.parse(date);
      } else {
        startDate = calendar.getTime();
      }

      for (int i = beforeMonth; i >= 0; i--) {
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, -i);
        Date date02 = calendar.getTime();
        String currentDate = format.format(date02);
        list.add(currentDate);
      }

    } catch (ParseException e) {
    }
    return list;
  }

  /**
   * 获取某月的最后一天
   * 
   * @Title:getLastDayOfMonth @Description: @param:@param strDate @param:@param
   *                          pattern @param:@return @return:String @throws
   */
  public static String getLastDayOfMonth(String strDate, String pattern) {
    Date date = getLastDayOfMonthDate(strDate, pattern);
    // 格式化日期
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    String lastDayOfMonth = sdf.format(date);
    return lastDayOfMonth;
  }

  /**
   * 获取某月的最后一天
   * 
   * @Title:getLastDayOfMonth @Description: @param:@param strDate @param:@param
   *                          pattern @param:@return @return:Date @throws
   */
  public static Date getLastDayOfMonthDate(String strDate, String pattern) {
    Date date = changeStringToDate(strDate, pattern);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    // 设置年份
    cal.set(Calendar.YEAR, year);
    // 设置月份
    cal.set(Calendar.MONTH, month);
    // 获取某月最大天数
    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    // 设置日历中月份的最大天数
    cal.set(Calendar.DAY_OF_MONTH, lastDay);
    return cal.getTime();

  }

  /**
   * 当月第一天
   * 
   * @return
   */
  public static String monthStart() {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    Calendar curCal = Calendar.getInstance();
    curCal.set(Calendar.DAY_OF_MONTH, 1);
    Date beginTime = curCal.getTime();
    String sTime = format.format(beginTime);
    return sTime;
  }

  public static String lastMonthStart() {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    Calendar cal_1 = Calendar.getInstance();
    cal_1.add(Calendar.MONTH, -1);
    cal_1.set(Calendar.DAY_OF_MONTH, 1);
    String lastMonthStart = format.format(cal_1.getTime());
    return lastMonthStart;
  }

  /**
   * 
   * 创建者: 李昆岭 创建时间: 2014年11月13日 下午9:31:11 描述:获取从开始日期到结束日期之间的日期(包括开始和结束)
   * 
   * @param @param beginDate
   * @param @param endDate
   * @param @return
   */
  public static List<String> getDataList(String beginDate, String endDate, String pattern) {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    Date d = null;
    try {
      d = sdf.parse(beginDate);
    } catch (ParseException e) {
      return new ArrayList();
    }
    cal.setTime(d);
    List<String> dateList = new ArrayList<String>();
    if (beginDate.equals(endDate)) {
      dateList.add(beginDate);
    } else {
      String date = null;
      dateList.add(beginDate);
      do {
        cal.add(Calendar.DAY_OF_MONTH, 1);
        date = sdf.format(cal.getTime());
        dateList.add(date);
      } while (date.compareTo(endDate) < 0);
    }
    return dateList;
  }

  /**
   * 转换成纯数字的
   * 
   * @param date
   * @return
   */
  public static String convertToNumberDate(String date) {
    if (StringUtils.isBlank(date)) {
      return "";
    } else {
      return date.replaceAll("\\D", "");
    }
  }

  public static Date now() {

    return new Date();
  }

  /**
   * 判断当前时间是否在开始/结束时间之内
   * 
   * @param startTime
   * @param endTime
   * @return
   * @author zhoupeiwen
   */
  public static boolean isNowBetweenTimes(Date startTime, Date endTime) {
    boolean ret = false;

    Date now = new Date();
    if (startTime != null) {
      if (endTime == null && now.compareTo(startTime) >= 0) {
        ret = true;
      } else if (endTime != null && now.compareTo(startTime) >= 0 && now.compareTo(endTime) <= 0) {
        ret = true;
      }
    }
    return ret;
  }

  /**
   * 转义成yyyymmddhhmmss
   * 
   * @param str
   * @return
   */
  public static String convertToStr(String str) {

    try {
      SimpleDateFormat ft = new SimpleDateFormat(PATTERN_FULL_2);
      Date parse = ft.parse(str);
      return ft.format(parse);
    } catch (ParseException e) {
    }
    return null;


  }


  /**
   * yyyyMMdd to date
   * 
   * @param str
   * @return
   */
  public static Date yyyyMMddToDate(String str) {

    try {
      if (StringUtils.isNotBlank(str)) {
        SimpleDateFormat sf = new SimpleDateFormat(PATTERN_YYYY_MM_DD_3);
        return sf.parse(str);
      }

    } catch (ParseException e) {
      // TODO Auto-generated catch block
    }
    return null;

  }


  public static int getWeekOfDate(String date) {
    SimpleDateFormat sf = new SimpleDateFormat(PATTERN_YYYY_MM_DD_3);
    Date newDate = null;
    try {
      newDate = sf.parse(date);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(newDate);
    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
    if (w < 0) w = 0;
    return w;
  }


  /**
   * 转义成标准时间
   * 
   * @param date
   * @param typeDate
   * @return
   */
  public static String converteToStr(Date date, String typeDate) {
    if (null == date || null == typeDate) {
      return " ";
    }
    return new SimpleDateFormat(typeDate).format(date);
  }

  /**
   * yyyyMMddHHmmss to yyyy-MM-dd HH:mm:ss
   * 
   * @param dateStr14
   * @return
   */
  public static String str14To19(String dateStr14) {
    if (dateStr14 == null || "".equals(dateStr14)) return "";
    Date date = strToDate(dateStr14, PATTERN_FULL_2);
    String dateStr19 = formatDate(date, PATTERN_FULL_1);
    return dateStr19;
  }

  /**
   * 获取某月的第一天 @Title:getLastDayOfMonth @Description: @param:@param strDate @param:@param
   * pattern @param:@return @return:String @throws
   */
  public static String getFirstDayOfMonth(String strDate, String pattern) {
    Date date = getFirstDayOfMonthDate(strDate, pattern);
    // 格式化日期
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    String firstDayOfMonth = sdf.format(date);
    return firstDayOfMonth;
  }

  /**
   * 获取某月的第一天 @Title:getLastDayOfMonth @Description: @param:@param strDate @param:@param
   * pattern @param:@return @return:Date @throws
   */
  public static Date getFirstDayOfMonthDate(String strDate, String pattern) {
    Date date = changeStringToDate(strDate, pattern);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    // 设置年份
    cal.set(Calendar.YEAR, year);
    // 设置月份
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    return cal.getTime();
  }

  public static boolean isFirstDayOfMonth() {
    Calendar cal = Calendar.getInstance();
    return cal.get(Calendar.DAY_OF_MONTH) == 1;
  }

  public static void main(String[] args) {
    // System.out.println(addMins(new Date(), -5));
    System.out.println(getCurrentDateTime(PATTERN_FULL_5));

  }

}
