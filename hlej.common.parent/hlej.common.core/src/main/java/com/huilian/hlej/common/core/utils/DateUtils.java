package com.huilian.hlej.common.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  日期工具类
 *  类            名:   DateUtils
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年3月17日
 *  @author   qinlinhai
 *
 */
public class DateUtils {

	/**
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date createDate(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = null;
		try {
			d = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 字符串转日期 接收格式yyyy-MM-dd
	 * @param str
	 * @return
	 */
	public static Date getDate(String str) {
		return createDate(str, "yyyy-MM-dd");
	}

	/**
	 * 字符串转日期 接收格式yyyy-MM-dd
	 * @param str
	 * @return
	 */
	public static Date getDateYmd(String str) {
		return createDate(str, "yyyyMMdd");
	}

	/**
	 * 字符串转日期 接收格式yyyy-MM-dd HH:mm:ss
	 * @param str
	 * @return
	 */
	public static Date getDateTime(String str) {
		return createDate(str, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化当前日期
	 * @param format
	 * @return
	 */
	public static String dateToStr(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 加一天
	 * @param date
	 * @return
	 */
	public static Date addDay(Date date, int dayNum) {
		Calendar c = Calendar.getInstance();
		c.setTime(date); // 设置当前日期
		c.add(Calendar.DATE, dayNum); // 日期加1
		return c.getTime(); // 结果

	}

	/**
	 * 将日期对象格式化为日期yyyy-MM-dd时间标准格式的字符串
	 * @param date 待格式化的日期
	 * @return 格式化为日期、时间格式的字符串
	 */
	public static String formatDateTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("yyyy-MM-dd");
		return dateFormat.format(date);
	}
	
	public static String formatDateStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern("yyyyMMddHHmmsss");
		return dateFormat.format(date);
	}

	/**
	 * 将日期对象转化为指定format格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDateTime(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	
	/**
	 * 将日期对象转化为指定format格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDateYmdHms(Date date) {
		String str = null;
		if(date != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			str = dateFormat.format(date);
		}
		return str;
	}

	/**
	 * 将日期对象转化为指定format格式
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseDateYmdHms(String dateStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.parse(dateStr);
	}
	
	/**
	 * sDate大于eDate返回true
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public static boolean compareDate(String sDate, String eDate) {
		Date sd = createDate(sDate, "yyyy-MM-dd");
		Date ed = createDate(eDate, "yyyy-MM-dd");
		if (sd.after(ed)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate  较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 将日期对象格式化为日期时间标准格式的字符串
	 * @param date 待格式化的日期对豄
	 * @return 格式化为日期、时间格式的字符串 默认 yyyy-MM-dd
	 */
	public static String formatDate(Date date) {
		String format = "yyyy-MM-dd";
		return formatDateTime(date, format);
	}

	/**
	 * 当前月份加上month月
	 * @param month
	 * @return
	 */
	public static Date addMonth(int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, month);
		return getDate(formatDateTime(c.getTime()));
	}

	/**
	 * 给 date 减去 year年
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date subYear(Date date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		return c.getTime();
	}

	public static String formatDateTime(String strDate, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			Date date = dateFormat.parse(strDate);
			return DateUtils.formatDateTime(date, format);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 在传入的时间上加上month月
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		return getDate(formatDateTime(c.getTime()));
	}
	public static Date addMonthDays(Date date, int month,int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.DAY_OF_MONTH, days);
		return getDate(formatDateTime(c.getTime()));
	}
	public static Date setDay(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, day);
		return getDate(formatDateTime(c.getTime()));
	}
	/**
	 * 延后秒数值
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date afterSecond(Date date, int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, second);
		return c.getTime();
	}
	/**
	 * 判断日期是否是双休日
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isOverDay(Date date) {
		if (date == null) {
			return false;
		}
		if (date.getDay() == 0 || date.getDay() == 6) {
			return true;
		}
		return false;
	}

	private static final Map<Integer, Integer> seasons = new HashMap<Integer, Integer>();
	static {
		seasons.put(0, 0);
		seasons.put(1, 0);
		seasons.put(2, 0);
		seasons.put(3, 1);
		seasons.put(4, 1);
		seasons.put(5, 1);
		seasons.put(6, 2);
		seasons.put(7, 2);
		seasons.put(8, 2);
		seasons.put(9, 3);
		seasons.put(10, 3);
		seasons.put(11, 3);
	}

	/**
	 * 获取日期对应的季节(0:spring 1:summer 2:autumn 3:winter)
	 * 
	 * @param date
	 * @return
	 * @author wangzheng
	 * @date 2014年7月14日-上午10:23:11
	 */
	@SuppressWarnings({ "deprecation" })
	public static int getDateSeason(Date date) {
		if (null == date)
			return 0;
		return seasons.get(date.getMonth());
	}

