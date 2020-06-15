/**
 * 
 */
package com.tf.base.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 日期操作 工具类
 *
 */
public class DateUtil {

	/**
     * 日期格式枚举
     *
     */
    public enum DateStyle {  
        
        MM_DD("MM-dd"),  
        YYYY_MM("yyyy-MM"),  
        YYYY_MM_DD("yyyy-MM-dd"),  
        MM_DD_HH_MM("MM-dd HH:mm"),  
        MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),  
        YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),  
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),  
          
        MM_DD_EN("MM/dd"),  
        YYYY_MM_EN("yyyy/MM"),  
        YYYY_MM_DD_EN("yyyy/MM/dd"),  
        MM_DD_HH_MM_EN("MM/dd HH:mm"),  
        MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),  
        YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),  
        YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),  
          
        MM_DD_CN("MM月dd日"),  
        YYYY_MM_CN("yyyy年MM月"),  
        YYYY_MM_DD_CN("yyyy年MM月dd日"),  
        MM_DD_HH_MM_CN("MM月dd日 HH:mm"),  
        MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss"),  
        YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"),  
        YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),  
          
        HH_MM("HH:mm"),  
        HH_MM_SS("HH:mm:ss");  
          
          
        private String value;  
          
        DateStyle(String value) {  
            this.value = value;  
        }  
          
        public String getValue() {  
            return value;  
        }  
    }  
    
    /**
     * 星期枚举
     *
     */
    public enum Week {  
    	  
        MONDAY("星期一", "Monday", "Mon.", 1),  
        TUESDAY("星期二", "Tuesday", "Tues.", 2),  
        WEDNESDAY("星期三", "Wednesday", "Wed.", 3),  
        THURSDAY("星期四", "Thursday", "Thur.", 4),  
        FRIDAY("星期五", "Friday", "Fri.", 5),  
        SATURDAY("星期六", "Saturday", "Sat.", 6),  
        SUNDAY("星期日", "Sunday", "Sun.", 7);  
          
        String name_cn;  
        String name_en;  
        String name_enShort;  
        int number;  
          
        Week(String name_cn, String name_en, String name_enShort, int number) {  
            this.name_cn = name_cn;  
            this.name_en = name_en;  
            this.name_enShort = name_enShort;  
            this.number = number;  
        }  
          
        public String getChineseName() {  
            return name_cn;  
        }  
      
        public String getName() {  
            return name_en;  
        }  
      
        public String getShortName() {  
            return name_enShort;  
        }  
      
        public int getNumber() {  
            return number;  
        }  
    } 
    
    
    //########2015-09-11 add by yangkun########
    static SimpleDateFormat sdfDay=new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdfTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 将字符串转换成日期类型 yyyy-MM-dd.
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static Date getDateFromString(String str) {
		Date date = null;

		try {
			if (StringUtils.isNotEmpty(str)) {
				date = sdfDay.parse(str);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将字符串转换成日期类型 yyyy-MM-dd HH:mm:ss.
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static Date getTimeFromString(String str) {
		Date date=null;
		try {
		 date=	sdfTime.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 获取系统当前时间  yyyy-MM-dd
	 * @return
	 */
	public static Date getOSNowDay() {
		Date date=null;
		try {
		 date=	sdfDay.parse(sdfDay.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/**
	 *  获取系统当前时间  yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	
	public static Date getOSNowTime() {
		Date date=null;
		try {
		 date=	sdfTime.parse(sdfTime.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 日期转字符串 yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String DateToString(Date date){
		String s="";
		try{
			s=sdfDay.format(date);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	/**
	 * 日期转字符串 yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String TimeToString(Date time){
		String s="";
		try{
		s= sdfTime.format(time);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 解析时间字符串，将16、17、18位的补齐为19位，即"yyyy-MM-dd HH:mm:ss"的格式.
	 * 
	 * @param dateStr
	 *            字符串
	 * @return Date对象
	 */
	public static Date parseDateStr(String dateStr) {
		if (dateStr == null) {
			return null;
		}

		// 最少是14位：2015-3-4 1:2:3
		dateStr = dateStr.trim();
		if (dateStr.length() < 14) {
			return null;
		}

		// 不是19位的，要处理一下
		if (dateStr.length() != 19) {

			// 补齐格式
			String regex = "^\\d{4}-(\\d{1,2})-(\\d{1,2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$";
			Matcher m = Pattern.compile(regex).matcher(dateStr);
			if (m.find()) {
				StringBuilder builder = new StringBuilder(dateStr);
				int posDiff = 0;
				for (int i = 1; i <= 5; i++) {
					String dd = m.group(i);
					if (dd.length() == 1) {
						builder.replace(m.start(i) + posDiff, m.end(i)
								+ posDiff, "0" + dd);
						posDiff += 1;
					}
				}
				dateStr = builder.toString();
			} else {
				return null;
			}
		}

		//补齐19位，格式化返回
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

	}
	/**
	 * 获取两个日期相差天数
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	public static long  getDateSub(String beginDateStr,String endDateStr){
		
		long day=0;
        java.util.Date beginDate;
        java.util.Date endDate;
        try
        {
            beginDate = sdfDay.parse(beginDateStr);
            endDate   = sdfDay.parse(endDateStr);    
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);    
        } catch (ParseException e)
        {
            e.printStackTrace();
        }   
        return day;
	}
	/**
	 * 获取新的日期
	 * @param dateOper
	 * @param type  是针对年月日时,哪个时间相加减
	 * @param num   加减数量
	 * @param format  返回日期格式化
	 * @return
	 */
	public static String getNewDate(Date dateOper,String type, int num,String format){
		
		Calendar Cal=Calendar.getInstance();    
		Cal.setTime(dateOper); 
		if("Y".equalsIgnoreCase(type)){
			Cal.add(Calendar.YEAR,num);  
		}else if("M".equalsIgnoreCase(type)){
			Cal.add(Calendar.MONTH,num);  
		}else if("D".equalsIgnoreCase(type)){
			Cal.add(Calendar.DAY_OF_YEAR,num);  
		}else if("H".equalsIgnoreCase(type)){
			Cal.add(Calendar.HOUR_OF_DAY,num);  
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			if(format!=null && !"".equals(format)){
				df = new SimpleDateFormat(format);
			}
		}catch(Exception e){
			
		}
		return df.format(Cal.getTime());
	}
	
	
	/**
	 * 获取当前时间上n的小时的时间 yyyy-MM-dd HH:mm:ss
	 * @param n
	 * @return
	 */
	public static String getLastHourTime(int n){
		Calendar calendar = Calendar.getInstance();
		//HOUR_OF_DAY 指示一天中的小时
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - n);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));
		//System.out.println("当前的时间：" + df.format(new Date()));
		return df.format(calendar.getTime());
	}
	/**
	 * 判断输入的字符串是否符合日期格式
	 * @param s
	 * @return
	 */
	public static Date getTimeFromStringBug(String s) {
		Date date = null;
		if (!StringUtil.isEmpty(s)) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
			try {
				date = sdf1.parse(s);
				return date;
			} catch (Exception e) {
			}
			try {
				date = sdf2.parse(s);
				return date;
			} catch (Exception e) {
			}
			try {
				date = sdf3.parse(s);
				return date;
			} catch (Exception e) {
			}
			try {
				date = sdf4.parse(s);
				return date;
			} catch (Exception e) {
			}
			try {
				date = sdf5.parse(s);
				return date;
			} catch (Exception e) {
			}
		}
		return date;
	}
	
	
    //########################################################
	public static void main(String [] args){
		String a= DateUtil.getDateAfterForDays("2016-11-30",1);
		System.out.println(a);
	}
	
	
	
	/**
	 * 得到形如：x天x小时x分钟x秒 格式的时长
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static String getTimeLength(String beginTime,String endTime){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int ss = 1000;
	    int mi = ss * 60;
	    int hh = mi * 60;
	    int dd = hh * 24;
	    try {
			Date beginTimeDate = sf.parse(beginTime);
			Date endTimeDate = sf.parse(endTime);
			long interval = endTimeDate.getTime()-beginTimeDate.getTime();
			long day = interval / dd;
		    long hour = (interval % dd) / hh;
		    long minute = ((interval % dd) % hh) / mi;
		    long second = (((interval % dd) % hh)% mi) / ss;
		    long milliSecond = (((interval % dd) % hh)% mi) % ss;
		    if(second<0||milliSecond<0){
		    	return "";
		    }
		    return "约 " + day + "天" + hour + "小时" + minute + "分钟" + second+  "秒";
		} catch (ParseException e) {
			return "";
		}
	}
	
	public static String getDateAfterForDays(String beginDateStr,int days) {
		Date begin = null;
		try {
			begin = sdfDay.parse(beginDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		calendar.add(Calendar.DATE, days);
		
		return sdfDay.format(calendar.getTime());
	}
	
	public static String getDateAfterForDays(Date beginDate,int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.add(Calendar.DATE, days);
		
		return sdfDay.format(calendar.getTime());
	}
	
	}