	/**
	 * 判断日期是否为当前季度的最后一月
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isLastMonthInSeason(Date date) {
		if (3 * getDateSeason(date) + 2 == date.getMonth()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取日期对应季度最后一月
	 * 
	 * @param date
	 * @return "yyyy-MM"
	 */
	public static String getMonthInSeasonLast(Date date) {
		int season = DateUtils.getDateSeason(date);
		int lastMonth = 3 * season + 3;
		return String.format("%tY-%02d", date, lastMonth);
	}

	/**
	 * 获取日期对应季度最前一月
	 * 
	 * @param date
	 * @return "yyyy-MM"
	 */
	public static String getMonthInSeasonFirst(Date date) {
		int season = DateUtils.getDateSeason(date);
		int firstMonth = 3 * season + 1;
		return String.format("%tY-%02d", date, firstMonth);
	}

	/**
	 * 将String类型的日期按指定格式转换
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(String date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	public static String getYmd(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	public static String getYm(Date date) {
		return new SimpleDateFormat("yyyyMM").format(date);
	}

	/**
	 * 时间格式yyyyMMdd
	 * @param date
	 * @return
	 */
	public static Date getYmd(String date) {
		try {
			return new SimpleDateFormat("yyyyMMdd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取给定日期延后 多少天/月的日期
	 * 
	 * @param date  给定的日期
	 * @param amount  日/天
	 * @param type 1：按日 2：按月 3：按年
	 * @return
	 */
	public static String getYmdAfter(String date, int amount, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getYmd(date));
		if (type == 1) { // 按日
			cal.add(Calendar.DATE, amount);
		} else if (type == 2) { // 按月
			cal.add(Calendar.MONTH, amount);
		} else if (type == 3) {
			cal.add(Calendar.YEAR, amount);
		}
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}

	/**
	 * 日期和当前日期比较
	 * 
	 * @param date
	 * @return 0：日期相等 1：日期大于当前日期 2：日期小于当前日期
	 */
	public static Integer compareDate(Date date) {
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String srcDate = format.format(date);
		String currDate = format.format(new Date());
		Integer retDate = null;
		try {
			Date sDate = format.parse(srcDate);
			Date cDate = format.parse(currDate);
			if (sDate.equals(cDate)) {
				retDate = 0;
			} else if (sDate.after(cDate)) {
				retDate = 1;
			} else {
				retDate = 2;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retDate;
	}

	/**
	 * 获取data最后一天时间
	 * @param data
	 * @return yyyy-MM-dd
	 */
	public static String getLastDay(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return formatDateTime(c.getTime());
	}

	/**
	 * 设置时间
	 * 
	 * @param date 给定的日期
	 * @param amount  日/天/年
	 * @param type  Calendar.DAY_OF_MONTH
	 * @return
	 */
	public static Date setDate(Date date, int amount, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(type, amount);
		return cal.getTime();
	}

	/**
	 * 获取给定日期延后 多少天/月的日期
	 * 
	 * @param date 给定的日期
	 * @param amount  日/天
	 * @param type  0:按小时（24H） 1：按日 2：按月 3：按年
	 * @return
	 */
	public static Date getYmdAfter(Date date, int amount, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (type == 0) {
			cal.add(Calendar.HOUR_OF_DAY, amount);
		} else if (type == 1) { // 按日
			cal.add(Calendar.DAY_OF_MONTH, amount);
		} else if (type == 2) { // 按月
			cal.add(Calendar.MONTH, amount);
		} else if (type == 3) {
			cal.add(Calendar.YEAR, amount);
		}
		return cal.getTime();
	}

	/**
	 * java 时间转为echop时间
	 * @author qinlinhai 
	 * @param time
	 * @return
	 */
	public static Long dateToEcTime(Date time){
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(time);
		cal.add(Calendar.HOUR_OF_DAY, -8);
		return cal.getTimeInMillis()/1000;
	}
	/**
	 * 时间格式MMdd
	 * 
	 * @param date
	 * @return
	 */
	public static Date getAfterTime(Date date,int amount) {
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date);
		cal.add(Calendar.DATE, amount);
		return cal.getTime();
	}
	/**
	 * ecshop时间转java时间
	 * @author qinlinhai 
	 * @param time
	 * @return
	 */
	public static Date ectimeToDate(Long time){
		Long tempTime = Long.valueOf(time*1000);
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(new Date(tempTime));
		cal.add(Calendar.HOUR_OF_DAY, 8);
		return cal.getTime();
	}
	
	public static Date longToDate(Long timestamp){
		Date d = null;
		if(timestamp != null){
			d = new Date(timestamp);
		}
		return d;
	}
	
	public static void main(String[] args) {
		Date d = getDateTime("2016-07-07 09:16:23");
		long l = dateToEcTime(d);
		System.out.println(l);
	}
}
